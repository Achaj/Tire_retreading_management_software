package org.main.Admin;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.main.App;
import org.main.Entity.Departments;
import org.main.Entity.DepartmentsRepositoryImpl;
import org.main.Entity.Workers;
import org.main.Entity.WorkersRepositoryImpl;
import org.main.Utils.ConectionCardReader;
import org.main.Utils.PasswordHashing;
import org.main.Utils.ValidadiotData;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class WorkerDetailsControler implements Initializable {

    @FXML
    public TextField idTag;
    @FXML
    public TextField firstName;
    @FXML
    public TextField secondName;
    @FXML
    public TextField email;
    @FXML
    public TextField pass;
    @FXML
    public TextField confirmPass;
    @FXML
    public ChoiceBox<String> positionChoiceBox;
    @FXML
    public ChoiceBox<String> departmentChoiceBox;
    @FXML
    public DatePicker enploymentDate;
    @FXML
    public DatePicker stackingDate;

    WorkersRepositoryImpl workersRepository = new WorkersRepositoryImpl();

    private static Workers editWorker;

    public static void setEditWorker(Workers editWorker) {
        WorkerDetailsControler.editWorker = editWorker;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (editWorker != null) {
            loadWorkerDetails();
        }
        listinerTextField();
        loadDataChoiceBox();
        getDepartmens();
        try {
            listeningPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadWorkerDetails() {
        idTag.setText(editWorker.getTag());
        firstName.setText(editWorker.getFirstName());
        secondName.setText(editWorker.getLastName());
        email.setText(editWorker.getEmail());
        //pass.setText(editWorker.getPassword());
        //confirmPass.setText(editWorker.getPassword());
        positionChoiceBox.setValue(editWorker.getPosition());
        departmentChoiceBox.setValue(editWorker.getDepartments().getName());
        try {
            stackingDate.setValue(editWorker.getStackingDate().toLocalDate());
        } catch (NullPointerException e) {
            e.getMessage();
        }
        enploymentDate.setValue(editWorker.getEnploymentDate().toLocalDate());
    }

    public void backToPreviousScene() throws IOException {
        App.setPrevRootScene();
        if (editWorker != null) {
            editWorker = null;
        }
        clearFields();
    }

    private boolean correctFirstname = false;
    private boolean correctLastname = false;
    private boolean correctEmail = false;
    private boolean correctPassword = false;
    private boolean correctPosition = false;
    private boolean correctTag = false;
    private boolean correctDepartment = false;
    private boolean correctEnploymentDate = false;
    private boolean correctStackingtDate = false;


    public void addWorkerToDB() {
        if (correctFirstname && correctLastname &&
                correctEmail && correctPassword &&
                correctPosition && correctTag &&
                correctDepartment && correctEnploymentDate) {
            Workers worker = new Workers();
            worker.setIdWorker(0);
            worker.setFirstName(firstName.getText());
            worker.setLastName(secondName.getText());
            worker.setEmail(email.getText());
            worker.setPassword(PasswordHashing.doHashing(pass.getText()));
            worker.setTag(idTag.getText());
            worker.setPosition(positionChoiceBox.getValue());
            worker.setDepartments(seearchDepartment());
            worker.setEnploymentDate(Date.valueOf(enploymentDate.getValue()));
            if (stackingDate.getValue() != null) {
                editWorker.setStackingDate(Date.valueOf(stackingDate.getValue()));
            }
            Alert alert = new Alert(Alert.AlertType.NONE);
            if (workersRepository.saveWorker(worker)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Dane zostały zapisane");
                clearFields();

            } else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText("Taki adres emial jest już używany");
                alert.setContentText("Użyj innego adresu email");

            }
            alert.show();

        }

    }

    private Departments seearchDepartment() {
        Departments department = null;
        for (Departments departments : departmentsList) {
            if (departments.getName().equals(departmentChoiceBox.getValue())) {
                department = departments;
            }
        }
        return department;
    }

    public void editWorkerInDB() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        if (correctFirstname || correctLastname ||
                correctEmail || correctStackingtDate||
                correctPosition || correctTag ||
                correctDepartment || correctEnploymentDate
                || (correctPassword  && !editWorker.getPassword().equals(PasswordHashing.doHashing(pass.getText())))) {
            editWorker.setFirstName(firstName.getText());
            editWorker.setLastName(secondName.getText());
            editWorker.setEmail(email.getText());
            if (!editWorker.getPassword().equals(PasswordHashing.doHashing(pass.getText()))) {
                editWorker.setPassword(PasswordHashing.doHashing(pass.getText()));
            }
            editWorker.setTag(idTag.getText());
            editWorker.setPosition(positionChoiceBox.getValue());
            editWorker.setDepartments(seearchDepartment());
            editWorker.setEnploymentDate(Date.valueOf(enploymentDate.getValue()));
            if (stackingDate.getValue() != null) {
                editWorker.setStackingDate(Date.valueOf(stackingDate.getValue()));
            }
            if (workersRepository.changeDadataWorker(editWorker)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Dane zostały zapisane");
                alert.setContentText("OK");
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setHeaderText("Bład zapisu danych");
                alert.setContentText("Sprawdz dane");
            }
        } else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Dane nie zostały zmienione");
            alert.setContentText("Brak informacji do zmiany");
        }
        alert.show();
    }

    public void remoweWorkerInDB() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Czy na pewno chcesz usunąć  konto ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (workersRepository.delateWorker(editWorker.getIdWorker())) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Opracja została wykonana poprawnie");
                backToPreviousScene();
            } else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText("Opracja nie została wykonana poprwnie");
                alert.setContentText("Pracownik jest przyspisny do zadania usuń zadania");
                alert.show();
            }
        } else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Opracja została anulowana");

        }
        alert.show();

    }

    private void clearFields() {
        idTag.setText("");
        firstName.setText("");
        secondName.setText("");
        email.setText("");
        pass.setText("");
        confirmPass.setText("");
        positionChoiceBox.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
        departmentChoiceBox.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
        loadDataChoiceBox();
        getDepartmens();
        stackingDate.setValue(null);
        enploymentDate.setValue(null);

    }

    DepartmentsRepositoryImpl departmentsRepository = new DepartmentsRepositoryImpl();
    List<Departments> departmentsList;

    private void getDepartmens() {
        departmentsList = departmentsRepository.getDepartments();
        departmentChoiceBox.getItems().clear();
        for (Departments departments : departmentsList) {
            departmentChoiceBox.getItems().add(departments.getName());
        }
    }

    private void loadDataChoiceBox() {
        positionChoiceBox.getItems().clear();
        positionChoiceBox.getItems().add("ADMIN");
        positionChoiceBox.getItems().add("CHECKER");
        positionChoiceBox.getItems().add("STOREMAN");
        positionChoiceBox.getItems().add("VULCANISER");
    }

    public void listinerTextField() {
        // Listen for TextField text changes
        firstName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.valideteName(newValue)) {
                firstName.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                correctFirstname = true;

            } else {
                firstName.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                correctFirstname = false;
            }

        });
        secondName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.valideteName(newValue)) {
                secondName.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                correctLastname = true;

            } else {
                secondName.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                correctLastname = false;
            }

        });
        email.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validdateEmail(newValue)) {
                email.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                correctEmail = true;

            } else {
                email.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                correctEmail = false;
            }

        });
        pass.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validatePassword(newValue)) {
                pass.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");

            } else {
                pass.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");

            }

        });
        confirmPass.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validatePassword(newValue, pass.getText())) {
                confirmPass.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                correctPassword = true;

            } else {
                confirmPass.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                correctPassword = false;
            }

        });
        idTag.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateTAG(newValue)) {
                idTag.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                correctTag = true;

            } else {
                idTag.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                correctTag = false;
            }

        });
        positionChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    positionChoiceBox.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                    correctPosition = true;
                });
        departmentChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    departmentChoiceBox.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                    correctDepartment = true;
                });

        enploymentDate.valueProperty().addListener((ov, oldValue, newValue) -> {
            try {

                correctEnploymentDate = true;
                enploymentDate.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");


            } catch (DateTimeParseException dateTimeParseException) {
                correctEnploymentDate = false;
                enploymentDate.setStyle("-fx-background-color:  white;-fx-border-color:   red;-fx-border-width:   0px 0px 2px 2px;");
            }
        });
        stackingDate.valueProperty().addListener((ov, oldValue, newValue) -> {
            try {
                correctStackingtDate = true;
                stackingDate.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");

            } catch (DateTimeParseException exception) {
                correctStackingtDate = false;
                stackingDate.setStyle("-fx-background-color:  white;-fx-border-color:   red;-fx-border-width:   0px 0px 2px 2px;");
            }
        });
    }

    String idTagReaded = "";
    public void listeningPort() throws Exception {
        ConectionCardReader.initSerialPort(ConectionCardReader.portName, 9600);
        ConectionCardReader.serialPort.addDataListener(new SerialPortDataListener() {
                                                           @Override
                                                           public int getListeningEvents() {
                                                               return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
                                                           }

                                                           @Override
                                                           public void serialEvent(SerialPortEvent serialPortEvent) {
                                                               String databBuffer = "";
                                                               byte[] newData = serialPortEvent.getReceivedData();
                                                               for (int i = 0; i < newData.length; i++) {
                                                                   databBuffer += (char) newData[i];
                                                               }
                                                               if (!idTagReaded.equals(databBuffer)) {
                                                                   idTagReaded = databBuffer;
                                                                   idTag.setText(idTagReaded);
                                                               }
                                                               // System.out.println(idTagReaded);
                                                           }
                                                       }
        );

    }
}
