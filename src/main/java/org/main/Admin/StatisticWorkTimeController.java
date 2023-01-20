package org.main.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.main.App;
import org.main.Entity.*;
import org.main.Entity.Temporaty.DaytimeWork;
import org.main.Entity.Temporaty.WorkNameDate;
import org.main.Utils.ConnectionCardReader;
import org.main.Utils.Temporary;
import org.main.Utils.ValidadiotData;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticWorkTimeController extends ConnectionCardReader implements Initializable {

    @FXML
    private AreaChart<String, Integer> worksAreaChart;
    @FXML
    private TextField searchTextField;
    @FXML
    private ComboBox<String> positionComboBox;
    @FXML
    private ComboBox<Workers> workerComboBox;
    @FXML
    private ComboBox<String> worksNameComboBox;
    @FXML
    private ComboBox<Departments> departmentComboBox;
    @FXML private LineChart<String,Float> workTimeLineChart;
    @FXML private BarChart<String,Integer> averageOverallTimeWithItsTimeBarChart;

    WorksRepositoryImpl worksRepository=new WorksRepositoryImpl();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadComboBoxPosition();
        loadComboBoxDepartment();
        loadListenersComboBoxes();
        loadComboBoxWorkName();
        loadAreaChart(worksRepository.countTimeWorkNameWorker(null));
        try {
            initSerialPort(portName, 9600);
            listeningPort(searchTextField);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void backToPreviousScene() throws IOException {
        App.setPrevRootScene();
    }
    ObservableList<Departments> departmentsObservableList = FXCollections.observableArrayList();
    DepartmentsRepositoryImpl departmentsRepository = new DepartmentsRepositoryImpl();

    private void loadComboBoxDepartment() {
        List<Departments> departmentsList = new ArrayList<>();
        departmentsList = departmentsRepository.getDepartments();
        departmentsObservableList.addAll(departmentsList);
        departmentComboBox.getItems().addAll(departmentsObservableList);
        departmentComboBox.setCellFactory(new Callback<ListView<Departments>, ListCell<Departments>>() {
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

    ObservableList<Workers> workersObservableList = FXCollections.observableArrayList();

    private void loadComboBoxWorkers(List<Workers> workersList) {
        if(workerComboBox.getItems()!=null) {
            workerComboBox.getItems().clear();
        }
        workersObservableList.addAll(workersList);
        workerComboBox.setItems(workersObservableList);
        workerComboBox.setCellFactory(new Callback<ListView<Workers>, ListCell<Workers>>() {
            @Override
            public ListCell<Workers> call(ListView<Workers> p) {
                final ListCell<Workers> cell = new ListCell<Workers>() {
                    @Override
                    protected void updateItem(Workers t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getFirstName() + " " + t.getLastName() + " " + t.getTag());
                        } else {
                            setText(null);
                        }
                    }

                };
                return cell;
            }
        });

    }

    private void loadComboBoxPosition() {
        positionComboBox.getItems().add("ADMIN");
        positionComboBox.getItems().add("CHECKER");
        positionComboBox.getItems().add("STOREMAN");
        positionComboBox.getItems().add("VULCANISER");
    }
    private void loadComboBoxWorkName() {
        worksNameComboBox.getItems().addAll(Temporary.getWorkName());
    }
    WorkersRepositoryImpl workersRepository = new WorkersRepositoryImpl();
    WorkingTimeRepositoryImpl workingTimeRepository=new WorkingTimeRepositoryImpl();
    private void loadListenersComboBoxes() {
        departmentComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            List<Workers> workersList = workersRepository.getWorkersByPosition(positionComboBox.getValue());
            if (workersList != null) {
                workersList.removeIf(workers -> workers.getDepartments().getIdDepartment() != newValue.getIdDepartment());
                loadComboBoxWorkers(workersList);
            }
        });
        positionComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            List<Workers> workersList = workersRepository.getWorkersByPosition(newValue);
            if (workersList != null && departmentComboBox.getValue() != null) {
                workersList.removeIf(workers -> workers.getDepartments().getIdDepartment() != departmentComboBox.getValue().getIdDepartment());
                loadComboBoxWorkers(workersList);
            }
            loadComboBoxWorkers(workersList);
        });
        workerComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue!=null) {
               loadLineChart(workingTimeRepository.getListOfEmployeeMinutesWorked(newValue.getIdWorker()),newValue);
               loadAreaChart(worksRepository.countTimeWorkNameWorker(newValue));
               loadBarChart(worksRepository.countTimeWorkNameWorker(newValue),worksRepository.countTimeWorkNameWorker(null));
            }

        });
        worksNameComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(workerComboBox.getValue()!=null) {
                loadBarChart(worksRepository.countTimeWorkNameWorker(workerComboBox.getValue()),worksRepository.countTimeWorkNameWorker(null));
            }

        });
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateTAG(newValue)) {
                searchTextField.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");


            } else {
                searchTextField.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");

            }
        });

    }


    @FXML
    private void searchWorkerTag() {
        Alert alert=new Alert(Alert.AlertType.NONE);
        if (ValidadiotData.validateTAG(searchTextField.getText())) {
            Workers workers = workersRepository.getWorkerByTag(searchTextField.getText().trim());
            if(workers!=null) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Zostają wyświetlone statystki dla:");
                alert.setContentText(workers.getFirstName() +" "+workers.getLastName());
                //Do something hear
                loadLineChart(workingTimeRepository.getListOfEmployeeMinutesWorked(workers.getIdWorker()),workers);
                loadAreaChart(worksRepository.countTimeWorkNameWorker(workers));
                loadBarChart(worksRepository.countTimeWorkNameWorker(workers),worksRepository.countTimeWorkNameWorker(null));

            }else{
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Nie udał znaleźć się takiego pracownika");
            }
        }else {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setHeaderText("Wpisano zabronione znaki ");
            alert.setContentText("Dozwolony znaki składa się z cyfr 0-9 \n od długości od 8-10");
            searchTextField.setStyle("-fx-background-color:  red;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
        }
        alert.show();
    }

    public static float minutesToHoursAndMinutesFloat(int minutes) {
        float hours = (float) Math.floor((float)minutes / 60);
        float remainderMinutes = ((float)minutes % 60)/100;
        DecimalFormat df = new DecimalFormat("#.##");
       // df.setDecimalSeparatorAlwaysShown(false);
        float val=hours +  remainderMinutes;
        return  val ;
    }
    private  void  loadLineChart(List<DaytimeWork> daytimeWorkList,Workers workers){
        workTimeLineChart.getData().clear();
        workTimeLineChart.setTitle("Przepracowane Godziny");
        XYChart.Series<String, Float> series1 = new XYChart.Series();
        series1.setName(workers.getFirstName() +" "+ workers.getLastName());
        if(daytimeWorkList!=null){
            for(DaytimeWork daytimeWork:daytimeWorkList){
                series1.getData().add(new XYChart.Data(daytimeWork.getDate().toString(),minutesToHoursAndMinutesFloat(daytimeWork.getMinutes())));
                System.out.println(daytimeWork.getMinutes()+"xx"+minutesToHoursAndMinutesFloat(daytimeWork.getMinutes()));
            }
            workTimeLineChart.getData().add(series1);
        }



    }
    private void loadAreaChart(List<WorkNameDate> workNameDateList) {

        worksAreaChart.getData().clear();
        worksAreaChart.setTitle("Wykreś średniego czasu pracy potrzebnog na wykonanie zadania  z ostatnich  31 dni");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Przyjęcie na stan");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Wstępna kontrola");
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Kontrola maszynowa");
        XYChart.Series series4 = new XYChart.Series();
        series4.setName("Szorstkowanie krasu");
        XYChart.Series series5 = new XYChart.Series();
        series5.setName("Szlifowanie opony");
        XYChart.Series series6 = new XYChart.Series();
        series6.setName("Wypełnienie ubytków");
        XYChart.Series series7 = new XYChart.Series();
        series7.setName("Nakładanie bieżnika");
        XYChart.Series series8 = new XYChart.Series();
        series8.setName("Wulkanizacja opony");
        XYChart.Series series9 = new XYChart.Series();
        series9.setName("Kontrola jakości wyrobu");
        XYChart.Series series10 = new XYChart.Series();
        series10.setName("Magazynowanie");
        if (workNameDateList != null) {
            for (WorkNameDate workNameDate : workNameDateList) {
                if (workNameDate.getName().equals("Przyjęcie na stan")) {
                    series1.getData().add(new XYChart.Data<>(workNameDate.getDate().toString(), workNameDate.getCount()));
                } else if (workNameDate.getName().equals("Wstępna kontrola")) {
                    series2.getData().add(new XYChart.Data<>(workNameDate.getDate().toString(), workNameDate.getCount()));
                } else if (workNameDate.getName().equals("Kontrola maszynowa")) {
                    series3.getData().add(new XYChart.Data<>(workNameDate.getDate().toString(), workNameDate.getCount()));
                } else if (workNameDate.getName().equals("Szorstkowanie krasu")) {
                    series4.getData().add(new XYChart.Data<>(workNameDate.getDate().toString(), workNameDate.getCount()));
                } else if (workNameDate.getName().equals("Szlifowanie opony")) {
                    series5.getData().add(new XYChart.Data<>(workNameDate.getDate().toString(), workNameDate.getCount()));
                } else if (workNameDate.getName().equals("Wypełnienie ubytków")) {
                    series6.getData().add(new XYChart.Data<>(workNameDate.getDate().toString(), workNameDate.getCount()));
                } else if (workNameDate.getName().equals("Nakładanie bieżnika")) {
                    series7.getData().add(new XYChart.Data<>(workNameDate.getDate().toString(), workNameDate.getCount()));
                } else if (workNameDate.getName().equals("Wulkanizacja opony")) {
                    series8.getData().add(new XYChart.Data<>(workNameDate.getDate().toString(), workNameDate.getCount()));
                } else if (workNameDate.getName().equals("Kontrola jakości wyrobu")) {
                    series9.getData().add(new XYChart.Data<>(workNameDate.getDate(), workNameDate.getCount()));
                } else if (workNameDate.getName().equals("Magazynowanie")) {
                    series10.getData().add(new XYChart.Data<>(workNameDate.getDate(), workNameDate.getCount()));
                }

            }
        }
        List<XYChart.Series<String, Integer>> seriesList = new ArrayList<>();
        seriesList.add(series1);
        seriesList.add(series2);
        seriesList.add(series3);
        seriesList.add(series4);
        seriesList.add(series5);
        seriesList.add(series6);
        seriesList.add(series7);
        seriesList.add(series8);
        seriesList.add(series9);
        seriesList.add(series10);
        for (XYChart.Series<String, Integer> series : seriesList) {
            if (!series.getData().isEmpty()) {
                worksAreaChart.getData().add(series);
            }
        }


    }
    private void loadBarChart(List<WorkNameDate> employeeAverageTimeList,List<WorkNameDate> overallAverageList) {
        if(worksNameComboBox.getValue()!=null) {
            averageOverallTimeWithItsTimeBarChart.getData().clear();
            averageOverallTimeWithItsTimeBarChart.setTitle("Zestawienie pracownika z zakładem");
            XYChart.Series<String, Integer> series1 = new XYChart.Series();
            series1.setName("Średni czas pracownika");
            XYChart.Series<String, Integer> series2 = new XYChart.Series();
            series2.setName("Ogólny średni czas");


            if (employeeAverageTimeList != null) {
                for (WorkNameDate employee : employeeAverageTimeList) {
                    if(employee.getName().equals(worksNameComboBox.getValue())) {
                        series1.getData().add(new XYChart.Data(employee.getDate().toString(), employee.getCount()));
                    }
                }
            }
            if (overallAverageList != null) {
                for (WorkNameDate overall : overallAverageList) {
                    if(overall.getName().equals(worksNameComboBox.getValue())) {
                        series2.getData().add(new XYChart.Data(overall.getDate().toString(), overall.getCount()));
                    }
                }
            }
            List<XYChart.Series<String, Integer>> seriesList = new ArrayList<>();
            seriesList.add(series1);
            seriesList.add(series2);

            for (XYChart.Series<String, Integer> series : seriesList) {
                if (!series.getData().isEmpty()) {
                    averageOverallTimeWithItsTimeBarChart.getData().add(series);
                }
            }
        }else {
            worksNameComboBox.requestFocus();
        }
        //nameWorkBarChart.getData().addAll(series1, series2, series3, series4, series5, series6, series7, series8, series9, series10);
    }

}
