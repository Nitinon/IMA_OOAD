import classss.Student;
import classss.Subject;
import component.passwordDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class enrollController implements Initializable {
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
    private ScrollPane scrollPane1;
    @FXML
    private ScrollPane scrollPane2;
    //    get list of all subject
//    get current id-------------------------------------------------
    Preferences userPreferences = Preferences.userRoot();
    long id = userPreferences.getLong("currentUser", 0);
    Student currentStudent = getObjStudent(id);
    List<Subject> listAllSubject = getAllSubject();

    public void initialize(URL url, ResourceBundle rb) {
        updateScreen();
    }
    public void updateScreen(){
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

        GridPane gridpane = new GridPane();
        gridpane.setMinSize(scrollPane1.getMinWidth(), 0);
        gridpane.setStyle("-fx-border-color:black; -fx-alignment:center;");
        scrollPane1.setContent(gridpane);
        gridpane.add(createHeader2(), 1, 1);
        int i = 0;
        for (Subject a : currentStudent.getSubject()) {
            i++;
            System.out.println(a.getId_sub());
            double wScore = scrollPane2.getPrefWidth();
            double wLable = wScore / 4;
            gridpane.add(createPane2(i, a), 1, i + 2);
        }
        System.out.println("=============================================");
        gridpane = new GridPane();
        gridpane.setMinSize(scrollPane2.getMinWidth(), 0);
        gridpane.setStyle("-fx-border-color:black; -fx-alignment:center;");
        scrollPane2.setContent(gridpane);
        gridpane.add(createHeader(), 1, 1);
        i = 0;
        for (Subject a : listAllSubject) {
            i++;
            if(!findSubject(a)) {
                System.out.println(a.getId_sub());
                double wScore = scrollPane2.getPrefWidth();
                double wLable = wScore / 4;
                gridpane.add(createPane(i, a), 1, i + 2);
            }
        }
    }
    public Boolean findSubject(Subject sub){
        for (Subject a : currentStudent.getSubject()) {
            if(a.getId_sub()==sub.getId_sub())
            return true;
        }
        return false;
    }
    public Pane createHeader() {
        Pane pane = new Pane();
        double wScore = scrollPane2.getPrefWidth();
        double wLable = wScore / 5;
        pane.setMinSize(100, 25);
        Label topic_header = createLable("ID", 25, wLable, 0);
        Label score_header = createLable("Subject", 25, wLable, wLable);
        Label maxscore_header = createLable("Teacher", 25, wLable, wLable * 2);
        Label empty = createLable("Description", 25, wLable, wLable * 3);
        Label empty2 = createLable("Enroll", 25, wLable, wLable * 4);
        topic_header.setStyle("-fx-border-color:black;-fx-background-color: pink; -fx-alignment:center;-fx-font-size:15 ");
        score_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        maxscore_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        empty.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        empty2.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");

        pane.getChildren().addAll(topic_header, score_header, maxscore_header, empty, empty2);
        return pane;
    }


    public Pane createPane(int id, Subject subject) {
        Pane pane = new Pane();
        double wScore = scrollPane1.getPrefWidth();
        double wLable = wScore / 5;

        pane.setMinSize(100, 25);

        Label topic_text = createLable(subject.getId_sub() + "", 25, wLable, 0);
        Label score_text = createLable(subject.getName(), 25, wLable, wLable);
        Label maxscore_text = createLable(subject.getTeacher().getName() + " " + subject.getTeacher().getSurname(), 25, wLable, wLable * 2);
        Label empty1 = createLable("", 25, wLable, wLable * 3);
        Label empty2 = createLable("", 25, wLable, wLable * 4);
//      set style
        topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        score_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        maxscore_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        empty1.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        empty2.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");

        Button btn1 = createDesBT("description", subject.getId_sub());
        btn1.setStyle("-fx-alignment:center; -fx-font-size: 10");
        btn1.setLayoutX(wLable * 3 + 20);
        btn1.setMinSize(50, 10);

        Button btn2 = createEnrollBT("enroll", subject.getId_sub());
        btn2.setStyle("-fx-alignment:center; -fx-font-size: 10");
        btn2.setLayoutX(wLable * 4 + 25);
        btn2.setMinSize(50, 10);

        pane.getChildren().addAll(topic_text, score_text, maxscore_text, empty1, empty2, btn1, btn2);

        return pane;
    }

    public Pane createHeader2() {
        Pane pane = new Pane();
        double wScore = scrollPane2.getPrefWidth();
        double wLable = wScore / 4;
        pane.setMinSize(100, 25);
        Label topic_header = createLable("ID", 25, wLable, 0);
        Label score_header = createLable("Subject", 25, wLable, wLable);
        Label maxscore_header = createLable("Teacher", 25, wLable, wLable * 2);
        Label empty = createLable("Description", 25, wLable, wLable * 3);
        topic_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        score_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        maxscore_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        empty.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");

        pane.getChildren().addAll(topic_header, score_header, maxscore_header, empty );
        return pane;
    }


    public Pane createPane2(int id, Subject subject) {
        Pane pane = new Pane();
        double wScore = scrollPane1.getPrefWidth();
        double wLable = wScore / 4;

        pane.setMinSize(100, 25);

        Label topic_text = createLable(subject.getId_sub() + "", 25, wLable, 0);
        Label score_text = createLable(subject.getName(), 25, wLable, wLable);
        Label maxscore_text = createLable(subject.getTeacher().getName() + " " + subject.getTeacher().getSurname(), 25, wLable, wLable * 2);
        Label empty1 = createLable("", 25, wLable, wLable * 3);
//      set style
        topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        score_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        maxscore_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        empty1.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15;");

        Button btn1 = createDesBT("description", subject.getId_sub());
//        btn1.setStyle("-fx-alignment:center; -fx-font-size: 10 ; -fx-pref-height: 10px; -fx-pref-width: 125px;\n" +
//                "\t-fx-background-color: rgb(250, 250, 250);-fx-border-color: black;-fx-hand");
        btn1.setLayoutX(wLable * 3+ 20);
        //btn1.setMinSize(50, 10);

        pane.getChildren().addAll(topic_text, score_text, maxscore_text, empty1,  btn1);

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

    public Button createDesBT(String txt, long subject) {
        Button btn = new Button(txt);

        btn.setOnAction(event -> {
            createDialog(subject);
        });
        return btn;
    }
    public Button createEnrollBT(String txt, long subject) {
        Button btn = new Button(txt);
        btn.setOnAction(event -> {
            createEnrollDialog(subject);
        });
        return btn;
    }
    public void createDialog(long id) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Course Info");

        Subject foundedSubject = getSubject(id);
        alert.setHeaderText("Info of " + foundedSubject.getName());
        String info = "Name: " + foundedSubject.getName() + "\n" +
                "Teacher: " + foundedSubject.getTeacher() + "\n" +
                "Description: " + foundedSubject.getDiscription() + "\n" +
                "Time: " + foundedSubject.getTime() + "\n" +
                "Teaching day: " + foundedSubject.getDay();
        alert.setContentText(info);
        alert.showAndWait();

    }

    public void createEnrollDialog(long id) {
        passwordDialog pd = new passwordDialog();
        Optional<String> result = pd.showAndWait();
        result.ifPresent(password -> {
            if (password.equals(currentStudent.getPassword())) {
                enroll(id);
            } else {
                System.out.println("Error");
            }
        });
    }
    public void enroll(long id) {
        System.out.println(id);
        enrollCourse((int)currentStudent.getId(), id);
        updateScreen();
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

    //    jump=======================================================================================
    public void jumpEnroll() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/enroll.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        enrollController controller = fxmlLoader.<enrollController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpChange() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/changeCourse.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        changeCourseController controller = fxmlLoader.<changeCourseController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpDrop() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/drop.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        dropController controller = fxmlLoader.<dropController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

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

    public void jumpLogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }


}
