package org.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.main.Entity.WorkingTime;
import org.main.Entity.WorkingTimeRepositoryImpl;
import org.main.Utils.Temporary;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

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

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    if(Temporary.getWorkers()!=null){
                        App app=new App();
                        Temporary.workingTime.setDateLogOut(LocalDateTime.now());
                        app.workingTimeRepository.change(Temporary.workingTime);
                    }
                    Platform.exit();
                    System.exit(0);
                }
            });

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
    WorkingTimeRepositoryImpl  workingTimeRepository=new WorkingTimeRepositoryImpl();

    public  static void setLoginRootScene() throws IOException {
        App app=new App();
       Temporary.workingTime.setDateLogOut(LocalDateTime.now());
        app.workingTimeRepository.change(Temporary.workingTime);
        Temporary.setWorkers(null);
        linkedListSceneName.clear();
        linkedListSceneName.add("LoginView");
        scene.setRoot(loadFXML(linkedListSceneName.getLast()));
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }



}