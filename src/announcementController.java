import component.passwordDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Date;

public class announcementController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private GridPane announcepane;
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

    Date date = new Date();
    int month = date.getMonth();
    int year = date.getYear();

    Button[][] aa = new Button[10][10];
    String[] dayy = {"sunday","monday","tuesday","wednesday","thursday","friday","saturday"};
    public void initialize(URL url, ResourceBundle rb) {
        nameLB.setText("Nitinon");
        surnameLB.setText("Penglao");
        contactLB.setText("0848841659");
        ageLB.setText("21");
        emailLB.setText("nitinon556@hotmail.com");
        initCalendar(month,year);

    }
    private void initCalendar(int month,int year){
        Date date = new Date();
        int firstday = 1;
        date.setDate(1);
        date.setMonth(month);
        date.setYear(year);
        int day = date.getDay();
        int check = 0;
        for(int k = 0;k<7;k++){
            announcepane.add(new Label(dayy[k]),k,0);
        }
        for (int i = 1;i<7;i++) {
            for (int j = 0;j<7;j++) {
                if(j == day){
                    check = 1;
                }
                if(check==0){
                    announcepane.add(createButton(0,0,j,i), j, i);
                }
                else{
                    announcepane.add(createButton(firstday,month+1,j,i), j, i);
                    firstday++;
                }
            }
        }
    }
    private Button createButton(int day,int month,int j,int i){
        aa[j][i] = new Button();
        aa[j][i].setMinHeight(60.0);
        aa[j][i].setMinWidth(80.0);
        if (day ==0) aa[j][i].setText(" ");
        else aa[j][i].setText(day+" "+month+" "+year);
        aa[j][i].setOnAction(e ->{
            dayBtnAction(day,month);
        });
        return aa[j][i];
    }
    public void dayBtnAction(int day,int month){
        System.out.println(day+" "+month+" "+year);
    }
    public void nxtMonth(){
        if(month==11){
            month = 0;
            year++;
        }else{
            month++;
        }
        initCalendar(month,year);
    }
    public void preMonth(){
        if(month==-1){
            month = 11;
            year--;
        }else{
            month--;
        }
        initCalendar(month,year);
    }


    public void createDialog() {
        passwordDialog pd = new passwordDialog();
        Optional<String> result = pd.showAndWait();
        result.ifPresent(password -> System.out.println(password));
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

    public void jumpLogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }


}