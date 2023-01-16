package org.main.Admin;

import javafx.fxml.Initializable;
import org.main.App;
import org.main.Utils.Temporary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainController implements Initializable {

    public void logOutUser() throws IOException {
        App.setLoginRootScene();
    }
    public void showWorkers() throws IOException {
        App.setNextRootScene("Admin/WorkerManager");
    }
    public void showDepartmentsView() throws IOException {
        App.setNextRootScene("Admin/DepartmentManager");
    }
    public void showBasicUser() throws IOException {
        App.setNextRootScene("User/UserMainView");
    }
    public void showStatistic() throws IOException {
        App.setNextRootScene("Admin/StatisticWorks");
    }
    public void showStatisticWortkTime() throws IOException {
        App.setNextRootScene("Admin/StatisticWorkTime");
    }
    public void showGeneratorStatements() throws IOException {
        App.setNextRootScene("Admin/GeneratingStatements");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Temporary.getWorkers()!=null && Temporary.getWorkers().getPosition().equals("ADMIN")){

        }else {
            try {
                logOutUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
