package org.syy.rz.charts; /**
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 */
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.NumberAxisBuilder;

/**
 * A sample that displays data in a stacked area chart.
 *
 * @see javafx.scene.chart.Chart
 * @see javafx.scene.chart.NumberAxis
 * @related charts/area/AreaChart
 */
public class StackedAreaChartSample extends Application {

    private void init(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setScene(new Scene(root));
        NumberAxis xAxis = NumberAxisBuilder.create()
                           .label("X Values")
                           .lowerBound(1.0d)
                           .upperBound(9.0d)
                           .tickUnit(2.0d).build();
                     
        NumberAxis yAxis = NumberAxisBuilder.create()
                           .label("Y Values")
                           .lowerBound(0.0d)
                           .upperBound(30.0d)
                           .tickUnit(2.0d).build();
                
        ObservableList<StackedAreaChart.Series> areaChartData = FXCollections.observableArrayList(
                new StackedAreaChart.Series("Series 1",FXCollections.observableArrayList(
                    new StackedAreaChart.Data(0,4),
                    new StackedAreaChart.Data(2,5),
                    new StackedAreaChart.Data(4,4),
                    new StackedAreaChart.Data(6,2),
                    new StackedAreaChart.Data(8,6),
                    new StackedAreaChart.Data(10,8)
                )),
                new StackedAreaChart.Series("Series 2", FXCollections.observableArrayList(
                    new StackedAreaChart.Data(0,8),
                    new StackedAreaChart.Data(2,2),
                    new StackedAreaChart.Data(4,9),
                    new StackedAreaChart.Data(6,7),
                    new StackedAreaChart.Data(8,5),
                    new StackedAreaChart.Data(10,7)
                )),
                new StackedAreaChart.Series("Series 3", FXCollections.observableArrayList(
                    new StackedAreaChart.Data(0,2),
                    new StackedAreaChart.Data(2,5),
                    new StackedAreaChart.Data(4,8),
                    new StackedAreaChart.Data(6,6),
                    new StackedAreaChart.Data(8,9),
                    new StackedAreaChart.Data(10,7),
                    new StackedAreaChart.Data(12,7),
                    new StackedAreaChart.Data(14,7),
                    new StackedAreaChart.Data(16,7)
                ))
        );
        StackedAreaChart chart = new StackedAreaChart(xAxis, yAxis, areaChartData);
        root.getChildren().add(chart);
    }

    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }
}
