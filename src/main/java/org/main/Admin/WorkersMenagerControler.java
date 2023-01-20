package org.main.Admin;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.main.Admin.GeneratorPDF.GenerateListOfHoursWorked;
import org.main.App;
import org.main.Entity.Temporaty.EmployeesOverworkedTime;
import org.main.Entity.Workers;
import org.main.Entity.WorkersRepositoryImpl;
import org.main.Utils.ConnectionCardReader;
import org.main.Utils.ValidadiotData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class WorkersMenagerControler extends ConnectionCardReader implements Initializable {
    @FXML
    public TableView<Workers> tableWorkers;
    @FXML
    public TableColumn<Workers, Integer> id;
    @FXML
    public TableColumn<Workers, String> firstName;
    @FXML
    public TableColumn<Workers, String> surName;
    @FXML
    public TableColumn<Workers, String> email;
    @FXML
    public TableColumn<Workers, String> position;
    @FXML
    public TextField serchField;
    @FXML
    private DatePicker workingDate;

    WorkersRepositoryImpl workerRepository = new WorkersRepositoryImpl();


    private void initializeColumn() {
        id.setCellValueFactory(new PropertyValueFactory<>("idWorker"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
    }

    ObservableList<Workers> workersObservableList;

    private void loadDateUser(List<Workers> workersList) {
        if (tableWorkers != null) {
            tableWorkers.getItems().clear();
        }

        if (!workersList.isEmpty()) {
            workersObservableList = FXCollections.observableArrayList();
            workersObservableList.removeAll(workersObservableList);
            workersObservableList.addAll(workersList);
            tableWorkers.getItems().addAll(workersObservableList);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Brak pracowników do wyświetlenia");
            alert.show();
        }
    }


    public void backToMainViewOfAdmin(ActionEvent actionEvent) throws IOException {

        App.setPrevRootScene();

    }


    public void addNewUserView() throws IOException {
        App.setNextRootScene("Admin/WorkerDetails");

    }


    public void getAllChecker() {
        loadDateUser(workerRepository.getWorkersByPosition("CHECKER"));

    }

    public void getAllStoremen() {
        loadDateUser(workerRepository.getWorkersByPosition("STOREMAN"));

    }

    public void getAllVulcaniser() {
        loadDateUser(workerRepository.getWorkersByPosition("VULCANISER"));

    }

    public void getAllWorker() {
        loadDateUser(workerRepository.getWorkers());
    }

    @FXML
    private void searchWorkers() {
        //file is not empty
        if (!serchField.getText().equals("")) {
            List<Workers> workersList = new ArrayList<>();
            Workers workers = null;
            if (ValidadiotData.validateTAG(serchField.getText().trim())) {
                workers = workerRepository.getWorkerByTag(serchField.getText());
            } else if (ValidadiotData.validdateEmail(serchField.getText().trim())) {
                workers = workerRepository.getWorkerByEmail(serchField.getText());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Można wyszukiwać po adresie email/numerze tagu");
            }
            if (workers != null) {
                workersList.add(workers);
                loadDateUser(workersList);
            }
        }
    }

    @FXML
    private void editOneUser() throws IOException {
        Workers workers = tableWorkers.getSelectionModel().getSelectedItem();
        if (workers != null) {
            WorkerDetailsControler.setEditWorker(workers);
            App.setNextRootScene("Admin/WorkerDetails");
        }
    }

    @FXML
    private void generateHoursWorked() throws FileNotFoundException {

        Date date = null;
        if (workingDate.getValue() != null) {
            date = Date.valueOf(workingDate.getValue());
        } else {
            date = Date.valueOf(LocalDate.now());
        }
        Workers workers = null;
        List<EmployeesOverworkedTime> overworkedTimeList;
        if (tableWorkers.getSelectionModel().getSelectedItem() != null) {
            workers = tableWorkers.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Wybierz jak wygerujesz raport ");
            alert.setContentText("Wybierz opcje ");

            ButtonType buttonTypeOne = new ButtonType("Dla dni w miesiącu");
            ButtonType buttonTypeTwo = new ButtonType("Dal całego misiąca");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                assert workers != null;
                overworkedTimeList = workerRepository.getEmployeesOverworkedTimeByDaysList(workers, date);
                if (overworkedTimeList != null) {
                    GenerateListOfHoursWorked.generatePDFSumMonthByDays(overworkedTimeList);
                } else {
                    showAlerLackOfData();
                }
            } else if (result.get() == buttonTypeTwo) {
                overworkedTimeList = workerRepository.getEmployeesOverworkedTimeList(workers, date);
                if (overworkedTimeList != null) {
                    GenerateListOfHoursWorked.generatePDFSumMonth(overworkedTimeList, "");
                } else {
                    showAlerLackOfData();
                }
            }
        } else {
            overworkedTimeList = workerRepository.getEmployeesOverworkedTimeList(workers, date);
            if (overworkedTimeList != null) {
                GenerateListOfHoursWorked.generatePDFSumMonth(overworkedTimeList, "");
            } else {
                showAlerLackOfData();
            }
        }

    }

    private void showAlerLackOfData() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Brak danych ");
        alert.setContentText("Wybierz inny miesiąc lub innego pracownika");
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumn();
        loadDateUser(workerRepository.getWorkers());
        try {
            initSerialPort(portName, 9600);
            listeningPort(serchField);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
