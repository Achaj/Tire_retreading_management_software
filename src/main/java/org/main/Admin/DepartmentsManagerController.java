package org.main.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.main.Admin.GeneratorPDF.GenerateListOfHoursWorked;
import org.main.App;
import org.main.Entity.Departments;
import org.main.Entity.DepartmentsRepositoryImpl;
import org.main.Entity.Temporaty.EmployeesOverworkedTime;
import org.main.Entity.Workers;
import org.main.Entity.WorkersRepositoryImpl;
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
    @FXML private DatePicker workingDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumn();
        loadDateUser(departmentsRepository.getDepartments());
        listenerTable();
    }

    public void backToPreviousScene() throws IOException {
        App.setPrevRootScene();
    }

    private void listenerTable() {
        tableDepartments.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    try {
                        editDepartment();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
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
    WorkersRepositoryImpl workersRepository=new WorkersRepositoryImpl();
    @FXML
    private void generateHoursWorked() throws FileNotFoundException {

        Date date = null;
        if(workingDate.getValue()!=null){
            date=Date.valueOf(workingDate.getValue());
        }else {
            date=Date.valueOf(LocalDate.now());
        }
        List<EmployeesOverworkedTime> overworkedTimeList;
        Departments departments=null;
        String depatrmentsName="";
        if(tableDepartments.getSelectionModel().getSelectedItem()!=null) {
            departments = tableDepartments.getSelectionModel().getSelectedItem();
            depatrmentsName=departments.getName();
            overworkedTimeList=workersRepository.getEmployeesOverworkedTimeByDepatmentList(departments,date);
        }else {
            overworkedTimeList=workersRepository.getEmployeesOverworkedTimeList(null,date);
        }
        if(overworkedTimeList!=null){
            GenerateListOfHoursWorked.generatePDFSumMonth(overworkedTimeList,depatrmentsName);
        }else {
            showAlerLackOfData();
        }

    }

    private void showAlerLackOfData(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Brak danych ");
        alert.setContentText("Wybierz inny miesiąc lub inny wydzuał");
        alert.show();
    }
}

