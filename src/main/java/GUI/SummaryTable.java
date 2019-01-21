package GUI;

import Bootstrap.BootstrapSimulation;
import Bootstrap.StatisticType;
import Runtime.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SummaryTable {

    private final static int WINDOW_WIDTH = 900;
    private final static int WINDOW_HEIGHT = 400;
    private final static int DEFAULT_COLUMN_WIDTH = 200;
    private static final double SIGNIFICANCE_LEVEL = 5;


    public void createSummaryTable(Session session) {


        Group root = Controller.buildNewWindow(WINDOW_WIDTH, WINDOW_HEIGHT);

        final TableView<TableRow> table = new TableView<>();
        final ObservableList<TableRow> data = FXCollections.observableArrayList();

        TableColumn distributionColumn = createTableColumn("distribution", 300);
        TableColumn valueObtainedColumn = createTableColumn("value obtained", "valueObtained", DEFAULT_COLUMN_WIDTH);
        TableColumn meanColumn = createTableColumn("mean", DEFAULT_COLUMN_WIDTH);
        TableColumn medianColumn = createTableColumn("median", DEFAULT_COLUMN_WIDTH);


        table.getColumns().addAll(distributionColumn, valueObtainedColumn, meanColumn, medianColumn);

        data.add(new TableRow(session.getOriginalSample().getNameOfDistribution(),
                "true value",
                session.getOriginalSample().getTrueMean(),
                session.getOriginalSample().getTrueMedian())
        );

        for (BootstrapSimulation bootstrapSimulation : session.getBootstrapSimulationsChosen()) {

            String description = bootstrapSimulation.getDescription();
            String valueObtained = "estimate";
            double mean = bootstrapSimulation.getEstimate(StatisticType.MEAN);
            double median = bootstrapSimulation.getEstimate(StatisticType.MEDIAN);
            data.add(new TableRow(description, valueObtained, mean, median));

        }

        for (BootstrapSimulation bootstrapSimulation : session.getBootstrapSimulationsChosen()) {

            String description = bootstrapSimulation.getDescription();
            String valueObtained = "variance";
            double mean = bootstrapSimulation.getVarianceEstimate(StatisticType.MEAN);
            double median = bootstrapSimulation.getVarianceEstimate(StatisticType.MEDIAN);
            data.add(new TableRow(description, valueObtained, mean, median));

        }

        for (BootstrapSimulation bootstrapSimulation : session.getBootstrapSimulationsChosen()) {

            String description = bootstrapSimulation.getDescription();
            String valueObtained = "lower bound of " + (100 - SIGNIFICANCE_LEVEL) + "% CI";
            double mean = bootstrapSimulation.getPercentileEstimate(StatisticType.MEAN, SIGNIFICANCE_LEVEL / 2);
            double median = bootstrapSimulation.getPercentileEstimate(StatisticType.MEDIAN, SIGNIFICANCE_LEVEL / 2);
            data.add(new TableRow(description, valueObtained, mean, median));

        }

        for (BootstrapSimulation bootstrapSimulation : session.getBootstrapSimulationsChosen()) {

            String description = bootstrapSimulation.getDescription();
            String valueObtained = "upper bound of " + (100 - SIGNIFICANCE_LEVEL) + "% CI";
            double mean = bootstrapSimulation.getPercentileEstimate(StatisticType.MEAN, 100 - SIGNIFICANCE_LEVEL / 2);
            double median = bootstrapSimulation.getPercentileEstimate(StatisticType.MEDIAN, 100 - SIGNIFICANCE_LEVEL / 2);
            data.add(new TableRow(description, valueObtained, mean, median));

        }

        table.setItems(data);

        root.getChildren().add(table);

    }


    private TableColumn createTableColumn(String title, String valueFactory, int width) {

        TableColumn column = new TableColumn(title);
        column.setPrefWidth(width);
        column.setCellValueFactory(
                new PropertyValueFactory<>(valueFactory));

        return column;
    }

    private TableColumn createTableColumn(String title, int width) {
        return createTableColumn(title, title, width);
    }

}
