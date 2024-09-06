package form;

import com.fazecast.jSerialComm.SerialPort;
import com.opencsv.CSVWriter;
import entitas.Tampung;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.CardKhusus;
import model.ModelCard;
import modul.Global;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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

    private boolean statusPompa = false;

    private boolean statusNormal = false;
    private boolean statusBearing = false;
    private boolean statusAwal = false;
    private boolean statusAnalisa = false;

    private JFreeChart chartXA, chartYA, chartZA;
    private XYDataset datasetTempXA, datasetTempYA, datasetTempZA;
    
    private boolean ambilData = false;
    private boolean tampung = false;
    private ArrayList<Tampung> dataTampung = new ArrayList<>();
    
    private int jumlahNormal = 0;

    public FormDashboard() {
        initComponents();

        resetNilaiXYZ();

        cardPure.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Value", "0", "0", "0", ""));
        cardRMS.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "RMS", "0", "0", "0", ""));
        cardAverage.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Average", "0", "0", "0", ""));
        cardMax.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Max", "0", "0", "0", ""));
        cardMin.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Min", "0", "0", "0", ""));
        cardHasil.setData(new CardKhusus(new ImageIcon(getClass().getResource("/icon/stock.png")), "Alarm", Color.BLUE));

        cbCom.removeAllItems();
        SerialPort[] portnames = SerialPort.getCommPorts();
        for (int i = 0; i < portnames.length; i++) {
            cbCom.addItem(portnames[i].getSystemPortName());
        }

        datasetTempXA = createDatasetXA();
        chartXA = createChart(datasetTempXA, "X Axis", "Time", "Acceleration (mm/S^2)", 0);
        chartXA.setBackgroundPaint(Color.white);
        ChartPanel chartPanelXA = new ChartPanel(chartXA);
        panelLineX.add(chartPanelXA);
        panelLineX.setBackground(Color.white);

        datasetTempYA = createDatasetYA();
        chartYA = createChart(datasetTempYA, "Y Axis", "Time", "Acceleration (mm/S^2)", 1);
        chartYA.setBackgroundPaint(Color.white);
        ChartPanel chartPanelYA = new ChartPanel(chartYA);
        panelLineY.add(chartPanelYA);
        panelLineY.setBackground(Color.white);

        datasetTempZA = createDatasetZA();
        chartZA = createChart(datasetTempZA, "Z Axis", "Time", "Acceleration (mm/S^2)", 2);
        chartZA.setBackgroundPaint(Color.white);
        ChartPanel chartPanelZA = new ChartPanel(chartZA);
        panelLineZ.add(chartPanelZA);
        panelLineZ.setBackground(Color.white);

        XYDataset datasetFn = createDataset();
        JFreeChart chart = createChart(datasetFn, "XYZ Axis", "Time", "Acceleration (mm/S^2)", 3);
        chart.setBackgroundPaint(Color.white);
        ChartPanel chartPanel = new ChartPanel(chart);
        panelGrafikTengah.add(chartPanel);
        panelGrafikTengah.setBackground(Color.white);
        
        Timer timer = new Timer(1000, (ActionEvent e) -> {
            updateTimeAndDate();
        });
        timer.start();
    }
    
    private void updateTimeAndDate() {
        // Get the current time and format it
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        String time = timeFormatter.format(calendar.getTime());

        // Get the current date and format it
//        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, MMM dd, yyyy");
//        String date = dateFormatter.format(calendar.getTime());

        // Update the time and date labels
        lbJam.setText(time);
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
        cardHasil = new component.CardHasil();
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
        jPanel1 = new javax.swing.JPanel();
        cbCom = new javax.swing.JComboBox<>();
        btnConnect = new javax.swing.JButton();
        btnCalibrasi = new javax.swing.JButton();
        btnSaveXYZ = new javax.swing.JButton();
        btnSaveSumary = new javax.swing.JButton();
        btnSaveByTime = new javax.swing.JToggleButton();
        btnSaveTampung = new javax.swing.JButton();
        btnPompa = new javax.swing.JToggleButton();
        btnAnalisa = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lbJam = new javax.swing.JLabel();

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
        panelAtas.add(cardHasil);

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
        panelBawah.setLayout(new java.awt.BorderLayout());

        jPanel1.add(cbCom);

        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });
        jPanel1.add(btnConnect);

        btnCalibrasi.setText("Calibrasi");
        btnCalibrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalibrasiActionPerformed(evt);
            }
        });
        jPanel1.add(btnCalibrasi);

        btnSaveXYZ.setText("Save XYZ");
        btnSaveXYZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveXYZActionPerformed(evt);
            }
        });
        jPanel1.add(btnSaveXYZ);

        btnSaveSumary.setText("Save Summary");
        btnSaveSumary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveSumaryActionPerformed(evt);
            }
        });
        jPanel1.add(btnSaveSumary);

        btnSaveByTime.setText("Tampung");
        btnSaveByTime.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnSaveByTimeItemStateChanged(evt);
            }
        });
        btnSaveByTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveByTimeActionPerformed(evt);
            }
        });
        jPanel1.add(btnSaveByTime);

        btnSaveTampung.setText("Save Data Tampung");
        btnSaveTampung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTampungActionPerformed(evt);
            }
        });
        jPanel1.add(btnSaveTampung);

        btnPompa.setText("Pompa On");
        btnPompa.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnPompaStateChanged(evt);
            }
        });
        btnPompa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPompaActionPerformed(evt);
            }
        });
        jPanel1.add(btnPompa);

        btnAnalisa.setText("Analisa");
        btnAnalisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalisaActionPerformed(evt);
            }
        });
        jPanel1.add(btnAnalisa);

        panelBawah.add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel3.setLayout(new java.awt.BorderLayout());

        lbJam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbJam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbJam.setText("00:00:00");
        jPanel3.add(lbJam, java.awt.BorderLayout.EAST);

        panelBawah.add(jPanel3, java.awt.BorderLayout.CENTER);

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
//                                        
//                                        try {
//                                            Millisecond milis = new Millisecond();
//                                            seriesXA.add(milis, xa);
//                                            seriesYA.add(milis, ya);
//                                            seriesZA.add(milis, za);
//                                        } catch (Exception ex) {
//                                        }
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

    }//GEN-LAST:event_btnCalibrasiActionPerformed

    private void btnSaveXYZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveXYZActionPerformed
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
                    List<String[]> csvData = createCsvDataXYZ();
                    writer.writeAll(csvData);
                    JOptionPane.showMessageDialog(null, "Data tersimpan", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }//GEN-LAST:event_btnSaveXYZActionPerformed

    private void btnSaveSumaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveSumaryActionPerformed
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
                try (FileWriter myWriter = new FileWriter(absolutePath); 
                        CSVWriter writer = new CSVWriter(myWriter)) {
                    List<String[]> csvData = createCsvSumary();
                    writer.writeAll(csvData);
                    JOptionPane.showMessageDialog(null, "Data tersimpan", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }//GEN-LAST:event_btnSaveSumaryActionPerformed

    private void btnPompaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPompaActionPerformed
        OutputStream outputStream = serial.getOutputStream();

        String dataToSend = "";
        if (btnPompa.isSelected()) {
            dataToSend = "1";
            btnPompa.setText("Pompa Off");
            statusPompa = true;
            statusAwal = true;

        } else {
            dataToSend = "0";
            btnPompa.setText("Pompa On");
            statusPompa = false;

            // reset
            statusNormal = false;
            statusBearing = false;
            statusAwal = false;
            statusAnalisa = false;
        }
        try {
            outputStream.write(dataToSend.getBytes());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btnPompaActionPerformed

    private void btnAnalisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalisaActionPerformed
        if (statusPompa) {
            statusAnalisa = true;
        }
    }//GEN-LAST:event_btnAnalisaActionPerformed

    private void btnSaveByTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveByTimeActionPerformed
        
    }//GEN-LAST:event_btnSaveByTimeActionPerformed

    private void btnPompaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnPompaStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPompaStateChanged

    private void btnSaveByTimeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnSaveByTimeItemStateChanged
        int state = evt.getStateChange();
        if (state == ItemEvent.SELECTED) {
            System.out.println("Tampung");
            tampung = true;
        } else {
            System.out.println("Tidak Tampung");
            tampung = false;
        }
    }//GEN-LAST:event_btnSaveByTimeItemStateChanged

    private void btnSaveTampungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTampungActionPerformed
        if (!tampung) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Csv file", "csv");
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                
                String absolutePath = file.getAbsolutePath();
		if (!absolutePath.substring(absolutePath.lastIndexOf(".") + 1).equals("csv")) {
			absolutePath += ".csv";
		}
                
                //writeDataAtOnce(absolutePath);
                try (FileWriter myWriter = new FileWriter(absolutePath); CSVWriter writer = new CSVWriter(myWriter)) {
                    List<String[]> csvData = createCsvDataTampung();
                    writer.writeAll(csvData);
                    JOptionPane.showMessageDialog(null, "Data tersimpan", "Info", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    Logger.getLogger(FormDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Angkat button tampung");
        }
    }//GEN-LAST:event_btnSaveTampungActionPerformed

    private List<String[]> createCsvDataXYZ() {
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
    
    private List<String[]> createCsvDataTampung() {
        String[] header = {"TIME", "X", "Y", "Z"};

        List<String[]> list = new ArrayList<>();
        list.add(header);

        for (int i = 0; i < dataTampung.size(); i++) {
            String[] record1 = {dataTampung.get(i).getWaktu(),
                dataTampung.get(i).getX(),
                dataTampung.get(i).getY(),
                dataTampung.get(i).getZ()};
            list.add(record1);
        }
        return list;
    }

    private List<String[]> createCsvSumary() {
        String[] header = {"RMS X", "RMS Y", "RMS Z",
            "AVG X", "AVG Y", "AVG Z",
            "MAX X", "MAX Y", "MAX Z",
            "MIN X", "MIN Y", "MIN Z"};

        List<String[]> list = new ArrayList<>();
        list.add(header);
        String[] body = {cardRMS.getValue(), cardRMS.getValue1(), cardRMS.getValue2(),
            cardAverage.getValue(), cardAverage.getValue1(), cardAverage.getValue2(),
            cardMax.getValue(), cardMax.getValue1(), cardMax.getValue2(),
            cardMin.getValue(), cardMin.getValue1(), cardMin.getValue2()};
        list.add(body);

        return list;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel batas;
    private javax.swing.JPanel batasAtas;
    private javax.swing.JButton btnAnalisa;
    private javax.swing.JButton btnCalibrasi;
    private javax.swing.JButton btnConnect;
    private javax.swing.JToggleButton btnPompa;
    private javax.swing.JToggleButton btnSaveByTime;
    private javax.swing.JButton btnSaveSumary;
    private javax.swing.JButton btnSaveTampung;
    private javax.swing.JButton btnSaveXYZ;
    private component.Card cardAverage;
    private component.CardHasil cardHasil;
    private component.Card cardMax;
    private component.Card cardMin;
    private component.Card cardPure;
    private component.Card cardRMS;
    private javax.swing.JComboBox<String> cbCom;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbJam;
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

    public void writeDataAtOnce(String filePath) {
        File file = new File(filePath);
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            ArrayList<String[]> temp = new ArrayList<>();
            for (int i = 0; i < dataTampung.size(); i++) {
                String data1[] = new String[]{dataTampung.get(i).getWaktu(), dataTampung.get(i).getX(), dataTampung.get(i).getY(),dataTampung.get(i).getZ()};
                temp.add(data1);
            }
            writer.writeAll(temp);
            writer.close();
            JOptionPane.showMessageDialog(null, "Data tersimpan");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    private void resetNilaiXYZ() {
        wadah_xa.clear();
        wadah_ya.clear();
        wadah_za.clear();

//        wadah_waktuFull.clear();
//        wadah_xaFull.clear();
//        wadah_yaFull.clear();
//        wadah_zaFull.clear();
    }

    private void masukkandata(String waktu, double x, double y, double z) {
        wadah_waktuFull.add(waktu);
        wadah_xaFull.add(x);
        wadah_yaFull.add(y);
        wadah_zaFull.add(z);

        wadah_xa.add(x);
        wadah_ya.add(y);
        wadah_za.add(z);
        
        if (tampung) {
            Tampung tam = new Tampung();
            tam.setWaktu(waktu);
            tam.setX(String.valueOf(x));
            tam.setY(String.valueOf(y));
            tam.setZ(String.valueOf(z));
            dataTampung.add(tam);
        }

        if (wadah_xa.size() > 19) {
            double jml_xa = 0;
            double jml_ya = 0;
            double jml_za = 0;
            try {

                for (int i = 0; i < wadah_xa.size(); i++) {
                    jml_xa += wadah_xa.get(i);
                    jml_ya += wadah_ya.get(i);
                    jml_za += wadah_za.get(i);
                }

                double RMS_X = Math.sqrt(Math.pow(jml_xa, 2) / 20);
                double RMS_Y = Math.sqrt(Math.pow(jml_ya, 2) / 20);
                double RMS_Z = Math.sqrt(Math.pow(jml_za, 2) / 20);

                String a = g.pembulatan(RMS_X);
                String b = g.pembulatan(RMS_Y);
                String c = g.pembulatan(RMS_Z);

                cardRMS.setValue("X : " + a);
                cardRMS.setValue1("Y : " + b);
                cardRMS.setValue2("Z : " + c);

                // menghitung rata2
                double rata_xa = jml_xa / 20;
                double rata_ya = jml_ya / 20;
                double rata_za = jml_za / 20;

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
                String aa_max = g.pembulatan(max_x);
                String bb_max = g.pembulatan(max_y);
                String cc_max = g.pembulatan(max_z);
                cardMax.setValue("X : " + aa_max);
                cardMax.setValue1("Y : " + bb_max);
                cardMax.setValue2("Z : " + cc_max);

                // mencari minimal
                double min_x = Collections.min(wadah_xa);
                double min_y = Collections.min(wadah_ya);
                double min_z = Collections.min(wadah_za);
                String aa_min = g.pembulatan(min_x);
                String bb_min = g.pembulatan(min_y);
                String cc_min = g.pembulatan(min_z);
                cardMin.setValue("X : " + aa_min);
                cardMin.setValue1("Y : " + bb_min);
                cardMin.setValue2("Z : " + cc_min);

                // --------------------------------- RUMUSAN ANALISA ---------------------------------
                // patokan max z dan min z
                try {
                    if (statusAnalisa) {
                        String hasil_analisa = sendPredictionRequest(cc_min + " " + cc_max);
                        System.out.println(hasil_analisa);
                        if (statusBearing && statusNormal) {
                            cardHasil.setHasil("Impeller");
                        } else {
                            if (hasil_analisa.equals("1")) {
                                cardHasil.setHasil("Bearing");
                                statusBearing = true;
                            } else if (hasil_analisa.equals("3")) {
                                cardHasil.setHasil("Normal");
                                if (statusAwal) {
                                    statusNormal = true;
                                }
                            }
                        }
                    }else{
                        cardHasil.setHasil("Normal");
                    }

                } catch (Exception e) {
                }

//                if ((max_x >= 1.6 && max_x <= 3.5) || (min_x >= -3.1 && min_x <= -1.3)) {
//                    System.out.println("Bearing");
//                } else if ((max_x >= 0.4 && max_x <= 1) || (min_x >= -0.4 && min_x <= -1.25)) {
//                    System.out.println("Impeller");
//                } else if ((max_x >= 0 && max_x <= 0.2) || (min_x >= 0 && min_x <= -0.2)) {
//                    System.out.println("Normal");
//                }
                // -----------------------------------------------------------------------------------
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

    private String sendPredictionRequest(String message) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("http://localhost:5000/predict");
            JSONObject json = new JSONObject();
            json.put("features", message);
            StringEntity entity = new StringEntity(json.toString());
            request.setEntity(entity);
            request.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = httpClient.execute(request);
            String jsonResponse = EntityUtils.toString(response.getEntity());
            JSONObject responseObject = new JSONObject(jsonResponse);
            return responseObject.get("prediction").toString();

        } catch (Exception e) {
            return "Error";
        }
    }
}
