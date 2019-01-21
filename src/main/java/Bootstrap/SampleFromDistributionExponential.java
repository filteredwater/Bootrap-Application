package Bootstrap;

import org.apache.commons.math3.distribution.ExponentialDistribution;

public class SampleFromDistributionExponential extends SampleFromDistribution {

    public SampleFromDistributionExponential(double lambda, int size) {
        super(lambda, lambda * Math.log(2));
        this.setObservations(new ExponentialDistribution(lambda).sample(size));
    }


    @Override
    public String getNameOfDistribution() {
        return "exponential";
    }
}
