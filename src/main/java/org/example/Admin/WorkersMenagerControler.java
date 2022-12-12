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
import org.example.Entity.Workers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WorkersMenagerControler implements Initializable {
    @FXML
    public TableView<Workers> tableUsers;
    @FXML
    public TableColumn<Workers, Integer> id;
    @FXML
    public TableColumn<Workers, String> firstName;
    @FXML
    public TableColumn<Workers, String> surname;
    @FXML
    public TableColumn<Workers, String> email;
    @FXML
    public TableColumn<Workers, String> rola;


    private void initializeColumn() {
        id.setCellValueFactory(new PropertyValueFactory<>("idPracownika"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surname.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        rola.setCellValueFactory(new PropertyValueFactory<>("rola"));
    }

    //ObservableList<Pracownik> pracownikObservableList;

    private void loadDateUser() {
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

    }
}
