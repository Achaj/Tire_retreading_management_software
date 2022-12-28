package org.main.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.main.App;
import org.main.Entity.Departments;
import org.main.Entity.DepartmentsRepositoryImpl;
import org.main.Utils.ValidadiotData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentsManagerController implements Initializable {

    @FXML
    public TextField searchField;
    @FXML
    public TableView<Departments> tableDepartments;
    @FXML
    public TableColumn<Departments, Integer> id;
    @FXML
    public TableColumn<Departments, String> name;
    @FXML
    public TableColumn<Departments, String> hallName;
    @FXML
    public TableColumn<Departments, String> city;
    @FXML
    public TableColumn<Departments, String> street;
    @FXML
    public TableColumn<Departments, String> flatNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumn();
        loadDateUser(departmentsRepository.getDepartments());
    }

    public void backToPreviousScene() throws IOException {
        App.setPrevRootScene();
    }

    private void initializeColumn() {
        id.setCellValueFactory(new PropertyValueFactory<>("idDepartment"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        hallName.setCellValueFactory(new PropertyValueFactory<>("hallName"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        street.setCellValueFactory(new PropertyValueFactory<>("street"));
        flatNumber.setCellValueFactory(new PropertyValueFactory<>("flatNumber"));
    }

    ObservableList<Departments> departmentsObservableList;
    DepartmentsRepositoryImpl departmentsRepository = new DepartmentsRepositoryImpl();

    private void loadDateUser(List<Departments> departmentsList) {
        if (tableDepartments != null) {
            tableDepartments.getItems().clear();
        }
        if (!departmentsList.isEmpty()) {
            departmentsObservableList = FXCollections.observableArrayList();
            departmentsObservableList.clear();
            departmentsObservableList.addAll(departmentsList);
            tableDepartments.getItems().addAll(departmentsObservableList);
            System.out.println(departmentsObservableList);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Brak działów do wyświetlenia");
            alert.show();
        }
    }
    public void createDepartment() throws IOException{
        App.setNextRootScene("Admin/DepartmentDetails");
    }
    public void editDepartment() throws IOException {
        Departments departments = tableDepartments.getSelectionModel().getSelectedItem();
        if (departments != null) {
            // set next scene
            DepartmentsDetailsController.setDepartments(departments);
            App.setNextRootScene("Admin/DepartmentDetails");
            //
        }
    }
    public void getAllDepartments(){
        loadDateUser(departmentsRepository.getDepartments());
    }
    public void searchDepartment(){
        if(ValidadiotData.validateStreet(searchField.getText())){
            List<Departments>  departmentsList=new ArrayList<>();
             Departments tmpDepartments=departmentsRepository.getDepartmentByName(searchField.getText());
             if(tmpDepartments !=null){
                departmentsList.add(tmpDepartments);
                loadDateUser(departmentsList);
            }
        }
    }
}

