package GUI;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableRow {

    private final SimpleStringProperty distribution;
    private final SimpleStringProperty valueObtained;
    private final SimpleDoubleProperty mean;
    private final SimpleDoubleProperty median;

    public TableRow(String distribution, String valueObtained, double mean, double median) {
        this.distribution = new SimpleStringProperty(distribution);
        this.valueObtained = new SimpleStringProperty(valueObtained);
        this.mean = new SimpleDoubleProperty(mean);
        this.median = new SimpleDoubleProperty(median);
    }

    public String getDistribution() {
        return distribution.get();
    }

    public void setDistribution(String distribution) {
        this.distribution.set(distribution);
    }

    public String getValueObtained() {
        return valueObtained.get();
    }

    public void setValueObtained(String valueObtained) {
        this.valueObtained.set(valueObtained);
    }

    public double getMean() {
        return mean.get();
    }

    public void setMean(double mean) {
        this.mean.set(mean);
    }

    public double getMedian() {
        return median.get();
    }

    public void setMedian(double median) {
        this.median.set(median);
    }
}

