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
import org.main.Utils.ConectionCardReader;
import org.main.Utils.ValidadiotData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddSemiProductToWorkController implements Initializable {
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

    private static Works works = null;

    public static void setWorks(Works works) {
        AddSemiProductToWorkController.works = works;
    }

    private static Works worksEdit = null;

    public static void setWorksEdit(Works worksEdit) {
        AddSemiProductToWorkController.worksEdit = worksEdit;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeTableColumn();
        loadTableData(semiProductsRepository.getSemiProducts());
        if (worksEdit != null && worksEdit.getWorkSemiProducts() != null) {

            for (WorkSemiProducts workSemiProducts : worksEdit.getWorkSemiProducts()) {
                SemiProducts copySemiProducts = new SemiProducts();
                copySemiProducts.copy(workSemiProducts.getSemiProducts());
                copySemiProducts.setAmount(workSemiProducts.getAmount());
                semiProductsAddWorkList.add(copySemiProducts);
            }
            loadTableAddProductWorkData(semiProductsAddWorkList);
        }
        try {
            listeningPort();
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
                    Integer.valueOf(amountTextField.getText()) <= tableView.getSelectionModel().getSelectedItem().getAmount()) {
                amountTextField.setStyle("-fx-border-color: green");
                int tmpAmount = semiProducts.getAmount() - Integer.valueOf(amountTextField.getText());
                SemiProducts tempProducts = new SemiProducts();
                tempProducts.copy(semiProducts);
                //if amount value semi product is more then 0
                if (tmpAmount >= 0) {
                    semiProductsList.get(semiProductsList.indexOf(semiProducts)).setAmount(tmpAmount);
                    int index = findDuplicateinList(semiProductsAddWorkList, tempProducts);
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
    WorksRepositoryImpl worksRepository = new WorksRepositoryImpl();

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
                        logger.log(Level.SEVERE, "Suceesful save Semiprodut on work." + products.toString());
                        saveSuccessful = true;
                    } else {
                        logger.log(Level.SEVERE, "Faild save Semiprodut on work." + products.toString());
                        saveSuccessful = false;
                        break;
                    }

                }
            }
            if (worksEdit != null) {
                List<WorkSemiProducts> workSemiProductsList = workSemiProductsToChange(semiProductsAddWorkList);
                if (workSemiProductsList != null) {
                    for (WorkSemiProducts products : workSemiProductsList) {
                        if (products.getIdWorkSemiProduct() > 0) {
                            if (products.getAmount() > 0) {
                                if (workSemiProductsRepository.change(products)) {
                                    logger.log(Level.SEVERE, "Suceesful change Semiprodut on work." + products.toString());
                                    saveSuccessful = true;
                                } else {
                                    logger.log(Level.SEVERE, "Faild change Semiprodut on work." + products.toString());
                                    saveSuccessful = false;
                                    break;
                                }
                            }else {
                                if (workSemiProductsRepository.remove(products.getIdWorkSemiProduct())) {
                                    logger.log(Level.SEVERE, "Suceesful removed Semiprodut on work." + products.toString());
                                    saveSuccessful = true;
                                } else {
                                    logger.log(Level.SEVERE, "Faild removed Semiprodut on work." + products.toString());
                                    saveSuccessful = false;
                                    break;
                                }
                            }
                        } else {
                            if (workSemiProductsRepository.save(products)) {
                                logger.log(Level.SEVERE, "Suceesful save Semiprodut on work." + products.toString());
                                saveSuccessful = true;
                            } else {
                                logger.log(Level.SEVERE, "Faild save Semiprodut on work." + products.toString());
                                saveSuccessful = false;
                                break;
                            }
                        }

                    }
                }
            }

            if (saveSuccessful) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("WorksDetails.fxml"));
                Parent root = (Parent) loader.load();
                WorksDetailsController controller = (WorksDetailsController) loader.getController();
                Stage stage = (Stage) buttonSaveWorkSemiProducts.getScene().getWindow(); // get the current stage
                stage.close(); // close the stage
                controller.backToPreviousScene();
                works = null;
                worksEdit = null;
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

    String idTagReaded = "";

    public void listeningPort() throws Exception {
        ConectionCardReader.initSerialPort(ConectionCardReader.portName, 9600);
        ConectionCardReader.serialPort.
                addDataListener(
                        new SerialPortDataListener() {
                            @Override
                            public int getListeningEvents() {
                                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
                            }

                            @Override
                            public void serialEvent(SerialPortEvent serialPortEvent) {
                                String databBuffer = "";
                                byte[] newData = serialPortEvent.getReceivedData();
                                for (int i = 0; i < newData.length; i++) {
                                    databBuffer += (char) newData[i];
                                }
                                if (!idTagReaded.equals(databBuffer)) {
                                    idTagReaded = databBuffer;
                                    tagTextField.setText(idTagReaded);
                                }
                                // System.out.println(idTagReaded);
                            }
                        }
                );

    }


}

