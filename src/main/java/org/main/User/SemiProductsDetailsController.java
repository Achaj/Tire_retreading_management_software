package org.main.User;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.main.App;
import org.main.Entity.SemiProducts;
import org.main.Entity.SemiProductsRepositoryImpl;
import org.main.Utils.ConectionCardReader;
import org.main.Utils.Temporary;
import org.main.Utils.ValidadiotData;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SemiProductsDetailsController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inizjalizeChoiceBox();
        try {
            listeningPort();
        } catch (Exception e) {
            e.getMessage();
        }
        loadEditSemiProd();
        listinerField();
    }

    @FXML
    public TextField name;
    @FXML
    public ChoiceBox<String> category;
    @FXML
    public TextField tag;
    @FXML
    public TextField amount;
    private boolean corectName = false;
    private boolean corectCategory = false;
    private boolean corectTag = false;
    private boolean corectAmount = false;

    private void inizjalizeChoiceBox() {
        category.getItems().clear();
        for (String cat : Temporary.getCategorySemi()) {
            category.getItems().add(cat);
        }
    }

    private static SemiProducts semiProductsEdit = null;

    public static void setSemiProductsEdit(SemiProducts semiProductsEdit) {
        SemiProductsDetailsController.semiProductsEdit = semiProductsEdit;
    }

    private void loadEditSemiProd(){
        if(semiProductsEdit!=null) {
            name.setText(semiProductsEdit.getName());
            tag.setText(semiProductsEdit.getTag());
            amount.setText(String.valueOf(semiProductsEdit.getAmount()));
            category.setValue(semiProductsEdit.getCategory());
        }
    }

    public void backToPreviousScene() throws IOException {
        App.setPrevRootScene();
        if (semiProductsEdit != null) {
            semiProductsEdit = null;
        }
    }

    SemiProductsRepositoryImpl semiProductsRepository = new SemiProductsRepositoryImpl();

    public void save() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        if (corectName && corectTag && corectAmount && corectCategory) {
            SemiProducts semiProducts = new SemiProducts();
            semiProducts.setName(name.getText());
            semiProducts.setCategory(category.getValue());
            semiProducts.setTag(tag.getText());
            semiProducts.setAmount(Integer.valueOf(amount.getText()));
            if (semiProductsRepository.saveSemiProduct(semiProducts)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Dane zostały zapisane");
                clearFields();
            } else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText("Taki numer tagu jest używany");

            }

        } else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Wypełnij komurki nie zaznaczone na zielono");
        }
        alert.show();
    }

    public void edit() {
        if (semiProductsEdit != null) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            if (corectName || corectTag || corectAmount || corectCategory) {
                semiProductsEdit.setName(name.getText());
                semiProductsEdit.setCategory(category.getValue());
                semiProductsEdit.setTag(tag.getText());
                semiProductsEdit.setAmount(Integer.valueOf(amount.getText()));
                if (semiProductsRepository.changeSemiProduct(semiProductsEdit)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Dane zostały edytowane");
                    clearFields();
                } else {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setHeaderText("Taki numer tagu jest używany");

                }

            } else {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Wypełnij komurki nie zaznaczone na zielono");
            }
            alert.show();
        }
    }


    public void remove() throws IOException {
        if(semiProductsEdit!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Czy na pewno chcesz usunąć  produkt ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (semiProductsRepository.delateSemiProduct(semiProductsEdit.getIdSemiProduct())) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Opracja została wykonana poprawnie");
                    semiProductsEdit=null;
                    backToPreviousScene();
                } else {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setHeaderText("Opracja nie została wykonana poprwnie");
                    alert.show();
                }
            } else {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Opracja została anulowana");

            }
            alert.show();
        }
    }

    private void clearFields() {
        name.setText("");
        tag.setText("");
        amount.setText("");
        category.getSelectionModel().clearSelection();
        category.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");

        corectName = false;
        corectCategory = false;
        corectTag = false;
        corectAmount = false;

    }

    public void listinerField() {
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateMarkaAndModel(newValue)) {
                name.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                corectName = true;

            } else {
                name.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                corectName = false;
            }

        });
        tag.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateTAG(newValue)) {
                tag.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                corectTag = true;

            } else {
                tag.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                corectTag = false;
            }

        });
        amount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateDecimalNumber(newValue)) {
                amount.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                corectAmount = true;

            } else {
                amount.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                corectAmount = false;
            }

        });
        category.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    category.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                    corectCategory = true;

                });

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
                                    tag.setText(idTagReaded);
                                }
                                // System.out.println(idTagReaded);
                            }
                        }
                );

    }
}
