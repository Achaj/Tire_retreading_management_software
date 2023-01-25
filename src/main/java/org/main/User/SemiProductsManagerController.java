package org.main.User;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.main.App;
import org.main.Entity.SemiProducts;
import org.main.Entity.SemiProductsRepositoryImpl;
import org.main.Utils.ConnectionCardReader;
import org.main.Utils.ValidadiotData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SemiProductsManagerController extends ConnectionCardReader implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableColumn();
        loadTableData(semiProductsRepository.getSemiProducts());
        try {
            initSerialPort(portName, 9600);
            listeningPort(searchField);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listenerTable();
    }

    private void listenerTable() {
        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    try {
                        editSemiProduct();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
    }

    @FXML
    public TableView<SemiProducts> tableView;
    @FXML
    public TableColumn<SemiProducts, Integer> idSemiProduct;
    @FXML
    public TableColumn<SemiProducts, String> name;

    @FXML
    public TableColumn<SemiProducts, String> category;
    @FXML
    public TableColumn<SemiProducts, String> tag;
    @FXML
    public TableColumn<SemiProducts, Integer> amount;
    @FXML
    public TextField searchField;

    public void backToPreviousScene() throws IOException {
        App.setPrevRootScene();

    }

    ObservableList<SemiProducts> semiProductsObservableList;
    SemiProductsRepositoryImpl semiProductsRepository = new SemiProductsRepositoryImpl();

    private void loadTableData(List<SemiProducts> list) {
        if (tableView != null) {
            tableView.getItems().clear();
        }
        if (list != null) {

            semiProductsObservableList = FXCollections.observableArrayList();
            semiProductsObservableList.clear();
            semiProductsObservableList.addAll(list);
            tableView.getItems().addAll(semiProductsObservableList);

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Brak danych do wyświetlenia");
            alert.show();
        }
    }

    private void initializeTableColumn() {
        idSemiProduct.setCellValueFactory(new PropertyValueFactory<>("idSemiProduct"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        tag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

    }

    public void createSemiProduct() throws IOException {
        App.setNextRootScene("User/SemiProductsDetails");
    }

    public void editSemiProduct() throws IOException {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            SemiProductsDetailsController.setSemiProductsEdit(tableView.getSelectionModel().getSelectedItem());
            App.setNextRootScene("User/SemiProductsDetails");
        }
    }
    public void search(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (!searchField.getText().equals("")) {
            List<SemiProducts> semiProductsList = new ArrayList<>();
            SemiProducts semiProducts= null;
            if (ValidadiotData.validateTAG(searchField.getText().trim())) {
                semiProducts =semiProductsRepository.getSemiProductsTag(searchField.getText().trim());
                if (semiProducts != null) {
                    semiProductsList.add(semiProducts);
                    loadTableData(semiProductsList);
                } else {
                    alert.setHeaderText("Nie znaleziono produktu\n o takim numerze tag");
                    alert.show();
                }
            } else {
                alert.setHeaderText("Można wyszukiwać po numerze tagu");
                alert.show();
            }

        }
    }


}