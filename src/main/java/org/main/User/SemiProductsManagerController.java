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
import org.main.Entity.SemiProducts;
import org.main.Entity.SemiProductsRepositoryImpl;
import org.main.Entity.WorksRepositoryImpl;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SemiProductsManagerController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableColumn();
        loadTableData(semiProductsRepository.getSemiProducts());
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


    public void backToPreviousScene() throws IOException {
        App.setPrevRootScene();
        //ConectionCardReader.closePort();
    }

    ObservableList<SemiProducts> semiProductsObservableList;
     SemiProductsRepositoryImpl semiProductsRepository= new SemiProductsRepositoryImpl();

    private void loadTableData(List<SemiProducts> list) {
        if (tableView != null) {
            tableView.getItems().clear();
        }
        if (list!=null) {

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
}