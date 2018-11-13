import classss.Student;
import classss.Subject;
import component.passwordDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
    long id=userPreferences.getLong("currentUser",0);
    Student currentStudent =getObjStudent(id);
    List<Subject> listAllSubject=getAllSubject();

    public void initialize(URL url, ResourceBundle rb) {
//    show info of current user---------------------------------------
        nameLB.setText(currentStudent.getName());
        surnameLB.setText(currentStudent.getSurname());
        contactLB.setText(currentStudent.getPhonenumber());
        ageLB.setText(currentStudent.getBirthday());
        emailLB.setText(currentStudent.getEmail());
        telLB.setText(currentStudent.getPhonenumber());
        yearLB.setText(Integer.toString(currentStudent.getYear_of_study()));
        facultyLB.setText(currentStudent.getFaculty());

        for (Subject a:listAllSubject){
            System.out.println(a.getName());
        }

        GridPane gridpane = new GridPane();
        gridpane.setMinSize(scrollPane1.getMinWidth(), 0);
        gridpane.setStyle("-fx-border-color:black; -fx-alignment:center;");
        scrollPane1.setContent(gridpane);
        gridpane.add(createHeader(), 1, 1);

        for (int i = 0; i < 5; i++) {

            double wScore = scrollPane1.getPrefWidth();
            double wLable = wScore / 4;
            Pane panebtn = createPanebutton("eieiei",123);
            gridpane.add(createPane( i, "Zaa"), 1, i+2);
            gridpane.add(panebtn, 2, i+2);

        }


        gridpane = new GridPane();
        gridpane.setMinSize(scrollPane2.getMinWidth(), 0);
        scrollPane2.setContent(gridpane);
        gridpane.add(createHeader(), 1, 1);
        for (int i = 0; i < 100; i++) {
            gridpane.add(createPane( i, "Zaa"), 1, i+2);
        }

    }
    public Pane createHeader() {
        Pane pane = new Pane();
        double wScore = scrollPane2.getPrefWidth();
        double wLable = wScore / 4;
        pane.setMinSize(100, 25);
        Label topic_header = createLable("ID", 25, wLable, 0);
        Label score_header = createLable("Subject", 25, wLable, wLable);
        Label maxscore_header = createLable("eiei", 25, wLable, wLable * 2);
        pane.getChildren().addAll(topic_header, score_header, maxscore_header);
        return pane;
    }

    public Button createButton(String txt,int subject) {
        Button btn = new Button();
        btn.setOnAction(event -> {
            createDialog(subject);
        });
        btn.setText("click");
        btn.setUserData(txt);

        return btn;
    }

    public Pane createPanebutton (String txt , int subject){
        Pane pane = new Pane();
        double wScore = scrollPane1.getPrefWidth();
        double wLable = wScore / 4;

        Button btn1 = createButton("Eieieiei",123);
        btn1.setStyle("-fx-border-color:black; -fx-alignment:center;");
        btn1.setLayoutX(wLable);
        btn1.setMinSize(100 , 25);
        btn1.setLayoutX(5);
        btn1.setLayoutY(5);

        pane.getChildren().addAll(btn1);

        return pane;
    }

    public Pane createPane(int id, String teacher) {
        Pane pane = new Pane();
        double wScore = scrollPane1.getPrefWidth();
        double wLable = wScore / 4;

        pane.setMinSize(100, 25);
        Label topic_text = createLable("topic", 25, wLable, 0);
        Label score_text = createLable("topic", 25, wLable, wLable);
        Label maxscore_text = createLable("topic", 25, wLable, wLable * 2);




        pane.getChildren().addAll(topic_text, score_text, maxscore_text);

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

    public void createDialog(int id) {
        passwordDialog pd = new passwordDialog();
        Optional<String> result = pd.showAndWait();
//        result.ifPresent(password -> System.out.println(password));
        System.out.println(id);
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
    public void jumpChangePass() throws  IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/changePass.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        changePassController controller = fxmlLoader.<changePassController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    public void jumpEditProfile() throws IOException{
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
    public static List<Subject> getAllSubject(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql2 = "SELECT c FROM Subject c ";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();
        return results2;
    }
}
