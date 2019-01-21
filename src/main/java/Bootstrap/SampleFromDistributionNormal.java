package Bootstrap;

import org.apache.commons.math3.distribution.NormalDistribution;

public class SampleFromDistributionNormal extends SampleFromDistribution {

    public SampleFromDistributionNormal(double mu, double var, int size) {
        super(mu, mu);
        this.setObservations(new NormalDistribution(mu, var).sample(size));
    }

    @Override
    public String getNameOfDistribution() {
        return "normal";
    }
}
