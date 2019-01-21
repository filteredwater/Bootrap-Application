package Bootstrap;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.List;

public class SampleFromDistributionMA extends SampleFromDistribution {

    public SampleFromDistributionMA(List<Double> thetas, double mu, int size) {

        super(mu, mu);

        int numberOfThetas = thetas.size();

        double[] whiteNoises = new NormalDistribution().sample(size + numberOfThetas);

        double[] observations = new double[size];

        for (int i = 0; i < size; ++i) {
            observations[i] = mu;
            for (int j = 0; j < numberOfThetas; ++j) {
                observations[i] += thetas.get(j) * whiteNoises[i + j];
            }
        }

        this.setObservations(observations);

    }

    @Override
    public String getNameOfDistribution() {
        return "MA process";
    }
}
