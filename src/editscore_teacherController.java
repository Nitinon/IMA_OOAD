import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class editscore_teacherController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private ScrollPane scrollPane;

    ArrayList<TextField> listScore=new ArrayList<>();

    public void initialize(URL url, ResourceBundle rb) {

        GridPane gridpane = new GridPane();
        gridpane.setStyle("-fx-border-color:black;");
        gridpane.setMinSize(737, 588);
        gridpane.getColumnConstraints().add(new ColumnConstraints(0));
        scrollPane.setContent(gridpane);
        gridpane.add(createHeader(),0, 0);
        for(int i=1;i<20;i++){
            gridpane.add(createHeader(),0, i);
        }
    }
    public Pane createHeader() {
        Pane pane = new Pane();
        double wScore = scrollPane.getPrefWidth();
        double wLable = wScore / 4;
        pane.setMinSize(100, 25);
        Label topic_header = createLable("ID", 25, wLable, 0);
        Label score_header = createLable("Subject", 25, wLable, wLable);
        Label maxscore_header = createLable("eiei", 25, wLable, wLable * 2);

        TextField btn=createTextField(1);
        btn.setLayoutX(wLable*2+30);
        btn.setLayoutY(3);
        listScore.add(btn);

        pane.getChildren().addAll(topic_header, score_header, maxscore_header,btn);
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
    public Label createLable(String txt, double height, double width, double pos) {
        Label label = new Label(txt);
        label.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:20");
        label.setMinHeight(height);
        label.setMinWidth(width);
        label.setMaxWidth(width);
        label.setLayoutX(pos);
        return label;
    }

    public TextField createTextField (int txt){
        String txtbtn = Integer.toString(txt);
        TextField textField = new TextField();
        double wscore = scrollPane.getPrefWidth();
        double wLabel = wscore/3;

        textField.setMinSize(100,25);
        textField.setUserData(txtbtn);
        return textField;
    }

    public void jumpEnroll()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/opencourse_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        opencourse_teacherController controller = fxmlLoader.<opencourse_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
    public void jumpEditCourse()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/editsubject_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        editsubject_teacherController controller = fxmlLoader.<editsubject_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpDeleteCourse()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/deletesubject_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        deletesubject_teacherController controller = fxmlLoader.<deletesubject_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpEditScore()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/editscore_teacher.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        editscore_teacherController controller = fxmlLoader.<editscore_teacherController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

    public void jumpLogout() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }

}
