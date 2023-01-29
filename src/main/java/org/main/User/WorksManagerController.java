package org.main.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.main.App;
import org.main.Entity.Works;
import org.main.Entity.WorksRepositoryImpl;
import org.main.Utils.Temporary;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class WorksManagerController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableColumn();
        loadTableData(worksRepository.getListWorks());
        loadComboBox();


        listenerTable();

    }

    private void listenerTable() {
        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    try {
                        editSelectWork();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

        });
        tableView.setRowFactory(tv -> new TableRow<Works>() {
            @Override
            public void updateItem(Works item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (isSelected()) {
                    setStyle(" -fx-text-fill: black;-fx-background-color: green;");
                } else if (item.getStatus().equals("Do Zrobienia")) {
                    setStyle("-fx-background-color: #fff7d9;");
                } else if (item.getStatus().equals("Zaczęto")) {
                    setStyle("-fx-background-color: #d9ffe6;");
                } else if (item.getStatus().equals("Odrzucono")) {
                    setStyle("-fx-background-color: #f09090;");
                } else if (item.getStatus().equals("Zakończono")) {
                    setStyle("-fx-background-color: #ededcc;");
                } else if (item.getStatus().equals("Anulowano")) {
                    setStyle("-fx-background-color: #b0acac;");
                } else {
                    setStyle("");
                }

            }
        });


    }
    @FXML
    public TableView<Works> tableView;
    @FXML
    public TableColumn<Works, Integer> idWork;
    @FXML
    public TableColumn<Works, String> name;
    @FXML
    public TableColumn<Works, String> status;
    @FXML
    public TableColumn<Works, LocalDateTime> dateStart;
    @FXML
    public TableColumn<Works, LocalDateTime> dateStop;
    @FXML
    private ComboBox<String> statusWorkComboBox;
    @FXML
    private TextField filterField;

    public void backToPreviousScene() throws IOException {
        App.setPrevRootScene();
        statusWorkComboBox.getItems().clear();
    }


    ObservableList<Works> worksObservableList=FXCollections.observableArrayList();
    WorksRepositoryImpl worksRepository = new WorksRepositoryImpl();

    public void showAllWorks() {
        loadTableData(worksRepository.getListWorks());
    }

    public void showMyWorks() {
        loadTableData(worksRepository.getListWorksByWorker(Temporary.getWorkers().getIdWorker()));
    }

    private void filtredData() {
        // Create a FilteredList wrapping the ObservableList of items in the table
        FilteredList<Works> filteredData = new FilteredList<>(worksObservableList, p -> true);

// Set the filter Predicate whenever the filter changes
        statusWorkComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(works -> {
                // If filter text is empty, display all works
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                  if (works.getStatus().contains(newValue)) {
                    return true; // Filter matches status
                }

                return false; // Does not match
            });
        });

// Set the filtered data as the table's items
        tableView.setItems(filteredData);

    }

    private void loadComboBox() {

        statusWorkComboBox.getItems().addAll(Temporary.getStatus());
    }

    private void loadTableData(List<Works> list) {
        if (list != null) {
            worksObservableList.clear();
            worksObservableList.addAll(list);
            tableView.setItems(worksObservableList);
            filtredData();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Brak zadań do wyświetlenia");
            alert.show();
        }
    }

    public void crateNewWork() throws IOException {
        App.setNextRootScene("User/WorksDetails");
    }

    public void editSelectWork() throws IOException {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            WorksDetailsController.setWorksEdit(tableView.getSelectionModel().getSelectedItem());
            App.setNextRootScene("User/WorksDetails");
        }
    }

    private void initializeTableColumn() {
        idWork.setCellValueFactory(new PropertyValueFactory<>("idWork"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        dateStop.setCellValueFactory(new PropertyValueFactory<>("dateStop"));

    }


}
