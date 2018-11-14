import classss.Student;
import classss.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.xml.soap.Text;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class ediProfileController implements Initializable {
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
    private DatePicker datePicker;
    @FXML
    private ComboBox year;
    @FXML
    private ComboBox faculty;

    //    get current id-------------------------------------------------
    Preferences userPreferences = Preferences.userRoot();
    long id = userPreferences.getLong("currentUser", 0);
    Student currentStudent = getObjStudent(id);

    public void initialize(URL url, ResourceBundle rb) {
        name.setText(currentStudent.getName());
        surname.setText(currentStudent.getSurname());
        email.setText(currentStudent.getEmail());
        tel.setText(currentStudent.getPhonenumber());
        year.getItems().addAll(1, 2, 3, 4, 5);
        faculty.getItems().addAll("Computer Engineering");
        year.getSelectionModel().select(currentStudent.getYear_of_study()-1);
        faculty.getSelectionModel().selectFirst();
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
    public void edit() throws IOException {
        String nameIn = name.getText();
        String surnameIn = surname.getText();
        String emailIn = email.getText();
        String telIn = tel.getText();
        String facultyIn = (String) faculty.getValue();
        int yearIn = (int) year.getValue();
        LocalDate date = datePicker.getValue();
        String dateIn="";
        if (date!=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
            dateIn = date.format(formatter);
        }
            if (nameIn.isEmpty() || surnameIn.isEmpty() || emailIn.isEmpty() || telIn.isEmpty() || facultyIn.isEmpty() || dateIn.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning alert");
                alert.setHeaderText(null);
                alert.setContentText("Empty!!!!");
                alert.showAndWait();
            } else {
//                register in DB
                editProfileStudent(currentStudent.getId(),nameIn, surnameIn, dateIn, emailIn, telIn, yearIn);
//                show pop-up alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Edit Date Success");
                alert.showAndWait();
                jumpBack();
            }

    }
    public static void editProfileStudent(long id,String name, String surname, String birthday, String email, String phonenumber, int year_of_study){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Student c Where c.id = " + id+"" ;
        TypedQuery<classss.Student> query1 = em.createQuery(sql1, classss.Student.class);
        List<classss.Student> results = query1.getResultList();
        em.getTransaction().begin();
        results.get(0).setName(name);
        results.get(0).setSurname(surname);
        results.get(0).setBirthday(birthday);
        results.get(0).setEmail(email);
        results.get(0).setPhonenumber(phonenumber);
        results.get(0).setYear_of_study(year_of_study);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
//    ====================jump=======================================================
    public void jumpBack() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/enroll.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        enrollController controller = fxmlLoader.<enrollController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

}
