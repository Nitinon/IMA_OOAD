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

        topicIn.clear();
        maxIn.clear();

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
        int numCol=4;
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / 4;
        pane.setMinSize(100, 25);
        String[] topic = {"ID","Student","Score","Max"};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15;-fx-background-color: #ffd410; ");
            pane.getChildren().add(topic_text);
        }
        return pane;
    }

    public Pane createPane(int id, Score score) {
        Pane pane = new Pane();
        int numCol=4;
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / 4;
        Student foundedStudent=getObjStudent(score.getIdStudent());
        pane.setMinSize(100, 25);

        String[] topic = {foundedStudent.getId() + "",foundedStudent.getName()+" "+foundedStudent.getSurname(),score.getPoint()+"",score.getMax()+"",score.getPoint()+""};
        for (int i = 0; i < numCol; i++) {
            Label topic_text = createLable(topic[i], 25, wLable, wLable * i);
            topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:15;-fx-background-color: white; ");
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
    public void deleteTopic(){
        if (topicSelector.getValue()==null){
            popUp(false,"Error","Please select topic to delete");
        }
        else {
            Object temp=topicSelector.getValue();
            deleteScore((int)subjectSelected.getId_sub(),topicSelector.getValue()+"");
            popUp(true,"Success","Delete topic complete");
            try {
                jumpEditScore();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void createNewTopic() {
        for(Score a:viewScoreBySubject((int)subjectSelected.getId_sub())){
            if(topicIn.getText().equals(a.getTopic())){
                popUp(false,"Error","Already this topic");
                return;
            }
        }
        for (Student temp : subjectSelected.getStudent()) {
            createScore((int) temp.getId(), (int) subjectSelected.getId_sub(), topicIn.getText(), 0,Integer.parseInt(maxIn.getText()));
        }
        popUp(true,"Success","Create new topic complete");
        try {
            jumpEditScore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void submitScore() {
        if(subjectSelector.getValue()==null||topicSelector.getValue()==null){
            popUp(false,"Error","Please select subject and topic");
            return;
        }
        for (int i = 0; i < listScoreIn.size(); i++) {
            Score current=listScoreSelected.get(i);
            editScore(current.getIdStudent(),current.getIdSubject(),current.getTopic(),Integer.parseInt(listScoreIn.get(i).getText()));
        }
        popUp(true,"Success","Edit score complete");

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
    public static void deleteScore(int id_sub,String topic){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql2 = "SELECT c FROM Score c Where c.IdSubject =" + id_sub;
        TypedQuery<classss.Score> query2 = em.createQuery(sql2, classss.Score.class);
        List<classss.Score> results2 = query2.getResultList();
        em.getTransaction().begin();
        int size=results2.size();
        int i=0;
        while (i<size){
            System.out.println("size: "+size);
            if(results2.get(i).getTopic().equals(topic)){
                System.out.println(results2.get(i));
                em.remove(results2.get(i));
                results2.remove(i);
                i=-1;
                size=results2.size();
            }
            i++;
        }
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
