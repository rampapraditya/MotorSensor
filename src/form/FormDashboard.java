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
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.json.JSONException;
import org.json.JSONObject;

public class FormDashboard extends javax.swing.JPanel {

    private SerialPort serial;
    private final String dataBuffer = "";
    private final String temp = "";
    private final String hasil = "";
    private final Global g = new Global();

    private final ArrayList<Double> wadah_xa = new ArrayList<>();
    private final ArrayList<Double> wadah_ya = new ArrayList<>();
    private final ArrayList<Double> wadah_za = new ArrayList<>();

    private double lastValueXA = 0.0;
    private double lastValueYA = 0.0;
    private double lastValueZA = 0.0;

    // untuk final
    private final TimeSeriesCollection datasetFinal = new TimeSeriesCollection();

    // XA
    private final TimeSeriesCollection datasetXA = new TimeSeriesCollection();
    private final TimeSeries seriesXA = new TimeSeries("X Axis", Millisecond.class);
    // YA
    private final TimeSeriesCollection datasetYA = new TimeSeriesCollection();
    private final TimeSeries seriesYA = new TimeSeries("Y Axis", Millisecond.class);
    // ZA
    private final TimeSeriesCollection datasetZA = new TimeSeriesCollection();
    private final TimeSeries seriesZA = new TimeSeries("Z Axis", Millisecond.class);

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

        XYDataset datasetTempXA = createDatasetXA();
        JFreeChart chartXA = createChart(datasetTempXA, "X Axis", "Time", "Value");
        chartXA.setBackgroundPaint(Color.white);
        ChartPanel chartPanelXA = new ChartPanel(chartXA);
        panelLineX.add(chartPanelXA);
        panelLineX.setBackground(Color.white);

        XYDataset datasetTempYA = createDatasetYA();
        JFreeChart chartYA = createChart(datasetTempYA, "Y Axis", "Time", "Value");
        chartYA.setBackgroundPaint(Color.white);
        ChartPanel chartPanelYA = new ChartPanel(chartYA);
        panelLineY.add(chartPanelYA);
        panelLineY.setBackground(Color.white);

        XYDataset datasetTempZA = createDatasetZA();
        JFreeChart chartZA = createChart(datasetTempZA, "Z Axis", "Time", "Value");
        chartZA.setBackgroundPaint(Color.white);
        ChartPanel chartPanelZA = new ChartPanel(chartZA);
        panelLineZ.add(chartPanelZA);
        panelLineZ.setBackground(Color.white);

        XYDataset datasetFn = createDataset();
        JFreeChart chart = createChart(datasetFn, "XYZ Axis", "Time", "Value");
        chart.setBackgroundPaint(Color.white);
        ChartPanel chartPanel = new ChartPanel(chart);
        panelGrafikTengah.add(chartPanel);
        panelGrafikTengah.setBackground(Color.white);
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
        btnCalibrasi = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnAnalisis = new javax.swing.JButton();

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
        panelBawah.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        panelBawah.add(cbCom);

        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });
        panelBawah.add(btnConnect);

        btnCalibrasi.setText("Calibrasi");
        panelBawah.add(btnCalibrasi);

        btnSave.setText("Save");
        panelBawah.add(btnSave);

        btnAnalisis.setText("Analisis");
        panelBawah.add(btnAnalisis);

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

                                try {
                                    Millisecond milis = new Millisecond();
                                    seriesXA.add(milis, xa);
                                    seriesYA.add(milis, ya);
                                    seriesZA.add(milis, za);
                                } catch (Exception ex) {
                                }
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
    private javax.swing.JButton btnAnalisis;
    private javax.swing.JButton btnCalibrasi;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnSave;
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

    private XYDataset createDatasetXA() {
        datasetXA.addSeries(seriesXA);
        return datasetXA;
    }

    private XYDataset createDatasetYA() {
        datasetYA.addSeries(seriesYA);
        return datasetYA;
    }

    private XYDataset createDatasetZA() {
        datasetZA.addSeries(seriesZA);
        return datasetZA;
    }

    private JFreeChart createChart(final XYDataset dataset, String judul, String judulX, String judulY) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(judul, judulX, judulY, dataset, true, true, false);

        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer renderer = plot.getRenderer();
        if (renderer instanceof StandardXYItemRenderer) {
            StandardXYItemRenderer rr = (StandardXYItemRenderer) renderer;
            rr.setPlotLines(true);
            rr.setShapesFilled(true);
            rr.setItemLabelsVisible(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("HH:m:ss"));
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);

        return chart;
    }

    private XYDataset createDataset() {
        datasetFinal.addSeries(seriesXA);
        datasetFinal.addSeries(seriesYA);
        datasetFinal.addSeries(seriesZA);

        return datasetFinal;
    }

    private void customizeChartFinal(JFreeChart chart) {   // here we make some customization
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // sets paint color for each series
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.YELLOW);

        // sets thickness for series (using strokes)
        renderer.setSeriesStroke(0, new BasicStroke(0.0001f));
        renderer.setSeriesStroke(1, new BasicStroke(0.0001f));
        renderer.setSeriesStroke(2, new BasicStroke(0.0001f));

        // sets paint color for plot outlines
        plot.setOutlinePaint(Color.white);
        plot.setOutlineStroke(new BasicStroke(0.0001f));

        // sets renderer for lines
        plot.setRenderer(renderer);

        // sets plot background
        plot.setBackgroundPaint(Color.WHITE);

        // sets paint color for the grid lines
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
    }
}
