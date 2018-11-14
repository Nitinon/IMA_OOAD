import classss.Student;
import classss.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import sun.font.BidiUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class changePassController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private Button cancelBT;
    @FXML
    private Button enterBT;
    @FXML
    private PasswordField oldPass;
    @FXML
    private PasswordField newPass;
    @FXML
    private PasswordField newPassCon;

    Preferences userPreferences = Preferences.userRoot();
    long id = userPreferences.getLong("currentUser", 0);
    String role;
    Teacher currentTeacher;
    Student currentStudent;
    public void initialize(URL url, ResourceBundle rb) {
        if (id % 200000 !=1) {
             currentTeacher = getObjTeacher(id);
            role="teacher";
            enterBT.setOnAction(e -> {
                editPass();
            });
            cancelBT.setOnAction(e -> {
                try {
                    jumpBackTeacher();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

        } else {
             currentStudent = getObjStudent(id);
            role="student";

            enterBT.setOnAction(e -> {
                editPass();
            });
            cancelBT.setOnAction(e -> {
                try {
                    jumpBackStudent();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        }
    }

    public void editPass() {
        String oldPassIn=oldPass.getText();
        String newPassIn=newPass.getText();
        String newPassConIn=newPassCon.getText();
        if(oldPassIn.isEmpty()||newPassIn.isEmpty()||newPassConIn.isEmpty())popUp(false,"Empty","Please enter all Field");
        else if(newPassConIn.equals(newPassIn)){
            System.out.println(role);
            if(role.equals("student")){
                if(oldPassIn.equals(currentStudent.getPassword())){
                    editPassword(currentStudent.getId(),newPassIn);
                    try {
                        jumpBackStudent();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else popUp(false,"old password incorrect","old password incorrect please try again");
            }
            else {
                if(oldPassIn.equals(currentTeacher.getPassword())){
                    editPassword(currentTeacher.getId(),newPassIn);
                    try {
                        jumpBackTeacher();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else popUp(false,"old password incorrect","old password incorrect please try again");
            }
        }else if(!newPassConIn.equals(newPassIn)) popUp(false,"password not math","new password & confirm new password not match");

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
    //    ============DB=======================================
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

    public static classss.Teacher getObjTeacher(long id_tea) {
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

    public static void editPassword(long id, String password) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        if (id % 200000 == 1) {
            String sql1 = "SELECT c FROM Student c Where c.id = " + id + "";
            TypedQuery<classss.Student> query1 = em.createQuery(sql1, classss.Student.class);
            List<classss.Student> results = query1.getResultList();
            em.getTransaction().begin();
            results.get(0).setPassword(password);
        } else {
            String sql1 = "SELECT c FROM Teacher c Where c.id = " + id + "";
            TypedQuery<classss.Teacher> query1 = em.createQuery(sql1, classss.Teacher.class);
            List<classss.Teacher> results = query1.getResultList();
            em.getTransaction().begin();
            results.get(0).setPassword(password);
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    //    ======================jump===============================================
    public void jumpBackStudent() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/enroll.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        enrollController controller = fxmlLoader.<enrollController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpBackTeacher() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/opencourse_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        opencourse_teacherController controller = fxmlLoader.<opencourse_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
}
