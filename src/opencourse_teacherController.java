import classss.Student;
import classss.Subject;
import classss.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class opencourse_teacherController implements Initializable {
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
    private Label posLB;
    //------------------------------------------------------------------------
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
    @FXML
    private ScrollPane scrollPane;

    Preferences userPreferences = Preferences.userRoot();
    long id = userPreferences.getLong("currentUser", 0);
    Teacher currentTeacher = getObjTeacher(id);

    public void initialize(URL url, ResourceBundle rb) {
        updateScreen();
    }

    public void updateScreen() {
        department.getItems().addAll("Computer Engineering");
        department.getSelectionModel().selectFirst();
        sessionTime.getItems().addAll("Morning", "Afternoon", "Evening");
        sessionTime.getSelectionModel().selectFirst();
        teachingDate.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        teachingDate.getSelectionModel().selectFirst();
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
        GridPane gridpane = new GridPane();
        gridpane.setMinSize(scrollPane.getMinWidth(), 0);
        gridpane.setStyle("-fx-border-color:black; -fx-alignment:center;");
        scrollPane.setContent(gridpane);
        gridpane.add(createHeader(), 1, 1);
        int i = 0;
        for (Subject a : currentTeacher.getSubjects()) {
            i++;
            System.out.println(a.getId_sub());
            double wScore = scrollPane.getPrefWidth();
            double wLable = wScore / 4;
            gridpane.add(createPane(i, a), 1, i + 2);
        }
    }

    public Pane createHeader() {
        Pane pane = new Pane();
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / 5;
        pane.setMinSize(100, 25);
        Label topic_header = createLable("ID", 25, wLable, 0);
        Label score_header = createLable("Subject", 25, wLable, wLable);
        Label maxscore_header = createLable("Time", 25, wLable, wLable * 2);
        Label empty = createLable("Day", 25, wLable, wLable * 3);
        Label empty2 = createLable("Description", 25, wLable, wLable * 4);
        topic_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        score_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        maxscore_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        empty.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        empty2.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");

        pane.getChildren().addAll(topic_header, score_header, maxscore_header, empty, empty2);
        return pane;
    }


    public Pane createPane(int id, Subject subject) {
        Pane pane = new Pane();
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / 5;

        pane.setMinSize(100, 25);

        Label subjectID_text = createLable(subject.getId_sub() + "", 25, wLable, 0);
        Label subjectName_text = createLable(subject.getName(), 25, wLable, wLable);
        Label time_text = createLable(subject.getTime(), 25, wLable, wLable * 2);
        Label gay_text = createLable(subject.getDay(), 25, wLable, wLable * 3);
        Label empty2 = createLable("", 25, wLable, wLable * 4);
//      set style
        subjectID_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        subjectName_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        time_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        gay_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        empty2.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");

        Button btn1 = createDesBT("Description", subject.getId_sub());
        btn1.setStyle("-fx-alignment:center; -fx-font-size: 10");
        btn1.setLayoutX(wLable * 4 + 20);
        btn1.setMinSize(50, 10);

        pane.getChildren().addAll(subjectID_text, subjectName_text, time_text, gay_text, empty2, btn1);

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


    public void openSubject() {
        String name = subjectName.getText();
        String time = (String) sessionTime.getValue();
        String date = (String) teachingDate.getValue();
        String des = description.getText();
        Boolean dup = false;
        if (subjectName.getText().isEmpty() || ((String) sessionTime.getValue()).isEmpty() || ((String) teachingDate.getValue()).isEmpty() || description.getText().isEmpty()) {
            popUp(false, "Empty", "please enter all field");
            return;
        }
        for (Subject temp : currentTeacher.getSubjects()) {
            if (temp.getName().equals(name) && temp.getDay().equals(date) && temp.getTime().equals(time)) {
                dup = true;
            }
        }
        if (dup == true) popUp(false, "Subject Duplicated", "Already Subject");
        else {
            openSbuject((int) currentTeacher.getId(), createSubject(name, 40, time, date, des));
            popUp(true, "Success", "Open Subject Success");
        }
        updateScreen();

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
    public static Subject getSubject(long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql2 = "SELECT c FROM Subject c Where c.id_sub =" + id + "";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();
        return results2.get(0);
    }
    public static void openSbuject(int id_tea, int id_sub) {
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
//    =============================jump============================================

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
    public void jumpEditProfile() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/editProfile_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        editProfile_teacherController controller = fxmlLoader.<editProfile_teacherController>getController();
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
