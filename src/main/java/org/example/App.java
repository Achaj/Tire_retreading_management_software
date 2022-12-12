package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static LinkedList<String> linkedListSceneName= new LinkedList<>();
    @Override
    public void start(Stage stage) throws IOException {

        scene= new Scene(loadFXML("LoginView"), 800, 600);
        linkedListSceneName.add("LoginView");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    public static  void setNextRootScene(String fxml) throws IOException {
        linkedListSceneName.add(fxml);
        scene.setRoot(loadFXML(fxml));
    }
    public static  void setPrevRootScene() throws IOException {
        linkedListSceneName.removeLast();
        scene.setRoot(loadFXML(linkedListSceneName.getLast()));
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }



}