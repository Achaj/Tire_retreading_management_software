package org.main.User;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.main.App;
import org.main.Entity.SemiProducts;
import org.main.Utils.ConectionCardReader;
import org.main.Utils.Temporary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SemiProductsDetailsController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inizjalizeChoiceBox();
    }

    @FXML
    public TextField name;
    @FXML
    public ChoiceBox<String> category;
    @FXML
    public TextField tag;
    @FXML
    public TextField amount;

    private void inizjalizeChoiceBox() {
        category.getItems().clear();
        for (String cat : Temporary.getCategorySemi()) {
            category.getItems().add(cat);
        }
    }

    private SemiProducts semiProducts = null;

    public void setSemiProducts(SemiProducts semiProducts) {
        this.semiProducts = semiProducts;
    }

    public void backToPreviousScene() throws IOException {
        App.setPrevRootScene();
        ConectionCardReader.closePort();
        if (semiProducts != null) {
            semiProducts = null;
        }
    }

    public void save() {

    }

    public void edit() {

    }

    public void remove() {

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
