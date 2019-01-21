package Bootstrap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.distribution.GeometricDistribution;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;

public class BootstrapSimulationSB extends BootstrapSimulation {

    public BootstrapSimulationSB(Sample originalSample, int numberOfBootstrapSamples, int blockLength) {
        super(originalSample, numberOfBootstrapSamples, blockLength);
        this.type = "SB";
    }

    @Override
    public Sample resample(int blockLength) {

        int sampleSize = getSampleSize();
        double[] newObservations = null;
        UniformIntegerDistribution randomIndexGenerator = new UniformIntegerDistribution(0, sampleSize - 1);
        GeometricDistribution randomBlockLengthGenerator = new GeometricDistribution((double) 1 / blockLength);
        int currentSize = 0;

        while (currentSize < sampleSize) {
            int startingIndex = randomIndexGenerator.sample();
            int randomBlockLength = randomBlockLengthGenerator.sample() + 1;
            double[] block = originalSample.getBlock(startingIndex, randomBlockLength);
            newObservations = ArrayUtils.addAll(newObservations, block);
            currentSize = newObservations.length;
        }

        newObservations = ArrayUtils.subarray(newObservations, 0, sampleSize);
        return new Sample(newObservations);
    }


    public String getDescription() {
        return type + " (n = " + numberOfBootstrapSamples + "; expected block length = " + parameter + ")";
    }
}
