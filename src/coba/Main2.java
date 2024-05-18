/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package coba;

import static coba.Main.TRAINING_DATA;
import static coba.Main.ZERO;
import static coba.Main.displayInfoTables;
import static coba.Main.svm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.IntStream;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.commons.math3.linear.MatrixUtils;

/**
 *
 * @author RAMPA
 */
public class Main2 {

    static SupportVectorMachines svm;
            
    public static void main(String[] args) {
        double[][][] TRAINING_DATA = {{
            {9.123456, 3.123456}, {+1}},
            {{9.123456, 5.123456}, {+1}},
            {{5.123456, 5.123456}, {-1}},
            {{8.123456, 6.654321}, {+1}},
            {{4.654321, 4.123456}, {-1}},
            {{2.123456, 4.123456}, {-1}},
            {{9.123456, 7.123456}, {+1}},
            {{4.123456, 4.654321}, {-1}},
            {{8.654321, 2.123456}, {+1}},
            {{2.123456, 2.123456}, {-1}},
            {{3.123456, 3.123456}, {-1}},
            {{8.654321, 4.123456}, {+1}},
            {{7.123456, 6.123456}, {+1}},
            {{4.123456, 7.123456}, {-1}},
            {{6.923456, 4.623456}, {-1}},
            {{8.123456, 5.123456}, {+1}},
            {{3.123456, 4.123456}, {-1}}
        };
        
        double ZERO = 0.000000009;
        
    
        System.out.println(TRAINING_DATA.length);
        System.out.println("============================");
        double[][] xArray = new double[TRAINING_DATA.length][2];
        double[][] yArray = new double[TRAINING_DATA.length][1];
        for (int i = 0; i < TRAINING_DATA.length; i++) {
            xArray[i][0] = TRAINING_DATA[i][0][0];
            xArray[i][1] = TRAINING_DATA[i][0][1];
            yArray[i][0] = TRAINING_DATA[i][1][0];
            
            System.out.println(xArray[i][0] + " " + xArray[i][1] + " " + yArray[i][0]);
        }
        
        svm = svm = new SupportVectorMachines(MatrixUtils.createRealMatrix(xArray), MatrixUtils.createRealMatrix(yArray));
        displayInfoTables(xArray, yArray);
        launch();
    }
    
    static void displayInfoTables(double[][] xArray, double[][] yArray) {
        System.out.println("    Support Vector   | label | alpha");
        IntStream.range(0, 50).forEach(i -> System.out.print("-"));
        System.out.println();
        for (int i = 0; i < xArray.length; i++) {
            if (svm.getAlpha().getData()[i][0] > ZERO && svm.getAlpha().getData()[i][0] != SupportVectorMachines.C) {
                StringBuffer ySB = new StringBuffer(String.valueOf(yArray[i][0]));
                ySB.setLength(5);
                System.out.println(Arrays.toString(xArray[i]) + " | " + ySB + " | "
                        + new String(String.format("%.10f", svm.getAlpha().getData()[i][0])));
            }
        }
        System.out.println("\n             wT              |  b  ");
        IntStream.range(0, 50).forEach(i -> System.out.print("-"));
        System.out.println();
        System.out.println("<" + (new String(String.format("%.9f", svm.getW().getData()[0][0])) + ", "
                + new String(String.format("%.9f", svm.getW().getData()[1][0]))) + ">   | " + svm.getB());
    }

    static void handleCommandLine() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("\n> to classify new candidate enter scores for interviews 1 & 2 (or exit):");
            String[] values = (bufferedReader.readLine()).split(" ");
            if (values[0].equals("exit")) {
                System.exit(0);
            } else {
                try {
                    System.out.println(svm.classify(
                            MatrixUtils.createRealMatrix(new double[][]{{Double.valueOf(values[0]), Double.valueOf(values[1])}})));
                } catch (Exception e) {
                    System.out.println("invalid input");
                }
            }
        }
    }

    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(false);
        XYChart.Series<Number, Number> series01 = new XYChart.Series<Number, Number>();
        series01.setName("Candidate Not Hired");
        XYChart.Series<Number, Number> series02 = new XYChart.Series<Number, Number>();
        series02.setName("Candidate Hired");
        IntStream.range(0, Main.TRAINING_DATA.length).forEach(i -> {
            double x = Main.TRAINING_DATA[i][0][0], y = Main.TRAINING_DATA[i][0][1];
            if (Main.TRAINING_DATA[i][1][0] == -1.0) {
                series01.getData().add(new XYChart.Data<Number, Number>(x, y));
            } else {
                series02.getData().add(new XYChart.Data<Number, Number>(x, y));
            }
        });
        NumberAxis xAxis = new NumberAxis(0, 10, 1.0);
        xAxis.setLabel("Score for candidate interview # 1");
        NumberAxis yAxis = new NumberAxis(0, 10, 1.0);
        yAxis.setLabel("Score for candidate interview # 2");
        ScatterChart<Number, Number> scatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);
        scatterChart.getData().add(series01);
        scatterChart.getData().add(series02);
        double m = -(svm.getW().getData()[0][0] / svm.getW().getData()[1][0]);
        double b = -(svm.getB() / svm.getW().getData()[1][0]);
        double score1X = 0.00, score1Y = m * score1X + b, score2X = 10.00, score2Y = m * score2X + b;
        XYChart.Series<Number, Number> series03 = new XYChart.Series<Number, Number>();
        series03.getData().add(new XYChart.Data<Number, Number>(score1X, score1Y));
        series03.getData().add(new XYChart.Data<Number, Number>(score2X, score2Y));
        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.getData().add(series03);
        lineChart.setOpacity(0.4);
        Pane pane = new Pane();
        pane.getChildren().addAll(scatterChart, lineChart);
        stage.setScene(new Scene(pane, 550, 420));
        stage.setOnHidden(e -> {
            try {
                handleCommandLine();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        System.out.println("\nClose display window to proceed");
        stage.setTitle("Support Vector Machines (01) - w/ SMO (Sequential Minimal Optimization)");
        stage.show();
    }

}
