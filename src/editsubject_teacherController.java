import classss.Subject;
import classss.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class editsubject_teacherController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private Label nameLB;
    @FXML
    private Label surnameLB;
    @FXML
    private Label ageLB;
    @FXML
    private Label contactLB;
    @FXML
    private Label emailLB;
    //------------------------------------------------------------------------
    @FXML
    private ComboBox subjectSelector;
    @FXML
    private TextField nameIn;
    @FXML
    private TextArea description;

    Preferences userPreferences = Preferences.userRoot();
    long id = userPreferences.getLong("currentUser", 0);
    Teacher currentTeacher = getObjTeacher(id);

    Subject subjectSelected;

    public void initialize(URL url, ResourceBundle rb) {
        updateScreen();
    }

    public void updateScreen() {
        currentTeacher = getObjTeacher(id);
//        System.out.println("Updateddddddddddddddddddddddddddddddddddddddd");
//        //    show info of current user---------------------------------------
//        nameLB.setText(currentStudent.getName());
//        surnameLB.setText(currentStudent.getSurname());
//        contactLB.setText(currentStudent.getPhonenumber());
//        ageLB.setText(currentStudent.getBirthday());
//        emailLB.setText(currentStudent.getEmail());
//        telLB.setText(currentStudent.getPhonenumber());
//        yearLB.setText(Integer.toString(currentStudent.getYear_of_study()));
//        facultyLB.setText(currentStudent.getFaculty());
        subjectSelector.getItems().clear();
        for (Subject temp : currentTeacher.getSubjects()) {
            subjectSelector.getItems().add(temp.getId_sub() + " " + temp.getName());
        }


    }

    public void selected() {
        if (subjectSelector.getValue() != null) {
            String id = (String) subjectSelector.getValue();
            id = id.substring(0, 4);
            long idSub = Long.parseLong(id);
            subjectSelected = getSubject(idSub);
            nameIn.setText(subjectSelected.getName());
            description.setText(subjectSelected.getDiscription());
        }
    }




    public void editSubject() {
        editCourse(subjectSelected.getId_sub(), nameIn.getText(), description.getText());
        updateScreen();
    }

    //    =================================DB=========================================================
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

    public static Subject getSubject(long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql2 = "SELECT c FROM Subject c Where c.id_sub =" + id + "";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();
        return results2.get(0);
    }

    public static void editCourse(long id, String name, String discription) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Subject c Where c.id_sub = " + id + "";
        TypedQuery<classss.Subject> query1 = em.createQuery(sql1, classss.Subject.class);
        List<classss.Subject> results = query1.getResultList();
        em.getTransaction().begin();
        results.get(0).setName(name);
        results.get(0).setDiscription(discription);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public static void createScore(int idStudent, int idSubject, String topic, int point,int max) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        classss.Score a = new classss.Score(idStudent, idSubject, topic, point,max);
        em.persist(a);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    //    =====================jump=============================
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
    public void jumpCreateAnnounce() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/createAnnounce_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        createAnnounceController controller = fxmlLoader.<createAnnounceController>getController();
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
    public void jumpChangePass() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/changePass.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        changePassController controller = fxmlLoader.<changePassController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    public void jumpEditProfile() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/editProfile.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        ediProfileController controller = fxmlLoader.<ediProfileController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

}
