package org.example;

import com.fazecast.jSerialComm.SerialPort;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML public TextField username;
    @FXML public PasswordField password;
    @FXML public ProgressBar progrsTagScan;



     public void logInApplication() throws IOException {
         double progres=0;
        String user=username.getText();
        String pass= password.getText();
        if(user.equals("jan")) {
            progres+=0.33;
            if(pass.equals("123")) {
                progres+=0.33;
                if (ConectionCardReader.dataTagUID.trim().equals("17985235")) {
                    progres+=0.34;
                    App.setNextRootScene("ReadData");
                    ConectionCardReader.dataTagUID="";

                }
            }
        }
        progrsTagScan.setProgress(progres);
     }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connectionCardReader();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!ConectionCardReader.dataTagUID.equals("")){
            progrsTagScan.setProgress(100);
        }
    }
    public void connectionCardReader() throws Exception {
        ConectionCardReader.initSerialPort(ConectionCardReader.portName,9600);
        if(ConectionCardReader.serialPort.isOpen()){

        }else {
            List<String> choices = ConectionCardReader.getPortNames();
            ChoiceDialog<String> dialog = new ChoiceDialog<>("COM7",choices);
            dialog.setTitle("Choice your Com Port");
            dialog.setHeaderText("Look, a Choice Port");
            dialog.setContentText("Choose your Port:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            // The Java 8 way to get the response value (with lambda expression).
            result.ifPresent(letter -> System.out.println("Your choice: " + letter));
            ConectionCardReader.portName=result.get().trim().toString();
            ConectionCardReader.initSerialPort(ConectionCardReader.portName,9600);
        }
    }

}
