package org.main.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField textField;

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    private ServerSocketHandler serverSocketHandler;

    public void initialize() {
        serverSocketHandler = new ServerSocketHandler();
        serverSocketHandler.start();
    }

    public void cleanup() {
        if (serverSocketHandler != null) {
            try {
                serverSocketHandler.interrupt();  //stop the thread
                serverSocketHandler.serverSocket.close(); // close the server socket
                if (serverSocketHandler.clientSocket != null)
                    serverSocketHandler.clientSocket.shutdownInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ServerSocketHandler extends Thread {
        Logger logger = MyLogger.getInstance().getLogger();
        private ServerSocket serverSocket;
        private Socket clientSocket;

        public void run() {
            try {
                serverSocket = new ServerSocket();
                serverSocket.setReuseAddress(true);
                serverSocket.bind(new InetSocketAddress(9090));
                while (!isInterrupted()) {
                    try {
                        clientSocket = serverSocket.accept();
                        System.out.println("Connection received from " + clientSocket.getInetAddress().getHostAddress());
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        String request;
                        while (!clientSocket.isClosed() && (request = in.readLine()) != null) {
                            String finalRequest = request;
                            System.out.println("Received data: " + request);
                            logger.log(Level.INFO, "Received data from " + clientSocket.getInetAddress().getHostAddress() + ": " + request);
                            Platform.runLater(() -> textField.setText(finalRequest));
                        }
                    } catch (IOException e) {
                        if (isInterrupted()) {
                            // thread was interrupted, close the socket
                            try {
                                serverSocket.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            break;
                        } else {
                            e.printStackTrace();
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}