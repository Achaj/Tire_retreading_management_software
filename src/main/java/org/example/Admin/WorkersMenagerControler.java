package org.example.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import org.example.App;
import org.example.Entity.WorkersRepositoryImpl;
import org.example.Entity.Workers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WorkersMenagerControler implements Initializable {
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

    WorkersRepositoryImpl workerRepository=new WorkersRepositoryImpl();
    private void initializeColumn() {
        id.setCellValueFactory(new PropertyValueFactory<>("idWorker"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
    }

    ObservableList<Workers> workersObservableList;

    private void loadDateUser(List<Workers>  workersList) {
        if(tableWorkers !=null){
            tableWorkers.getItems().clear();
        }
        //List<Workers>  workersList=workerRepository.getWorkers();
        //List<Workers>  workersList=new ArrayList<>();
        if(!workersList.isEmpty()){
            workersObservableList=FXCollections.observableArrayList();
            workersObservableList.removeAll(workersObservableList);
            workersObservableList.addAll(workersList);
            tableWorkers.getItems().addAll(workersObservableList);
            System.out.println(workersObservableList);
        }else{
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
        loadDateUser(workerRepository.getWorkersByPosition("STOREMEN"));

    }
    public void getAllVulcaniser() {
        loadDateUser(workerRepository.getWorkersByPosition("VULCANISER"));

    }

    public void getAllWorker(ActionEvent actionEvent) {
        loadDateUser(workerRepository.getWorkers());
    }


    public void editOneUser() throws IOException {
        Workers workers=tableWorkers.getSelectionModel().getSelectedItem();
        if(workers!=null) {
            WorkerDetailsControler.setEditWorker(workers);
            App.setNextRootScene("Admin/WorkerDetails");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumn();
        loadDateUser(workerRepository.getWorkers());
    }
}
