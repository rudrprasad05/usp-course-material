package com.assignment1.controllers;
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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static com.assignment1.Libs.calculateMedian;

public class MeanMedianSimulator {

    public static void plotResultsThirtyRun(Map<String, ArrayList<Integer>> algorithmComparisonCounter) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, ArrayList<Integer>> entry : algorithmComparisonCounter.entrySet()) {
            String algorithmName = entry.getKey();
            ArrayList<Integer> comparisons = entry.getValue();

            // Calculate best, mean, median, and worst
            int best = Collections.min(comparisons);
            int worst = Collections.max(comparisons);
            double mean = comparisons.stream().mapToInt(val -> val).average().orElse(0.0);
            double median = calculateMedian(comparisons);

            // Add data to the dataset
            dataset.addValue(best, "Best", algorithmName);
            dataset.addValue(mean, "Mean", algorithmName);
            dataset.addValue(median, "Median", algorithmName);
            dataset.addValue(worst, "Worst", algorithmName);
        }

        // Create the chart
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Sorting Algorithm Comparison",
                "Algorithm",
                "Number of Comparisons",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Display the chart in a window
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        ApplicationFrame chartFrame = new ApplicationFrame("Sorting Algorithm Performance");
        chartFrame.setContentPane(chartPanel);
        chartFrame.pack();
        chartFrame.setVisible(true);
        System.out.println("\n *** Close the graph window before proceeding *** \n");
    }

}
