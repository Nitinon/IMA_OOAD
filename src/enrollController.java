package controller;

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

public class enrollController implements Initializable {
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
    private ScrollPane scrollPane1;
    @FXML
    private ScrollPane scrollPane2;


    public void initialize(URL url, ResourceBundle rb) {
        nameLB.setText("Nitinon");
        surnameLB.setText("Penglao");
        contactLB.setText("0848841659");
        ageLB.setText("21");
        emailLB.setText("nitinon556@hotmail.com");

        GridPane gridpane = new GridPane();
        gridpane.setStyle("-fx-border-color:black;");
        gridpane.setMinSize(scrollPane1.getMinWidth(), 588);
        gridpane.getColumnConstraints().add(new ColumnConstraints(0));

        scrollPane1.setContent(gridpane);
        gridpane.add(createHeader(), 1, 1);
        for (int i = 2; i < 20; i++) {
            gridpane.add(createPane("eiei", "Zaa"), 1, i);
        }

    }


    public Label createLable(String txt, double height, double width,double pos) {
        Label label = new Label(txt);
        label.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:20");
        label.setMinHeight(height);
        label.setMinWidth(width);
        label.setMaxWidth(width);
        label.setLayoutX(pos);
        return label;
    }

    public Pane createHeader() {
        Pane pane = new Pane();
        double wScore = scrollPane1.getPrefWidth();
        double wLable = wScore / 4;
        pane.setMinSize(100, 25);

        Label topic_header = createLable("ID", 25,wLable, 0);
        Label score_header = createLable("Subject", 25, wLable,wLable );
        Label maxscore_header = createLable("eiei", 25, wLable,wLable * 2);
        Label enroll_header = createLable("enroll", 25, wLable-18,wLable * 3);

        pane.getChildren().addAll(topic_header, score_header, maxscore_header, enroll_header);

        return pane;
    }

    public Button createButton(int txt) {
        String txtbtn = Integer.toString(txt);
        Button btn = new Button();
        btn.setOnAction(event -> {
//            System.out.println(((Button)event.getSource()).getUserData());//prints out Click Me
        });
        btn.setText("click");
        btn.setUserData(txtbtn);
        return btn;
    }

    public Pane createPane(String subject, String teacher) {
        Pane pane = new Pane();
        double wScore = scrollPane1.getPrefWidth();
        double wLable = wScore / 4;

        pane.setMinSize(100, 25);
        Label topic_text = new Label("");
        topic_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:17 ");
        topic_text.setMinHeight(25);
        topic_text.setMinWidth(wLable);
        topic_text.setMaxWidth(wLable);

        Label score_text = new Label("");
        score_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:17");
        score_text.setMinHeight(25);
        score_text.setLayoutX(wLable);
        score_text.setMinWidth(wLable);
        score_text.setMaxWidth(wLable);

        Label maxscore_text = new Label("");
        maxscore_text.setStyle("-fx-border-color:black; -fx-alignment:center;-fx-font-size:17");
        maxscore_text.setMinHeight(25);
        maxscore_text.setLayoutX(wLable * 2);
        maxscore_text.setMinWidth(wLable);
        maxscore_text.setMaxWidth(wLable);

        Button btn1 = createButton(1);
        btn1.setStyle("-fx-border-color:black; -fx-alignment:center;");
        btn1.setLayoutX(wLable * 3);
        btn1.setMinSize(wLable - 18, 25);

        pane.getChildren().addAll(topic_text, score_text, maxscore_text, btn1);

        return pane;
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

    public void jumpLogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }


}
