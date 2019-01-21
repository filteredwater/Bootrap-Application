package GUI;

import Bootstrap.SampleFromDistribution;
import Bootstrap.SampleFromDistributionExponential;
import Bootstrap.SampleFromDistributionMA;
import Bootstrap.SampleFromDistributionNormal;
import Runtime.DataGeneratingProcess;
import Runtime.TypeOfObservations;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SampleGeneratingSceneController extends Controller implements Initializable {

    @FXML
    private VBox SampleGeneratingVBox;
    @FXML
    private ChoiceBox TypeOfDataChoiceBox;
    @FXML
    private Slider SampleSizeSlider;
    @FXML
    private Label SampleSizeLabel;
    private final static int DEFAULT_SAMPLE_SIZE = 200;
    private int sampleSize = DEFAULT_SAMPLE_SIZE;

    @FXML
    private Button ConfirmSettingsButton;
    @FXML
    private Button GenerateButton;

    private final static int SLIDER_MIN = 10;
    private final static int SLIDER_MAX = 300;

    private double ExpectedValueNormal = 0;
    private double VarianceNormal = 1;

    private double ExpectedValueExponential = 1;

    private double ExpectedValueMA = 0;
    private final static double DEFAULT_THETA = 1;
    private ArrayList<Double> MACoefficients = new ArrayList<>();

    private SpacedHBox DistributionParametersHBox = new SpacedHBox();
    private BoldLabel SetDistributionParametersLabel = new BoldLabel();
    private TextField ExpectedValueTextField = new TextField();
    private TextField VarianceTextField;
    private BoldLabel CurrentProcessLabel = new BoldLabel();
    private TextField MAExpectedValueTextField = new TextField();
    private ArrayList<TextField> textFields = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TypeOfDataChoiceBox.setItems(FXCollections.observableArrayList("IID observations", "dependent observations"));
        TypeOfDataChoiceBox.setValue("IID observations");

        setSliderAndLabel(SampleSizeSlider, SampleSizeLabel, DEFAULT_SAMPLE_SIZE, SLIDER_MIN, SLIDER_MAX);
        SampleSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int valueFromSlider = getIntFromSlider(SampleSizeSlider);
            SampleSizeLabel.setText(String.valueOf(valueFromSlider));
            sampleSize = valueFromSlider;
        });

    }

    public void pushClearButton(ActionEvent actionEvent) {
        switchToScene(actionEvent, "SampleGeneratingScene.fxml");
    }

    public void pushConfirmSettingsButton() {

        TypeOfDataChoiceBox.setDisable(true);
        ConfirmSettingsButton.setDisable(true);
        SampleSizeSlider.setDisable(true);

        switch (TypeOfDataChoiceBox.getValue().toString()) {
            case "IID observations":
                reactForIID();
                break;
            case "dependent observations":
                reactForDependent();
                break;
        }

    }

    public void pushGenerateButton(ActionEvent actionEvent) {

        if (isInputValid()) {

            switch (session.getDataGeneratingProcess()) {
                case NORMAL:
                    if (isVarianceNonzero()) {
                        SampleFromDistribution sample = new SampleFromDistributionNormal(
                                getDoubleFromString(ExpectedValueTextField.getText()),
                                getDoubleFromString(VarianceTextField.getText()),
                                sampleSize);
                        session.setOriginalSample(sample);
                    }
                    break;
                case EXPONENTIAL:
                    if (isExpectedValueNonzero()) {
                        SampleFromDistribution sample = new SampleFromDistributionExponential(
                                getDoubleFromString(ExpectedValueTextField.getText()),
                                sampleSize);
                        session.setOriginalSample(sample);
                    }
                    break;
                case MA:
                    SampleFromDistribution sample = new SampleFromDistributionMA(
                            MACoefficients,
                            getDoubleFromString(MAExpectedValueTextField.getText()),
                            sampleSize);
                    session.setOriginalSample(sample);
            }

            switchToScene(actionEvent, "BootstrapSpecificationScene.fxml");
        }
    }

    public void reactForIID() {

        session.setTypeOfObservations(TypeOfObservations.IID);

        SampleGeneratingVBox.getChildren().add(new BoldLabel((String.format("Your choice: %d IID observations", sampleSize))));

        SpacedHBox IIDHBox = new SpacedHBox();

        ChoiceBox IIDDistributionChoiceBox = new ChoiceBox();
        IIDDistributionChoiceBox.setItems(FXCollections.observableArrayList("Normal Distribution", "Exponential Distribution"));
        IIDDistributionChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            GenerateButton.disableProperty().setValue(false);
            String IIDDistributionChoice = newValue.toString();
            DistributionParametersHBox.getChildren().clear();
            switch (IIDDistributionChoice) {
                case "Normal Distribution":
                    reactForNormal();
                    break;
                case "Exponential Distribution":
                    reactForExponential();
                    break;
            }
        });

        IIDHBox.getChildren().addAll(new BoldLabel("Choose the distribution:"), IIDDistributionChoiceBox);
        SampleGeneratingVBox.getChildren().addAll(IIDHBox, SetDistributionParametersLabel, DistributionParametersHBox);

    }

    public void reactForDependent() {

        session.setTypeOfObservations(TypeOfObservations.DEPENDENT);
        session.setDataGeneratingProcess(DataGeneratingProcess.MA);

        GenerateButton.disableProperty().setValue(false);

        SpacedHBox ExpectedValueMAHbox = new SpacedHBox();

        BoldLabel ExpectedValueMALabel = new BoldLabel("Expected value");

        MAExpectedValueTextField = new TextField(String.valueOf(ExpectedValueMA));
        ExpectedValueMAHbox.getChildren().addAll(ExpectedValueMALabel, MAExpectedValueTextField);
        textFields.add(MAExpectedValueTextField);
        MAExpectedValueTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isStringValidNumericalNonnegativeInput(newValue)) {
                MAExpectedValueTextField.setText(oldValue);
            }
        });

        Button AddMACoefficientButton = new Button("Add coefficient");
        AddMACoefficientButton.setOnAction(e -> {
            addMAAddingHBox();
        });

        SampleGeneratingVBox.getChildren().addAll(
                new BoldLabel(String.format("Your choice: %d dependent observations", sampleSize)),
                CurrentProcessLabel,
                ExpectedValueMAHbox,
                AddMACoefficientButton
        );

        addMAAddingHBox();

    }

    public void reactForNormal() {

        session.setDataGeneratingProcess(DataGeneratingProcess.NORMAL);

        SetDistributionParametersLabel.setText("Set the parameters of the distribution:");

        DistributionParametersHBox.getChildren().add(new BoldLabel(("Expected Value:")));
        ExpectedValueTextField.setText(String.valueOf(ExpectedValueNormal));
        textFields.add(ExpectedValueTextField);
        DistributionParametersHBox.getChildren().add(ExpectedValueTextField);

        ExpectedValueTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isStringValidNumericalInput(newValue)) {
                ExpectedValueTextField.setText(oldValue);
            }
        });

        DistributionParametersHBox.getChildren().add(new BoldLabel("Variance:"));
        VarianceTextField = new TextField(String.valueOf(VarianceNormal));
        textFields.add(VarianceTextField);
        DistributionParametersHBox.getChildren().add(VarianceTextField);
        VarianceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isStringValidNumericalNonnegativeInput(newValue)) {
                VarianceTextField.setText(oldValue);
            }
        });
    }

    public void reactForExponential() {

        session.setDataGeneratingProcess(DataGeneratingProcess.EXPONENTIAL);

        SetDistributionParametersLabel.setText("Set the parameter of the distribution:");

        DistributionParametersHBox.getChildren().add(new BoldLabel("Expected Value:"));

        ExpectedValueTextField = new TextField(String.valueOf(ExpectedValueExponential));
        textFields.add(ExpectedValueTextField);
        DistributionParametersHBox.getChildren().add(ExpectedValueTextField);
        ExpectedValueTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isStringValidNumericalNonnegativeInput(newValue)) {
                ExpectedValueTextField.setText(oldValue);
            }
        });
    }

    public void addMAAddingHBox() {

        MACoefficients.add(DEFAULT_THETA);
        int sizeOfMACoefficients = MACoefficients.size();
        CurrentProcessLabel.setText(String.format("Current process: MA(%d)", sizeOfMACoefficients));

        SpacedHBox MAAddingHBox = new SpacedHBox();
        SampleGeneratingVBox.getChildren().add(MAAddingHBox);

        MAAddingHBox.getChildren().add(new BoldLabel(String.format("\u03B8(%d) = ", sizeOfMACoefficients))); // \\uo3B8 = DEFAULT_THETA

        TextField MACoefficientTextFiled = new TextField(String.valueOf(DEFAULT_THETA));
        textFields.add(MACoefficientTextFiled);
        MAAddingHBox.getChildren().add(MACoefficientTextFiled);
        MACoefficientTextFiled.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isStringValidNumericalInputWithoutValue(newValue)) {
                if (!isStringValidNumericalInput(newValue)) {
                    MACoefficientTextFiled.setText(oldValue);
                } else {
                    MACoefficients.set(sizeOfMACoefficients - 1, getDoubleFromString(newValue));
                }
            }
        });

    }

    private boolean isInputValid() {

        for (TextField tf : textFields) {
            if (isStringValidNumericalInputWithoutValue(tf.getText())) {
                throwWarning("Parameters are wrongly specified.");
                return false;
            }
        }
        return true;
    }

    private boolean isVarianceNonzero() {
        if (isStringValidNumericalInputWithoutValue(VarianceTextField.getText())) {
            return false;
        } else {
            if (isStringNonzero(VarianceTextField.getText())) {
                throwWarning("Variance should be a positive number");
                return false;
            }
            return true;
        }
    }

    private boolean isExpectedValueNonzero() {
        if (isStringValidNumericalInputWithoutValue(ExpectedValueTextField.getText())) {
            return false;
        } else {
            if (isStringNonzero(ExpectedValueTextField.getText())) {
                throwWarning("Expected value should be a positive number");
                return false;
            }
            return true;
        }
    }

}

