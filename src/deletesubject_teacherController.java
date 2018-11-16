import classss.Subject;
import classss.Teacher;
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

public class deletesubject_teacherController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private ScrollPane scrollPane;
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

    Preferences userPreferences = Preferences.userRoot();
    long id = userPreferences.getLong("currentUser", 0);
    Teacher currentTeacher = getObjTeacher(id);

    public void initialize(URL url, ResourceBundle rb) {
        updateScreen();

    }

    public void updateScreen() {
        currentTeacher = getObjTeacher(id);
        //    show info of current user---------------------------------------
        nameLB.setText(currentTeacher.getName());
        surnameLB.setText(currentTeacher.getSurname());
        contactLB.setText(currentTeacher.getPhonenumber());
        ageLB.setText(currentTeacher.getBirthday());
        ageLB.setText(currentTeacher.getBirthday());
        emailLB.setText(currentTeacher.getEmail());
        telLB.setText(currentTeacher.getPhonenumber());
        posLB.setText(currentTeacher.getPost());
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
        int numCol=5;
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / 5;
        pane.setMinSize(100, 25);
        String[] topic = {"ID","Subject","Teacher","Description","Delete"};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15;-fx-background-color: #ffd410; ");
            pane.getChildren().add(topic_text);
        }
        return pane;
    }


    public Pane createPane(int id, Subject subject) {
        Pane pane = new Pane();
        int numCol=5;
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / 5;

        pane.setMinSize(100, 25);

        String[] topic = {subject.getId_sub() + "",subject.getName(),subject.getTeacher().getName() + " " + subject.getTeacher().getSurname(),"",""};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15;-fx-background-color: white; ");
            pane.getChildren().add(topic_text);
        }

        Button btn1 = createDesBT("Description", subject.getId_sub());
        btn1.setStyle("-fx-alignment:center; -fx-font-size: 10");
        btn1.setLayoutX(wLable * 3 + 20);
        btn1.setMinSize(50, 10);

        Button btn2 = createDelBT("Delete", subject.getId_sub());
        btn2.setStyle("-fx-alignment:center; -fx-font-size: 10");
        btn2.setLayoutX(wLable * 4 + 25);
        btn2.setMinSize(50, 10);

        pane.getChildren().addAll(btn1, btn2);

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

    public Button createDelBT(String txt, long subject) {
        Button btn = new Button(txt);
        btn.setOnAction(event -> {
            createDelDialog(subject);
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

    public void createDelDialog(long id) {
        passwordDialog pd = new passwordDialog();
        Optional<String> result = pd.showAndWait();
        result.ifPresent(password -> {
            if (password.equals(currentTeacher.getPassword())) {
//              delete Subject
                delSubject((int) currentTeacher.getId(),(int)id);
                deleteScore((int)id);
                popUp(true,"Delete Subject","Delete Subject Success");
                updateScreen();
            } else {
                popUp(false,"Wrong password","Please try again");

            }
        });
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
    public static void delSubject(int id_tea,int id_sub){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

        String sql2 = "SELECT c FROM Subject c Where c.id_sub =" + id_sub + "";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();

        for(classss.Student a:results2.get(0).getStudent()){
            for(classss.Subject b:a.getSubject()){
                if(b.getId_sub()==id_sub){
                    a.getSubject().remove(b);
                    break;
                }
            }
        }

        em.getTransaction().begin();
        em.remove(results2.get(0));
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
    public static void deleteScore(int id_sub){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql2 = "SELECT c FROM Score c Where c.IdSubject =" + id_sub;
        TypedQuery<classss.Score> query2 = em.createQuery(sql2, classss.Score.class);
        List<classss.Score> results2 = query2.getResultList();
        em.getTransaction().begin();
        int size=results2.size();
        for (int i=0;i<size;i++){
            em.remove(results2.get(0));
            results2.remove(0);
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
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
    public void jumpCreateAnnounce() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/createAnnounce_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        createAnnounceController controller = fxmlLoader.<createAnnounceController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    public void jumpViewStudent() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/viewStudent.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        viewStudentController controller = fxmlLoader.<viewStudentController>getController();
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
