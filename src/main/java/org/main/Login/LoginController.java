package org.main.Login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.main.App;
import org.main.Entity.WorkersRepositoryImpl;
import org.main.Entity.Workers;
import org.main.Entity.WorkingTime;
import org.main.Entity.WorkingTimeRepositoryImpl;
import org.main.Utils.ConnectionCardReader;
import org.main.Utils.PasswordHashing;
import org.main.Utils.Temporary;
import org.main.Utils.ValidadiotData;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController extends ConnectionCardReader implements Initializable {
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public PasswordField passTag;

    WorkersRepositoryImpl workerRepository = new WorkersRepositoryImpl();
    WorkingTimeRepositoryImpl workingTimeRepository = new WorkingTimeRepositoryImpl();

    public void logInApplication() throws IOException {

        //  String user = username.getText();
        //  String pass = password.getText();

        Alert alert = new Alert(Alert.AlertType.NONE);
        if (ValidadiotData.validdateEmail(username.getText()) && ValidadiotData.validatePassword(password.getText())) {
            Workers worker = workerRepository.getWorkerByEmail(username.getText());

            if (worker != null) {
                if (PasswordHashing.doHashing(password.getText()).equals(worker.getPassword())) {
                        if (passTag.getText().trim().equals(worker.getTag())) {
                            Temporary.setWorkers(worker);
                           WorkingTime workingTime= new WorkingTime();
                           workingTime.setDateLogin(LocalDateTime.now());
                           workingTime.setWorkers(worker);
                           Temporary.workingTime=workingTime;
                            workingTimeRepository.save(Temporary.workingTime);
                            alert.setAlertType(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Welcome " + worker.getFirstName());
                            alert.setGraphic(new ImageView(this.getClass().getResource("/img/user24px.png").toString()));
                            if(worker.getPosition().equals("ADMIN")) {
                                App.setNextRootScene("Admin/AdminMainView");
                            }else{
                                App.setNextRootScene("User/UserMainView");
                            }

                        } else {
                            passTag.setStyle("-fx-background-color:  transparent;-fx-border-color:   red;-fx-border-width:   0px 0px 4px 0px;");
                            alert.setAlertType(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Zeskanowano nieprawidłową kartę");
                            alert.setContentText("Zeskanuj ponownie dobrą kartę");

                        }
                } else {
                    password.setStyle("-fx-background-color:  transparent;-fx-border-color:   red;-fx-border-width:   0px 0px 4px 0px;");
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Wpisano błędne hasło");
                    alert.setContentText("Wpisz poprawne hasło");
                }
            } else {

                username.setStyle("-fx-background-color:  transparent;-fx-border-color:   red;-fx-border-width:   0px 0px 4px 0px;");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Nie ma konta z podany adresem email");
            }
        } else {
            username.setStyle("-fx-background-color:  transparent;-fx-border-color:   red;-fx-border-width:   0px 0px 4px 0px;");
            password.setStyle("-fx-background-color:  transparent;-fx-border-color:   red;-fx-border-width:   0px 0px 4px 0px;");
            passTag.setStyle("-fx-background-color:  transparent;-fx-border-color:   red;-fx-border-width:   0px 0px 4px 0px;");
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setHeaderText("Nie uzupełiono pól lub wprowdzono zabronione znaki");
        }

        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       /*  Workers workers = new Workers();
        workers.setIdWorker(0);
        workers.setFirstName("jan");
        workers.setLastName("ss");
        workers.setPassword("ZAQ!2wsx");
        workers.setEmail("jan@op.pl");
        workers.setTag("17985235");
        workers.setStackingDate(Date.valueOf("2015-01-01"));
        workers.setEnploymentDate(Date.valueOf("2015-01-02"));
        WorkerRepository workerRepository = new WorkerRepository();

        workerRepository.saveWorker(workers);

*/
        username.setText("jan@op.pl");
        password.setText("ZAQ!2wsx");
        passTag.setText("17985235");
        //ConectionCardReader.dataTagUID="17985235";
        try {
            connectionCardReader();
            listinerFileds();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void listinerFileds() {
        // Listen for TextField text changes
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validatePassword(newValue)) {
                password.setStyle("-fx-background-color:  transparent;-fx-border-color:   green;-fx-border-width:   0px 0px 4px 0px;");

            } else {
                password.setStyle("-fx-background-color:  transparent;-fx-border-color:   #404040;-fx-border-width:   0px 0px 2px 0px;");
            }

        });
        // Listen for TextField text changes
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validdateEmail(newValue)) {
                username.setStyle("-fx-background-color:  transparent;-fx-border-color:   green;-fx-border-width:   0px 0px 4px 0px;");

            } else {
                username.setStyle("-fx-background-color:  transparent;-fx-border-color:   #404040;-fx-border-width:   0px 0px 2px 0px;");
            }

        });
        passTag.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateTAG(newValue)) {
                passTag.setStyle("-fx-background-color:  transparent;-fx-border-color:   green;-fx-border-width:   0px 0px 4px 0px;");


            } else {
                passTag.setStyle("-fx-background-color:  transparent;-fx-border-color:   #404040;-fx-border-width:   0px 0px 2px 0px;");

            }

        });


    }

    public void connectionCardReader() throws Exception {
        initSerialPort(portName, 9600);
        if (!serialPort.isOpen()) {
            List<String> choices = getPortNames();
            if (!choices.isEmpty()) {
                ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
                dialog.setTitle("Choice your Com Port");
                dialog.setHeaderText("Look, a Choice Port");
                dialog.setContentText("Choose your Port:");

                // Traditional way to get the response value.
                Optional<String> result = dialog.showAndWait();
                // The Java 8 way to get the response value (with lambda expression).
                result.ifPresent(letter -> System.out.println("Your choice: " + letter));
                portName = result.get().trim().toString();
                initSerialPort(portName, 9600);
                listeningPort(passTag);
            }else {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText("Nie wykryto czytnika !");
                alert.showAndWait();
                //passTag.setEditable(true);
                //connectionCardReader();
            }
        } else {
            listeningPort(passTag);
        }
    }


}
