import classss.Student;
import classss.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
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

public class opencourse_teacherController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private TextField subjectName;
    @FXML
    private ComboBox sessionTime;
    @FXML
    private ComboBox teachingDate;
    @FXML
    private ComboBox department;
    @FXML
    private TextArea description;

    Preferences userPreferences = Preferences.userRoot();
    long id = userPreferences.getLong("currentUser", 0);
    Teacher currentTeacher = getObjTeacher(id);

    public void initialize(URL url, ResourceBundle rb) {
        department.getItems().addAll("Computer Engineering");
        department.getSelectionModel().selectFirst();
        sessionTime.getItems().addAll("Morning", "Afternoon", "Evening");
        sessionTime.getSelectionModel().selectFirst();
        teachingDate.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        teachingDate.getSelectionModel().selectFirst();
    }

    public void openSubject() {
        String name = subjectName.getText();
        String time = (String) sessionTime.getValue();
        String date = (String) teachingDate.getValue();
        String des = description.getText();
        openCourse((int)currentTeacher.getId(),createSubject(name,40,time,date,des));
        System.out.println("eieieieiei");
    }

    //    ==================DB====================
    public static classss.Teacher getObjTeacher(long id_tea) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Teacher c Where c.id =" + id_tea + "";
        TypedQuery<Teacher> query1 = em.createQuery(sql1, classss.Teacher.class);
        List<Teacher> results1 = query1.getResultList();
        if (results1.size() == 0) {
            return null;
        } else {
            return results1.get(0);
        }
    }
    public static void openCourse(int id_tea,int id_sub){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

        String sql1 = "SELECT c FROM Teacher c Where c.id =" + id_tea + "";
        TypedQuery<classss.Teacher> query1 = em.createQuery(sql1, classss.Teacher.class);
        List<classss.Teacher> results1 = query1.getResultList();

        String sql2 = "SELECT c FROM Subject c Where c.id_sub =" + id_sub + "";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();

        em.getTransaction().begin();
        results1.get(0).addSubjects(results2.get(0));
        //em.persist(results1.get(0));
        //em.persist(results2.get(0));
        // System.out.println(results1);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
    public static int createSubject(String name, int no_student, String time, String day, String description) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

        classss.Subject a = new classss.Subject(name, no_student, time, day, description);
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        em.getTransaction().begin();
        a.setId_sub(a.getId() + 1000);
        em.getTransaction().commit();
        em.close();
        emf.close();
        return (int) a.getId_sub();
    }

    public void jumpEnroll() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/opencourse_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        opencourse_teacherController controller = fxmlLoader.<opencourse_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpEditCourse() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/editsubject_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        editsubject_teacherController controller = fxmlLoader.<editsubject_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpDeleteCourse() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/deletesubject_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        deletesubject_teacherController controller = fxmlLoader.<deletesubject_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpEditScore() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/editscore_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        editscore_teacherController controller = fxmlLoader.<editscore_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpLogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

}
