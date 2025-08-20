package org.example;
/*
 * @author
 * Rudr Prasad - S11219309
 * Ahad Ali - S11221529
 * Rishal Prasad - S11221067
 *
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;
import java.awt.*;
import java.util.concurrent.ConcurrentMap;

public class JFreeChartController {

    public static void createChart(ConcurrentMap<String, Long> timesMap) {
        // Create dataset
        CategoryDataset dataset = createDataset(timesMap);

        // Create chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Sorting Algorithm Performance",
                "Number of Elements",
                "Time (milliseconds)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // Create and display chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("Sorting Time Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        renderer.setMaximumBarWidth(0.5);
//        renderer.setItemMargin(0.2);

        // Set colors for each series
        renderer.setSeriesPaint(0, Color.decode("#CCCCFF"));    // Color for Merge ArrayList
        renderer.setSeriesPaint(1, Color.decode("#6495ED"));   // Color for Insertion ArrayList
        renderer.setSeriesPaint(2, Color.decode("#6CB4EE"));  // Color for Bubble ArrayList
        renderer.setSeriesPaint(3, Color.decode("#008E97")); // Color for Built-in ArrayList
        renderer.setSeriesPaint(4, Color.decode("#5A4FCF")); // Color for Merge LinkedList
        renderer.setSeriesPaint(5, Color.decode("#0066b2"));   // Color for Insertion LinkedList
        renderer.setSeriesPaint(6, Color.decode("#000FFF")); // Color for Bubble LinkedList
        renderer.setSeriesPaint(7, Color.decode("#002fa7"));
    }

    private static CategoryDataset createDataset(ConcurrentMap<String, Long> timesMap) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

//         Arbitrary number of elements
//        int[] numElements = {5, 10, 15, 20, 30, 50, 100};

        // Adding data to dataset
//        for (int i = 0; i < numElements.length; i++) {
            dataset.addValue(timesMap.get("MergeArray"), "Merge ArrayList", "ArrayList");
            dataset.addValue(timesMap.get("InsertionArray"), "Insertion ArrayList", "ArrayList");
            dataset.addValue(timesMap.get("BubbleArray"), "Bubble ArrayList", "ArrayList");
            dataset.addValue(timesMap.get("BuiltInArray"), "Built-in ArrayList", "ArrayList");
            dataset.addValue(timesMap.get("MergeLink"), "Merge LinkedList", "LinkedList");
            dataset.addValue(timesMap.get("InsertionLink"), "Insertion LinkedList", "LinkedList");
            dataset.addValue(timesMap.get("BubbleLink"), "Bubble LinkedList", "LinkedList");
            dataset.addValue(timesMap.get("BuiltILink"), "Built-in LinkedList", "LinkedList");
//        }

        return dataset;
    }
}
