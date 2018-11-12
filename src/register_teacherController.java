import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class register_teacherController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField tel;
    @FXML
    private TextField email;
    @FXML
    private TextField position;

    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordCon;

    public void initialize(URL url, ResourceBundle rb) {

    }
    public void register() throws IOException {
        String nameIn = name.getText();
        String surnameIn = surname.getText();
        String emailIn = email.getText();
        String telIn = tel.getText();
        String pass = password.getText();
        String passCon = passwordCon.getText();
        String positionIn=position.getText();

        LocalDate date = datePicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String dateIn = date.format(formatter);

        if (!pass.isEmpty() && !passCon.isEmpty() && (!pass.equals(passCon))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not Match");
            alert.setHeaderText(null);
            alert.setContentText("Password & confirm password Not match");
            alert.showAndWait();
        } else {
            if (nameIn.isEmpty() || surnameIn.isEmpty() || emailIn.isEmpty() || telIn.isEmpty() || positionIn.isEmpty() || dateIn.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning alert");
                alert.setHeaderText(null);
                alert.setContentText("Empty!!!!");
                alert.showAndWait();
            } else {
//                register in DB
                long id = createTeacher(pass, nameIn, surnameIn,dateIn, emailIn, telIn,positionIn);
//                show pop-up alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("register success\n your ID: " + id);
                alert.showAndWait();
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
    //    ====================DB===========================================
    public static long createTeacher(String password, String name, String surname, String birthday, String email, String phonenumber, String post) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        classss.Teacher a = new classss.Teacher(password, name, surname, birthday, email, phonenumber, post);

        em.persist(a);
        em.getTransaction().commit();
        em.getTransaction().begin();
        a.setId(a.getId_teacher() + 100000);
        em.getTransaction().commit();
        em.close();
        emf.close();
        return a.getId();

    }


}
