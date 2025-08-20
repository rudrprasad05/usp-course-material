package com.assignment1.controllers;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;

public class TimeComplexitySimulator extends JFrame {

    private DefaultCategoryDataset dataset;
    private ConcurrentMap<String, Map<Integer, Long>> algorithmFinishTime;
    private int currentStep = 0;
    private final int maxDataPoints;
    private final Timer timer; // Timer as a class-level variable

    public TimeComplexitySimulator(String title, ConcurrentMap<String, Map<Integer, Long>> algorithmFinishTime) {
        super(title);
        this.algorithmFinishTime = algorithmFinishTime;
        this.dataset = new DefaultCategoryDataset();
        this.maxDataPoints = algorithmFinishTime.values().stream().mapToInt(Map::size).max().orElse(0); // Get the max number of data points

        // Create the initial chart
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Sorting Algorithm Comparison",
                "Input Size (n)",
                "Time (ms)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Display the chart in a window
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);

        // Set up the timer to update the chart dynamically
        timer = new Timer(1000, e -> updateChart());
        timer.start();
    }

    // Method to update the dataset incrementally
    private void updateChart() {
        if (currentStep < maxDataPoints) {
            // Add data incrementally for each algorithm
            for (Map.Entry<String, Map<Integer, Long>> entry : algorithmFinishTime.entrySet()) {
                String algorithmName = entry.getKey();
                // Use TreeMap to ensure entries are sorted by key (n values)
                Map<Integer, Long> sortedTimeData = new TreeMap<>(entry.getValue());

                int stepCount = 0;
                for (Map.Entry<Integer, Long> dataPoint : sortedTimeData.entrySet()) {
                    if (stepCount > currentStep) break; // Only add up to the current step count
                    Integer inputSize = dataPoint.getKey();
                    Long timeInNs = dataPoint.getValue();
                    double timeInMs = timeInNs / 1_000_000.0; // Convert ns to ms for better readability
                    dataset.addValue(timeInMs, algorithmName, inputSize);
                    stepCount++;
                }
            }
            currentStep++;
        } else {
            // Stop the timer when all data points are added
            timer.stop();
        }
    }
}
