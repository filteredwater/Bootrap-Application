package GUI;

import Bootstrap.BootstrapSimulation;
import Runtime.Session;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Histogram {

    public static final int BINS = 100;
    public static final int HEIGHT = 600;
    public static final int WIDTH = 1000;
    private static final float BACKGROUND_ALPHA = 0f;
    private static final float FOREGROUND_ALPHA_FOR_ONE_BAR = 0.25f;
    private static final Paint[] paintArray = {
            new Color(0xFFFF0000, true),
            new Color(0xFFFFFF00, true),
            new Color(0xFF0000FF, true)
    };


    public void createHistogramOfMeans() {

        Session session = Session.getInstance();

        HistogramDataset histogramDataset = new HistogramDataset();
        histogramDataset.setType(HistogramType.RELATIVE_FREQUENCY);

        for (BootstrapSimulation bootstrapSimulation : session.getBootstrapSimulationsChosen()) {

            final String key = bootstrapSimulation.getDescription();
            final double[] means = bootstrapSimulation.getMeans();

            histogramDataset.addSeries(key, means, BINS);
        }

        JFreeChart histogram = buildHistogram(histogramDataset, "Bootstrap distribution of mean");

        displayHistogram(histogram);
    }

    public void createHistogramOfMedians() {

        Session session = Session.getInstance();

        HistogramDataset histogramDataset = new HistogramDataset();
        histogramDataset.setType(HistogramType.RELATIVE_FREQUENCY);

        for (BootstrapSimulation bootstrapSimulation : session.getBootstrapSimulationsChosen()) {

            final String key = bootstrapSimulation.getDescription();
            final double[] medians = bootstrapSimulation.getMedians();

            histogramDataset.addSeries(key, medians, BINS);
        }

        JFreeChart histogram = buildHistogram(histogramDataset, "Bootstrap distribution of medians");

        displayHistogram(histogram);

    }

    private void displayHistogram(JFreeChart histogram) {
        File histogramFile = new File("histogram.jpeg");

        try {
            ChartUtilities.saveChartAsJPEG(histogramFile, histogram, WIDTH, HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Group root = Controller.buildNewWindow(WIDTH, HEIGHT);

        ImageView histogramImageView = new ImageView();
        root.getChildren().add(histogramImageView);

        Image histogramImage = new Image(histogramFile.toURI().toString());
        histogramImageView.setImage(histogramImage);
        histogramFile.delete();
    }

    private JFreeChart buildHistogram(HistogramDataset histogramDataset, String title) {
        JFreeChart histogram = ChartFactory.createHistogram(
                title,
                null,
                null,
                histogramDataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        XYPlot plot = (XYPlot) histogram.getPlot();
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardXYBarPainter());
        renderer.setShadowVisible(false);
        plot.setBackgroundAlpha(BACKGROUND_ALPHA);
        plot.setForegroundAlpha(computeAlpha(histogramDataset));

        plot.setDrawingSupplier(new DefaultDrawingSupplier(
                paintArray,
                DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));

        return histogram;
    }

    private float computeAlpha(HistogramDataset dataset) {
        int numberOfSeries = dataset.getSeriesCount();
        return 1 - (numberOfSeries - 1) * FOREGROUND_ALPHA_FOR_ONE_BAR;
    }

}
