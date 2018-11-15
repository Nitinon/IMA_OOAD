import classss.Announcement;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.prefs.Preferences;

public class AdminController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private ToggleButton enableBT;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ScrollPane scrollPane2;
    @FXML
    private ScrollPane scrollPane3;
    Preferences userPreferences = Preferences.userRoot();
    Boolean enable;
    List<Subject> allSubject;
    List<DatePicker> midtermList = new ArrayList<>();
    List<DatePicker> finalList = new ArrayList<>();
    List<ComboBox> listTimeMidterm = new ArrayList<>();
    List<ComboBox> listTimeFinal = new ArrayList<>();

    public void initialize(URL url, ResourceBundle rb) {
        update();
    }

    public void update() {
        allSubject = getApprove(false);

        enable = userPreferences.getBoolean("Enable", true);
        if (enable) enableBT.setText("Enable");
        else enableBT.setText("Disable");

        GridPane gridpane = new GridPane();
        gridpane.setMinSize(scrollPane.getMinWidth(), 0);
        gridpane.setStyle("-fx-border-color:black; -fx-alignment:center;");
        scrollPane.setContent(gridpane);
        gridpane.add(createHeader(), 1, 1);
        int i = 0;
        for (Subject a : allSubject) {
            gridpane.add(createPane(i, a), 1, i + 2);
            i++;
        }
        allSubject = getApprove(true);
//        allSubject.sort(Comparator.comparing(Subject::getMidtermExam));
        gridpane = new GridPane();
        gridpane.setMinSize(scrollPane2.getMinWidth(), 0);
        gridpane.setStyle("-fx-border-color:black; -fx-alignment:center;");
        gridpane.add(createHeader2(), 1, 1);
        scrollPane2.setContent(gridpane);
        i = 0;
        for (Subject a : allSubject) {
            gridpane.add(createPane2(a, "midterm"), 1, i + 2);
            i++;
        }
        gridpane = new GridPane();
        gridpane.setMinSize(scrollPane2.getMinWidth(), 0);
        gridpane.setStyle("-fx-border-color:black; -fx-alignment:center;");
        gridpane.add(createHeader2(), 1, 1);
        scrollPane3.setContent(gridpane);
        i = 0;
        for (Subject a : allSubject) {
            gridpane.add(createPane2(a, "final"), 1, i + 2);
            i++;
        }

    }

    public Pane createHeader() {
        Pane pane = new Pane();
        int numCol = 7;
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / numCol;
        pane.setMinSize(100, 25);
        String[] topic = {"Subject", "Midterm", "Time Midterm", "Final", "Time Final", "Description", "Approve", "", ""};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
            pane.getChildren().add(topic_text);
        }
        return pane;
    }

    public Pane createHeader2() {
        Pane pane = new Pane();
        int numCol = 4;
        double wScore = scrollPane2.getPrefWidth();
        double wLable = wScore / numCol;
        pane.setMinSize(100, 25);
        String[] topic = {"ID", "Subject name", "Time Exam", "Date"};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
            pane.getChildren().add(topic_text);
        }
        return pane;
    }

    public Pane createPane(int id, Subject subject) {
        Pane pane = new Pane();
        int numCol = 7;
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / numCol;

        pane.setMinSize(100, 25);
        String[] topic = {subject.getName(), "", "", "", "", "", ""};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
            pane.getChildren().add(topic_text);
        }
        DatePicker midtermExam = new DatePicker();
        midtermExam.setLayoutX(wLable * 1);
        midtermExam.setPrefWidth(wLable);
        midtermList.add(midtermExam);
//        --------------------------------
        DatePicker finalExam = new DatePicker();
        finalExam.setLayoutX(wLable * 3);
        finalExam.setPrefWidth(wLable);
        finalList.add(finalExam);

        ComboBox listTime = new ComboBox();
        listTime.getItems().addAll("Morning", "Afternoon");
        listTime.getSelectionModel().selectFirst();
        listTime.setPrefWidth(wLable);
        listTime.setLayoutX(wLable * 2);
        listTimeMidterm.add(listTime);

        ComboBox listTime2 = new ComboBox();
        listTime2.getItems().addAll("Morning", "Afternoon");
        listTime2.getSelectionModel().selectFirst();
        listTime2.setPrefWidth(wLable);
        listTime2.setLayoutX(wLable * 4);
        listTimeFinal.add(listTime2);

        Button btn1 = createDesBT("description", subject.getId_sub());
        btn1.setLayoutX(wLable * 5 + 20);
        Button btnApprove = createApproveBT(id, "Approve", subject.getId_sub());
        btnApprove.setLayoutX(wLable * 6 + 20);

        pane.getChildren().addAll(midtermExam, finalExam, btn1, btnApprove, listTime, listTime2);

        return pane;
    }

    public Pane createPane2(Subject subject, String type) {
        Pane pane = new Pane();
        int numCol = 4;
        double wScore = scrollPane2.getPrefWidth();
        double wLable = wScore / numCol;
        String date;
        String time;
        if (type.equals("midterm")) {
            time = subject.getMidtermTime();
            date = subject.getMidtermExam();
        } else {
            time = subject.getFinalTime();
            date = subject.getFinalExam();
        }
        pane.setMinSize(100, 25);
        String[] topic = {subject.getId_sub() + "", subject.getName(), time, date};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
            pane.getChildren().add(topic_text);
        }

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

    public Button createApproveBT(int id, String txt, long subject) {
        Button btn = new Button(txt);
        btn.setOnAction(event -> {
            if (enable) {
                DatePicker midtermD = midtermList.get(id);
                DatePicker finalD = finalList.get(id);
                LocalDate mid = midtermD.getValue();
                LocalDate finall = finalD.getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");

                String midtermIn = mid.format(formatter);
                String finnalIn = finall.format(formatter);

                String midTimeIn = listTimeMidterm.get(id).getValue() + "";
                String finTimeIn = listTimeFinal.get(id).getValue() + "";
                addAnnouncement(subject,"important","midterm exam \n"+"Date: "+midtermIn+"\nTime: "+midTimeIn,"Midterm",midtermIn);
                addAnnouncement(subject,"important","final exam \n"+"Date: "+finnalIn+"\nTime: "+finTimeIn,"Final",finnalIn);


                setApprove(subject, midtermIn, finnalIn, midTimeIn, finTimeIn);
                update();
            } else popUp(false, "Disable", "Can not enroll this time");
        });
        return btn;
    }

    public void createDialog(long id) {
        Subject foundedSubject = getSubject(id);
        String info = "Name: " + foundedSubject.getName() + "\n" +
                "Teacher: " + foundedSubject.getTeacher() + "\n" +
                "Description: " + foundedSubject.getDiscription() + "\n" +
                "Time: " + foundedSubject.getTime() + "\n" +
                "Teaching day: " + foundedSubject.getDay() + "\n" +
                "Student: " + foundedSubject.getStudentNum() + "/" + foundedSubject.getNo_student();
        popUp(true, "Course Info", info);
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

    public static List<Subject> getApprove(Boolean app) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql2 = "SELECT c FROM Subject c Where c.approve =" + app + "";
        TypedQuery<Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();
        return results2;
    }

    public static void setApprove(long id, String midtermIn, String finalIn, String midTimeIn, String finTimeIn) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Subject c Where c.id_sub = " + id + "";
        TypedQuery<classss.Subject> query1 = em.createQuery(sql1, classss.Subject.class);
        List<classss.Subject> results = query1.getResultList();
        em.getTransaction().begin();
        results.get(0).setApprove(true);
        results.get(0).setMidtermExam(midtermIn);
        results.get(0).setFinalExam(finalIn);
        results.get(0).setMidtermTime(midTimeIn);
        results.get(0).setFinalTime(finTimeIn);
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
//    =======================================jump======================================================
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
