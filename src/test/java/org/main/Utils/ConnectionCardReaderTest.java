package org.main.Utils;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.main.Utils.ConnectionCardReader.serialPort;
import static org.mockito.Mockito.*;

import javafx.scene.control.TextField;
import org.main.Utils.ConnectionCardReader;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class ConnectionCardReaderTest {
    @Mock
    private ConnectionCardReader connectionCardReader;

    SerialPort[] ports;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        connectionCardReader = new ConnectionCardReader();
        ports = SerialPort.getCommPorts();
    }

    @Test
    public void initSerialPortTest() throws Exception {
        connectionCardReader.initSerialPort(ports[0].getSystemPortName(), 9600);
        assertTrue(serialPort.isOpen());
        assertEquals(SerialPort.NO_PARITY, serialPort.getParity());
        assertEquals(SerialPort.ONE_STOP_BIT, serialPort.getNumStopBits());
        assertEquals(8, serialPort.getNumDataBits());
        assertEquals(9600, serialPort.getBaudRate());
    }

    @Test
    public void getPortNamesTest() {
        List<String> portNames = ConnectionCardReader.getPortNames();
        assertTrue(portNames.size() > 0);
    }


}

