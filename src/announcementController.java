import classss.Announcement;
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
import javax.xml.soap.Text;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.YearMonth;
import java.util.prefs.Preferences;

public class announcementController implements Initializable {
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
    private GridPane announcepane;
    @FXML
    private Label announcetitle;

    Date date = new Date();

    int month = date.getMonth();
    int year = date.getYear();

    Button[][] aa = new Button[10][10];
    String[] dayy = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
    String[] monthh = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    //    get current id-------------------------------------------------
    Preferences userPreferences = Preferences.userRoot();
    long id = userPreferences.getLong("currentUser", 0);
    Student currentStudent = getObjStudent(id);

    public void initialize(URL url, ResourceBundle rb) {
        date.setYear(2018);
        year = date.getYear();
        //    show info of current user---------------------------------------
        nameLB.setText(currentStudent.getName());
        surnameLB.setText(currentStudent.getSurname());
        contactLB.setText(currentStudent.getPhonenumber());
        ageLB.setText(currentStudent.getBirthday());
        emailLB.setText(currentStudent.getEmail());
        telLB.setText(currentStudent.getPhonenumber());
        yearLB.setText(Integer.toString(currentStudent.getYear_of_study()));
        facultyLB.setText(currentStudent.getFaculty());

        initCalendar(month, year);


    }

    private void initCalendar(int month, int year) {
        Date date = new Date();
        int firstday = 1;
        date.setDate(0);
        date.setMonth(month);
        date.setYear(year);
        int day = date.getDay();
        int check = 0;
        YearMonth yearMonthObject = YearMonth.of(year, month + 1);
        int limit = yearMonthObject.lengthOfMonth();
        announcetitle.setText("Annoucement " + monthh[month] + " " + year);
        for (int k = 0; k < 7; k++) {
            announcepane.add(new Label(dayy[k]), k, 0);
        }
        for (int i = 1; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (j == day) {
                    check = 1;
                }
                if (check == 0) {
                    announcepane.add(createButton(0, 0, j, i), j, i);
                } else {
                    if (firstday < limit + 1) {
                        announcepane.add(createButton(firstday, month + 1, j, i), j, i);

                    } else {
                        announcepane.add(createButton(0, 0, j, i), j, i);
                    }
                    firstday++;
                }
            }
        }
    }

    private Button createButton(int day, int month, int j, int i) {
        String dateIn=day+"/"+month+"/"+year;
        aa[j][i] = new Button();
        aa[j][i].setMinHeight(60.0);
        aa[j][i].setMinWidth(80.0);
        if (day == 0) aa[j][i].setText(" ");
        else aa[j][i].setText(day + "");
        Boolean founded = findAnnounce(day, month, year, false);

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        String dateNow = date.format(formatter);
        String style="";
        if(dateNow.equals(dateIn))style="-fx-border-color: red; -fx-border-width: 4;";
        if (founded) {
            aa[j][i].setStyle("-fx-background-color: #7dff69; -fx-border-color: black; "+style);
            String finalStyle = style;
            aa[j][i].setOnMouseEntered(e -> aa[j][i].setStyle("-fx-background-color: #00bc00; -fx-border-color: black;"+ finalStyle));
            String finalStyle1 = style;
            aa[j][i].setOnMouseExited(e -> aa[j][i].setStyle("-fx-background-color: #7dff69; -fx-border-color: black; "+ finalStyle1));
        } else {
            aa[j][i].setStyle("-fx-background-color: white; -fx-border-color: black; "+style);
            String finalStyle2 = style;
            aa[j][i].setOnMouseExited(e -> aa[j][i].setStyle("-fx-background-color: white; -fx-border-color: black;"+ finalStyle2));
        }
        aa[j][i].setOnAction(e -> {
            findAnnounce(day, month, year, true);
        });

        return aa[j][i];
    }

    public boolean findAnnounce(int day, int month, int year, boolean print) {
        String strD="";
        if (day>9)strD=day+"";
        else strD="0"+day;
        String date = strD + "/" + month + "/" + year;
        ArrayList<Announcement> listAnnounce = new ArrayList<>();

        for (Subject temp : currentStudent.getSubject()) {
            for (Announcement a : temp.getAnnouncements()) {
                if (a.getDate().equals(date)) {
                    listAnnounce.add(a);
                }
            }
        }
        String allAnnounce="";
        if (listAnnounce.size() != 0) {
            for (Announcement aa : listAnnounce) {
                if (print == true){
                    System.out.println(aa);
                    String announce="Title: "+aa.getTitle()+" | Type: "+aa.getType()+"\n"+
                            "Subject: "+aa.getSubjectsss().getName()+"\n"+
                            "info: "+aa.getInfo()+"\n"+
                            "_______________________________________";
                    allAnnounce+=announce+"\n";
                }

            }
            if (print == true)
            popUp(true,date,allAnnounce);
            return true;
        } else {
            return false;
        }
    }

    public void nxtMonth() {
        if (month == 11) {
            month = 0;
            year++;
        } else {
            month++;
        }
        initCalendar(month, year);
    }

    public void preMonth() {
        if (month == 0) {
            month = 11;
            year--;
        } else {
            month--;
        }
        initCalendar(month, year);
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
    public void jumpLogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }


}