package com.assignment1.controllers;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.awt.*;

public class AlgorithmRaceSimulator extends JFrame {

        // Simulated algorithm finish times in milliseconds
        private static final ConcurrentMap<String, Long> algorithmFinishTimes = new ConcurrentHashMap<>();

        private DefaultCategoryDataset dataset;
        private JFreeChart barChart;

        public AlgorithmRaceSimulator(ConcurrentMap<String, Long> algorithmFinishTimes)  {
            this.algorithmFinishTimes.putAll(algorithmFinishTimes);
            dataset = new DefaultCategoryDataset();
            barChart = ChartFactory.createBarChart(
                    "Algorithm Race Simulation",
                    "Algorithms",
                    "Completion %",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false);

            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new Dimension(800, 600));
            setContentPane(chartPanel);

            new Thread(this::simulateRace).start();
        }

        private void simulateRace() {

            for (long currentTime = 0; currentTime <= 1000L; currentTime += 10) {
                long finalCurrentTime = currentTime;
                SwingUtilities.invokeLater(() -> updateProgress(finalCurrentTime));
                try {
                    Thread.sleep(1000); // Slow down for visualization
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void updateProgress(long currentTime) {
            dataset.clear(); // Clear previous data points

            for (Map.Entry<String, Long> entry : algorithmFinishTimes.entrySet()) {
                String algorithm = entry.getKey();
                long finishTime = entry.getValue();

                // Calculate completion percentage for each algorithm
                double completion = Math.min((double) currentTime / finishTime * 100, 100);
                dataset.addValue(completion, "Completion %", algorithm); // Update dataset
            }

            // Redraw the chart with updated data
            barChart.fireChartChanged();
        }

}
