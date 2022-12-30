package org.main.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.main.App;
import org.main.Entity.Tires;
import org.main.Entity.TiresRepositoryImpl;
import org.main.Entity.Works;
import org.main.Entity.WorksRepositoryImpl;
import org.main.Utils.ConectionCardReader;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class WorksManagerController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableColumn();
        loadTableData(worksRepository.getListWorks());
    }
    @FXML public TableView<Works> tableView;
    @FXML public TableColumn<Works,Integer> idWork;
    @FXML public TableColumn<Works,String> name;
    @FXML public TableColumn<Works,String> status;
    @FXML public TableColumn<Works, LocalDateTime> dateStart;
    @FXML public TableColumn<Works,LocalDateTime> dateStop;

    public void backToPreviousScene() throws IOException {
        App.setPrevRootScene();
    }

    ObservableList<Works> worksObservableList;
    WorksRepositoryImpl worksRepository=new WorksRepositoryImpl();

    private void loadTableData(List<Works> list) {
        if (tableView != null) {
            tableView.getItems().clear();
        }
        if (list!=null) {

            worksObservableList = FXCollections.observableArrayList();
            worksObservableList.clear();
            worksObservableList.addAll(list);
            tableView.getItems().addAll(worksObservableList);

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Brak zadań do wyświetlenia");
            alert.show();
        }
    }

    public void crateNewWork() throws IOException {
        App.setNextRootScene("User/WorksDetails");
    }
    private void initializeTableColumn() {
        idWork.setCellValueFactory(new PropertyValueFactory<>("idWork"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        dateStop.setCellValueFactory(new PropertyValueFactory<>("dateStop"));

    }


}
