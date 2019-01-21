package Bootstrap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTest {

    @Test
    public void computeMeanTest() {
        double[] observations = {-1, 0, 1, 10, 10, 10};
        Sample sample = new Sample(observations);

        assertEquals(sample.computeMean(), 5);
    }


    @Test
    public void computeMedianForOddTest() {
        double[] observations = {7, 11.5, 6};
        Sample sample = new Sample(observations);

        assertEquals(sample.computeMedian(), 7);
    }

    @Test
    public void computeMedianForEvenTest() {
        double[] observations = {0, 10, 11.5, 9};
        Sample sample = new Sample(observations);

        assertEquals(sample.computeMedian(), 9.5);
    }
}
