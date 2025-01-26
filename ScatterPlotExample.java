import org.ejml.simple.SimpleMatrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.StandardTickUnitSource;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.axis.*;

import javax.swing.*;
import java.awt.*;

public class ScatterPlotExample extends ApplicationFrame {

    public ScatterPlotExample(String title, double[][] points) {
        super(title);
        // Create the dataset
        XYDataset dataset = createDataset(points);
        // Create the chart
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Auton Waypoints",  // Chart title
                "X-Axis",                // X-Axis Label
                "Y-Axis",                // Y-Axis Label
                dataset,                 // Dataset
                PlotOrientation.VERTICAL,
                true,                    // Show legend
                true,                    // Use tooltips
                false                    // Configure chart to generate URLs?
        );

        // Customize the chart
        XYPlot plot = (XYPlot) chart.getPlot();
        XYItemRenderer renderer = new XYLineAndShapeRenderer(false, true);
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.getDomainAxis().setRange(-72, 72);
        plot.getRangeAxis().setRange(-72, 72);
        TickUnits units = new TickUnits();
        units.add(new NumberTickUnit(-72));
        units.add(new NumberTickUnit(-48));
        units.add(new NumberTickUnit(-24));
        units.add(new NumberTickUnit(0));
        units.add(new NumberTickUnit(72));
        units.add(new NumberTickUnit(48));
        units.add(new NumberTickUnit(24));
        plot.getDomainAxis().setStandardTickUnits(units);
        plot.getRangeAxis().setStandardTickUnits(units);
        // Add the chart to a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 800));
        setContentPane(chartPanel);
    }

    private XYDataset createDataset(double[][] points) {
        // Create a series
        XYSeries series = new XYSeries("Random Data");
        for (int i = 0; i<points[0].length; i++) {
            series.add(points[0][i], points[1][i]);
        }

        // Add the series to a dataset
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProfiledPathGenerator gen = new ProfiledPathGenerator();
            ScatterPlotExample example = new ScatterPlotExample("AutonPoints", gen.result);
            example.pack();
            RefineryUtilities.centerFrameOnScreen(example);
            example.setVisible(true);
            example.setResizable(false);
        });
    }
}
