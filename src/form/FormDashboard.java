package form;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.BasicStroke;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import model.ModelCard;
import modul.Global;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.JSONException;
import org.json.JSONObject;

public class FormDashboard extends javax.swing.JPanel {

    private SerialPort serial;
    private final String dataBuffer = "";
    private String temp = "";
    private String hasil = "";
    private Global g = new Global();

    private ArrayList<Double> wadah_xa = new ArrayList<>();
    private ArrayList<Double> wadah_ya = new ArrayList<>();
    private ArrayList<Double> wadah_za = new ArrayList<>();

    private TimeSeries seriesXA, seriesYA, seriesZA;
    private double lastValueXA = 0.0;
    private double lastValueYA = 0.0;
    private double lastValueZA = 0.0;
    private TimeSeriesCollection datasetXA, datasetYA, datasetZA;
    private JFreeChart chartXA, chartYA, chartZA;
    private ChartPanel chartPanelXA, chartPanelYA, chartPanelZA;

    public FormDashboard() {
        initComponents();

        resetNilaiXYZ();

        cardPure.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Value", "X : 0  Y : 0  Z : 0", ""));
        cardRMS.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "RMS", "X : 0  Y : 0  Z : 0", ""));
        cardAverage.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Average", "0", ""));
        cardMax.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Max", "0", ""));
        cardMin.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Min", "0", ""));
        cardAlarm.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Alarm", "0", ""));

        cbCom.removeAllItems();
        SerialPort[] portnames = SerialPort.getCommPorts();
        for (int i = 0; i < portnames.length; i++) {
            cbCom.addItem(portnames[i].getSystemPortName());
        }
        
        grafikFinal();

        seriesXA = new TimeSeries("X Axis", Millisecond.class);
        datasetXA = new TimeSeriesCollection(seriesXA);
        chartXA = createChart(datasetXA);
        chartPanelXA = new ChartPanel(chartXA);
        chartXA.setBackgroundPaint(Color.white);
        chartXA.getPlot().setBackgroundPaint(Color.white);
        panelLineX.add(chartPanelXA);
        panelLineX.setBackground(Color.white);

        seriesYA = new TimeSeries("Y Axis", Millisecond.class);
        datasetYA = new TimeSeriesCollection(seriesYA);
        chartYA = createChartY(datasetYA);
        chartPanelYA = new ChartPanel(chartYA);
        chartYA.setBackgroundPaint(Color.white);
        chartYA.getPlot().setBackgroundPaint(Color.white);
        panelLineY.add(chartPanelYA);
        panelLineY.setBackground(Color.white);

        seriesZA = new TimeSeries("Z Axis", Millisecond.class);
        datasetZA = new TimeSeriesCollection(seriesZA);
        chartZA = createChartZ(datasetZA);
        chartPanelZA = new ChartPanel(chartZA);
        chartZA.setBackgroundPaint(Color.white);
        chartZA.getPlot().setBackgroundPaint(Color.white);
        panelLineZ.add(chartPanelZA);
        panelLineZ.setBackground(Color.white);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelAtas = new javax.swing.JPanel();
        cardPure = new component.Card();
        cardRMS = new component.Card();
        cardAverage = new component.Card();
        cardMax = new component.Card();
        cardMin = new component.Card();
        cardAlarm = new component.Card();
        panelContent = new javax.swing.JPanel();
        lineAtas = new javax.swing.JPanel();
        batas = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panelLineX = new javax.swing.JPanel();
        panelLineY = new javax.swing.JPanel();
        panelLineZ = new javax.swing.JPanel();
        lineTengah = new javax.swing.JPanel();
        batasAtas = new javax.swing.JPanel();
        panelGrafikTengah = new javax.swing.JPanel();
        panelBawah = new javax.swing.JPanel();
        cbCom = new javax.swing.JComboBox<>();
        btnConnect = new javax.swing.JButton();

        setBackground(new java.awt.Color(250, 250, 250));
        setLayout(new java.awt.BorderLayout());

        panelAtas.setBackground(new java.awt.Color(255, 255, 255));
        panelAtas.setInheritsPopupMenu(true);
        panelAtas.setPreferredSize(new java.awt.Dimension(1118, 140));
        panelAtas.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        cardPure.setColor1(new java.awt.Color(130, 125, 215));
        cardPure.setColor2(new java.awt.Color(54, 49, 128));
        panelAtas.add(cardPure);

        cardRMS.setColor1(new java.awt.Color(165, 32, 83));
        cardRMS.setColor2(new java.awt.Color(165, 32, 83));
        panelAtas.add(cardRMS);

        cardAverage.setColor1(new java.awt.Color(225, 108, 210));
        cardAverage.setColor2(new java.awt.Color(133, 37, 120));
        panelAtas.add(cardAverage);

        cardMax.setColor1(new java.awt.Color(111, 214, 103));
        cardMax.setColor2(new java.awt.Color(31, 107, 25));
        panelAtas.add(cardMax);

        cardMin.setColor1(new java.awt.Color(21, 238, 225));
        cardMin.setColor2(new java.awt.Color(6, 176, 166));
        panelAtas.add(cardMin);

        cardAlarm.setColor1(new java.awt.Color(189, 143, 9));
        cardAlarm.setColor2(new java.awt.Color(88, 70, 10));
        panelAtas.add(cardAlarm);

        add(panelAtas, java.awt.BorderLayout.NORTH);

        panelContent.setBackground(new java.awt.Color(255, 255, 255));
        panelContent.setLayout(new java.awt.BorderLayout());

        lineAtas.setBackground(new java.awt.Color(255, 255, 255));
        lineAtas.setPreferredSize(new java.awt.Dimension(10, 250));
        lineAtas.setLayout(new java.awt.BorderLayout());

        batas.setBackground(new java.awt.Color(255, 255, 255));
        batas.setPreferredSize(new java.awt.Dimension(10, 15));
        lineAtas.add(batas, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        panelLineX.setBackground(new java.awt.Color(255, 255, 255));
        panelLineX.setLayout(new java.awt.BorderLayout());
        jPanel2.add(panelLineX);

        panelLineY.setBackground(new java.awt.Color(255, 255, 255));
        panelLineY.setLayout(new java.awt.BorderLayout());
        jPanel2.add(panelLineY);

        panelLineZ.setBackground(new java.awt.Color(255, 255, 255));
        panelLineZ.setLayout(new java.awt.BorderLayout());
        jPanel2.add(panelLineZ);

        lineAtas.add(jPanel2, java.awt.BorderLayout.CENTER);

        panelContent.add(lineAtas, java.awt.BorderLayout.NORTH);

        lineTengah.setBackground(new java.awt.Color(255, 255, 255));
        lineTengah.setLayout(new java.awt.BorderLayout());

        batasAtas.setBackground(new java.awt.Color(255, 255, 255));
        lineTengah.add(batasAtas, java.awt.BorderLayout.NORTH);

        panelGrafikTengah.setBackground(new java.awt.Color(255, 255, 255));
        panelGrafikTengah.setLayout(new java.awt.BorderLayout());
        lineTengah.add(panelGrafikTengah, java.awt.BorderLayout.CENTER);

        panelContent.add(lineTengah, java.awt.BorderLayout.CENTER);

        add(panelContent, java.awt.BorderLayout.CENTER);

        panelBawah.setBackground(new java.awt.Color(255, 255, 255));
        panelBawah.setLayout(new java.awt.FlowLayout(3));

        panelBawah.add(cbCom);

        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });
        panelBawah.add(btnConnect);

        add(panelBawah, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        if (btnConnect.getText().equals("Connect")) {
            serial = SerialPort.getCommPort(cbCom.getSelectedItem().toString());
            serial.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
            serial.setBaudRate(115200);
            serial.setNumDataBits(8);
            serial.setNumStopBits(1);
            serial.setParity(0);

            if (serial.openPort()) {
                btnConnect.setText("Disconnect");
                cbCom.setEnabled(false);
            }

            Thread thread;
            thread = new Thread() {

                @Override
                public void run() {
                    Scanner scanner = new Scanner(serial.getInputStream());
                    while (scanner.hasNextLine()) {
                        try {
                            String line = scanner.nextLine();
                            if (line.startsWith("{") && line.endsWith("}")) {
                                JSONObject obj = new JSONObject(line);
                                double xa = Double.parseDouble(obj.get("xa").toString());
                                double ya = Double.parseDouble(obj.get("ya").toString());
                                double za = Double.parseDouble(obj.get("za").toString());

                                cardPure.setValue("X : " + xa + "  Y : " + ya + "  Z : " + za);
                                masukkandata(xa, ya, za);

                                SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
                                    @Override
                                    public String doInBackground() {
                                        try {
                                            seriesXA.add(new Millisecond(), xa);
                                            seriesYA.add(new Millisecond(), ya);
                                            seriesZA.add(new Millisecond(), za);
                                        } catch (Exception ex) {
                                        }

                                        return "";
                                    }

                                    @Override
                                    protected void done() {

                                    }
                                };
                                worker.execute();
                            }
                        } catch (NumberFormatException | JSONException e) {
                        }
                    }
                    scanner.close();
                }
            };
            thread.start();
        } else {
            serial.closePort();
            cbCom.setEnabled(true);
            btnConnect.setText("Connect");
        }
    }//GEN-LAST:event_btnConnectActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel batas;
    private javax.swing.JPanel batasAtas;
    private javax.swing.JButton btnConnect;
    private component.Card cardAlarm;
    private component.Card cardAverage;
    private component.Card cardMax;
    private component.Card cardMin;
    private component.Card cardPure;
    private component.Card cardRMS;
    private javax.swing.JComboBox<String> cbCom;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel lineAtas;
    private javax.swing.JPanel lineTengah;
    private javax.swing.JPanel panelAtas;
    private javax.swing.JPanel panelBawah;
    private javax.swing.JPanel panelContent;
    private javax.swing.JPanel panelGrafikTengah;
    private javax.swing.JPanel panelLineX;
    private javax.swing.JPanel panelLineY;
    private javax.swing.JPanel panelLineZ;
    // End of variables declaration//GEN-END:variables

    private void resetNilaiXYZ() {
        wadah_xa.clear();
        wadah_ya.clear();
        wadah_za.clear();
    }

    private void grafikFinal() {
        String chartTitle = "XYZ Movement";
        String xAxisLabel = "X";
        String yAxisLabel = "Y";

        XYDataset dataset = createDatasetFinal();

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset, PlotOrientation.HORIZONTAL, true, true, true);
        customizeChart(chart);
        
        ChartPanel cp = new ChartPanel(chart);
        panelGrafikTengah.add(cp);
        
        chart.setBackgroundPaint(Color.white);
        chart.getPlot().setBackgroundPaint(Color.white);
        panelGrafikTengah.setBackground(Color.white);
    }

    private XYDataset createDatasetFinal() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Object 1");
        XYSeries series2 = new XYSeries("Object 2");
        XYSeries series3 = new XYSeries("Object 3");

        series1.add(1.0, 2.0);
        series1.add(2.0, 3.0);
        series1.add(3.0, 2.5);
        series1.add(3.5, 2.8);
        series1.add(4.2, 6.0);

        series2.add(2.0, 1.0);
        series2.add(2.5, 2.4);
        series2.add(3.2, 1.2);
        series2.add(3.9, 2.8);
        series2.add(4.6, 3.0);

        series3.add(1.2, 4.0);
        series3.add(2.5, 4.4);
        series3.add(3.8, 4.2);
        series3.add(4.3, 3.8);
        series3.add(4.5, 4.0);

        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        return dataset;
    }

    private void customizeChart(JFreeChart chart) {   // here we make some customization
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // sets paint color for each series
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.YELLOW);

        // sets thickness for series (using strokes)
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));

        // sets paint color for plot outlines
        plot.setOutlinePaint(Color.BLUE);
        plot.setOutlineStroke(new BasicStroke(2.0f));

        // sets renderer for lines
        plot.setRenderer(renderer);

        // sets plot background
        plot.setBackgroundPaint(Color.DARK_GRAY);

        // sets paint color for the grid lines
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

    }

    private void masukkandata(double x, double y, double z) {
        if (wadah_xa.size() > 93) {
            double jml_xa = 0;
            double jml_ya = 0;
            double jml_za = 0;
            try {

                double rata_xa = 0;
                double rata_ya = 0;
                double rata_za = 0;

                for (int i = 0; i < wadah_xa.size(); i++) {
                    jml_xa += wadah_xa.get(i);
                    jml_ya += wadah_ya.get(i);
                    jml_za += wadah_za.get(i);
                }
                rata_xa = jml_xa / 94;
                rata_ya = jml_ya / 94;
                rata_za = jml_za / 94;

                String a = g.pembulatan(jml_xa);
                String b = g.pembulatan(jml_ya);
                String c = g.pembulatan(jml_za);
                cardRMS.setValue("X : " + a + "  Y : " + b + "  Z : " + c);

                // menghitung rata2
                a = g.pembulatan(rata_xa);
                b = g.pembulatan(rata_ya);
                c = g.pembulatan(rata_za);
                cardAverage.setValue("X : " + a + "  Y : " + b + "  Z : " + c);

                // mencari maximal
                double max_x = Collections.max(wadah_xa);
                double max_y = Collections.max(wadah_ya);
                double max_z = Collections.max(wadah_za);
                a = g.pembulatan(max_x);
                b = g.pembulatan(max_y);
                c = g.pembulatan(max_z);
                cardMax.setValue("X : " + a + "  Y : " + b + "  Z : " + c);

                // mencari minimal
                double min_x = Collections.min(wadah_xa);
                double min_y = Collections.min(wadah_ya);
                double min_z = Collections.min(wadah_za);
                a = g.pembulatan(min_x);
                b = g.pembulatan(min_y);
                c = g.pembulatan(min_z);
                cardMin.setValue("X : " + a + "  Y : " + b + "  Z : " + c);

            } catch (NumberFormatException e) {
                jml_xa = 0;
                jml_ya = 0;
                jml_za = 0;
            }
            resetNilaiXYZ();
            jml_xa = 0;
            jml_ya = 0;
            jml_za = 0;
        }

        wadah_xa.add(x);
        wadah_ya.add(y);
        wadah_za.add(z);

    }

    private JFreeChart createChart(final XYDataset dataset) {
        JFreeChart result = ChartFactory.createTimeSeriesChart(
                "X Axis",
                "Time",
                "Value",
                dataset,
                true,
                true,
                false
        );
        XYPlot plot = result.getXYPlot();
        DateAxis dateAxis = new DateAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat("HH:m:ss"));
        plot.setDomainAxis(dateAxis);

        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(40000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(-20.0, 20.0);
        return result;
    }

    private JFreeChart createChartY(final XYDataset dataset) {
        JFreeChart result = ChartFactory.createTimeSeriesChart(
                "Y Axis",
                "Time",
                "Value",
                dataset,
                true,
                true,
                false
        );
        XYPlot plot = result.getXYPlot();
        DateAxis dateAxis = new DateAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat("HH:m:ss"));
        plot.setDomainAxis(dateAxis);

        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(40000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(-20.0, 20.0);
        return result;
    }

    private JFreeChart createChartZ(final XYDataset dataset) {
        JFreeChart result = ChartFactory.createTimeSeriesChart(
                "Z Axis",
                "Time",
                "Value",
                dataset,
                true,
                true,
                false
        );
        XYPlot plot = result.getXYPlot();
        DateAxis dateAxis = new DateAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat("HH:m:ss"));
        plot.setDomainAxis(dateAxis);

        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(40000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(-20.0, 20.0);
        return result;
    }
}
