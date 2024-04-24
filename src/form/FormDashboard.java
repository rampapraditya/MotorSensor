package form;

import com.fazecast.jSerialComm.SerialPort;
import com.opencsv.CSVWriter;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.ModelCard;
import modul.Global;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
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

    private final ArrayList<String> wadah_waktuFull = new ArrayList<>();
    private final ArrayList<Double> wadah_xaFull = new ArrayList<>();
    private final ArrayList<Double> wadah_yaFull = new ArrayList<>();
    private final ArrayList<Double> wadah_zaFull = new ArrayList<>();
    
    private final ArrayList<Double> wadah_xa = new ArrayList<>();
    private final ArrayList<Double> wadah_ya = new ArrayList<>();
    private final ArrayList<Double> wadah_za = new ArrayList<>();

    private boolean statusKalibrasi = false;
    private double nilaiAcuanX = 0.0;
    private String tandaBacaX = "+";

    private double nilaiAcuanY = 0.0;
    private String tandaBacaY = "+";

    private double nilaiAcuanZ = 0.0;
    private String tandaBacaZ = "+";

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

        cardPure.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Value", "0", "0", "0", ""));
        cardRMS.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "RMS", "0", "0", "0", ""));
        cardAverage.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Average", "0", "0", "0", ""));
        cardMax.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Max", "0", "0", "0", ""));
        cardMin.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Min", "0", "0", "0", ""));
        cardAlarm.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Alarm", "0", "0", "0", ""));

        cbCom.removeAllItems();
        SerialPort[] portnames = SerialPort.getCommPorts();
        for (int i = 0; i < portnames.length; i++) {
            cbCom.addItem(portnames[i].getSystemPortName());
        }

        XYDataset datasetTempXA = createDatasetXA();
        JFreeChart chartXA = createChart(datasetTempXA, "X Axis", "Time", "Acceleration", 0);
        chartXA.setBackgroundPaint(Color.white);
        ChartPanel chartPanelXA = new ChartPanel(chartXA);
        panelLineX.add(chartPanelXA);
        panelLineX.setBackground(Color.white);

        XYDataset datasetTempYA = createDatasetYA();
        JFreeChart chartYA = createChart(datasetTempYA, "Y Axis", "Time", "Acceleration", 1);
        chartYA.setBackgroundPaint(Color.white);
        ChartPanel chartPanelYA = new ChartPanel(chartYA);
        panelLineY.add(chartPanelYA);
        panelLineY.setBackground(Color.white);

        XYDataset datasetTempZA = createDatasetZA();
        JFreeChart chartZA = createChart(datasetTempZA, "Z Axis", "Time", "Acceleration", 2);
        chartZA.setBackgroundPaint(Color.white);
        ChartPanel chartPanelZA = new ChartPanel(chartZA);
        panelLineZ.add(chartPanelZA);
        panelLineZ.setBackground(Color.white);

        XYDataset datasetFn = createDataset();
        JFreeChart chart = createChart(datasetFn, "XYZ Axis", "Time", "Acceleration", 3);
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
        btnCalibrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalibrasiActionPerformed(evt);
            }
        });
        panelBawah.add(btnCalibrasi);

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
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
                    try (Scanner scanner = new Scanner(serial.getInputStream())) {
                        while (scanner.hasNextLine()) {
                            try {
                                String line = scanner.nextLine();
                                if (line.startsWith("{") && line.endsWith("}")) {
                                    JSONObject obj = new JSONObject(line);
                                    double xa = Double.parseDouble(obj.get("xa").toString());
                                    double ya = Double.parseDouble(obj.get("ya").toString());
                                    double za = Double.parseDouble(obj.get("za").toString());
                                    String waktu = g.curTime(new Date());
                                    
                                    if (statusKalibrasi) {
                                        if (tandaBacaX.equals("+")) {
                                            xa += nilaiAcuanX;
                                        } else {
                                            xa -= nilaiAcuanX;
                                        }
                                        
                                        if (tandaBacaY.equals("+")) {
                                            ya += nilaiAcuanY;
                                        } else {
                                            ya -= nilaiAcuanY;
                                        }
                                        
                                        if (tandaBacaZ.equals("+")) {
                                            za += nilaiAcuanZ;
                                        } else {
                                            za -= nilaiAcuanZ;
                                        }
                                        
                                        String a = g.pembulatan(xa);
                                        String b = g.pembulatan(ya);
                                        String c = g.pembulatan(za);
                                        
                                        cardPure.setValue("X : " + a);
                                        cardPure.setValue1("Y : " + b);
                                        cardPure.setValue2("Z : " + c);
                                        
                                        
                                        masukkandata(waktu, xa, ya, za);
                                        
                                        try {
                                            Millisecond milis = new Millisecond();
                                            seriesXA.add(milis, xa);
                                            seriesYA.add(milis, ya);
                                            seriesZA.add(milis, za);
                                        } catch (Exception ex) {
                                        }
                                        
                                    } else {

                                        String a = g.pembulatan(xa);
                                        String b = g.pembulatan(ya);
                                        String c = g.pembulatan(za);
                                        
                                        cardPure.setValue("X : " + a);
                                        cardPure.setValue1("Y : " + b);
                                        cardPure.setValue2("Z : " + c);
                                        
                                        masukkandata(waktu, xa, ya, za);
                                        
                                        try {
                                            Millisecond milis = new Millisecond();
                                            seriesXA.add(milis, xa);
                                            seriesYA.add(milis, ya);
                                            seriesZA.add(milis, za);
                                        } catch (Exception ex) {
                                        }
                                    }
                                }
                            } catch (NumberFormatException | JSONException e) {
                            }
                        }
                    }
                }
            };
            thread.start();
        } else {
            serial.closePort();
            cbCom.setEnabled(true);
            btnConnect.setText("Connect");

        }
    }//GEN-LAST:event_btnConnectActionPerformed

    private void btnCalibrasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalibrasiActionPerformed
        statusKalibrasi = true;

        double nilaiX = Collections.max(wadah_xa);
        double nilaiY = Collections.max(wadah_ya);
        double nilaiZ = Collections.max(wadah_za);

        // menentukan nilai acuan
        if (nilaiX > 0) {
            nilaiAcuanX = nilaiX;
            tandaBacaX = "-";
        } else {
            nilaiX = Collections.min(wadah_xa);
            nilaiAcuanX = Math.abs(nilaiX);
            tandaBacaX = "+";
        }

        if (nilaiY > 0) {
            nilaiAcuanY = nilaiY;
            tandaBacaY = "-";
        } else {
            nilaiY = Collections.min(wadah_ya);
            nilaiAcuanY = Math.abs(nilaiY);
            tandaBacaY = "+";
        }

        if (nilaiZ > 0) {
            nilaiAcuanZ = nilaiZ;
            tandaBacaZ = "-";
        } else {
            nilaiZ = Collections.min(wadah_za);
            nilaiAcuanZ = Math.abs(nilaiZ);
            tandaBacaZ = "+";
        }

        btnCalibrasi.setEnabled(false);
    }//GEN-LAST:event_btnCalibrasiActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV File", "csv");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // proses simpan
            try {
                String absolutePath = file.getAbsolutePath();
                if (!absolutePath.substring(absolutePath.lastIndexOf(".") + 1).equals("csv")) {
                    absolutePath += ".csv";
                }
                try (FileWriter myWriter = new FileWriter(absolutePath); CSVWriter writer = new CSVWriter(myWriter)) {
                    List<String[]> csvData = createCsvDataSimple();
                    writer.writeAll(csvData);
                    JOptionPane.showMessageDialog(null, "Data tersimpan", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    
    private List<String[]> createCsvDataSimple() {
        String[] header = {"TIME", "X", "Y", "Z"};
        
        List<String[]> list = new ArrayList<>();
        list.add(header);
        
        for (int i = 0; i < wadah_waktuFull.size(); i++) {
            String[] record1 = {wadah_waktuFull.get(i), 
                wadah_xaFull.get(i).toString(), 
                wadah_yaFull.get(i).toString(), 
                wadah_zaFull.get(i).toString()};
            list.add(record1);
        }
        return list;
    }

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

    private void masukkandata(String waktu, double x, double y, double z) {
        wadah_waktuFull.add(waktu);
        wadah_xaFull.add(x);
        wadah_yaFull.add(y);
        wadah_zaFull.add(z);
        
        wadah_xa.add(x);
        wadah_ya.add(y);
        wadah_za.add(z);

        if (wadah_xa.size() > 93) {
            double jml_xa = 0;
            double jml_ya = 0;
            double jml_za = 0;
            try {

                for (int i = 0; i < wadah_xa.size(); i++) {
                    jml_xa += wadah_xa.get(i);
                    jml_ya += wadah_ya.get(i);
                    jml_za += wadah_za.get(i);
                }
                
                double RMS_X = Math.sqrt(jml_xa / 94);
                double RMS_Y = Math.sqrt(jml_ya / 94);
                double RMS_Z = Math.sqrt(jml_za / 94);
                
                String a = g.pembulatan(RMS_X);
                String b = g.pembulatan(RMS_Y);
                String c = g.pembulatan(RMS_Z);
                if(a.equals("NaN")){
                    cardRMS.setValue("X : 0");
                }else{
                    cardRMS.setValue("X : " + a);
                }
                
                if(b.equals("NaN")){
                    cardRMS.setValue1("Y : 0");
                }else{
                    cardRMS.setValue1("Y : " + b);
                }
                
                if(c.equals("NaN")){
                    cardRMS.setValue2("Z : 0");
                }else{
                    cardRMS.setValue2("Z : " + c);
                }

                // menghitung rata2
                double rata_xa = jml_xa / 94;
                double rata_ya = jml_ya / 94;
                double rata_za = jml_za / 94;

                String aa = g.pembulatan(rata_xa);
                String bb = g.pembulatan(rata_ya);
                String cc = g.pembulatan(rata_za);
                cardAverage.setValue("X : " + aa);
                cardAverage.setValue1("Y : " + bb);
                cardAverage.setValue2("Z : " + cc);

                // mencari maximal
                double max_x = Collections.max(wadah_xa);
                double max_y = Collections.max(wadah_ya);
                double max_z = Collections.max(wadah_za);
                aa = g.pembulatan(max_x);
                bb = g.pembulatan(max_y);
                cc = g.pembulatan(max_z);
                cardMax.setValue("X : " + aa);
                cardMax.setValue1("Y : " + bb);
                cardMax.setValue2("Z : " + cc);

                // mencari minimal
                double min_x = Collections.min(wadah_xa);
                double min_y = Collections.min(wadah_ya);
                double min_z = Collections.min(wadah_za);
                aa = g.pembulatan(min_x);
                bb = g.pembulatan(min_y);
                cc = g.pembulatan(min_z);
                cardMin.setValue("X : " + aa);
                cardMin.setValue1("Y : " + bb);
                cardMin.setValue2("Z : " + cc);

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

    private JFreeChart createChart(final XYDataset dataset, String judul, String judulX, String judulY, int pilAxis) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(judul, judulX, judulY, dataset, true, true, false);

        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer renderer = plot.getRenderer();
        switch (pilAxis) {
            case 0:
                renderer.setSeriesPaint(0, Color.RED);
                break;
            case 1:
                renderer.setSeriesPaint(0, Color.BLUE);
                break;
            case 2:
                renderer.setSeriesPaint(0, Color.GREEN);
                break;
        }
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
}
