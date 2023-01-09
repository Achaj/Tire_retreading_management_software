package org.main.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import org.main.App;
import org.main.Entity.Temporaty.DailyStatusWork;
import org.main.Entity.Temporaty.WorkNameDate;
import org.main.Entity.WorksRepositoryImpl;
import org.main.Utils.Temporary;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class StatisticWorkController implements Initializable {
    WorksRepositoryImpl worksRepository = new WorksRepositoryImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPieChart(worksRepository.countWorkStatus(null));
        loadAreaChart(worksRepository.countWorkname(null));
    }

    @FXML
    private BarChart<String, Integer> nameWorkBarChart;

    @FXML
    private PieChart workStatusPieChart;

    @FXML
    private AreaChart<String, Integer> worksAreaChart;

    @FXML
    void backToPreviousScene() throws IOException {
        App.setPrevRootScene();
    }

    private void loadBarChart(List<DailyStatusWork> dailyStatusWorks) {
        //Defining the x axis
        CategoryAxis xAxis = new CategoryAxis();

        xAxis.setCategories(FXCollections.<String>observableArrayList(Temporary.getStatus()));
        xAxis.setLabel("Status");

        //Defining the y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("value");

        //Creating the Bar chart
        nameWorkBarChart.setTitle("Dzienny Wykres Pracy");

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
        worksAreaChart.setTitle("Wykres pracy z ostatnich 30 dni");

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
        worksAreaChart.getData().addAll(series1, series2, series3, series4, series5, series6, series7, series8, series9, series10);


    }
}
