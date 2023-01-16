package org.main.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.main.Admin.GeneratorPDF.GenerateListOfHoursWorked;
import org.main.Admin.GeneratorPDF.GenerateStockLevel;
import org.main.Admin.GeneratorPDF.GenerateSumaryOfWork;
import org.main.App;
import org.main.Entity.*;
import org.main.Entity.Temporaty.EmployeesOverworkedTime;
import org.main.Entity.Temporaty.TireDepartmentTime;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GeneratingStatementsController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadComboBoxDepartment();
        loadRadioButton();
    }

    @FXML
    private ComboBox<Departments> employeeDepartmentComboBox;

    @FXML
    private CheckBox semifinishedCheckBox;

    @FXML
    private ComboBox<Departments> stockDepartmentComboBox;

    @FXML
    private CheckBox tireCheckBox;

    @FXML
    private DatePicker workingEmployeeDatePicker;

    @FXML
    private DatePicker worksDateDatePicker;
    @FXML
    private DatePicker worksEndDateDatePicker;

    @FXML
    private ComboBox<Departments> worksDepartmnetComboBox;

    @FXML
    private RadioButton oneDateRadioButton;
    @FXML
    private RadioButton twoDateRadioButton;

    @FXML
    private void backToPreviousScene() throws IOException {
        App.setPrevRootScene();
    }

    ToggleGroup group = new ToggleGroup();

    private void loadRadioButton() {

        oneDateRadioButton.setToggleGroup(group);
        twoDateRadioButton.setToggleGroup(group);
    }


    ObservableList<Departments> departmentsObservableList = FXCollections.observableArrayList();
    DepartmentsRepositoryImpl departmentsRepository = new DepartmentsRepositoryImpl();

    private void loadComboBoxDepartment() {
        List<Departments> departmentsList = new ArrayList<>();
        departmentsList = departmentsRepository.getDepartments();
        departmentsObservableList.addAll(departmentsList);
        employeeDepartmentComboBox.getItems().addAll(departmentsObservableList);
        employeeDepartmentComboBox.setCellFactory(new Callback<ListView<Departments>, ListCell<Departments>>() {
            @Override
            public ListCell<Departments> call(ListView<Departments> p) {
                final ListCell<Departments> cell = new ListCell<Departments>() {
                    @Override
                    protected void updateItem(Departments t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName() + " " + t.getHallName());
                        } else {
                            setText(null);
                        }
                    }

                };

                return cell;
            }
        });
        stockDepartmentComboBox.getItems().addAll(departmentsObservableList);
        stockDepartmentComboBox.setCellFactory(new Callback<ListView<Departments>, ListCell<Departments>>() {
            @Override
            public ListCell<Departments> call(ListView<Departments> p) {
                final ListCell<Departments> cell = new ListCell<Departments>() {
                    @Override
                    protected void updateItem(Departments t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName() + " " + t.getHallName());
                        } else {
                            setText(null);
                        }
                    }

                };

                return cell;
            }
        });
        worksDepartmnetComboBox.getItems().addAll(departmentsObservableList);
        worksDepartmnetComboBox.setCellFactory(new Callback<ListView<Departments>, ListCell<Departments>>() {
            @Override
            public ListCell<Departments> call(ListView<Departments> p) {
                final ListCell<Departments> cell = new ListCell<Departments>() {
                    @Override
                    protected void updateItem(Departments t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName() + " " + t.getHallName());
                        } else {
                            setText(null);
                        }
                    }

                };

                return cell;
            }
        });

    }


    WorkersRepositoryImpl workersRepository = new WorkersRepositoryImpl();

    @FXML
    void generatePDFHoursWorked() throws FileNotFoundException {

        Date date = null;
        if (workingEmployeeDatePicker.getValue() != null) {
            date = Date.valueOf(workingEmployeeDatePicker.getValue());
        } else {
            LocalDate localDate = LocalDate.now();
            date = Date.valueOf(localDate);
            workingEmployeeDatePicker.setValue(localDate);

        }

        List<EmployeesOverworkedTime> overworkedTimeList;
        Departments departments = null;
        departments = employeeDepartmentComboBox.getValue();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (departments != null) {
            overworkedTimeList = workersRepository.getEmployeesOverworkedTimeByDepatmentList(departments, date);
            if (overworkedTimeList != null) {
                GenerateListOfHoursWorked.generatePDFSumMonth(overworkedTimeList, departments.getName());
            } else {
                alert.setHeaderText("Brak danych ");
                alert.setContentText("Wybierz inny miesiąc lub inny wydział");
                alert.show();
            }
        } else {
            overworkedTimeList = workersRepository.getEmployeesOverworkedTimeList(null, date);
            if (overworkedTimeList != null) {
                GenerateListOfHoursWorked.generatePDFSumMonth(overworkedTimeList, "");
            } else {
                alert.setHeaderText("Brak danych ");
                alert.setContentText("Wybierz inny miesiąc");
                alert.show();
            }
        }
    }

    TiresRepositoryImpl tiresRepository = new TiresRepositoryImpl();
    SemiProductsRepositoryImpl semiProductsRepository = new SemiProductsRepositoryImpl();

    @FXML
    void generatePDFStockMagazine() throws FileNotFoundException {
        if (tireCheckBox.isSelected()) {
            Departments departments = stockDepartmentComboBox.getValue();
            List<TireDepartmentTime> tireDepartmentTimeList = null;
            if (departments != null) {
                tireDepartmentTimeList = tiresRepository.getTiresStockLevel(departments);
                if (tireDepartmentTimeList != null) {
                    GenerateStockLevel.generatePDFStocktTires(tireDepartmentTimeList, departments.getName());
                } else {
                    showAlerLackOfDataTires();
                }
            } else {
                tireDepartmentTimeList = tiresRepository.getTiresStockLevel(null);
                if (tireDepartmentTimeList != null) {
                    GenerateStockLevel.generatePDFStocktTires(tireDepartmentTimeList, "");
                } else {
                    showAlerLackOfDataTires();
                }
            }
            tireCheckBox.setStyle("-fx-background-color: #dedad9");

        } else {
            if (!semifinishedCheckBox.isSelected()) {
                tireCheckBox.setStyle("-fx-background-color: red");
            }
        }
        if (semifinishedCheckBox.isSelected()) {
            List<SemiProducts> semiProductsList = semiProductsRepository.getSemiProducts();
            if (semiProductsList != null) {
                GenerateStockLevel.generatePDFStocktSemiProduct(semiProductsList);
            } else {
                showAlerLackOfDataSP();
            }
            semifinishedCheckBox.setStyle("-fx-background-color: #dedad9");
        } else {
            if (!tireCheckBox.isSelected()) {
                semifinishedCheckBox.setStyle("-fx-background-color: red");
            }
        }
    }

    private void showAlerLackOfDataTires() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Brak danych ");
        alert.setContentText("Wybierz inny Wydział lub brak danacy");
        alert.show();
    }

    private void showAlerLackOfDataSP() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Brak danych ");
        alert.setContentText("Brak danych o prefabrykatach");
        alert.show();
    }

    WorksRepositoryImpl worksRepository = new WorksRepositoryImpl();

    @FXML
    void generatePDFTasksPerformed() throws FileNotFoundException {
        LocalDate startDate = worksDateDatePicker.getValue();
        LocalDate stopDate=null;
        Departments departments = worksDepartmnetComboBox.getValue();
        if (startDate != null) {
            List<Works> worksList = null;
            if (oneDateRadioButton.isSelected()) {
                stopDate = worksEndDateDatePicker.getValue();
                if (startDate != null) {
                    worksList = worksRepository.getListWorksByDepartmentANDEBetweenDates(departments, startDate, stopDate);
                }
            }
            if (twoDateRadioButton.isSelected()) {
                stopDate = startDate.minusDays(31);
                if (startDate != null) {
                    worksList = worksRepository.getListWorksByDepartmentANDEBetweenDates(departments,stopDate, startDate);
                }
            }
            GenerateSumaryOfWork.generatePDF(worksList,"");
        }



    }
}
