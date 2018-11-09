import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.xml.soap.Text;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class loginController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("connect");

    }

    public void jumpSignIn() throws IOException {
        Preferences userPreferences = Preferences.userRoot();

        if (username.getText().equals("student")) {
            popUp(true);
            userPreferences.put("currentUser", "student");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("enroll.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            enrollController controller = fxmlLoader.<enrollController>getController();
            fxmlLoader.setController(controller);
            backpane.getChildren().setAll(root);
        } else if (username.getText().equals("teacher")) {
            popUp(true);
            userPreferences.put("currentUser", "teacher");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("opencourse_teacher.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            opencourse_teacherController controller = fxmlLoader.<opencourse_teacherController>getController();
            fxmlLoader.setController(controller);
            backpane.getChildren().setAll(root);
        }else{
            popUp(false);
        }


    }

    public void popUp(Boolean success) {

        if (success == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Welcome");
            alert.setHeaderText(null);
            alert.setContentText("Welcome");
            alert.showAndWait();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please Try Again");
            alert.showAndWait();

        }
    }

    public void jumpRegister() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        registerController controller = fxmlLoader.<registerController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }


}
