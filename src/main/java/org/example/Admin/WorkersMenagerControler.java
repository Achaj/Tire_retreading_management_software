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
import org.example.Entity.WorkerRepositoryImpl;
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

    WorkerRepositoryImpl workerRepository=new WorkerRepositoryImpl();


    private void initializeColumn() {
        id.setCellValueFactory(new PropertyValueFactory<>("idWorker"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
    }

    ObservableList<Workers> workersObservableList;

    private void loadDateUser() {
        if(tableWorkers !=null){
            tableWorkers.getItems().clear();
        }
        List<Workers>  workersList=workerRepository.getWorkers();
        if(workersList !=null){
            workersObservableList=FXCollections.observableArrayList();
            workersObservableList.clear();
            workersObservableList.addAll(workersList);
            tableWorkers.getItems().addAll(workersObservableList);
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


    }


    public void getAllSealer() {

    }

    public void getAllStorekeeper() {

    }

    public void getAllWorker(ActionEvent actionEvent) {
        loadDateUser();
    }


    public void editOneUser() throws IOException {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumn();
        loadDateUser();
    }
}
