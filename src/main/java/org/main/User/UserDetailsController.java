package org.main.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.main.App;
import org.main.Entity.WorkersRepositoryImpl;
import org.main.Utils.PasswordHashing;
import org.main.Utils.Temporary;
import org.main.Utils.ValidadiotData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserDetailsController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadWorkerDetails();
        listinerTextField();
    }

    @FXML
    public TextField idTag;
    @FXML
    public TextField firstName;
    @FXML
    public TextField secondName;
    @FXML
    public TextField email;
    @FXML
    public TextField pass;
    @FXML
    public TextField confirmPass;
    @FXML
    public TextField position;
    @FXML
    public TextField department;

    private boolean correctFirstname = false;
    private boolean correctLastname = false;
    private boolean correctEmail = false;
    private boolean correctPassword = false;

    private void loadWorkerDetails() {
        idTag.setText(Temporary.getWorkers().getTag());
        firstName.setText(Temporary.getWorkers().getFirstName());
        secondName.setText(Temporary.getWorkers().getLastName());
        email.setText(Temporary.getWorkers().getEmail());
        position.setText(Temporary.getWorkers().getPosition());
        department.setText(Temporary.getWorkers().getDepartments().getName());
    }

    public void backToPreviousScene() throws IOException {
        App.setPrevRootScene();

    }

    WorkersRepositoryImpl workersRepository = new WorkersRepositoryImpl();

    public void editWorkerInDB() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        if (correctFirstname ||
                correctLastname ||
                correctEmail ||
                (correctPassword && !Temporary.getWorkers().getPassword().equals(PasswordHashing.doHashing(pass.getText())))) {
            if (correctFirstname) {
                Temporary.getWorkers().setFirstName(firstName.getText());
            }
            if (correctLastname) {
                Temporary.getWorkers().setLastName(secondName.getText());
            }
            if (correctEmail) {
                Temporary.getWorkers().setEmail(email.getText());
            }
            if (correctPassword && !Temporary.getWorkers().getPassword().equals(PasswordHashing.doHashing(pass.getText()))) {
                Temporary.getWorkers().setPassword(PasswordHashing.doHashing(pass.getText()));
            }

            if (workersRepository.changeDadataWorker(Temporary.getWorkers())) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Dane zostały zapisane");
                alert.setContentText("OK");
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setHeaderText("Bład zapisu danych");
                alert.setContentText("Sprawdz dane");
            }

        }else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Brak informacji do zminay");
        }
        alert.show();
    }

    public void listinerTextField() {
        // Listen for TextField text changes
        firstName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.valideteName(newValue)) {
                firstName.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                correctFirstname = true;

            } else {
                firstName.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                correctFirstname = false;
            }

        });
        secondName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.valideteName(newValue)) {
                secondName.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                correctLastname = true;

            } else {
                secondName.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                correctLastname = false;
            }

        });
        email.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validdateEmail(newValue)) {
                email.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                correctEmail = true;

            } else {
                email.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                correctEmail = false;
            }

        });
        pass.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validatePassword(newValue)) {
                pass.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");

            } else {
                pass.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");

            }

        });
        confirmPass.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ValidadiotData.validatePassword(newValue, pass.getText())) {
                confirmPass.setStyle("-fx-background-color:  white;-fx-border-color:   green;-fx-border-width:   0px 0px 2px 2px;");
                correctPassword = true;

            } else {
                confirmPass.setStyle("-fx-background-color:  white;-fx-border-color:   #404040;-fx-border-width:   0px 0px 0px 0px;");
                correctPassword = false;
            }

        });
    }

}
