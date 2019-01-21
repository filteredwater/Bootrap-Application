package Bootstrap;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;

public class BootstrapSimulationIID extends BootstrapSimulation {

    public BootstrapSimulationIID(Sample originalSample, int numberOfBootstrapSamples, int parameter) {
        super(originalSample, numberOfBootstrapSamples, parameter);
        this.type = "IID";
    }

    @Override
    public Sample resample(int parameter) {

        int sampleSize = getSampleSize();

        UniformIntegerDistribution randomIndexGenerator = new UniformIntegerDistribution(0, sampleSize - 1);
        int[] indexes = randomIndexGenerator.sample(sampleSize);

        double[] originalObservations = getOriginalObservations();

        Sample sample = new Sample(sampleSize);

        for (int i = 0; i < sampleSize; ++i) {
            sample.setObservation(originalObservations[indexes[i]], i);
        }

        return sample;

    }

    @Override
    public String getDescription() {
        return type + " (n = " + numberOfBootstrapSamples + parameter + ")";
    }

}
