import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class AdminController implements Initializable {
    @FXML
    private AnchorPane backpane;
    @FXML
    private ToggleButton enableBT;
    Preferences userPreferences = Preferences.userRoot();
    Boolean enable;

    public void initialize(URL url, ResourceBundle rb) {
        update();
    }

    public void update() {
        enable = userPreferences.getBoolean("Enable", true);
        if (enable)enableBT.setText("Enable");
        else enableBT.setText("Disable");
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

    public void jumpBack() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("front/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        loginController controller = fxmlLoader.<loginController>getController();
        fxmlLoader.setController(controller);
        backpane.getChildren().setAll(root);
    }
}
