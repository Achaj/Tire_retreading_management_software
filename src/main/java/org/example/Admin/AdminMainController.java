package org.example.Admin;

import javafx.fxml.Initializable;
import org.example.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainController implements Initializable {

    public void logOutUser() throws IOException {
        App.setPrevRootScene();
    }
    public void showWorkers(){}
    public void showVulcaniserView(){}
    public void showCheckerView(){}
    public void showStoremenView(){}
    public void showPDFGenerator(){}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
