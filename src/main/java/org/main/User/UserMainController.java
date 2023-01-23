package org.main.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.main.App;
import org.main.Entity.Tires;
import org.main.Utils.Temporary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserMainController implements Initializable {

    @FXML
    private Button backAdminBTN;
    @FXML
    private Button employeeEditBNT;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Temporary.getWorkers() != null) {
            employeeEditBNT.setText(Temporary.getWorkers().getPosition());
            if (Temporary.getWorkers().getPosition().equals("ADMIN")) {
                backAdminBTN.setVisible(true);

            }
        } else {
            try {
                logOutApp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void backToAdminScene() throws IOException {
        App.setPrevRootScene();
    }

    public void logOutApp() throws IOException {
        App.setLoginRootScene();
    }

    public void showTireView() throws IOException {
        App.setNextRootScene("User/TiresManager");
    }

    public void showWorksView() throws IOException {
        App.setNextRootScene("User/WorksManager");
    }

    public void showSemiProductsView() throws IOException {
        App.setNextRootScene("User/SemiProductsManager");
    }

    public void showUserDetails() throws IOException {
        App.setNextRootScene("User/UserDetails");
    }
}
