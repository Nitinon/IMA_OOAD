package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class deletesubject_teacherController implements Initializable {
    @FXML
    private AnchorPane backpane;
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("connect");
    }

    public void jumpEnroll()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/opencourse_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        opencourse_teacherController controller = fxmlLoader.<opencourse_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    public void jumpEditCourse()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/editsubject_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        editsubject_teacherController controller = fxmlLoader.<editsubject_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpDeleteCourse()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/deletesubject_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        deletesubject_teacherController controller = fxmlLoader.<deletesubject_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpEditScore()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/editscore_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        editscore_teacherController controller = fxmlLoader.<editscore_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpLogout() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

}
