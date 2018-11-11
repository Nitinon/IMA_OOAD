import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class viewscoreController implements Initializable{
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
    private ScrollPane scrollPane;



    public void initialize(URL url, ResourceBundle rb) {
        nameLB.setText("Nitinon");
        surnameLB.setText("Penglao");
        contactLB.setText("0848841659");
        ageLB.setText("21");
        emailLB.setText("nitinon556@hotmail.com");

        GridPane gridpane = new GridPane();
        gridpane.setStyle("-fx-border-color:black;");
        gridpane.setMinSize(737, 588);
        gridpane.getColumnConstraints().add(new ColumnConstraints(0));
        scrollPane.setContent(gridpane);
        gridpane.add(createHeader(),0, 0);
        for(int i=1;i<20;i++){
            gridpane.add(createButton(i),0, i);
        }

    }
    public Pane createHeader (){
        Pane pane = new Pane();
        double wScore=scrollPane.getPrefWidth();
        double wLable=wScore/3;


        pane.setMinSize(100, 25);

        Label topic_header = new Label("TopicHeader");
        topic_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:20");
        topic_header.setMinHeight(25);
        topic_header.setMinWidth(wLable);
        topic_header.setMaxWidth(wLable);

        Label score_header = new Label("ScoreHeader");
        score_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:20");
        score_header.setMinHeight(25);
        score_header.setLayoutX(wLable);
        score_header.setMinWidth(wLable);
        score_header.setMaxWidth(wLable);

        Label maxscore_header = new Label("MaxScoreHeader");
        maxscore_header.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:20");
        maxscore_header.setMinHeight(25);
        maxscore_header.setLayoutX(wLable*2);
        maxscore_header.setMinWidth(258);
        maxscore_header.setMaxWidth(258);
        pane.getChildren().addAll(topic_header,score_header,maxscore_header);
        return pane;
    }

    public Pane createPane(String subject, String teacher ) {
        Pane pane = new Pane();
        double wScore=scrollPane.getPrefWidth();
        double wLable=wScore/3;

        pane.setMinSize(100, 25);
        Label topic_text = new Label("Topic");
        topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:17 ");
        topic_text.setMinHeight(25);
        topic_text.setMinWidth(wLable);
        topic_text.setMaxWidth(wLable);

        Label score_text = new Label("Score");
        score_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:17");
        score_text.setMinHeight(25);
        score_text.setLayoutX(wLable);
        score_text.setMinWidth(wLable);
        score_text.setMaxWidth(wLable);

        Label maxscore_text = new Label("Max Score");
        maxscore_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:17");
        maxscore_text.setMinHeight(25);
        maxscore_text.setLayoutX(wLable*2);
        maxscore_text.setMinWidth(258);
        maxscore_text.setMaxWidth(258);


        pane.getChildren().addAll(topic_text,score_text,maxscore_text);

        return pane;
    }

    public Button createButton (int txt){
        String txtbtn = Integer.toString(txt);
        Button btn = new Button();
        double wscore = scrollPane.getPrefWidth();
        double wLabel = wscore/3;
        btn.setOnAction(event -> {
//            System.out.println(((Button)event.getSource()).getUserData());//prints out Click Me
        });
        btn.setMinSize(100,25);
        btn.setText("click");
        btn.setUserData(txtbtn);
        return btn;
    }
    public void getID(int k){
        System.out.println(k);
    }

    @FXML
    public void jumpEnroll()throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/enroll.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        enrollController controller = fxmlLoader.<enrollController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    @FXML
    public void jumpChange()throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/changeCourse.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        changeCourseController controller = fxmlLoader.<changeCourseController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    @FXML
    public void jumpDrop()throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/drop.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        dropController controller = fxmlLoader.<dropController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    @FXML
    public void jumpView()throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/viewscore.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        viewscoreController controller = fxmlLoader.<viewscoreController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    public void jumpChangePass() throws  IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/changePass.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        changePassController controller = fxmlLoader.<changePassController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    public void jumpEditProfile() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/editProfile.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        ediProfileController controller = fxmlLoader.<ediProfileController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    @FXML
    public void jumpLogout() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }


}