import classss.Announcement;
import classss.Subject;
import classss.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.xml.soap.Text;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class createAnnounceController implements Initializable {

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
    @FXML
    private Label telLB;
    @FXML
    private Label posLB;
    @FXML
    private AnchorPane backpane;
    @FXML
    private ComboBox subjectSelector;
    @FXML
    private TextField titleIn;
    @FXML
    private ComboBox typeIn;
    @FXML
    private DatePicker dateIn;
    @FXML
    private TextArea infoIn;
    @FXML
    private ScrollPane scrollPane;


    Preferences userPreferences = Preferences.userRoot();
    long id = userPreferences.getLong("currentUser", 0);
    Teacher currentTeacher = getObjTeacher(id);

    public void initialize(URL url, ResourceBundle rb) {
        typeIn.getItems().addAll("Important", "class", "general");
        subjectSelector.getItems().clear();
        for (Subject temp : currentTeacher.getSubjects()) {
            subjectSelector.getItems().add(temp.getId_sub() + " " + temp.getName());
        }
        updateScreen();

    }
    public void updateScreen() {

        currentTeacher = getObjTeacher(id);
        //    show info of current user---------------------------------------
        nameLB.setText(currentTeacher.getName());
        surnameLB.setText(currentTeacher.getSurname());
        contactLB.setText(currentTeacher.getPhonenumber());
        ageLB.setText(currentTeacher.getBirthday());
        emailLB.setText(currentTeacher.getEmail());
        telLB.setText(currentTeacher.getPhonenumber());
        posLB.setText(currentTeacher.getPost());

        System.out.println("Updateddddddddddddddddddddddddddddddddddddddd");
        //    show info of current user---------------------------------------

    }


    public void createAnnounce() {
        long subjectID;
        String title = titleIn.getText();
        String type = (String) typeIn.getValue();
        LocalDate date = dateIn.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        String day = date.format(formatter);
        String info = infoIn.getText();

        String id = (String) subjectSelector.getValue();
        id = id.substring(0, 4);
        subjectID = Long.parseLong(id);
        addAnnouncement(subjectID, type, info, title, day);
        popUp(true,"Success","Create new announce");
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

    public static void addAnnouncement(long id_sub, String type, String info, String title, String date) {
        long id;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Subject c Where c.id_sub = " + id_sub + "";
        TypedQuery<classss.Subject> query1 = em.createQuery(sql1, classss.Subject.class);
        List<classss.Subject> results1 = query1.getResultList();


        em.getTransaction().begin();
        Announcement a = new Announcement(type, info, title, date);
        em.persist(a);
        em.getTransaction().commit();
        id = a.getId();

        String sql2 = "SELECT c FROM Announcement c Where c.id = " + id + "";
        TypedQuery<classss.Announcement> query2 = em.createQuery(sql2, classss.Announcement.class);
        List<classss.Announcement> results2 = query2.getResultList();

        em.getTransaction().begin();
        results1.get(0).addAnnouncement(results2.get(0));
        em.getTransaction().commit();
        em.close();
        emf.close();
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


    //    jump=======================================================================================
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/editProfile_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        editProfile_teacherController controller = fxmlLoader.<editProfile_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

}
