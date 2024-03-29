package org.main.User;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.main.Entity.*;
import org.main.Utils.ConnectionCardReader;
import org.main.Utils.ValidadiotData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddSemiProductToWorkController extends ConnectionCardReader implements Initializable {
    @FXML
    private TableColumn<SemiProducts, Integer> amount;
    @FXML
    private TableColumn<SemiProducts, Integer> amountAdd;
    @FXML
    private TextField amountTextField;
    @FXML
    private TableColumn<SemiProducts, String> category;
    @FXML
    private TableColumn<SemiProducts, String> categoryAdd;
    @FXML
    private TableColumn<SemiProducts, Integer> idSemiProduct;
    @FXML
    private TableColumn<SemiProducts, Integer> idSemiProductAdd;
    @FXML
    private TableColumn<SemiProducts, String> name;
    @FXML
    private TableColumn<SemiProducts, String> nameAdd;
    @FXML
    private TableView<SemiProducts> tableView;
    @FXML
    private TableView<SemiProducts> tableViewAddWork;
    @FXML
    private TableColumn<SemiProducts, String> tag;
    @FXML
    private TableColumn<SemiProducts, String> tagAdd;
    @FXML
    private TextField tagTextField;
    @FXML
    private Button buttonSaveWorkSemiProducts;

    public static Works works;
    static WorksRepositoryImpl worksRepository = new WorksRepositoryImpl();


    public static Works worksEdit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        initializeTableColumn();
        loadTableData(semiProductsRepository.getSemiProducts());
        if (worksEdit != null) {
            List<WorkSemiProducts> workSemiProductsList = workSemiProductsRepository.getSemiProductsByWork(worksEdit.getIdWork());
            if (workSemiProductsList != null) {
                for (WorkSemiProducts workSemiProducts : workSemiProductsList) {
                    SemiProducts copySemiProducts = new SemiProducts();
                    copySemiProducts.copy(workSemiProducts.getSemiProducts());
                    copySemiProducts.setAmount(workSemiProducts.getAmount());
                    semiProductsAddWorkList.add(copySemiProducts);
                }

                loadTableAddProductWorkData(semiProductsAddWorkList);
            }
        }
        try {
            initSerialPort(portName, 9600);
            listeningPort(tagTextField);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initializeTableColumn() {
        idSemiProduct.setCellValueFactory(new PropertyValueFactory<>("idSemiProduct"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        tag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        idSemiProductAdd.setCellValueFactory(new PropertyValueFactory<>("idSemiProduct"));
        nameAdd.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryAdd.setCellValueFactory(new PropertyValueFactory<>("category"));
        tagAdd.setCellValueFactory(new PropertyValueFactory<>("tag"));
        amountAdd.setCellValueFactory(new PropertyValueFactory<>("amount"));

    }

    ObservableList<SemiProducts> semiProductsObservableList;
    SemiProductsRepositoryImpl semiProductsRepository = new SemiProductsRepositoryImpl();
    List<SemiProducts> semiProductsList = semiProductsRepository.getSemiProducts();

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

    ObservableList<SemiProducts> semiProductsAddWorkObservableList;
    List<SemiProducts> semiProductsAddWorkList = new ArrayList<>();

    private void loadTableAddProductWorkData(List<SemiProducts> list) {
        if (tableViewAddWork != null) {
            tableViewAddWork.getItems().clear();
        }
        if (list != null) {

            semiProductsAddWorkObservableList = FXCollections.observableArrayList();
            semiProductsAddWorkObservableList.clear();
            semiProductsAddWorkObservableList.addAll(list);
            tableViewAddWork.getItems().addAll(semiProductsAddWorkObservableList);

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Brak danych do wyświetlenia");
            alert.show();
        }
    }


    public void addSemiProToWork() {
        SemiProducts semiProducts = tableView.getSelectionModel().getSelectedItem();

        if (semiProducts != null) {
            if (ValidadiotData.validateDecimalNumber(amountTextField.getText()) &&
                    Integer.valueOf(amountTextField.getText()) <= tableView.getSelectionModel().getSelectedItem().getAmount() && Integer.valueOf(amountTextField.getText()) > 0) {
                amountTextField.setStyle("-fx-border-color: green");
                int tmpAmount = semiProducts.getAmount() - Integer.valueOf(amountTextField.getText());
                SemiProducts tempProducts = new SemiProducts();
                tempProducts.copy(semiProducts);
                //if amount value semi product is more then 0
                if (tmpAmount >= 0) {
                    semiProductsList.get(semiProductsList.indexOf(semiProducts)).setAmount(tmpAmount);
                    int index = findDuplicateinList(semiProductsAddWorkList, tempProducts);
                    tempProducts.setAmount(Integer.valueOf(amountTextField.getText()));
                    if (findDuplicateinList(semiProductsAddWorkList, tempProducts) >= 0) {
                        semiProductsAddWorkList.get(index).setAmount(semiProductsAddWorkList.get(index).getAmount() + Integer.valueOf(amountTextField.getText()));
                    } else {
                        semiProductsList.get(semiProductsList.indexOf(semiProducts)).setAmount(tmpAmount);
                        semiProductsAddWorkList.add(tempProducts);
                    }
                }

            } else {
                amountTextField.setStyle("-fx-border-color: red");
            }
        }

        loadTableAddProductWorkData(semiProductsAddWorkList);

        loadTableData(semiProductsList);

    }

    //return  >0 when id is the seme
    //return -1 when not found
    private int findDuplicateinList(List<SemiProducts> semiProductsList, SemiProducts semiProducts) {
        int result = -1;
        for (SemiProducts products : semiProductsList) {
            if (semiProducts.getIdSemiProduct() == products.getIdSemiProduct()) {
                result = semiProductsList.indexOf(products);
                break;
            }
        }
        return result;
    }

    private WorkSemiProducts findDuplicate(SemiProducts semiProducts) {
        WorkSemiProducts result = null;
        if (worksEdit != null) {
            for (WorkSemiProducts products : worksEdit.getWorkSemiProducts()) {
                if (semiProducts.getIdSemiProduct() == products.getSemiProducts().getIdSemiProduct()) {
                    result = products;
                    break;
                }
            }
        }
        return result;
    }


    public void removeSemiProToWork() {
        Logger logger = Logger.getLogger(getClass().getName());
        SemiProducts selectedItemWork = tableViewAddWork.getSelectionModel().getSelectedItem();
        if (selectedItemWork != null) {
            if (works != null) {
                semiProductsAddWorkList.remove(selectedItemWork);
                int index = findDuplicateinList(semiProductsList, selectedItemWork);
                if (index >= 0) {
                    semiProductsList.get(index).setAmount(semiProductsList.get(index).getAmount() + selectedItemWork.getAmount());

                }
            }
            if (worksEdit != null) {

                int index = findDuplicateinList(semiProductsList, selectedItemWork);
                if (index >= 0) {
                    semiProductsList.get(index).setAmount(semiProductsList.get(index).getAmount() + selectedItemWork.getAmount());
                    semiProductsAddWorkList.get(semiProductsAddWorkList.indexOf(selectedItemWork)).setAmount(0);
                }

            }
        }


        loadTableData(semiProductsList);
        loadTableAddProductWorkData(semiProductsAddWorkList);
    }


    WorkSemiProductsRepositoryImpl workSemiProductsRepository = new WorkSemiProductsRepositoryImpl();


    public void save() throws IOException {
        if (!semiProductsAddWorkList.isEmpty()) {
            Boolean saveSuccessful = false;
            Logger logger = Logger.getLogger(getClass().getName());
            if (works != null) {
                for (SemiProducts products : semiProductsAddWorkList) {
                    WorkSemiProducts workSemiProducts = new WorkSemiProducts();
                    workSemiProducts.setIdWorkSemiProduct(0);
                    workSemiProducts.setSemiProducts(products);
                    workSemiProducts.setWorks(works);
                    workSemiProducts.setAmount(products.getAmount());
                    System.out.print(workSemiProducts);
                    if (workSemiProductsRepository.save(workSemiProducts)) {

                        saveSuccessful = true;
                    } else {

                        saveSuccessful = false;
                        break;
                    }

                }
                // worksRepository.change(works);
            }
            if (worksEdit != null) {
                List<WorkSemiProducts> workSemiProductsList = workSemiProductsToChange(semiProductsAddWorkList);
                if (workSemiProductsList != null) {
                    for (WorkSemiProducts products : workSemiProductsList) {
                        if (products.getIdWorkSemiProduct() > 0) {
                            if (workSemiProductsRepository.change(products)) {
                                logger.log(Level.SEVERE, "Suceesful change Semiprodut on work.");
                                saveSuccessful = true;
                            } else {
                                logger.log(Level.SEVERE, "Faild change Semiprodut on work.");
                                saveSuccessful = false;
                                break;
                            }
                        } else {
                            if (workSemiProductsRepository.save(products)) {
                                logger.log(Level.SEVERE, "Suceesful save Semiprodut on work.");
                                saveSuccessful = true;
                            } else {
                                logger.log(Level.SEVERE, "Faild save Semiprodut on work.");
                                saveSuccessful = false;
                                break;
                            }
                        }

                    }
                }
                //worksRepository.removeNative(worksEdit);
                // worksRepository.change(worksEdit);
                //
            }

            if (saveSuccessful) {
                ConnectionCardReader.closePort();

                works = null;
                worksEdit = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("WorksDetails.fxml"));
                Parent root = (Parent) loader.load();
                WorksDetailsController controller = (WorksDetailsController) loader.getController();

                Stage stage = (Stage) buttonSaveWorkSemiProducts.getScene().getWindow(); // get the current stage
                stage.close(); // close the stage
                controller.backToPreviousScene();

            }
        }

    }

    private List<WorkSemiProducts> workSemiProductsToChange(List<SemiProducts> productsList) {
        List<WorkSemiProducts> workSemiProductsList = new ArrayList<>();
        WorkSemiProducts workSemiProducts = new WorkSemiProducts();

        if (productsList != null) {
            for (SemiProducts products : productsList) {
                WorkSemiProducts duplicate = findDuplicate(products);
                if (duplicate == null) {
                    workSemiProducts.setIdWorkSemiProduct(0);
                    workSemiProducts.setSemiProducts(products);
                    workSemiProducts.setWorks(worksEdit);
                    workSemiProducts.setAmount(products.getAmount());
                    workSemiProductsList.add(workSemiProducts);
                } else {
                    int value = worksEdit.getWorkSemiProducts().get(worksEdit.getWorkSemiProducts().indexOf(duplicate)).getAmount();
                    if (value != products.getAmount()) {
                        duplicate.setAmount(products.getAmount());
                        workSemiProductsList.add(duplicate);
                    }
                }
            }
        }
        return workSemiProductsList.isEmpty() ? null : workSemiProductsList;
    }

    public void searchSemiPro() {
        if (ValidadiotData.validateTAG(tagTextField.getText())) {
            SemiProducts searchProduct = null;
            for (SemiProducts products : semiProductsList) {
                if (products.getTag().equals(tagTextField.getText())) {
                    searchProduct = products;
                    break;
                }
            }
            if (searchProduct != null) {
                tableView.getSelectionModel().select(searchProduct);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Nie udało się znaleźć");
                alert.show();
            }
        }
    }


}

