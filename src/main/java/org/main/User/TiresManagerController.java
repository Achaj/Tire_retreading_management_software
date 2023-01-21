package org.main.User;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.main.App;
import org.main.Entity.Tires;
import org.main.Entity.TiresRepositoryImpl;
import org.main.Utils.ConnectionCardReader;
import org.main.Utils.ValidadiotData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TiresManagerController extends ConnectionCardReader implements Initializable {
    @FXML
    public TextField searchField;
    @FXML
    public TableView<Tires> tableTires;
    @FXML
    public TableColumn<Tires, Integer> id;
    @FXML
    public TableColumn<Tires, Integer> height;
    @FXML
    public TableColumn<Tires, Integer> width;
    @FXML
    public TableColumn<Tires, Integer> diameter;
    @FXML
    public TableColumn<Tires, String> tag;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableColumn();
        loadTableData(tiresRepository.getTires());
        try {
            initSerialPort(portName, 9600);
            listeningPort(searchField);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backToPreviousScene() throws IOException {
        App.setPrevRootScene();
    }


    private void initializeTableColumn() {
        id.setCellValueFactory(new PropertyValueFactory<>("idTire"));
        height.setCellValueFactory(new PropertyValueFactory<>("height"));
        width.setCellValueFactory(new PropertyValueFactory<>("width"));
        diameter.setCellValueFactory(new PropertyValueFactory<>("diameter"));
        tag.setCellValueFactory(new PropertyValueFactory<>("tag"));

    }

    ObservableList<Tires> tiresObservableList;
    TiresRepositoryImpl tiresRepository = new TiresRepositoryImpl();

    private void loadTableData(List<Tires> tiresList) {
        if (tableTires != null) {
            tableTires.getItems().clear();
        }
        if (!tiresList.isEmpty()) {

            tiresObservableList = FXCollections.observableArrayList();
            tiresObservableList.clear();
            tiresObservableList.addAll(tiresList);
            tableTires.getItems().addAll(tiresObservableList);

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Brak Opon do wyświetlenia");
            alert.show();
        }
    }

    public void searchTires() {
        //file is not empty
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (!searchField.getText().equals("")) {
            List<Tires> tiresList = new ArrayList<>();
            Tires tire = null;
            if (ValidadiotData.validateTAG(searchField.getText().trim())) {
                tire = tiresRepository.getTireByTag(searchField.getText());
                if (tire != null) {
                    tiresList.add(tire);
                    loadTableData(tiresList);
                } else {
                    alert.setHeaderText("Nie znaleziono opony\n o takim numerze tag");
                    alert.show();
                }
            } else {
                alert.setHeaderText("Można wyszukiwać po numerze tagu");
                alert.show();
            }

        }

    }

    public void getTires() {
        loadTableData(tiresRepository.getTires());
    }


    public void crateTire() throws IOException {
        App.setNextRootScene("User/TiresDetails");
    }

    public void editTire() throws IOException {

        if (tableTires.getSelectionModel().getSelectedItem() != null) {
            TiresDetailsController.setTiresEdit(tableTires.getSelectionModel().getSelectedItem());
            App.setNextRootScene("User/TiresDetails");
        }
    }

}
