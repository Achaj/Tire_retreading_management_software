package org.example;


import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class ReadDataControler implements Initializable {
 @FXML ChoiceBox comPortChoiceBOX;
 @FXML TextArea textAreaRead;
 @FXML ProgressBar progresBar;
    private SerialPort serialPort;

    public void openPort() throws Exception {
    if(!comPortChoiceBOX.getSelectionModel().isEmpty()){
        initSerialPort(comPortChoiceBOX.getSelectionModel().getSelectedItem().toString(),9600);
        if(serialPort.isOpen()){
            progresBar.setProgress(100);
        }else {
            textAreaRead.setText("Nie udało się połączyć !");
        }

    }else{
        textAreaRead.setText("Wybierz Port !");
    }
 }
 private void initSerialPort(String name, int baud) throws Exception {
        if (serialPort != null && serialPort.isOpen()) {
            closePort();
        }
        serialPort = SerialPort.getCommPort(name);
        serialPort.setParity(SerialPort.NO_PARITY);
        serialPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        serialPort.setNumDataBits(8);
        serialPort.setBaudRate(baud);
        serialPort.openPort();
        listeningPort();
    }


 public void closePort(){
        if(serialPort.isOpen()){
            serialPort.closePort();
            progresBar.setProgress(0);
        }
 }
 /*
public void sendData(String data){
    OutputStream outputStream=serialPort.getOutputStream();

        try{
            outputStream.write(data.getBytes());
        }catch (IOException e){
            System.out.println("Error send");
        }
}
  */
public void listeningPort(){
    serialPort.addDataListener(new SerialPortDataListener() {
        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
        }

        @Override
        public void serialEvent(SerialPortEvent serialPortEvent) {
            String databBuffer="";
            byte []newData=serialPortEvent.getReceivedData();
            for(int i=0; i< newData.length;i++){
                databBuffer+=(char)newData[i];
            }
            textAreaRead.setText(databBuffer);
        }
    });
}


 @Override
 public void initialize(URL url, ResourceBundle resourceBundle) {

     comPortChoiceBOX.getItems().addAll(getPortNames());
 }
 public List<String> getPortNames() {
  return Arrays.stream(SerialPort.getCommPorts())
          .map(SerialPort::getSystemPortName)
          .collect(Collectors.toList());
 }
 public void backScene() throws IOException {
    App.setPrevRootScene();
 }

}
