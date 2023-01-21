package org.main.Utils;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.application.Platform;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ConnectionCardReader {
    public static SerialPort serialPort;
    String dataTagUID = "";
    public static String portName = "COM15";

    public void initSerialPort(String name, int baud) throws Exception {
        if (serialPort != null && serialPort.isOpen()) {
            closePort();
        }
        serialPort = SerialPort.getCommPort(name);
        serialPort.setParity(SerialPort.NO_PARITY);
        serialPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        serialPort.setNumDataBits(8);
        serialPort.setBaudRate(baud);
        serialPort.openPort();
        //listeningPort();

    }

    static Controller controller = new Controller();

    public static void closePort() {
        if (serialPort.isOpen()) {
            serialPort.closePort();

        }
        controller.cleanup();
    }


    public void listeningPort(TextField textField) {
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {

                    return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
                }

                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    StringBuilder dataBuffer = new StringBuilder();
                    byte[] newData = serialPortEvent.getReceivedData();
                    for (byte newDatum : newData) {
                        dataBuffer.append((char) newDatum);
                    }

                    dataTagUID = dataBuffer.toString();
                    textField.setText(dataTagUID);


                    System.out.println(dataTagUID);
                }
            });
        } else {

            controller.setTextField(textField);
            controller.initialize();

        }
    }


    public static List<String> getPortNames() {
        return Arrays.stream(SerialPort.getCommPorts())
                .map(SerialPort::getSystemPortName)
                .collect(Collectors.toList());
    }


}
