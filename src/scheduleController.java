import classss.Student;
import classss.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class scheduleController implements Initializable {
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
    private GridPane schedulePane;

    Preferences userPreferences = Preferences.userRoot();
    long id = userPreferences.getLong("currentUser", 0);
    Student currentStudent = getObjStudent(id);
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
        schedulePane.setMinSize(schedulePane.getMinWidth(), 0);
        createBlankSchedule();

        String[] topic = {"Day/Time", "Mornining 9:00-12:00 ", "Afternoon 13:00-16:00", "Evening 16:30-17:30"};
        int numCol = 4;
        double wScore = schedulePane.getPrefWidth();
        double wLable = wScore / numCol;
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], schedulePane.getPrefHeight()/8, wLable, 0);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
            schedulePane.add(topic_text, i, 0 );
        }
        for (Subject a : currentStudent.getSubject()) {
            Label topic_text = createLable(a.getName(), schedulePane.getPrefHeight()/8, wLable, 0);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
            int col=0,row=0;
            if(a.getTime().equals("Morning"))col=1;
            else if(a.getTime().equals("Afternoon"))col=2;
            else if(a.getTime().equals("Evening"))col=3;
            String []day={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
            for (int k=0;k<7;k++){
                if (a.getDay().equals(day[k])){
                    row=k;
                    break;
                }
            }
            schedulePane.add(topic_text, col, row+1);
        }
    }
    public void createBlankSchedule(){
        int numCol = 4;
        double wScore = schedulePane.getPrefWidth();
        double wLable = wScore / numCol;
        String []day={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        String []color={":#ffd656",":#ff8282",":#68ca7b",":#ee6422",":#3e77e9",":#644ca2",":#ff5952"};
        for (int i=0;i<4;i++){
            for (int j=0;j<8;j++){
                Label topic_text = createLable("", schedulePane.getPrefHeight()/8, wLable, 0);
                topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15; -fx-background-color: white;");
                if(i==0&&j>0){
                    topic_text.setText(day[j-1]);
                    topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15; -fx-background-color"+color[j-1]);
                }
                if(j==0)topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15; -fx-background-color:#ffdbb8");
                schedulePane.add(topic_text,i,j);
            }
        }

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

    public void jumpSchedule() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/schedule.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        scheduleController controller = fxmlLoader.<scheduleController>getController();
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
