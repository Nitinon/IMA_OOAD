import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.prefs.Preferences;


public class Main extends Application {
    public static void main (String[] args){
        launch(args);
    }
    @Override
    public void start (Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("front/login.fxml"));
        primaryStage.setTitle("IMA");
        primaryStage.setScene(new Scene(root,1280,720));
        primaryStage.show();
        Preferences userPreferences = Preferences.userRoot();
        userPreferences.put("aa","eieieieiei");
    }
}
