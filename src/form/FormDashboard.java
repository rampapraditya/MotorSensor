package form;

import chart.ModelChartLine;
import chart.ModelChartPie;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import model.ModelStaff;
import java.awt.Color;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import modul.Global;
import org.json.JSONException;
import org.json.JSONObject;

public class FormDashboard extends javax.swing.JPanel {

    private SerialPort serial;
    private final String dataBuffer = "";
    private String temp = "";
    private String hasil = "";
    
    public FormDashboard() {
        initComponents();
        initData();
        //initSerial();
        initTask();
    }

    private void initData() {
        //  Test Data table
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            String status;
            int ran = r.nextInt(3);
            if (ran == 0) {
                status = "Pending";
            } else if (ran == 1) {
                status = "Approved";
            } else {
                status = "Cancel";
            }
            model.addRow(new ModelStaff(new ImageIcon(getClass().getResource("/icon/staff.jpg")), "Mr Raven", "Male", "raven_programming@gmail.com", status).toDataTable());
        }
        table1.fixTable(jScrollPane1);
        List<ModelChartPie> list1 = new ArrayList<>();
        list1.add(new ModelChartPie("Monday", 10, new Color(4, 174, 243)));
        list1.add(new ModelChartPie("Tuesday", 150, new Color(215, 39, 250)));
        list1.add(new ModelChartPie("Wednesday", 80, new Color(44, 88, 236)));
        list1.add(new ModelChartPie("Thursday", 100, new Color(21, 202, 87)));
        list1.add(new ModelChartPie("Friday", 125, new Color(127, 63, 255)));
        list1.add(new ModelChartPie("Saturday", 80, new Color(238, 167, 35)));
        list1.add(new ModelChartPie("Sunday", 200, new Color(245, 79, 99)));
        chartPie.setModel(list1);
        //  Test data chart line
        List<ModelChartLine> list = new ArrayList<>();
        list.add(new ModelChartLine("Monday", 10));
        list.add(new ModelChartLine("Tuesday", 150));
        list.add(new ModelChartLine("Wednesday", 80));
        list.add(new ModelChartLine("Thursday", 100));
        list.add(new ModelChartLine("Friday", 125));
        list.add(new ModelChartLine("Saturday", 80));
        list.add(new ModelChartLine("Sunday", 200));
        chartLine1.setModel(list);
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chartPie = new chart.ChartPie();
        chartLine1 = new chart.ChartLine();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new swing.Table();

        setBackground(new java.awt.Color(250, 250, 250));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(66, 66, 66));
        jLabel1.setText("List Staff");

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Profile", "Name", "Gender", "Email", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table1);
        if (table1.getColumnModel().getColumnCount() > 0) {
            table1.getColumnModel().getColumn(0).setPreferredWidth(50);
            table1.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(chartLine1, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chartPie, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chartLine1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chartPie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private chart.ChartLine chartLine1;
    private chart.ChartPie chartPie;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.Table table1;
    // End of variables declaration//GEN-END:variables

    class SayHello extends TimerTask {
        @Override
        public void run() {
            Global g = new Global();
            System.out.println("Start " + g.getCurtimeFormat()); 
            initSerial();
        }
    }

    private void initTask(){
        // jalan tiap 5 detik
        Timer timer = new Timer();
        timer.schedule(new SayHello(), 0, 5000);
    }
    
    private void initSerial(){
        try {
            SerialPort[] portList = SerialPort.getCommPorts();
            if(portList.length > 0){
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
                    
                }else{
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
//                            try {
//                                JSONObject obj = new JSONObject(hasil);
//                                int nama = Integer.parseInt(obj.get("Nama").toString());
//                                //String waktu = obj.get("waktu").toString();
//                                String waktu1 = g.getCurtimeFormat();
//                                int score = Integer.parseInt(obj.get("Skor").toString());
//                                double prosen = ((double)score / 10) * 100;
//                                int convert_prosen = (int)prosen;
//
//                                setGrafik(nama, waktu1, convert_prosen, score);
//                            } catch (JSONException ex) {
//                            }

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
}
