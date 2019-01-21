package GUI;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class WelcomeSceneController extends Controller {


    public void pushContinueButton(ActionEvent actionEvent) {
        switchToScene(actionEvent, "SampleGeneratingScene.fxml");
    }

    public void pushAboutButton() {

        File file = new File(getClass().getClassLoader().getResource("About.html").getFile());

        Stage stage = new Stage();
        stage.show();

        try {
            URL url = file.toURI().toURL();
            Browser browser = new Browser(url.toString());
            stage.setScene(new Scene(browser));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
