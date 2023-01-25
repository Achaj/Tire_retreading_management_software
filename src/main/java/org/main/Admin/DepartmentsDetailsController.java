package org.main.Admin;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.main.App;
import org.main.Entity.Departments;
import org.main.Entity.DepartmentsRepositoryImpl;
import org.main.Entity.Workers;
import org.main.Entity.WorkersRepositoryImpl;
import org.main.Utils.ValidadiotData;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DepartmentsDetailsController implements Initializable {

    @FXML public TextField name;
    @FXML public TextField hallName;
    @FXML public TextField city;
    @FXML public TextField street;
    @FXML public TextField flatNumber;
    @FXML public TextField postCode;
    @FXML
    public TextField phoneNumber;
    @FXML
    public TreeView<Workers> workersTreeView;

    public static void setDepartments(Departments departments) {
        DepartmentsDetailsController.departmentsEdit = departments;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicjalizeTreeView();
        inicjalizeTextFields();
        listinerTextFiels();
        listenerTreeView();


    }
    public void backToPreviousScene() throws IOException {
        App.setPrevRootScene();
        departmentsEdit=null;
    }

    private static Departments departmentsEdit;

    DepartmentsRepositoryImpl departmentsRepository=new DepartmentsRepositoryImpl();
    WorkersRepositoryImpl workersRepository=new WorkersRepositoryImpl();

    private void inicjalizeTreeView() {
        if (departmentsEdit != null) {
            TreeItem rootItem = new TreeItem("Pracuje w dziale "+departmentsEdit.getName());
            rootItem.setExpanded(true);

            List<Workers> workersList = workersRepository.getWorkersByDepartment(departmentsEdit.getIdDepartment());

            if (workersList != null) {
                for (Workers worker : workersList) {
                    rootItem.getChildren().add(new TreeItem<>(worker));
                }
            }
            workersTreeView.setRoot(rootItem);
        }
    }

    private void listenerTreeView() {
        workersTreeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    TreeItem<Workers> item = workersTreeView.getSelectionModel().getSelectedItem();
                    System.out.println("Selected Text : " + item.getValue());
                    WorkerDetailsControler.setEditWorker(item.getValue());
                    try {
                        App.setNextRootScene("Admin/WorkerDetails");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
    }

    private void inicjalizeTextFields() {
        if (departmentsEdit != null) {
            name.setText(departmentsEdit.getName());
            hallName.setText(departmentsEdit.getHallName());
            city.setText(departmentsEdit.getCity());
            street.setText(departmentsEdit.getStreet());
            flatNumber.setText(departmentsEdit.getFlatNumber());
            postCode.setText(departmentsEdit.getPostCode());
            phoneNumber.setText(departmentsEdit.getPhoneNumber());
        }
    }
    public void save(){
        Alert alert = new Alert(Alert.AlertType.NONE);
        if(corectName && corectHallName &&
        corectCity && corectStreet &&
        corectFlatNumber && corectPostCode &&
        corectPhoneNumber){
            Departments departments = new Departments();
            departments.setIdDepartment(0);
            departments.setName(name.getText());
            departments.setCity(city.getText());
            departments.setStreet(street.getText());
            departments.setFlatNumber(flatNumber.getText());
            departments.setPostCode(postCode.getText());
            departments.setHallName(hallName.getText());
            departments.setPhoneNumber(phoneNumber.getText());
            if (departmentsRepository.saveDepartment(departments)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Dane zostały zapisane");
                clearTextFields();
            } else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText("Taki nazwa działu już istnieje ");
                alert.setContentText("Użyj innej nazwy");

            }

        }else{
            alert.setAlertType( Alert.AlertType.INFORMATION);
            alert.setHeaderText("Wszyskie dane nie są poprawne");
            alert.setContentText("Popraw komórki nie zaznaczone na zielono");
        }
        alert.show();
    }
    public void chenge(){
        Alert alert = new Alert(Alert.AlertType.NONE);
        if(corectName || corectHallName ||
                corectCity || corectStreet ||
                corectFlatNumber || corectPostCode ||
                corectPhoneNumber){

            departmentsEdit.setName(name.getText());
            departmentsEdit.setCity(city.getText());
            departmentsEdit.setStreet(street.getText());
            departmentsEdit.setFlatNumber(flatNumber.getText());
            departmentsEdit.setPostCode(postCode.getText());
            departmentsEdit.setHallName(hallName.getText());
            departmentsEdit.setPhoneNumber(phoneNumber.getText());
            if (departmentsRepository.changeDepartment(departmentsEdit)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Dane zostały zmienione");
                clearTextFields();
            } else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText("Taki nazwa działu już istnieje ");
                alert.setContentText("Użyj innej nazwy");

            }

        }else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Dane nie zostały zmienione");
            alert.setContentText("Brak informacji do zmiany");
        }
        alert.show();

    }
    public void delate() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Czy na pewno chcesz DZIAŁ  konto ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (departmentsRepository.delateDepartment(departmentsEdit.getIdDepartment())) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Opracja została wykonana poprawnie");
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
   private boolean corectName=false;
   private boolean corectHallName =false;
   private boolean corectCity=false;
   private boolean corectStreet=false;
   private boolean corectFlatNumber=false;
   private boolean corectPostCode=false;
   private boolean corectPhoneNumber=false;
    public void listinerTextFiels(){
        // Listen for TextField text changes
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateStreet(newValue)) {
                name.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                corectName=true;

            } else {
                name.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                corectName=false;
            }

        });
        hallName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateStreetNumber(newValue)) {
                hallName.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                corectHallName =true;

            } else {
                hallName.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                corectHallName =false;
            }

        });
        city.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateCity(newValue)) {
                city.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                corectCity=true;

            } else {
                city.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                corectCity=false;
            }

        });
        street.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateStreet(newValue)) {
                street.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                corectStreet=true;

            } else {
                street.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                corectStreet=false;
            }

        });
        flatNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateStreetNumber(newValue)) {
                flatNumber.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");

            corectFlatNumber=true;
            } else {
                flatNumber.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                corectFlatNumber=false;
            }

        });
        postCode.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateZipCode(newValue)) {
                postCode.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                corectPostCode=true;

            } else {
                postCode.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                corectPostCode=false;
            }

        });
        phoneNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validatePhoneNumber(newValue)) {
                phoneNumber.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                corectPhoneNumber=true;

            } else {
                phoneNumber.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                corectPhoneNumber=false;
            }

        });

    }
    public void clearTextFields(){
        name.setText("");
        hallName.setText("");
        city.setText("");
        street.setText("");
        flatNumber.setText("");
        postCode.setText("");
        phoneNumber.setText("");
    }

}
