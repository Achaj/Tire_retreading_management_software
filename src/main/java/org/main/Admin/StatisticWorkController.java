package org.main.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.main.App;
import org.main.Entity.*;
import org.main.Entity.Temporaty.DailyStatusWork;
import org.main.Entity.Temporaty.WorkNameDate;
import org.main.Utils.ConnectionCardReader;
import org.main.Utils.ValidadiotData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class StatisticWorkController extends ConnectionCardReader implements Initializable {
    WorksRepositoryImpl worksRepository = new WorksRepositoryImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPieChart(worksRepository.countWorkStatus(null));
        loadAreaChart(worksRepository.countWorkname(null));
        loadBarChart(worksRepository.countDailyWorkStatus(null));
        loadComboBoxPosition();
        loadComboBoxDepartment();
        loadListenersComboBoxes();
        try {
            initSerialPort(portName, 9600);
            listeningPort(searchTextField);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private BarChart<String, Integer> nameWorkBarChart;

    @FXML
    private PieChart workStatusPieChart;

    @FXML
    private AreaChart<String, Integer> worksAreaChart;
    @FXML
    private TextField searchTextField;

    @FXML
    private ComboBox<String> positionComboBox;
    @FXML
    private ComboBox<Workers> workerComboBox;
    @FXML
    private ComboBox<Departments> departmentComboBox;


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

    private void loadBarChart(List<DailyStatusWork> dailyStatusWorks) {
        nameWorkBarChart.getData().clear();
        nameWorkBarChart.setTitle("Dzienny Wykres Pracy");
        XYChart.Series<String, Integer> series1 = new XYChart.Series();
        series1.setName("Przyjęcie na stan");
        XYChart.Series<String, Integer> series2 = new XYChart.Series();
        series2.setName("Wstępna kontrola");
        XYChart.Series<String, Integer> series3 = new XYChart.Series();
        series3.setName("Kontrola maszynowa");
        XYChart.Series<String, Integer> series4 = new XYChart.Series();
        series4.setName("Szorstkowanie krasu");
        XYChart.Series<String, Integer> series5 = new XYChart.Series();
        series5.setName("Szlifowanie opony");
        XYChart.Series<String, Integer> series6 = new XYChart.Series();
        series6.setName("Wypełnienie ubytków");
        XYChart.Series<String, Integer> series7 = new XYChart.Series();
        series7.setName("Nakładanie bieżnika");
        XYChart.Series<String, Integer> series8 = new XYChart.Series();
        series8.setName("Wulkanizacja opony");
        XYChart.Series<String, Integer> series9 = new XYChart.Series();
        series9.setName("Kontrola jakości wyrobu");
        XYChart.Series<String, Integer> series10 = new XYChart.Series();
        series10.setName("Magazynowanie");

        if (dailyStatusWorks != null) {
            for (DailyStatusWork dailyStatusWork : dailyStatusWorks) {
                if (dailyStatusWork.getName().equals("Przyjęcie na stan")) {

                    series1.getData().add(new XYChart.Data(dailyStatusWork.getStatus(), dailyStatusWork.getCount()));

                } else if (dailyStatusWork.getName().equals("Wstępna kontrola")) {

                    series2.getData().add(new XYChart.Data(dailyStatusWork.getStatus(), dailyStatusWork.getCount()));

                } else if (dailyStatusWork.getName().equals("Kontrola maszynowa")) {

                    series3.getData().add(new XYChart.Data(dailyStatusWork.getStatus(), dailyStatusWork.getCount()));

                } else if (dailyStatusWork.getName().equals("Szorstkowanie krasu")) {

                    series4.getData().add(new XYChart.Data(dailyStatusWork.getStatus(), dailyStatusWork.getCount()));

                } else if (dailyStatusWork.getName().equals("Szlifowanie opony")) {

                    series5.getData().add(new XYChart.Data(dailyStatusWork.getStatus(), dailyStatusWork.getCount()));

                } else if (dailyStatusWork.getName().equals("Wypełnienie ubytków")) {

                    series6.getData().add(new XYChart.Data(dailyStatusWork.getStatus(), dailyStatusWork.getCount()));

                } else if (dailyStatusWork.getName().equals("Nakładanie bieżnika")) {

                    series7.getData().add(new XYChart.Data(dailyStatusWork.getStatus(), dailyStatusWork.getCount()));

                } else if (dailyStatusWork.getName().equals("Wulkanizacja opony")) {

                    series8.getData().add(new XYChart.Data(dailyStatusWork.getStatus(), dailyStatusWork.getCount()));

                } else if (dailyStatusWork.getName().equals("Kontrola jakości wyrobu")) {

                    series9.getData().add(new XYChart.Data(dailyStatusWork.getStatus(), dailyStatusWork.getCount()));

                } else if (dailyStatusWork.getName().equals("Magazynowanie")) {

                    series10.getData().add(new XYChart.Data(dailyStatusWork.getStatus(), dailyStatusWork.getCount()));

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
                nameWorkBarChart.getData().add(series);
            }
        }
        //nameWorkBarChart.getData().addAll(series1, series2, series3, series4, series5, series6, series7, series8, series9, series10);
    }

    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    private void loadPieChart(Map<String, Integer> map) {
        pieChartData.clear();
        if (map != null) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }
            //Load data a Pie chart
            workStatusPieChart.setData(pieChartData);
            //Setting the title of the Pie chart
            workStatusPieChart.setTitle("Status Pracy");
            workStatusPieChart.setLabelLineLength(50);
            workStatusPieChart.setLabelsVisible(true);
            //setting the direction to arrange the data
            workStatusPieChart.setClockwise(true);
        }
    }

    private void loadAreaChart(List<WorkNameDate> workNameDateList) {
        /*
        worksAreaChart.setTitle("Wykres pracy z ostatnich 30 dni");
        XYChart.Series series = new XYChart.Series();
        series.setName("Prace");

        Map<String, XYChart.Series> seriesMap = new HashMap<>();
        seriesMap.put("Przyjęcie na stan", series);
        seriesMap.put("Wstępna kontrola", series);
        seriesMap.put("Kontrola maszynowa", series);
        seriesMap.put("Szorstkowanie krasu", series);
        seriesMap.put("Szlifowanie opony", series);
        seriesMap.put("Wypełnienie ubytków", series);
        seriesMap.put("Nakładanie bieżnika", series);
        seriesMap.put("Wulkanizacja opony", series);
        seriesMap.put("Kontrola jakości wyrobu", series);
        seriesMap.put("Magazynowanie", series);

        if (workNameDateList != null) {
            for (WorkNameDate workNameDate : workNameDateList) {
                XYChart.Series workSeries = seriesMap.get(workNameDate.getName());
                workSeries.getData().add(new XYChart.Data<>(workNameDate.getDate().toString(), workNameDate.getCount()));
            }
        }

        worksAreaChart.getData().add(series);
        */

        worksAreaChart.getData().clear();
        worksAreaChart.setTitle("Wykres pracy z ostatnich 31 dni");

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
        // worksAreaChart.getData().addAll(series1, series2, series3, series4, series5, series6, series7, series8, series9, series10);


    }

    @FXML
    private void showAllDepartmentStatistic() {
        loadPieChart(worksRepository.countWorkStatus(null));
        loadAreaChart(worksRepository.countWorkname(null));
        loadBarChart(worksRepository.countDailyWorkStatus(null));
    }

    @FXML
    private void showStatisticChoseDepartment(Departments departments) {
        loadPieChart(worksRepository.countWorkStatus(departments));
        loadAreaChart(worksRepository.countWorkname(departments));
        loadBarChart(worksRepository.countDailyWorkStatus(departments));
    }

    WorkersRepositoryImpl workersRepository = new WorkersRepositoryImpl();

    private void loadListenersComboBoxes() {
        departmentComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            showStatisticChoseDepartment(newValue);
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
                loadPieChart(worksRepository.countWorkStatusWorker(newValue));
                loadAreaChart(worksRepository.countWorkNameWorker(newValue));
                loadBarChart(worksRepository.countDailyWorkStatusWorker(newValue));
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
                loadPieChart(worksRepository.countWorkStatusWorker(workers));
                loadAreaChart(worksRepository.countWorkNameWorker(workers));
                loadBarChart(worksRepository.countDailyWorkStatusWorker(workers));
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


}
