package org.main.User;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.main.App;
import org.main.Entity.*;
import org.main.Utils.ConnectionCardReader;
import org.main.Utils.Temporary;
import org.main.Utils.ValidadiotData;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TiresDetailsController extends ConnectionCardReader implements Initializable {
    @FXML
    public TextField idTag;
    @FXML
    public TextField height;
    @FXML
    public TextField width;
    @FXML
    public TextField diameter;

    @FXML
    public ChoiceBox<String> speedIndex;
    @FXML
    public ChoiceBox<String> loadIndex;
    @FXML
    public TreeView<Works> lastWork;
    @FXML
    private Button removeBTN;

    private boolean corectIdTag = false;
    private boolean corectHeight = false;
    private boolean corectWidth = false;
    private boolean corectDiameter = false;
    private boolean corectSpeedIndex = false;
    private boolean corectLoadIndex = false;

    private static Tires tiresEdit = null;

    public static void setTiresEdit(Tires tiresEdit) {
        TiresDetailsController.tiresEdit = tiresEdit;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Temporary.getWorkers().getPosition().equals("ADMIN")) {
            removeBTN.setDisable(false);
        }

        inizjalizeChoiceBox();

        if (tiresEdit != null) {
            loadTiere();
        }
        listinerField();
        try {
            initSerialPort(portName, 9600);
            listeningPort(idTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listenerTreeView();
    }

    public void inizjalizeChoiceBox() {
        loadIndex.getItems().clear();
        for (TireIndex load : Temporary.getLoadIndexTiere()) {
            loadIndex.getItems().add(load.getName() + " - " + load.getVlaue() + " kg");
        }
        speedIndex.getItems().clear();
        for (TireIndex speed : Temporary.getSpeedIndexTire()) {
            speedIndex.getItems().add(speed.getName() + " - " + speed.getVlaue() + " km/h");
        }

    }

    public void loadTiere() {
        idTag.setText(String.valueOf(tiresEdit.getTag()));
        height.setText(String.valueOf(tiresEdit.getHeight()));
        diameter.setText(String.valueOf(tiresEdit.getDiameter()));
        width.setText(String.valueOf(tiresEdit.getWidth()));
        speedIndex.setValue(tiresEdit.getSpeedIndex());
        loadIndex.setValue(tiresEdit.getLoadIndex());
        inicjalizeTreeView();

    }

    private void inicjalizeTreeView() {
        WorksRepositoryImpl worksRepository = new WorksRepositoryImpl();
        if (tiresEdit != null) {
            TreeItem rootItem = new TreeItem("Ostatnio wykonywana Praca");
            rootItem.setExpanded(true);

            List<Works> workersList = worksRepository.getListWorksByTire(tiresEdit.getIdTire());
            //System.out.println(tiresEdit.getIdTire());

            if (workersList != null) {
                for (Works works : workersList) {
                    rootItem.getChildren().add(new TreeItem<>(works));
                }
            }
            lastWork.setRoot(rootItem);

        }

    }


    TiresRepositoryImpl tiresRepository = new TiresRepositoryImpl();

    public void editTireInDB() {
        if (corectIdTag || corectHeight || corectWidth ||
                corectDiameter || corectSpeedIndex || corectLoadIndex) {
            tiresEdit.setDiameter(Integer.parseInt(diameter.getText()));
            tiresEdit.setWidth(Integer.parseInt(width.getText()));
            tiresEdit.setHeight(Integer.parseInt(height.getText()));
            tiresEdit.setTag(idTag.getText());
            tiresEdit.setLoadIndex(loadIndex.getValue());
            tiresEdit.setSpeedIndex(speedIndex.getValue());
            Alert alert = new Alert(Alert.AlertType.NONE);
            if (tiresRepository.changeTire(tiresEdit)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Dane zostały zapisane");
                clearFields();
            } else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText("Taki numer tagu jest używany");

            }
            alert.show();
        }

    }

    public void remoweTirerInDB() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Czy na pewno chcesz usunąć  produkt ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (tiresRepository.delateTire(tiresEdit.getIdTire())) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Opracja została wykonana poprawnie");
                tiresEdit = null;
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

    WorksRepositoryImpl worksRepository = new WorksRepositoryImpl();

    public void addTireToDB() {
        if (corectIdTag && corectHeight && corectWidth &&
                corectDiameter && corectSpeedIndex && corectLoadIndex) {
            Tires tires = new Tires();
            tires.setIdTire(0);
            tires.setDiameter(Integer.parseInt(diameter.getText()));
            tires.setWidth(Integer.parseInt(width.getText()));
            tires.setHeight(Integer.parseInt(height.getText()));
            tires.setTag(idTag.getText());
            tires.setLoadIndex(loadIndex.getValue());
            tires.setSpeedIndex(speedIndex.getValue());
            Alert alert = new Alert(Alert.AlertType.NONE);
            Tires savedTire = tiresRepository.save(tires);
            if (savedTire != null && savedTire.getIdTire() != 0) {
                Works works = new Works();
                works.setTires(savedTire);
                works.setWorkers(Temporary.getWorkers());
                works.setDateStart(LocalDateTime.now());
                works.setDateStop(LocalDateTime.now().plusMinutes(5));
                works.setDepartments(Temporary.getWorkers().getDepartments());
                works.setStatus("Zakończono");
                works.setName("Przyjęcie na stan");
                if (worksRepository.save(works)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Dane zostały zapisane");

                }
                clearFields();
            } else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText("Taki numer tagu jest używany");

            }
            alert.show();
        }
    }

    private void clearFields() {
        idTag.setText("");
        height.setText("");
        diameter.setText("");
        width.setText("");
        inizjalizeChoiceBox();
        corectIdTag = false;
        corectHeight = false;
        corectWidth = false;
        corectDiameter = false;
        corectSpeedIndex = false;
        corectLoadIndex = false;
        speedIndex.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
        loadIndex.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
    }

    public void listinerField() {

        height.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateDecimalNumberTIRE(newValue)) {
                height.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                corectHeight = true;

            } else {
                height.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                corectHeight = false;
            }

        });
        width.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateDecimalNumberTIRE(newValue)) {
                width.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                corectWidth = true;

            } else {
                width.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                corectWidth = false;
            }

        });
        diameter.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateDecimalNumberTIRE(newValue)) {
                diameter.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                corectDiameter = true;

            } else {
                diameter.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                corectDiameter = false;
            }

        });
        idTag.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateTAG(newValue)) {
                idTag.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                corectIdTag = true;

            } else {
                idTag.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                corectIdTag = false;
            }

        });
        loadIndex.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    loadIndex.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                    corectLoadIndex = true;
                });
        speedIndex.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    speedIndex.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                    corectSpeedIndex = true;
                });

    }

    public void backToPreviousScene() throws IOException {
        App.setPrevRootScene();
        if (tiresEdit != null) {
            tiresEdit = null;
        }
    }

    private void listenerTreeView() {
        lastWork.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    TreeItem<Works> item = lastWork.getSelectionModel().getSelectedItem();
                    System.out.println("Selected Text : " + item.getValue());
                    WorksDetailsController.setWorksEdit(item.getValue());
                    try {
                        App.setNextRootScene("User/WorksDetails");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });

    }

}
