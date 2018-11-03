import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class changeCourseController implements Initializable{
    @FXML
    private AnchorPane backpane;

    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("connect");
    }
    public void jumpEnroll()throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("enroll.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        enrollController controller = fxmlLoader.<enrollController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    public void jumpChange()throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("changeCourse.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        changeCourseController controller = fxmlLoader.<changeCourseController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    public void jumpDrop()throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("drop.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        dropController controller = fxmlLoader.<dropController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    public void jumpView()throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewscore.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        viewscoreController controller = fxmlLoader.<viewscoreController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    public void jumpLogout() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }


}
