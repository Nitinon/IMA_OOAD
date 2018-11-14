import classss.Score;
import classss.Student;
import classss.Subject;
import classss.Teacher;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
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

public class editscore_teacherController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private ComboBox subjectSelector;
    @FXML
    private ComboBox topicSelector;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField topicIn;
    @FXML
    private TextField maxIn;

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


    ArrayList<TextField> listScoreIn = new ArrayList<>();


    Preferences userPreferences = Preferences.userRoot();
    long id = userPreferences.getLong("currentUser", 0);
    Teacher currentTeacher = getObjTeacher(id);
    Subject subjectSelected;

    //    List of all topic in this subject
    List<String> listTopic = new ArrayList<String>();
    List<Score> listScoreSelected=new ArrayList<Score>();

    public void initialize(URL url, ResourceBundle rb) {
        for (Subject temp : currentTeacher.getSubjects()) {
            subjectSelector.getItems().add(temp.getId_sub() + " " + temp.getName());
        }
        updateScreen();
    }

    public void updateScreen() {
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



        GridPane gridpane = new GridPane();
        gridpane.getChildren().clear();
        gridpane.setMinSize(scrollPane.getMinWidth(), 0);
        gridpane.setStyle("-fx-border-color:black; -fx-alignment:center;");
        scrollPane.setContent(gridpane);
        gridpane.add(createHeader(), 1, 1);
        int i = 0;
        for (Score a : listScoreSelected) {
            i++;
            double wScore = scrollPane.getPrefWidth();
            double wLable = wScore / 4;
            gridpane.add(createPane(i, a), 1, i + 2);
        }



    }
    public Pane createHeader() {
        Pane pane = new Pane();
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / 4;
        pane.setMinSize(100, 25);
        Label ID_header = createLable("ID", 25, wLable, 0);
        Label student_header = createLable("student", 25, wLable, wLable);
        Label score_header = createLable("score", 25, wLable, wLable * 2);
        Label maxscore_header = createLable("max", 25, wLable, wLable * 3);
        ID_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        student_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        score_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        maxscore_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");

        pane.getChildren().addAll(ID_header, student_header, score_header, maxscore_header);
        return pane;
    }


    public Pane createPane(int id, Score score) {
        Pane pane = new Pane();
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / 4;
        Student foundedStudent=getObjStudent(score.getIdStudent());
        pane.setMinSize(100, 25);

        Label topic_text = createLable(foundedStudent.getId() + "", 25, wLable, 0);
        Label student_text = createLable(foundedStudent.getName()+" "+foundedStudent.getSurname(), 25, wLable, wLable);

        Label score_text = createLable(score.getPoint()+"", 25, wLable, wLable*2);
//        fake max score
        Label maxscore_text = createLable(score.getMax()+"", 25, wLable, wLable * 3);
//      set style
        TextField scoreIn=createTextField(score.getPoint()+"",25,wLable,wLable*2);
        listScoreIn.add(scoreIn);

        topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        student_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        score_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");
        maxscore_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15 ");

        pane.getChildren().addAll(topic_text,student_text,score_text, maxscore_text,scoreIn);

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
    public TextField createTextField(String txt, double height, double width, double pos) {
        TextField textField = new TextField(txt);
        textField.setMinHeight(height);
        textField.setMinWidth(width);
        textField.setMaxWidth(width);
        textField.setLayoutX(pos);
        return textField;
    }

    public void selected() {
        topicSelector.getItems().clear();
        listTopic.clear();
        if (subjectSelector.getValue() != null) {
            String id = (String) subjectSelector.getValue();
            id = id.substring(0, 4);
            long idSub = Long.parseLong(id);
            subjectSelected = getSubject(idSub);
            List<Score> listAll = viewScore((int) subjectSelected.getId_sub());
            for (Score a : listAll) {
                if (!listTopic.contains(a.getTopic())) {
                    listTopic.add(a.getTopic());
                    topicSelector.getItems().add(a.getTopic());
                }
            }
        }
    }

    public void selectedTopic() {
        listScoreSelected.clear();
        if (topicSelector.getValue() != null) {
            List<Student> all=(subjectSelected.getStudent());
            List<Score> listAll = viewScore((int) subjectSelected.getId_sub());
            String topic=(String)topicSelector.getValue();
            Boolean already=false;
            for(Student a:all){
                already=false;
                System.out.println(a.getName());
                for(Score b:listAll){
                    if(topic.equals(b.getTopic())&&a.getId()==b.getIdStudent()){
                        already=true;
                        System.out.println(b);
                    }
                }
                if(already==false){
                    System.out.println("created");
                    createScore((int) a.getId(), (int) subjectSelected.getId_sub(), topic, 0,listAll.get(0).getMax());
                }
            }
            listAll = viewScore((int) subjectSelected.getId_sub());
            for (Score a : listAll) {
                if (a.getTopic().equals((String) topicSelector.getValue())) {
                    listScoreSelected.add(a);
                }
            }
        }
        updateScreen();
    }
    public void createNewTopic() {
        for(Score a:viewScoreBySubject((int)subjectSelected.getId_sub())){
            if(topicIn.getText().equals(a.getTopic())){
                System.out.println("Already this topic");
                return;
            }
        }
        for (Student temp : subjectSelected.getStudent()) {
            createScore((int) temp.getId(), (int) subjectSelected.getId_sub(), topicIn.getText(), 0,Integer.parseInt(maxIn.getText()));
        }
        System.out.println("created");
    }
    public void submitScore() {
        for (int i = 0; i < listScoreIn.size(); i++) {
            Score current=listScoreSelected.get(i);
//            System.out.println(listScoreIn.get(i).getText());
//            System.out.println(listScoreSelected.get(i).getIdStudent());
            editScore(current.getIdStudent(),current.getIdSubject(),current.getTopic(),Integer.parseInt(listScoreIn.get(i).getText()));
            System.out.println("edited");
        }
    }



    //    =================================DB=========================================================
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

    public static void editCourse(long id, String name, String discription) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Subject c Where c.id_sub = " + id + "";
        TypedQuery<classss.Subject> query1 = em.createQuery(sql1, classss.Subject.class);
        List<classss.Subject> results = query1.getResultList();
        em.getTransaction().begin();
        results.get(0).setName(name);
        results.get(0).setDiscription(discription);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public static void createScore(int idStudent, int idSubject, String topic, int point,int max) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        classss.Score a = new classss.Score(idStudent, idSubject, topic, point,max);
        em.persist(a);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
    public static classss.Student getObjStudent(int id_stu){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Student c Where c.id =" + id_stu + "";
        TypedQuery<classss.Student> query1 = em.createQuery(sql1, classss.Student.class);
        List<classss.Student> results1 = query1.getResultList();
        if(results1.size()==0){
            return null;
        }
        else{
            return results1.get(0);
        }
    }

    public static void editScore(int idStudent, int idSubject, String topic,int point){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Score c Where c.IdStudent = " + idStudent + " AND c.IdSubject = "+idSubject;
        TypedQuery<classss.Score> query1 = em.createQuery(sql1, classss.Score.class);
        List<classss.Score> results = query1.getResultList();
        em.getTransaction().begin();
        for(classss.Score c : results){
            if(c.getTopic().equals(topic)){
                c.setPoint(point);
            }
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
    public static List<Score> viewScore(int idSubject) {
        int score = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

        String sql1 = "SELECT c FROM Score c Where c.IdSubject = " + idSubject;
        TypedQuery<classss.Score> query1 = em.createQuery(sql1, classss.Score.class);
        List<classss.Score> results = query1.getResultList();
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
        emf.close();
        return results;
    }
    public static List<Score> viewScoreBySubject(int idSubject) {
        int score = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

        String sql1 = "SELECT c FROM Score c Where c.IdSubject = " + idSubject;
        TypedQuery<classss.Score> query1 = em.createQuery(sql1, classss.Score.class);
        List<classss.Score> results = query1.getResultList();
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
        emf.close();
        return results;
    }
    //    =====================jump=============================

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
