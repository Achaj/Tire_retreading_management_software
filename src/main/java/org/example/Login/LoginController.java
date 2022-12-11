package org.example.Login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.App;
import org.example.Utils.ConectionCardReader;
import org.example.Entity.WorkerRepository;
import org.example.Entity.Workers;
import org.example.Utils.ValidadiotData;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML



    WorkerRepository workerRepository = new WorkerRepository();

    public void logInApplication() throws IOException {

        //  String user = username.getText();
        //  String pass = password.getText();

        Alert alert = new Alert(Alert.AlertType.NONE);
        if (ValidadiotData.validdateEmail(username.getText()) && ValidadiotData.validatePassword(password.getText())) {
            Workers worker = workerRepository.getWorkers(username.getText());
            if (worker != null) {
                if (password.getText().equals(worker.getPassword())) {
                    if (!ConectionCardReader.dataTagUID.equals("")) {
                        if (ConectionCardReader.dataTagUID.trim().equals(worker.getTag())) {
                            alert.setAlertType(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Welcome " + worker.getFirstName() + ":)");
                            App.setNextRootScene("ReadData");
                            ConectionCardReader.dataTagUID = "";
                        } else {
                            alert.setAlertType(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Zeskanowano nieprawidłową kartę");
                            alert.setContentText("Zeskanuj ponownie dobrą kartę");

                        }
                    } else {
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Nie zeskanowano karty");
                        alert.setContentText("Zeskanuj kartę");
                    }
                } else {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* Workers workers = new Workers();
        workers.setIdWorker(0);
        workers.setFirstName("jan");
        workers.setLastName("ss");
        workers.setPassword("ZAQ!2wsx");
        workers.setEmail("jan@op.pl");
        workers.setTag("17985235");
        workers.setStackingDate(Date.valueOf("2015-01-01"));
        workers.setEnploymentDate(Date.valueOf("2015-01-02"));
        WorkerRepository workerRepository = new WorkerRepository();

        workerRepository.saveWorker(workers);
        */
        try {
            connectionCardReader();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void connectionCardReader() throws Exception {
        ConectionCardReader.initSerialPort(ConectionCardReader.portName, 9600);
        if (ConectionCardReader.serialPort.isOpen()) {

        } else {
            List<String> choices = ConectionCardReader.getPortNames();
            ChoiceDialog<String> dialog = new ChoiceDialog<>("COM7", choices);
            dialog.setTitle("Choice your Com Port");
            dialog.setHeaderText("Look, a Choice Port");
            dialog.setContentText("Choose your Port:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            // The Java 8 way to get the response value (with lambda expression).
            result.ifPresent(letter -> System.out.println("Your choice: " + letter));
            ConectionCardReader.portName = result.get().trim().toString();
            ConectionCardReader.initSerialPort(ConectionCardReader.portName, 9600);
        }
    }

}
