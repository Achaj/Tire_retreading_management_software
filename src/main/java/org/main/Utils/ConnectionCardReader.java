package org.main.Utils;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConnectionCardReader {
    public static SerialPort serialPort;
    String dataTagUID = "";
    public static String portName = "COM7";

    protected void initSerialPort(String name, int baud) throws Exception {
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


    public static void closePort() {
        if (serialPort.isOpen()) {
            serialPort.closePort();
        }
    }

    protected void listeningPort(TextField textField) {
        serialPort.addDataListener(new SerialPortDataListener() {
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
                if (!dataTagUID.equals(databBuffer)) {
                    dataTagUID = databBuffer;
                    textField.setText(dataTagUID);

                }
                System.out.println(dataTagUID);
            }
        });

    }

    public static List<String> getPortNames() {
        return Arrays.stream(SerialPort.getCommPorts())
                .map(SerialPort::getSystemPortName)
                .collect(Collectors.toList());
    }
}
