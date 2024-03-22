package form;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.awt.Color;
import java.awt.HeadlessException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import model.ModelCard;
import modul.Global;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.json.JSONException;
import org.json.JSONObject;

public class FormDashboard1 extends javax.swing.JPanel {

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

    public FormDashboard1() {
        initComponents();

        resetNilaiXYZ();

        cardPure.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Value", "X : 0  Y : 0  Z : 0", ""));
        cardRMS.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "RMS", "X : 0  Y : 0  Z : 0", ""));
        cardAverage.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Average", "0", ""));
        cardMax.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Max", "0", ""));
        cardMin.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Min", "0", ""));
        cardAlarm.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Alarm", "0", ""));

        initSerial();

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
        jPanel1 = new javax.swing.JPanel();
        lineAtas = new javax.swing.JPanel();
        batas = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panelLineX = new javax.swing.JPanel();
        panelLineY = new javax.swing.JPanel();
        panelLineZ = new javax.swing.JPanel();

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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        lineAtas.setBackground(new java.awt.Color(255, 255, 255));
        lineAtas.setPreferredSize(new java.awt.Dimension(10, 300));
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

        jPanel1.add(lineAtas, java.awt.BorderLayout.NORTH);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel batas;
    private component.Card cardAlarm;
    private component.Card cardAverage;
    private component.Card cardMax;
    private component.Card cardMin;
    private component.Card cardPure;
    private component.Card cardRMS;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel lineAtas;
    private javax.swing.JPanel panelAtas;
    private javax.swing.JPanel panelLineX;
    private javax.swing.JPanel panelLineY;
    private javax.swing.JPanel panelLineZ;
    // End of variables declaration//GEN-END:variables

    class SayHello extends TimerTask {

        @Override
        public void run() {
            Global g = new Global();
            System.out.println("Start " + g.getCurtimeFormat());
            initSerial();
        }
    }

    private void initTask() {
        // jalan tiap 5 detik
        Timer timer = new Timer();
        timer.schedule(new SayHello(), 0, 5000);
    }

    private void resetNilaiXYZ() {
        wadah_xa.clear();
        wadah_ya.clear();
        wadah_za.clear();
    }

    private void initSerial() {
        try {
            SerialPort[] portList = SerialPort.getCommPorts();
            if (portList.length > 0) {
                serial = portList[0];
                serial.setBaudRate(115200);
                serial.setNumDataBits(8);
                serial.setNumStopBits(1);
                serial.setParity(0);

                if (serial.isOpen()) {
                    serial.closePort();
                    System.out.println("Tertutup");

                    serial.openPort();
                    System.out.println("Terbuka");

                    ProgresBarClass runSimpan = new ProgresBarClass(serial);
                    runSimpan.execute();

                } else {
                    serial.openPort();
                    System.out.println("Terbuka");

                    ProgresBarClass runSimpan = new ProgresBarClass(serial);
                    runSimpan.execute();
                }
            }
        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private class ProgresBarClass extends SwingWorker<String, Void> {

        private final SerialPort serial;

        public ProgresBarClass(SerialPort serial) {
            this.serial = serial;
        }

        @Override
        protected void done() {

        }

        @Override
        protected String doInBackground() {
            SerialEventHandling(serial);
            String hasil = "sukses";
            return hasil;
        }
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
                System.out.println(e);
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

    private void SerialEventHandling(SerialPort activePort) {
        activePort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                byte[] data = event.getReceivedData();
                //String s = new String(data);
                for (int i = 0; i < data.length; i++) {
                    switch ((char) data[i]) {
                        case '{':
                            temp += (char) data[i];
                            break;
                        case '}':
                            temp += (char) data[i];
                            hasil = temp;
                            try {
                                JSONObject obj = new JSONObject(hasil);
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

                            } catch (JSONException ex) {
                            }

                            temp = "";
                            hasil = "";

                            break;

                        default:
                            temp += (char) data[i];
                            break;
                    }
                }
            }
        });
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
        axis.setFixedAutoRange(500.0);  // 60 seconds
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
        axis.setFixedAutoRange(500.0);  // 60 seconds
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
        axis.setFixedAutoRange(500.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(-20.0, 20.0);
        return result;
    }
}
