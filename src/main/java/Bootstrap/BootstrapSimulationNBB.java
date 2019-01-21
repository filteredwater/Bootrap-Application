package Bootstrap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;

public class BootstrapSimulationNBB extends BootstrapSimulation {

    public BootstrapSimulationNBB(Sample originalSample, int numberOfBootstrapSamples, int blockLength) {
        super(originalSample, numberOfBootstrapSamples, blockLength);
        this.type = "NBB";
    }

    @Override
    public Sample resample(int blockLength) {
        int sampleSize = getSampleSize();
        int numberOfBlocks = (int) Math.ceil(sampleSize / blockLength);
        int numberOfBlocksToSampleFrom = (int) Math.floor(sampleSize / blockLength);
        double[] newObservations = new double[numberOfBlocks * blockLength];
        UniformIntegerDistribution randomBlockNumberGenerator = new UniformIntegerDistribution(0, numberOfBlocksToSampleFrom - 1);

        for (int i = 0; i < numberOfBlocks; ++i) {
            int firstIndexOfBlock = randomBlockNumberGenerator.sample() * blockLength;
            double[] block = originalSample.getBlock(firstIndexOfBlock, blockLength);
            int startIndex = i * blockLength;
            for (int j = 0; j < blockLength; j++) {
                newObservations[startIndex + j] = block[j];
            }
        }

        newObservations = ArrayUtils.subarray(newObservations, 0, sampleSize);
        return new Sample(newObservations);
    }

    public String getDescription() {
        return type + " (n = " + numberOfBootstrapSamples + "; block length = " + parameter + ")";
    }

}
