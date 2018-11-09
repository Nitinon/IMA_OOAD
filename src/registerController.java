import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class registerController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox year;
    @FXML
    private ComboBox faculty;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField tel;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordCon;

    public void initialize(URL url, ResourceBundle rb) {
        year.getItems().addAll(1, 2, 3, 4, 5);
        faculty.getItems().addAll("Computer Engineering");
        year.getSelectionModel().selectFirst();
        faculty.getSelectionModel().selectFirst();

    }

    public void register() throws IOException {
        String nameIn = name.getText();
        String surnameIn = surname.getText();
        String emailIn = email.getText();
        String telIn = tel.getText();
        String facultyIn = (String) faculty.getValue();
        int yearIn = (int) year.getValue();
        String pass=password.getText();
        String passCon=passwordCon.getText();

        LocalDate date = datePicker.getValue();
        if(!pass.isEmpty()&&!passCon.isEmpty()&&(!pass.equals(passCon))) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Not Match");
            alert.setHeaderText(null);
            alert.setContentText("Password & confirm password Not match");
            alert.showAndWait();
        }else{
        if (nameIn.isEmpty() || surnameIn.isEmpty() || emailIn.isEmpty() || telIn.isEmpty() || facultyIn.isEmpty() || date == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning alert");
            alert.setHeaderText(null);
            alert.setContentText("Empty!!!!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            String UserID="59010833";
            alert.setContentText("register success\n your ID: "+UserID);
            alert.showAndWait();
            System.out.println(nameIn + "\n" + surnameIn + "\n" + emailIn + "\n" + telIn + "\n" + facultyIn + "\n" + yearIn + "\n" + date);

            jumpBack();
        }
        }

    }

    public void jumpBack() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
}
