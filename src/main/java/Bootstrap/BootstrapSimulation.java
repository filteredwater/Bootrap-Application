package Bootstrap;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import java.util.ArrayList;

public abstract class BootstrapSimulation {

    protected Sample originalSample;
    protected int numberOfBootstrapSamples;
    protected ArrayList<Sample> bootstrapSamples = new ArrayList<>();
    protected String type;
    protected int parameter;


    public BootstrapSimulation(Sample originalSample, int numberOfBootstrapSamples, int parameter) {
        this.originalSample = originalSample;
        this.numberOfBootstrapSamples = numberOfBootstrapSamples;
        this.parameter = parameter;
        for (int i = 0; i < numberOfBootstrapSamples; i++) {
            this.bootstrapSamples.add(resample(parameter));
        }
    }

    public abstract Sample resample(int parameter);

    public abstract String getDescription();

    public double[] getMeans() {
        double[] means = new double[numberOfBootstrapSamples];

        for (int i = 0; i < numberOfBootstrapSamples; i++) {
            means[i] = bootstrapSamples.get(i).computeMean();
        }

        return means;
    }

    public double[] getMedians() {
        double[] medians = new double[numberOfBootstrapSamples];

        for (int i = 0; i < numberOfBootstrapSamples; i++) {
            medians[i] = bootstrapSamples.get(i).computeMedian();
        }

        return medians;
    }

    public double getEstimate(StatisticType statisticType) {
        switch (statisticType) {
            case MEAN:
                return new Mean().evaluate(getMeans());
            case MEDIAN:
                return new Mean().evaluate(getMedians());
            default:
                return Double.parseDouble(null);
        }
    }

    public double getVarianceEstimate(StatisticType statisticType) {

        double[] data = new double[0];

        switch (statisticType) {
            case MEAN:
                data = getMeans();
                break;
            case MEDIAN:
                data = getMedians();
                break;
        }

        return new Variance().evaluate(data);
    }

    public double getPercentileEstimate(StatisticType statisticType, double percentile) {

        double[] data = new double[0];

        switch (statisticType) {
            case MEAN:
                data = getMeans();
                break;
            case MEDIAN:
                data = getMedians();
                break;
        }
        return new Percentile(percentile).evaluate(data);
    }

    protected int getSampleSize() {
        return originalSample.getSampleSize();
    }

    protected double[] getOriginalObservations() {
        return originalSample.getObservations();
    }

    public String getType() {
        return this.type;
    }
}
