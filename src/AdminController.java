import classss.Subject;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class AdminController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private ToggleButton enableBT;
    @FXML
    private ScrollPane scrollPane;

    Preferences userPreferences = Preferences.userRoot();
    Boolean enable;
    List<Subject> allSubject=getNotApprove();

    public void initialize(URL url, ResourceBundle rb) {
        update();
    }

    public void update() {
        enable = userPreferences.getBoolean("Enable", true);
        if (enable)enableBT.setText("Enable");
        else enableBT.setText("Disable");

        GridPane gridpane = new GridPane();
        gridpane.setMinSize(scrollPane.getMinWidth(), 0);
        gridpane.setStyle("-fx-border-color:black; -fx-alignment:center;");
        scrollPane.setContent(gridpane);
        gridpane.add(createHeader(), 1, 1);
        int i = 0;
        for (Subject a : allSubject) {
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
        double wLable = wScore / numCol;
        pane.setMinSize(100, 25);
        String []topic={"ID","Subject","Teacher","Description","Approve","",""};
        for(int i=0;i<numCol;i++){
            Label topic_text = createLable(topic[i], 25, wLable, wLable*i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
            pane.getChildren().add(topic_text);
        }
        return pane;
    }


    public Pane createPane(int id, Subject subject) {
        Pane pane = new Pane();
        int numCol=5;
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / numCol;

        pane.setMinSize(100, 25);
        String []topic={subject.getId_sub()+"",subject.getName(),subject.getTeacher().getName() + " " + subject.getTeacher().getSurname(),"",""};
        for(int i=0;i<numCol;i++){
            Label topic_text = createLable(topic[i], 25, wLable, wLable*i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
            pane.getChildren().add(topic_text);
        }
        Button btn1 = createDesBT("description", subject.getId_sub());
        btn1.setLayoutX(wLable * 3+ 20);
        pane.getChildren().addAll(btn1);

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
        Subject foundedSubject = getSubject(id);
        String info = "Name: " + foundedSubject.getName() + "\n" +
                "Teacher: " + foundedSubject.getTeacher() + "\n" +
                "Description: " + foundedSubject.getDiscription() + "\n" +
                "Time: " + foundedSubject.getTime() + "\n" +
                "Teaching day: " + foundedSubject.getDay()+"\n"+
                "Student: "+foundedSubject.getStudentNum()+"/"+foundedSubject.getNo_student();
        popUp(true,"Course Info",info);
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
    public void enable() {
        if (enable) {
            userPreferences.putBoolean("Enable", false);
            update();

        } else {
            userPreferences.putBoolean("Enable", true);
            update();

        }
    }

    public static List<Subject> getNotApprove() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql2 = "SELECT c FROM Subject c Where c.approve =" + false + "";
        TypedQuery<Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();
        return results2;
    }
    public static void setApprove(long id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Subject c Where c.id_sub = " + id+"" ;
        TypedQuery<classss.Subject> query1 = em.createQuery(sql1, classss.Subject.class);
        List<classss.Subject> results = query1.getResultList();
        em.getTransaction().begin();
        results.get(0).setApprove(false);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public static Subject getSubject(long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql2 = "SELECT c FROM Subject c Where c.id_sub =" + id + "";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();
        return results2.get(0);
    }
    public void jumpRegister_teacher() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/register_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        register_teacherController controller = fxmlLoader.<register_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpBack() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
}
