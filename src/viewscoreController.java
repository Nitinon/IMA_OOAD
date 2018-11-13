import classss.Score;
import classss.Student;
import classss.Subject;
import com.sun.org.apache.xpath.internal.operations.And;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class viewscoreController implements Initializable {
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
    @FXML
    private Label telLB;
    @FXML
    private Label yearLB;
    @FXML
    private Label facultyLB;
    //------------------------------------------------------------------------
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ComboBox subjectSelector;

    Preferences userPreferences = Preferences.userRoot();
    long id = userPreferences.getLong("currentUser", 0);
    Student currentStudent;
    List<Score> listScore = new ArrayList<>();

    Subject subjectSelected;

    public void initialize(URL url, ResourceBundle rb) {
        for (Subject temp : currentStudent.getSubject()) {
            System.out.println(temp.getName());
            subjectSelector.getItems().add(temp.getId_sub() + " " + temp.getName());
        }
        updateScreen();
    }

    public void updateScreen() {
        currentStudent = getObjStudent(id);
        System.out.println("Updateddddddddddddddddddddddddddddddddddddddd");
        //    show info of current user---------------------------------------
        nameLB.setText(currentStudent.getName());
        surnameLB.setText(currentStudent.getSurname());
        contactLB.setText(currentStudent.getPhonenumber());
        ageLB.setText(currentStudent.getBirthday());
        emailLB.setText(currentStudent.getEmail());
        telLB.setText(currentStudent.getPhonenumber());
        yearLB.setText(Integer.toString(currentStudent.getYear_of_study()));
        facultyLB.setText(currentStudent.getFaculty());

        subjectSelector.getItems().clear();
        listScore = viewScoreIDnTopic((int) currentStudent.getId(), (String) subjectSelector.getValue());


        GridPane gridpane = new GridPane();
        gridpane.setMinSize(scrollPane.getMinWidth(), 0);
        gridpane.setStyle("-fx-border-color:black; -fx-alignment:center;");
        scrollPane.setContent(gridpane);
        gridpane.add(createHeader(), 1, 1);
        int i = 0;
        for (Score a : listScore) {
            i++;
            if (subjectSelected != null && a.getIdSubject() == subjectSelected.getId_sub()) {
                double wScore = scrollPane.getPrefWidth();
                double wLable = wScore / 4;
                gridpane.add(createPane(i, a), 1, i + 2);
            }
        }
    }

    public static List<Score> viewScoreIDnTopic(int stuID, String topic) {
        int score = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

//        String sql1 = "SELECT c FROM Score c Where c.IdStudent = " + stuID+ "AND c.Topic = "+topic;
        String sql1 = "SELECT c FROM Score c Where c.IdStudent = " + stuID;

        TypedQuery<classss.Score> query1 = em.createQuery(sql1, classss.Score.class);
        List<classss.Score> results = query1.getResultList();
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
        emf.close();
        return results;
    }

    public Boolean findSubject(Subject sub) {
        for (Subject a : currentStudent.getSubject()) {
            if (a.getId_sub() == sub.getId_sub())
                return true;
        }
        return false;
    }

    public void enterSubject() {
        listScore.clear();
        if (subjectSelector.getValue() != null) {
            String id = (String) subjectSelector.getValue();
            id = id.substring(0, 4);
            long idSub = Long.parseLong(id);
            subjectSelected = getSubject(idSub);
        }
        updateScreen();
    }

    public Pane createHeader() {
        Pane pane = new Pane();
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / 5;
        pane.setMinSize(100, 25);
        Label topic_header = createLable("ID", 25, wLable, 0);
        Label score_header = createLable("Subject", 25, wLable, wLable);
        Label maxscore_header = createLable("eiei", 25, wLable, wLable * 2);
        Label empty = createLable("description", 25, wLable, wLable * 3);
        Label empty2 = createLable("change", 25, wLable, wLable * 4);
        topic_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        score_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        maxscore_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        empty.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        empty2.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");

        pane.getChildren().addAll(topic_header, score_header, maxscore_header, empty, empty2);
        return pane;
    }


    public Pane createPane(int id, Score score) {
        Pane pane = new Pane();
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / 5;

        pane.setMinSize(100, 25);

        Label topic_text = createLable(score.getTopic() + "", 25, wLable, 0);
        Label score_text = createLable(score.getPoint() + "", 25, wLable, wLable);
        Label maxscore_text = createLable(score.getMax() + "", 25, wLable, wLable * 2);
        Label empty1 = createLable("", 25, wLable, wLable * 3);
        Label empty2 = createLable("", 25, wLable, wLable * 4);
//      set style
        topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        score_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        maxscore_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        empty1.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        empty2.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");


        pane.getChildren().addAll(topic_text, score_text, maxscore_text, empty1, empty2);

        return pane;
    }

    public Label createLable(String txt, double height, double width, double pos) {
        Label label = new Label(txt);
        label.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:20");
        label.setMinHeight(height);
        label.setMinWidth(width);
        label.setMaxWidth(width);
        label.setLayoutX(pos);
        return label;
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

    public static List<Subject> getAllSubject() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql2 = "SELECT c FROM Subject c ";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();
        return results2;
    }

    public static Subject getSubject(long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql2 = "SELECT c FROM Subject c Where c.id_sub =" + id + "";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();
        return results2.get(0);
    }

    public static void enrollCourse(int id_stu, long id_sub) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

        String sql1 = "SELECT c FROM Student c Where c.id =" + id_stu + "";
        TypedQuery<classss.Student> query1 = em.createQuery(sql1, classss.Student.class);
        List<classss.Student> results1 = query1.getResultList();

        String sql2 = "SELECT c FROM Subject c Where c.id_sub =" + id_sub + "";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();

        em.getTransaction().begin();
        results1.get(0).addSubject(results2.get(0));
        //em.persist(results1.get(0));
        //em.persist(results2.get(0));
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public static void dropSubject(int id_stu, int id_sub) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

        String sql1 = "SELECT c FROM Student c Where c.id =" + id_stu + "";
        TypedQuery<classss.Student> query1 = em.createQuery(sql1, classss.Student.class);
        List<classss.Student> results1 = query1.getResultList();

        String sql2 = "SELECT c FROM Subject c Where c.id_sub =" + id_sub + "";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();

        em.getTransaction().begin();

        for (classss.Subject a : results1.get(0).getSubject()) {
            if (a.getId_sub() == id_sub) {
                results1.get(0).getSubject().remove(a);
                break;
            }

        }
        for (classss.Student b : results2.get(0).getStudent()) {
            if (b.getId() == id_stu) {
                results2.get(0).getStudent().remove(b);
                break;
            }

        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    @FXML
    public void jumpEnroll() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/enroll.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        enrollController controller = fxmlLoader.<enrollController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    @FXML
    public void jumpChange() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/changeCourse.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        changeCourseController controller = fxmlLoader.<changeCourseController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    @FXML
    public void jumpDrop() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/drop.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        dropController controller = fxmlLoader.<dropController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    @FXML
    public void jumpView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/viewscore.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        viewscoreController controller = fxmlLoader.<viewscoreController>getController();
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

    public void jumpAnnounce() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/announcement.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        announcementController controller = fxmlLoader.<announcementController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    @FXML
    public void jumpLogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }


}