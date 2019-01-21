package Bootstrap;

public abstract class SampleFromDistribution extends Sample {

    private double trueMean;
    private double trueMedian;

    public SampleFromDistribution(double trueMean, double trueMedian) {
        this.trueMean = trueMean;
        this.trueMedian = trueMedian;
    }

    public double getTrueMean() {
        return trueMean;
    }

    public double getTrueMedian() {
        return trueMedian;
    }

    public abstract String getNameOfDistribution();

}
