package org.example.stockmanagementsystem.helperClasses;

import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import org.example.stockmanagementsystem.DAOclasses.PriceDAO;
import org.example.stockmanagementsystem.DAOclasses.StockDAO;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class StockChart {
    private final LineChart<String, Number> chart;
    private final Label stockNameLabel;
    private final Label maxPriceLabel;
    private final Label minPriceLabel;
    private String stockName;

    public StockChart(LineChart<String, Number> chart, Label stockNameLabel, Label maxPriceLabel, Label minPriceLabel) {
        this.chart = chart;
        this.stockNameLabel = stockNameLabel;
        this.maxPriceLabel = maxPriceLabel;
        this.minPriceLabel = minPriceLabel;
    }

    public void displayStockChart(int stockId, PriceDAO priceDAO, StockDAO stockDAO) {
        System.out.println("Displaying stock chart for stock ID: " + stockId);
        try {
            String stockName = stockDAO.getStockName(stockId);
            Map<Integer, List<Timestamp>> allTimestamps = priceDAO.getAllTimestamps();
            Map<Integer, List<Double>> allPrices = priceDAO.getAllPrices();

            List<Timestamp> timestamps = allTimestamps.get(stockId);
            List<Double> prices = allPrices.get(stockId);

            chart.getData().clear();

            if (stockName != null && timestamps != null && prices != null && !timestamps.isEmpty() && !prices.isEmpty()) {
                setStockName(stockName);
                updateChart(timestamps, prices);
            } else {
                System.out.println("No data available for the selected stock.");
            }
        } catch (Exception e) {
            System.err.println("Error displaying stock chart: " + e.getMessage());
        }
    }


    private void updateChart(List<Timestamp> timestamps, List<Double> prices) {
        if (timestamps == null || prices == null || timestamps.isEmpty() || prices.isEmpty()) {
            System.out.println("Timestamps or price history data is empty or null.");
            return;
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Price History");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        double maxPrice = Double.MIN_VALUE;
        double minPrice = Double.MAX_VALUE;
        int maxIndex = 0;
        int minIndex = 0;

        for (int i = 0; i < prices.size(); i++) {
            String dateString = sdf.format(timestamps.get(i));
            double price = prices.get(i);
            XYChart.Data<String, Number> data = new XYChart.Data<>(dateString, price);
            series.getData().add(data);

            if (price > maxPrice) {
                maxPrice = price;
                maxIndex = i;
            }
            if (price < minPrice) {
                minPrice = price;
                minIndex = i;
            }
        }

        chart.getData().clear();
        chart.getData().add(series);

        highlightMinMaxPrice(series, maxIndex, minIndex);

        maxPriceLabel.setText(maxPrice + "$");
        minPriceLabel.setText(minPrice + "$");

        stockNameLabel.setText(stockName);

        chart.setOnMouseMoved(event -> {
            for (XYChart.Data<String, Number> data : series.getData()) {
                Node node = data.getNode();
                if (node != null && node.getBoundsInParent().contains(event.getX(), event.getY())) {
                    Tooltip tooltip = new Tooltip(sdf.format(timestamps.get(series.getData().indexOf(data))) + ", $" + data.getYValue());
                    Tooltip.install(node, tooltip);
                    break;
                }
            }
        });
    }
    private void highlightMinMaxPrice(XYChart.Series<String, Number> series, int maxIndex, int minIndex) {
        if (maxIndex != -1) {
            XYChart.Data<String, Number> maxData = series.getData().get(maxIndex);
            if (maxData != null && maxData.getNode() != null) {
                maxData.getNode().setStyle("-fx-background-color: green;");
            }
        }
        if (minIndex != -1) {
            XYChart.Data<String, Number> minData = series.getData().get(minIndex);
            if (minData != null && minData.getNode() != null) {
                minData.getNode().setStyle("-fx-background-color: red;");
            }
        }
    }
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}