import classss.Announcement;
import classss.Student;
import classss.Subject;
import classss.Teacher;
import component.passwordDialog;
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
import java.util.*;

public class AdminViewAccountController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ScrollPane scrollPane2;

    List<Student> allStudent = new ArrayList<>();
    List<Teacher> allTeacher = new ArrayList<>();

    public void initialize(URL url, ResourceBundle rb) {
        update();
    }

    public void update() {
        allStudent = getAllStudent();
        allTeacher = getAllTeacher();
        GridPane gridpane = new GridPane();
        gridpane.setMinSize(scrollPane.getMinWidth(), 0);
        gridpane.setStyle("-fx-border-color:black; -fx-alignment:center;");
        scrollPane.setContent(gridpane);
        gridpane.add(createHeader(), 1, 1);
        int i = 0;
        for (Student a : allStudent) {
            gridpane.add(createPane(a), 1, i + 2);
            i++;
        }
//        allSubject.sort(Comparator.comparing(Subject::getMidtermExam));
        gridpane = new GridPane();
        gridpane.setMinSize(scrollPane2.getMinWidth(), 0);
        gridpane.setStyle("-fx-border-color:black; -fx-alignment:center;");
        gridpane.add(createHeader(), 1, 1);
        scrollPane2.setContent(gridpane);
        i = 0;
        for (Teacher a : allTeacher) {
            gridpane.add(createPane2(a), 1, i + 2);
            i++;
        }


    }

    public Pane createHeader() {
        Pane pane = new Pane();
        int numCol = 4;
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / numCol;
        pane.setMinSize(100, 25);
        String[] topic = {"ID", "Name", "Surname", "Info"};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
            pane.getChildren().add(topic_text);
        }
        return pane;
    }

    public Pane createPane(Student student) {
        Pane pane = new Pane();
        int numCol = 4;
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / numCol;

        pane.setMinSize(100, 25);
        String[] topic = {student.getId() + "", student.getName(), student.getSurname(), ""};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
            pane.getChildren().add(topic_text);
        }
        Button btn1 = createInfoBT("Info", student);
        btn1.setLayoutX(wLable * 3 + 50);
        pane.getChildren().addAll(btn1);

        return pane;
    }

    public Pane createPane2(Teacher teacher) {
        Pane pane = new Pane();
        int numCol = 4;
        double wScore = scrollPane2.getPrefWidth();
        double wLable = wScore / numCol;
        String[] topic = {teacher.getId() + "", teacher.getName(), teacher.getSurname(), ""};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
            pane.getChildren().add(topic_text);
        }
        Button btn1 = createInfoBT2("Info", teacher);
        btn1.setLayoutX(wLable * 3 + 50);
        pane.getChildren().addAll(btn1);

        return pane;
    }

    public Button createInfoBT(String txt, Student student) {
        String info = student.getName() + " " + student.getSurname() + "\n" +
                "BirthDay: " + student.getBirthday() + "\n" +
                "Tel.: " + student.getPhonenumber() + "\n" +
                "Email: " + student.getEmail() + "\n" +
                "Year: " + student.getYear_of_study() + "\n" +
                "Faculty: " + student.getFaculty();
        Button btn = new Button(txt);
        btn.setOnAction(event -> {
            popUp(true, "Student Info", info);
        });
        return btn;
    }

    public Button createInfoBT2(String txt, Teacher teacher) {
        String info = teacher.getName() + " " + teacher.getSurname() + "\n" +
                "BirthDay: " + teacher.getBirthday() + "\n" +
                "Email: " + teacher.getEmail() + "\n" +
                "Tel.: " + teacher.getPhonenumber() + "\n" +
                "Position: " + teacher.getPost();
        Button btn = new Button(txt);
        btn.setOnAction(event -> {
            popUp(true, "Student Info", info);
        });
        return btn;
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

    //    ===========================================DB==============================================================
    public static List<Student> getAllStudent() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql2 = "SELECT c FROM Student c ";
        TypedQuery<classss.Student> query2 = em.createQuery(sql2, classss.Student.class);
        List<classss.Student> results2 = query2.getResultList();
        return results2;
    }

    public static List<Teacher> getAllTeacher() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql2 = "SELECT c FROM Teacher c ";
        TypedQuery<classss.Teacher> query2 = em.createQuery(sql2, classss.Teacher.class);
        List<classss.Teacher> results2 = query2.getResultList();
        return results2;
    }

    //    =======================================jump======================================================

    public void jumpBack() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/AdminHome.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        AdminController controller = fxmlLoader.<AdminController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
}
