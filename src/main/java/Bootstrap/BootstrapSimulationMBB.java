package Bootstrap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;


public class BootstrapSimulationMBB extends BootstrapSimulation {

    public BootstrapSimulationMBB(Sample originalSample, int numberOfBootstrapSamples, int blockLength) {
        super(originalSample, numberOfBootstrapSamples, blockLength);
        this.type = "MBB";
    }

    @Override
    public Sample resample(int blockLength) {

        final int sampleSize = getSampleSize();
        int numberOfBlocks = (int) Math.ceil(sampleSize / blockLength);
        int maxStartingIndexOfBlock = sampleSize - blockLength;
        double[] newObservations = new double[numberOfBlocks * blockLength];
        UniformIntegerDistribution randomBlockNumberGenerator = new UniformIntegerDistribution(0, maxStartingIndexOfBlock);

        for (int i = 0; i < numberOfBlocks; ++i) {
            double[] block = originalSample.getBlock(randomBlockNumberGenerator.sample(), blockLength);
            int startIndex = i * blockLength;
            for (int j = 0; j < blockLength; j++) {
                newObservations[startIndex + j] = block[j];
            }
        }

        newObservations = ArrayUtils.subarray(newObservations, 0, sampleSize);
        return new Sample(newObservations);
    }

    @Override
    public String getDescription() {
        return type + " (n = " + numberOfBootstrapSamples + "; block length = " + parameter + ")";
    }
}
