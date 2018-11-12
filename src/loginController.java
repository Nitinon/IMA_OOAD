import classss.Account;
import classss.Student;
import classss.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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

    }

    public void jumpSignIn() throws IOException {
        int userID = Integer.parseInt(username.getText());
        Student foundedStudent = getObjStudent(userID);
        Teacher foundedTeacher = getObjTeacher(userID);
        if (foundedStudent == null && foundedTeacher == null) {
            popUp(false, "User not found", "User not found please try again!!");
        } else {
            if (foundedStudent != null) {
                if (foundedStudent.getPassword().equals(password.getText())) {
                    popUp(true, "Welcome", "Welcome" + " " + foundedStudent.getName() + " " + foundedStudent.getSurname());

                    Preferences userPreferences = Preferences.userRoot();
                    userPreferences.putLong("currentUser", foundedStudent.getId());

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/enroll.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    enrollController controller = fxmlLoader.<enrollController>getController();
                    fxmlLoader.setController(controller);
                    backpane.getChildren().setAll(root);
                }else {
                    popUp(false, "Wrong Password", "Please Enter Password Again");
                }
            }
            if (foundedTeacher != null) {
                if (foundedTeacher.getPassword().equals(password.getText())) {
                    popUp(true, "Welcome", "Welcome" + " " + foundedTeacher.getName() + " " + foundedTeacher.getSurname());

                    Preferences userPreferences = Preferences.userRoot();
                    userPreferences.putLong("currentUser", foundedTeacher.getId());

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/opencourse_teacher.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    opencourse_teacherController controller = fxmlLoader.<opencourse_teacherController>getController();
                    fxmlLoader.setController(controller);
                    backpane.getChildren().setAll(root);
                }else {
                    popUp(false, "Wrong Password", "Please Enter Password Again");
                }
            }

        }

    }

    public void popUp(Boolean success, String header, String txt) {
        if (success == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(header);
            alert.setHeaderText(null);
            alert.setContentText(txt);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(header);
            alert.setHeaderText(null);
            alert.setContentText(txt);
            alert.showAndWait();
        }
    }

    public void jumpRegister() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/register.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        registerController controller = fxmlLoader.<registerController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpRegister_teacher() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/register_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        register_teacherController controller = fxmlLoader.<register_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    //    ======================================DB==============================================================
    public static classss.Student getObjStudent(long id_stu) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Student c Where c.id =" + id_stu + "";
        TypedQuery<Student> query1 = em.createQuery(sql1, classss.Student.class);
        List<Student> results1 = query1.getResultList();
        if (results1.size() == 0) {
            return null;
        } else {
            return results1.get(0);
        }
    }

    public static classss.Teacher getObjTeacher(int id_tea) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Teacher c Where c.id =" + id_tea + "";
        TypedQuery<classss.Teacher> query1 = em.createQuery(sql1, classss.Teacher.class);
        List<classss.Teacher> results1 = query1.getResultList();
        if (results1.size() == 0) {
            return null;
        } else {
            return results1.get(0);
        }
    }
}
