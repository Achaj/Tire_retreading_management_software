package org.main.Login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.main.App;
import org.main.Entity.WorkersRepositoryImpl;
import org.main.Entity.Workers;
import org.main.Entity.WorkingTime;
import org.main.Entity.WorkingTimeRepositoryImpl;
import org.main.Utils.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginController extends ConnectionCardReader implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passTag;

    WorkersRepositoryImpl workerRepository = new WorkersRepositoryImpl();
    WorkingTimeRepositoryImpl workingTimeRepository = new WorkingTimeRepositoryImpl();

    public void logInApplication() throws IOException {
        Alert alert = new Alert(Alert.AlertType.NONE);
        if (ValidadiotData.validdateEmail(username.getText())) {
            Workers worker = workerRepository.getWorkerByEmail(username.getText());
            logger.log(Level.INFO, "Find User with email:" + username.getText() + " status:" + worker);
            if (worker != null) {
                if (PasswordHashing.doHashing(password.getText()).equals(worker.getPassword()) && ValidadiotData.validatePassword(password.getText())) {
                    if (passTag.getText().trim().equals(worker.getTag())) {
                        Temporary.setWorkers(worker);
                        WorkingTime workingTime = new WorkingTime();
                        workingTime.setDateLogin(LocalDateTime.now());
                        workingTime.setWorkers(worker);
                        Temporary.workingTime = workingTime;
                        workingTimeRepository.save(Temporary.workingTime);
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Welcome " + worker.getFirstName());
                        alert.setGraphic(new ImageView(this.getClass().getResource("/img/user24px.png").toString()));
                            if(worker.getPosition().equals("ADMIN")) {
                                App.setNextRootScene("Admin/AdminMainView");

                            }else{
                                App.setNextRootScene("User/UserMainView");
                            }

                        } else {
                        logger.log(Level.INFO, "Wrong tag passsword");
                        passTag.setStyle("-fx-background-color:  transparent;-fx-border-color:   red;-fx-border-width:   0px 0px 4px 0px;");
                            alert.setAlertType(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Zeskanowano nieprawidłową kartę");
                            alert.setContentText("Zeskanuj ponownie dobrą kartę");

                        }
                } else {
                    logger.log(Level.INFO, "Wrong  passsword");
                    password.setStyle("-fx-background-color:  transparent;-fx-border-color:   red;-fx-border-width:   0px 0px 4px 0px;");
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Wpisano błędne hasło");
                    alert.setContentText("Wpisz poprawne hasło");
                }
            } else {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Nie ma konta z podany adresem email");
            }
        } else {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setHeaderText("Nie uzupełiono pól lub wprowdzono zabronione znaki");
        }

        alert.show();
    }


    Logger logger = MyLogger.getInstance().getLogger();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        username.setText("jan@op.pl");
        password.setText("ZAQ!2wsx");
        passTag.setText("17985235");
        try {
            connectionCardReader();
            listinerFileds();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void listinerFileds() {
        // Listen for TextField text changes
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validatePassword(newValue)) {
                password.setStyle("-fx-background-color:  transparent;-fx-border-color:   green;-fx-border-width:   0px 0px 4px 0px;");

            } else {
                password.setStyle("-fx-background-color:  transparent;-fx-border-color:   #404040;-fx-border-width:   0px 0px 2px 0px;");
            }

        });
        // Listen for TextField text changes
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validdateEmail(newValue)) {
                username.setStyle("-fx-background-color:  transparent;-fx-border-color:   green;-fx-border-width:   0px 0px 4px 0px;");

            } else {
                username.setStyle("-fx-background-color:  transparent;-fx-border-color:   #404040;-fx-border-width:   0px 0px 2px 0px;");
            }

        });
        passTag.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validateTAG(newValue)) {
                passTag.setStyle("-fx-background-color:  transparent;-fx-border-color:   green;-fx-border-width:   0px 0px 4px 0px;");


            } else {
                passTag.setStyle("-fx-background-color:  transparent;-fx-border-color:   #404040;-fx-border-width:   0px 0px 2px 0px;");

            }

        });


    }

    public void connectionCardReader() throws Exception {
        initSerialPort(portName, 9600);
        if (!serialPort.isOpen()) {
            List<String> choices = getPortNames();
            if (!choices.isEmpty()) {
                ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
                dialog.setTitle("Wybirz port");
                dialog.setHeaderText("Wybierz port z dostępnych");
                dialog.setContentText("Wybrany port:");

                // Traditional way to get the response value.
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(letter -> System.out.println("Wybrany port: " + letter));
                portName = result.get().trim().toString();
                initSerialPort(portName, 9600);
                listeningPort(passTag);
            }else {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Nie wykryto czytnika ze stacjonarnym czytnikiem!");
                alert.setContentText("Został uruchomine połącznie do zdalnego czytnika");
                alert.showAndWait();
                listeningPort(passTag);
            }
        } else {
            listeningPort(passTag);
        }
    }


}
