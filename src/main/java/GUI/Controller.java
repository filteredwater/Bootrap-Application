package GUI;

import Runtime.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    protected Session session = Session.getInstance();
    private final static int HBOX_XSPACING = 15;
    protected final static Font DEFAULT_FONT = new Font("System Bold", 13);

    class BoldLabel extends Label {

        public BoldLabel() {
            this.setFont(DEFAULT_FONT);
        }

        public BoldLabel(String s) {
            super(s);
            this.setFont(DEFAULT_FONT);
        }
    }

    class SpacedHBox extends HBox {
        public SpacedHBox() {
            super();
            this.setSpacing(HBOX_XSPACING);
            this.setAlignment(Pos.BASELINE_LEFT);

        }
    }

    static class Browser extends StackPane {
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        public Browser(String url) {
            webEngine.load(url);
            getChildren().add(browser);
        }
    }

    public void switchToScene(ActionEvent actionEvent, String nextSceneFile) {
        Parent nextSceneParent = null;
        try {
            nextSceneParent = FXMLLoader.load(getClass().getClassLoader().getResource(nextSceneFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(nextScene);
        window.show();
    }

    public void setSliderAndLabel(Slider slider, Label label, int value, int min, int max) {
        slider.setValue(value);
        slider.setMin(min);
        slider.setMax(max);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        label.setText(Integer.toString(value));
    }

    public void setSliderAndLabelWithListener(Slider slider, Label label, int value, int min, int max) {

        setSliderAndLabel(slider, label, value, min, max);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            label.setText(String.valueOf(getIntFromSlider(slider)));
        });
    }

    public int getIntFromSlider(Slider slider) {
        return Math.round((float) slider.getValue());
    }

    public boolean isStringValidNumericalInput(String s) {
        return s.matches("(-)?\\d{0,4}([\\.]\\d{0,2})?");
    }

    public boolean isStringValidNumericalNonnegativeInput(String s) {
        return s.matches("\\d{0,4}([\\.]\\d{0,2})?");
    }

    public boolean isStringValidNumericalInputWithoutValue(String s) {
        return (s.equals("") || s.equals(".") || s.equals("-") || s.equals("-."));
    }

    public double getDoubleFromString(String s) {
        return Double.parseDouble(s);
    }

    public boolean isStringNonzero(String s) {
        return getDoubleFromString(s) == 0;
    }

    public void throwWarning(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Warning");
        alert.setContentText(s);
        alert.showAndWait();
    }

    public static Group buildNewWindow(int width, int height) {

        final Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
        return root;
    }
}
