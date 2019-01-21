package GUI;

import Bootstrap.BootstrapSimulationIID;
import Bootstrap.BootstrapSimulationMBB;
import Bootstrap.BootstrapSimulationNBB;
import Bootstrap.BootstrapSimulationSB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BootstrapSpecificationSceneController extends Controller implements Initializable {

    @FXML
    private Slider NumberOfBootstrapSamplesIIDSlider;
    @FXML
    private Label NumberOfBootstrapSamplesIIDLabel;

    @FXML
    private Slider BlockLengthMBBSlider;
    @FXML
    private Label BlockLengthMBBLabel;
    @FXML
    private Slider NumberOfBootstrapSamplesMBBSlider;
    @FXML
    private Label NumberOfBootstrapSamplesMBBLabel;

    @FXML
    private Slider BlockLengthNBBSlider;
    @FXML
    private Label BlockLengthNBBLabel;
    @FXML
    private Slider NumberOfBootstrapSamplesNBBSlider;
    @FXML
    private Label NumberOfBootstrapSamplesNBBLabel;

    @FXML
    private Slider BlockLengthSBSlider;
    @FXML
    private Label BlockLengthSBLabel;
    @FXML
    private Slider NumberOfBootstrapSamplesSBSlider;
    @FXML
    private Label NumberOfBootstrapSamplesSBLabel;

    @FXML
    private VBox BootstrapSpecificationSceneVBox;

    private static int MIN_NUMBER_OF_BOOTSTRAP_SAMPLES_CHOSEN = 500;
    private static int MAX_NUMBER_OF_BOOTSTRAP_SAMPLES_CHOSEN = 3000;
    private final int sampleSize = session.getOriginalSample().getSampleSize();
    private final static double BLOCK_LENGTH_LOWER_FRACTION = 0.1;
    private final static double BLOCK_LENGTH_UPPER_FRACTION = 0.5;
    private final int blockLengthLowerBound = (int) Math.round(BLOCK_LENGTH_LOWER_FRACTION * sampleSize);
    private final int blockLengthUpperBound = (int) Math.round(BLOCK_LENGTH_UPPER_FRACTION * sampleSize);
    private final int blockLengthInitialValue = (blockLengthLowerBound + blockLengthUpperBound) / 2;


    private class Row {
        private Slider numberOfBootstrapSamplesSlider;
        private Label numberOfBootstrapSamplesLabel;
        private Slider blockLengthSlider;
        private Label blockLengthLabel;

        private Row(Slider numberOfBootstrapSamplesSlider, Label numberOfBootstrapSamplesLabel, Slider blockLengthSlider, Label blockLengthLabel) {
            this.numberOfBootstrapSamplesSlider = numberOfBootstrapSamplesSlider;
            this.numberOfBootstrapSamplesLabel = numberOfBootstrapSamplesLabel;
            this.blockLengthSlider = blockLengthSlider;
            this.blockLengthLabel = blockLengthLabel;

            initializeRow();
        }

        private void initializeRow() {
            setSliderAndLabelWithListener(numberOfBootstrapSamplesSlider, numberOfBootstrapSamplesLabel, MIN_NUMBER_OF_BOOTSTRAP_SAMPLES_CHOSEN, MIN_NUMBER_OF_BOOTSTRAP_SAMPLES_CHOSEN, MAX_NUMBER_OF_BOOTSTRAP_SAMPLES_CHOSEN);
            setSliderAndLabelWithListener(blockLengthSlider, blockLengthLabel, blockLengthInitialValue, blockLengthLowerBound, blockLengthUpperBound);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setSliderAndLabelWithListener(NumberOfBootstrapSamplesIIDSlider, NumberOfBootstrapSamplesIIDLabel, MIN_NUMBER_OF_BOOTSTRAP_SAMPLES_CHOSEN, MIN_NUMBER_OF_BOOTSTRAP_SAMPLES_CHOSEN, MAX_NUMBER_OF_BOOTSTRAP_SAMPLES_CHOSEN);

        new Row(NumberOfBootstrapSamplesMBBSlider, NumberOfBootstrapSamplesMBBLabel, BlockLengthMBBSlider, BlockLengthMBBLabel);
        new Row(NumberOfBootstrapSamplesNBBSlider, NumberOfBootstrapSamplesNBBLabel, BlockLengthNBBSlider, BlockLengthNBBLabel);
        new Row(NumberOfBootstrapSamplesSBSlider, NumberOfBootstrapSamplesSBLabel, BlockLengthSBSlider, BlockLengthSBLabel);

    }


    public void pushAddIIDButton() {
        if (session.isBootstrapSimulationsChosenFull()) {
            throwTooManyBootstrapSimulationsAlert();
        } else {
            int numberOfBootstrapSimulations = getIntFromSlider(NumberOfBootstrapSamplesIIDSlider);
            BootstrapSimulationIID bootstrapSimulation = new BootstrapSimulationIID(session.getOriginalSample(), numberOfBootstrapSimulations, 0);
            session.addToBootstrapSimulationsChosen(bootstrapSimulation);
            addBootstrapChosenLabel(bootstrapSimulation.getType(), numberOfBootstrapSimulations);
        }
    }


    public void pushAddMBBButton() {
        if (session.isBootstrapSimulationsChosenFull()) {
            throwTooManyBootstrapSimulationsAlert();
        } else {
            int numberOfBootstrapSimulations = getIntFromSlider(NumberOfBootstrapSamplesMBBSlider);
            int blockLength = getIntFromSlider(BlockLengthMBBSlider);
            BootstrapSimulationMBB bootstrapSimulation = new BootstrapSimulationMBB(session.getOriginalSample(), numberOfBootstrapSimulations, blockLength);
            session.addToBootstrapSimulationsChosen(bootstrapSimulation);
            addBootstrapChosenLabel(bootstrapSimulation.getType(), numberOfBootstrapSimulations, blockLength);
        }
    }

    public void pushAddNBBButton() {
        if (session.isBootstrapSimulationsChosenFull()) {
            throwTooManyBootstrapSimulationsAlert();
        } else {
            int numberOfBootstrapSimulations = getIntFromSlider(NumberOfBootstrapSamplesNBBSlider);
            int blockLength = getIntFromSlider(BlockLengthNBBSlider);
            BootstrapSimulationNBB bootstrapSimulation = new BootstrapSimulationNBB(session.getOriginalSample(), numberOfBootstrapSimulations, blockLength);
            session.addToBootstrapSimulationsChosen(bootstrapSimulation);
            addBootstrapChosenLabel(bootstrapSimulation.getType(), numberOfBootstrapSimulations, blockLength);
        }
    }

    public void pushAddSBButton() {
        if (session.isBootstrapSimulationsChosenFull()) {
            throwTooManyBootstrapSimulationsAlert();
        } else {
            int numberOfBootstrapSimulations = getIntFromSlider(NumberOfBootstrapSamplesSBSlider);
            int blockLength = getIntFromSlider(BlockLengthSBSlider);
            BootstrapSimulationSB bootstrapSimulation = new BootstrapSimulationSB(session.getOriginalSample(), numberOfBootstrapSimulations, blockLength);
            session.addToBootstrapSimulationsChosen(bootstrapSimulation);
            addBootstrapChosenLabel(bootstrapSimulation.getType(), numberOfBootstrapSimulations, blockLength, true);
        }
    }

    public void pushRemoveButton() {

        if (session.isBootstrapSimulationsChosenEmpty()) {
            throwWarning("You have not chosen any bootstrap simulation");
        } else {
            session.removeFromBootstrapSimulationsChosen();
            BootstrapSpecificationSceneVBox.getChildren().remove(BootstrapSpecificationSceneVBox.getChildren().size() - 1);
        }
    }

    public void pushPlotForMeanButton() {
        if (session.isBootstrapSimulationsChosenEmpty()) {
            throwWarning("You have not chosen any bootstrap simulation");
        } else {
            new Histogram().createHistogramOfMeans();
        }
    }

    public void pushPlotForMedianButton() {
        if (session.isBootstrapSimulationsChosenEmpty()) {
            throwWarning("You have not chosen any bootstrap simulation");
        } else {
            new Histogram().createHistogramOfMedians();
        }
    }

    public void pushSummaryButton() {
        if (session.isBootstrapSimulationsChosenEmpty()) {
            throwWarning("You have not chosen any bootstrap simulation");
        } else {

            new SummaryTable().createSummaryTable(session);
        }
    }


    public void throwTooManyBootstrapSimulationsAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Warning");
        alert.setContentText("You can only choose " + session.getMAX_NUMBER_OF_BOOTSTRAP_SIMULATIONS_CHOSEN() + " bootstrap simulations");
        alert.showAndWait();
    }


    private void addBootstrapChosenLabel(String type, int numberOfBootstrapSimulations) {
        BoldLabel label = new BoldLabel(type + " Bootstrap: " + numberOfBootstrapSimulations + " bootstrap samples");
        BootstrapSpecificationSceneVBox.getChildren().add(label);
    }


    private void addBootstrapChosenLabel(String type, int numberOfBootstrapSimulations, int blockLength) {
        addBootstrapChosenLabel(type, numberOfBootstrapSimulations, blockLength, false);
    }

    private void addBootstrapChosenLabel(String type, int numberOfBootstrapSimulations, int blockLength, boolean expected) {
        String string;
        if (expected) {
            string = type + " Bootstrap: " + numberOfBootstrapSimulations + " bootstrap samples; expected block length: " + blockLength;
        } else {
            string = type + " Bootstrap: " + numberOfBootstrapSimulations + " bootstrap samples; block length: " + blockLength;
        }
        BoldLabel label = new BoldLabel(string);
        BootstrapSpecificationSceneVBox.getChildren().add(label);
    }

}


