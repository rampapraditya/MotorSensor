package form;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.awt.HeadlessException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import model.ModelCard;
import modul.Global;
import org.json.JSONException;
import org.json.JSONObject;

public class FormDashboard1 extends javax.swing.JPanel {

    private SerialPort serial;
    private final String dataBuffer = "";
    private String temp = "";
    private String hasil = "";
    private Global g = new Global();
    
    public FormDashboard1() {
        initComponents();
        
        cardPure.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Value", "X\nY\nZ", ""));
        cardRMS.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "RMS", "0", ""));
        cardAverage.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Average", "0", ""));
        cardMax.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Max", "0", ""));
        cardMin.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Min", "0", ""));
        cardAlarm.setData(new ModelCard(new ImageIcon(getClass().getResource("/icon/stock.png")), "Alarm", "0", ""));
        
        initSerial();
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
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private component.Card cardAlarm;
    private component.Card cardAverage;
    private component.Card cardMax;
    private component.Card cardMin;
    private component.Card cardPure;
    private component.Card cardRMS;
    private javax.swing.JPanel panelAtas;
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
                            try {
                                JSONObject obj = new JSONObject(hasil);
                                String xa = obj.get("xa").toString();
                                String ya = obj.get("ya").toString();
                                String za = obj.get("za").toString();
                                cardPure.setValue("X : " + xa + "  Y : " + ya + "  Z : " + za);
                                

//                                setGrafik(nama, waktu1, convert_prosen, score);
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
}
