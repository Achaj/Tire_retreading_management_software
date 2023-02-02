package org.main.User;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.jetbrains.annotations.NotNull;
import org.main.Admin.WorkerDetailsControler;
import org.main.App;
import org.main.Entity.*;
import org.main.Utils.ConnectionCardReader;
import org.main.Utils.Temporary;
import org.main.Utils.ValidadiotData;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorksDetailsController extends ConnectionCardReader implements Initializable {

    @FXML
    public ComboBox<Workers> workersComboBox;
    @FXML
    public ComboBox<Departments> departmentsComboBox;
    @FXML
    public TextField tag;

    @FXML
    public TextField timeStart;
    @FXML
    public TextField timeStop;
    @FXML
    public TreeView<Tires> tiresTreeView;
    @FXML
    public TreeView<SemiProducts> semiProductTreeView;
    @FXML
    public ComboBox<String> nameChoiceBox;
    @FXML
    public ComboBox<String> statusChoiceBox;
    @FXML
    private Button removeBTN;
    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Temporary.getWorkers().getPosition().equals("ADMIN")) {
            removeBTN.setDisable(false);
            departmentsComboBox.setDisable(false);
            editButton.setDisable(false);
            saveButton.setDisable(false);
        }
        loadWorkersToComboBox();
        loadDepartmentsToComboBox();
        loadChoiceBox();
        loadEditWork();
        try {
            initSerialPort(portName, 9600);
            listeningPort(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listeningField();
        listenerTreeView();
    }

    static private Works worksEdit = null;

    public static void setWorksEdit(Works worksEdit) {
        WorksDetailsController.worksEdit = worksEdit;
    }

    private void clearData() {
        workersComboBox.getSelectionModel().clearSelection();
        departmentsComboBox.getSelectionModel().clearSelection();
        statusChoiceBox.getSelectionModel().clearSelection();
        nameChoiceBox.getSelectionModel().clearSelection();

        timeStart.setText("");
        timeStop.setText("");
        //workersComboBox.getItems().clear();
        //departmentsComboBox.getItems().clear();
        //statusChoiceBox.getItems().clear();
        //nameChoiceBox.getItems().clear();
        workersComboBox.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
        departmentsComboBox.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
        statusChoiceBox.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
        nameChoiceBox.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
        tiresTreeView.getSelectionModel().clearSelection();
        tiresTreeView.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");

        semiProductTreeView.setRoot(null);

        correctNam = false;
        correctStatus = false;
        correctWorker = false;
        correctDeparetment = false;
        correctTire = false;

    }

    public void backToPreviousScene() throws IOException {
        if (worksEdit != null) {
            worksEdit = null;
        }
        clearData();
        App.setPrevRootScene();


    }


    public void edit() throws IOException {
        if (worksEdit != null) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            if (correctNam || correctStatus || correctWorker || correctDeparetment) {
                if (!worksEdit.getName().equals(nameChoiceBox.getSelectionModel().getSelectedItem())) {
                    worksEdit.setName(nameChoiceBox.getSelectionModel().getSelectedItem());
                }
                if (!worksEdit.getDepartments().equals(departmentsComboBox.getSelectionModel().getSelectedItem())) {
                    worksEdit.setDepartments(departmentsComboBox.getSelectionModel().getSelectedItem());
                }
                if (!worksEdit.getWorkers().equals(workersComboBox.getSelectionModel().getSelectedItem())) {
                    worksEdit.setWorkers(workersComboBox.getSelectionModel().getSelectedItem());
                }
                if (!worksEdit.getName().equals(nameChoiceBox.getSelectionModel().getSelectedItem())) {
                    worksEdit.setName(nameChoiceBox.getSelectionModel().getSelectedItem());
                }
                if (!worksEdit.getName().equals(nameChoiceBox.getSelectionModel().getSelectedItem())) {
                    worksEdit.setName(nameChoiceBox.getSelectionModel().getSelectedItem());
                }
                String firstStatus=worksEdit.getStatus();
                if (!worksEdit.getStatus().equals(statusChoiceBox.getSelectionModel().getSelectedItem())) {
                    worksEdit.setStatus(statusChoiceBox.getSelectionModel().getSelectedItem());

                    if(firstStatus.equals("Do Zrobienia")) {
                        worksEdit.setDateStart(LocalDateTime.now());
                    }
                    if (worksEdit.getStatus().equals("Zaczęto")) {
                        worksEdit.setDateStop(null);
                    }else if(worksEdit.getStatus().equals("Do Zrobienia")){
                        worksEdit.setDateStop(null);
                        worksEdit.setDateStart(null);
                    }
                    else {
                        worksEdit.setDateStop(LocalDateTime.now());
                    }
                }
                if (worksRepository.change(worksEdit)) {
                    alert.setAlertType(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Czy chcesz edytować komponenty pracy?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        //AddSemiProductToWorkController.setWorksEdit(worksEdit);
                        loadStageEditWorSemiPoroducts(worksEdit, "edit", "Edycja komponenty pracy");
                    } else {
                        clearData();
                        backToPreviousScene();
                    }
                } else {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setHeaderText("Nie udało się zapisać");
                    alert.show();
                }


            } else {
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Czy chcesz edytować tylko komponenty pracy?");
                //alert.setContentText("Czy chcesz dodać  pół produkty do zadania?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    loadStageEditWorSemiPoroducts(worksEdit, "edit", "Edycja pozycji pracy");
                }
            }

        }
    }


    public void remove() throws IOException {
        if (worksEdit != null) {

            Logger logger = Logger.getLogger(getClass().getName());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Czy na pewno chcesz usunąć  zadanie ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                if (worksRepository.removed(worksEdit)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    logger.log(Level.SEVERE, "Suceesful remove work.");
                    alert.setHeaderText("Opracja została wykonana poprawnie");
                    worksEdit = null;
                    backToPreviousScene();
                } else {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setHeaderText("Opracja nie została wykonana poprwnie");
                    logger.log(Level.SEVERE, "Fail remove work.");
                    alert.show();
                }

            } else {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Opracja została anulowana");
                alert.show();

            }

        }

    }

    private void loadStageEditWorSemiPoroducts(Works works, @NotNull String parameter, String title) {
        try {
            if (parameter.equals("save")) {
                AddSemiProductToWorkController.works = worksRepository.getWorkActual(works);
            } if(parameter.equals("edit")) {
                AddSemiProductToWorkController.worksEdit = worksRepository.getWorkActual(works);
            }
            ConnectionCardReader.closePort();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AddSemiProductToWork.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void loadEditWork() {
        if (worksEdit != null) {
            if (worksEdit.getWorkers().equals(Temporary.getWorkers())) {
                editButton.setDisable(false);
            }
            workersComboBox.getSelectionModel().select(worksEdit.getWorkers());
            departmentsComboBox.getSelectionModel().select(worksEdit.getDepartments());

            nameChoiceBox.getSelectionModel().select(worksEdit.getName());
            nameChoiceBox.setDisable(true);
            statusChoiceBox.getSelectionModel().select(worksEdit.getStatus());


            if (worksEdit.getTires() != null) {
                List<Tires> tiresList = new ArrayList<>();
                tiresList.add(worksEdit.getTires());
                loadTiresToTreeView(tiresList);
            }
            if (worksEdit.getDateStart() != null) {
                timeStart.setText(String.valueOf(worksEdit.getDateStart()));
            }
            if (worksEdit.getDateStop() != null) {
                timeStop.setText(String.valueOf(worksEdit.getDateStop()));
            }
            List<WorkSemiProducts> workSemiProductsList = workSemiProductsRepository.getSemiProductsByWork(worksEdit.getIdWork());
            loadSemiProductsToTreeView(workSemiProductsList);
        } else {
            loadTiresToTreeView(tiresRepository.getTires());
            loadSemiProductsToTreeView(null);
            departmentsComboBox.setDisable(false);
            // workersComboBox.getSelectionModel().select(Temporary.getWorkers());
            // departmentsComboBox.getSelectionModel().select(Temporary.getWorkers().getDepartments());
        }

    }

    WorksRepositoryImpl worksRepository = new WorksRepositoryImpl();

    public void save() throws IOException {
        Alert alert = new Alert(Alert.AlertType.NONE);
        if (correctNam && correctStatus && correctWorker && correctDeparetment && correctTire) {
            Works works = new Works();
            works.setIdWork(0);
            works.setDateStart(LocalDateTime.now());
            works.setTires(tiresTreeView.getSelectionModel().getSelectedItem().getValue());
            works.setDepartments(departmentsComboBox.getSelectionModel().getSelectedItem());
            works.setWorkers(workersComboBox.getSelectionModel().getSelectedItem());
            works.setName(nameChoiceBox.getSelectionModel().getSelectedItem());
            works.setStatus(statusChoiceBox.getSelectionModel().getSelectedItem());
            if (!works.getStatus().equals("Zaczęto")) {
                works.setDateStop(LocalDateTime.now().plusMinutes(5));
            }
             if(works.getStatus().equals("Do Zrobienia")){
                 works.setDateStop(null);
                 works.setDateStart(null);
            }
            Works workSave = worksRepository.saveWork(works);
            if (workSave != null) {
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Dane zostały zapisane");
                alert.setContentText("Czy chcesz dodać  komponenty do zadania?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    clearData();
                    loadStageEditWorSemiPoroducts(workSave, "save", "Dodawanie pozycji pracy");
                } else {
                    clearData();


                }
                //clearFields();
            } else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText("Nie udało się zapisać");
                alert.show();

            }

        } else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Wypełnij komurki nie zaznaczone na zielono");
            alert.show();
        }


    }

    WorkersRepositoryImpl workersRepository = new WorkersRepositoryImpl();
    ObservableList<Workers> workersObservableList = FXCollections.observableArrayList();

    public void loadWorkersToComboBox() {

        List<Workers> workersList = new ArrayList<>();

        if (Temporary.getWorkers().getPosition().equals("ADMIN")) {
            workersList = workersRepository.getWorkers();
        } else {
            workersList.add(Temporary.getWorkers());
        }
        workersObservableList.clear();
        workersObservableList.addAll(workersList);
        workersComboBox.getItems().addAll(workersObservableList);
        workersComboBox.setCellFactory(new Callback<ListView<Workers>, ListCell<Workers>>() {
            @Override
            public ListCell<Workers> call(ListView<Workers> p) {

                final ListCell<Workers> cell = new ListCell<Workers>() {

                    @Override
                    protected void updateItem(Workers t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getFirstName() + " " + t.getLastName() + " " + t.getPosition());
                        } else {
                            setText(null);
                        }
                    }

                };

                return cell;
            }
        });

    }

    ObservableList<Departments> departmentsObservableList = FXCollections.observableArrayList();
    DepartmentsRepositoryImpl departmentsRepository = new DepartmentsRepositoryImpl();

    public void loadDepartmentsToComboBox() {
        List<Departments> departmentsList = new ArrayList<>();

        if (Temporary.getWorkers().getPosition().equals("ADMIN")) {
            departmentsList = departmentsRepository.getDepartments();
        } else {
            departmentsList.add(Temporary.getWorkers().getDepartments());
        }
        departmentsObservableList.addAll(departmentsList);
        departmentsComboBox.getItems().addAll(departmentsObservableList);
        departmentsComboBox.setCellFactory(new Callback<ListView<Departments>, ListCell<Departments>>() {
            @Override
            public ListCell<Departments> call(ListView<Departments> p) {
                final ListCell<Departments> cell = new ListCell<Departments>() {
                    @Override
                    protected void updateItem(Departments t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName() + " " + t.getHallName());
                        } else {
                            setText(null);
                        }
                    }

                };

                return cell;
            }
        });
    }
    ObservableList<String> status ;
    ObservableList<String> nameWork ;
    public void loadChoiceBox() {
        //status = FXCollections.observableArrayList();
         //nameWork = FXCollections.observableArrayList();

         List<String> statusList=Temporary.getStatus();


        status = statusChoiceBox.getItems();
        if (!status.isEmpty()) {
            status.clear();
        }else {
            status = FXCollections.observableArrayList();
            status.addAll(statusList);
            statusChoiceBox.setItems(status);
        }

        List<String> nameList=Temporary.getWorkName();

        nameWork = nameChoiceBox.getItems();
        if (!nameWork.isEmpty()) {
            nameWork.clear();
        }else {
            if(worksEdit!=null) {

                int index = nameList.indexOf(worksEdit.getName());
                if (index > 0) {
                    nameList = nameList.subList(index, nameList.size());
                }
                nameWork = FXCollections.observableArrayList();
                nameWork.addAll(nameList);
                nameChoiceBox.setItems(nameWork);
            } else {
                nameWork = FXCollections.observableArrayList();
                nameWork.addAll(nameList);
                nameChoiceBox.setItems(nameWork);
            }

        }
    }

    public void loadTiresToTreeView(List<Tires> tiresList) {
        TreeItem rootItem = new TreeItem("Opony");
        rootItem.setExpanded(true);

        if (tiresList != null) {
            tiresList.stream().forEach(tires ->
            {
                rootItem.getChildren().add(new TreeItem<>(tires));
            });
        }
        tiresTreeView.setRoot(rootItem);
    }

    WorkSemiProductsRepositoryImpl workSemiProductsRepository = new WorkSemiProductsRepositoryImpl();

    public void loadSemiProductsToTreeView(List<WorkSemiProducts> workSemiProductsList) {

        TreeItem rootItem = new TreeItem("Użyte komponenty");
        rootItem.setExpanded(true);

        if (workSemiProductsList != null) {
            for (WorkSemiProducts workSemiProducts : workSemiProductsList) {
                rootItem.getChildren().add(new TreeItem<String>("Nazwa: " + workSemiProducts.getSemiProducts().getName() +
                        "| Kategoria: " + workSemiProducts.getSemiProducts().getCategory() +
                        "| Ilość :" + workSemiProducts.getAmount()));
            }
        } else {
            rootItem.getChildren().add(new TreeItem<>("brak"));
        }

        semiProductTreeView.setRoot(rootItem);
    }

    TiresRepositoryImpl tiresRepository = new TiresRepositoryImpl();

    public void searchTires() {
        //file is not empty
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (!tag.getText().equals("")) {
            List<Tires> tiresList = new ArrayList<>();
            Tires tire = null;
            if (ValidadiotData.validateTAG(tag.getText().trim())) {
                tire = tiresRepository.getTireByTag(tag.getText());
                if (tire != null) {
                    tiresList.add(tire);
                    loadTiresToTreeView(tiresList);
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



    private boolean correctNam = false;
    private boolean correctStatus = false;
    private boolean correctWorker = false;
    private boolean correctDeparetment = false;
    private boolean correctTire = false;

    public void listeningField() {
        nameChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    nameChoiceBox.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                    correctNam = true;
                });
        statusChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    statusChoiceBox.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                    correctStatus = true;
                });
        workersComboBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    workersComboBox.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                    correctWorker = true;
                });
        departmentsComboBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    departmentsComboBox.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                    correctDeparetment = true;
                });
        tiresTreeView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    tiresTreeView.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                    correctTire = true;
                });
    }

    private void listenerTreeView() {
        tiresTreeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    TreeItem<Tires> item = tiresTreeView.getSelectionModel().getSelectedItem();
                    System.out.println("Selected Text : " + item.getValue());
                    TiresDetailsController.setTiresEdit(item.getValue());
                    try {
                        App.setNextRootScene("User/TiresDetails");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
    }

}
