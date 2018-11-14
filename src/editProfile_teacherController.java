import classss.Student;
import classss.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class editProfile_teacherController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField email;
    @FXML
    private TextField tel;
    @FXML
    private TextField position;
    @FXML
    private DatePicker datePicker;
    //    get current id-------------------------------------------------
    Preferences userPreferences = Preferences.userRoot();
    long id = userPreferences.getLong("currentUser", 0);
    Teacher currentTeacher = getObjTeacher(id);

    public void initialize(URL url, ResourceBundle rb) {
        name.setText(currentTeacher.getName());
        surname.setText(currentTeacher.getSurname());
        email.setText(currentTeacher.getEmail());
        tel.setText(currentTeacher.getPhonenumber());
        position.setText(currentTeacher.getPost());

    }
    public void edit() throws IOException {
        String nameIn = name.getText();
        String surnameIn = surname.getText();
        String emailIn = email.getText();
        String telIn = tel.getText();
        String pos=position.getText();
        LocalDate date = datePicker.getValue();
        String dateIn="";
        if (date!=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
            dateIn = date.format(formatter);
        }
        if (nameIn.isEmpty() || surnameIn.isEmpty() || emailIn.isEmpty() || telIn.isEmpty() || dateIn.isEmpty()||pos.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning alert");
            alert.setHeaderText(null);
            alert.setContentText("Empty!!!!");
            alert.showAndWait();
        } else {
//                register in DB
            editProfileTeacher(currentTeacher.getId(),nameIn, surnameIn, dateIn, emailIn, telIn, pos);
//                show pop-up alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Edit Date Success");
            alert.showAndWait();
            jumpBack();
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
    public static void editProfileTeacher(long id,String name, String surname, String birthday, String email, String phonenumber, String post){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Teacher c Where c.id = " + id+"" ;
        TypedQuery<classss.Teacher> query1 = em.createQuery(sql1, classss.Teacher.class);
        List<classss.Teacher> results = query1.getResultList();
        em.getTransaction().begin();
        results.get(0).setName(name);
        results.get(0).setSurname(surname);
        results.get(0).setBirthday(birthday);
        results.get(0).setEmail(email);
        results.get(0).setPhonenumber(phonenumber);
        results.get(0).setPost(post);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
    public void jumpBack() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/opencourse_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        opencourse_teacherController controller = fxmlLoader.<opencourse_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
}
