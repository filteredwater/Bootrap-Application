package Runtime;

import Bootstrap.BootstrapSimulation;
import Bootstrap.SampleFromDistribution;

import java.util.ArrayList;

public class Session {

    //    singleton
    private static Session instance = new Session();

    private Session() {
    }

    public static Session getInstance() {
        return instance;
    }


    private TypeOfObservations typeOfObservations;
    private DataGeneratingProcess dataGeneratingProcess;
    private SampleFromDistribution originalSample;
    private final int MAX_NUMBER_OF_BOOTSTRAP_SIMULATIONS_CHOSEN = 3;
    private ArrayList<BootstrapSimulation> bootstrapSimulationsChosen = new ArrayList<>();


    public TypeOfObservations getTypeOfObservations() {
        return typeOfObservations;
    }

    public void setTypeOfObservations(TypeOfObservations typeOfObservations) {
        this.typeOfObservations = typeOfObservations;
    }

    public DataGeneratingProcess getDataGeneratingProcess() {
        return dataGeneratingProcess;
    }

    public void setDataGeneratingProcess(DataGeneratingProcess dataGeneratingProcess) {
        this.dataGeneratingProcess = dataGeneratingProcess;
    }

    public void setOriginalSample(SampleFromDistribution originalSample) {
        this.originalSample = originalSample;
    }

    public SampleFromDistribution getOriginalSample() {
        return originalSample;
    }

    public int getMAX_NUMBER_OF_BOOTSTRAP_SIMULATIONS_CHOSEN() {
        return MAX_NUMBER_OF_BOOTSTRAP_SIMULATIONS_CHOSEN;
    }

    public int getSizeOfBootstrapSimulationsChosen() {
        return bootstrapSimulationsChosen.size();
    }

    public ArrayList<BootstrapSimulation> getBootstrapSimulationsChosen() {
        return bootstrapSimulationsChosen;
    }

    public boolean isBootstrapSimulationsChosenFull() {
        return getSizeOfBootstrapSimulationsChosen() == MAX_NUMBER_OF_BOOTSTRAP_SIMULATIONS_CHOSEN;
    }

    public boolean isBootstrapSimulationsChosenEmpty() {
        return getSizeOfBootstrapSimulationsChosen() == 0;
    }

    public void addToBootstrapSimulationsChosen(BootstrapSimulation bootstrapSimulation) {
        bootstrapSimulationsChosen.add(bootstrapSimulation);
    }

    public void removeFromBootstrapSimulationsChosen() {
        bootstrapSimulationsChosen.remove(getSizeOfBootstrapSimulationsChosen() - 1);
    }
}
