package Bootstrap;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.rank.Median;

public class Sample {

    private double[] observations;

    public Sample() {
    }

    public Sample(int numberOfObservations) {
        this.observations = new double[numberOfObservations];
    }

    public Sample(double[] observations) {
        this.observations = observations;
    }

    public double[] getObservations() {
        return observations;
    }

    public void setObservations(double[] observations) {
        this.observations = observations;
    }

    public int getSampleSize() {
        return observations.length;
    }

    public double getObservation(int index) {
        return getObservations()[index];
    }

    public void setObservation(double observation, int index) {
        observations[index] = observation;
    }

    public double computeMean() {
        return new Mean().evaluate(observations);
    }

    public double computeMedian() {
        return new Median().evaluate(observations);
    }

    public double[] getBlock(int start, int blockLength) {
        int size = observations.length;
        double[] valuesToReturn = new double[blockLength];
        for (int i = 0; i < blockLength; i++) {
            if (start + i < size) {
                valuesToReturn[i] = observations[start + i];
            } else {
                int indexCountingFromTheBeginning = (start + i) % size;
                valuesToReturn[i] = observations[indexCountingFromTheBeginning];
            }
        }
        return valuesToReturn;
    }

}