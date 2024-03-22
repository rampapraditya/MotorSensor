/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;


import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Rampa
 */
public class Global {

    private String url = "jdbc:mysql://127.0.0.1/imago";
    private String user = "root";
    private String pswd = "";
    private String dbName = "imago";
    
    private Connection connection;

    public Global() {
    }

    public String getDBName(){
        return dbName;
    }
    
    public String getUrl(){
        return url;
    }
    
    public String getUser(){
        return user;
    }
    
    public String getPass(){
        return pswd;
    }
    
    public Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, user, pswd);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Koneksi", JOptionPane.WARNING_MESSAGE);
        }
        return connection;
    }
    
    public void simpan(String queryCek, String querySimpan){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd);
            java.sql.Statement stat = conn.createStatement();
            ResultSet data = stat.executeQuery(queryCek);
            if (data.next()) {
                JOptionPane.showMessageDialog(null, "Data sudah ada", "Simpan", JOptionPane.WARNING_MESSAGE);
            }else{
                java.sql.Statement save = conn.createStatement();
                int jumlah = save.executeUpdate(querySimpan);
                if(jumlah == 1){
                    JOptionPane.showMessageDialog(null, "Data tersimpan", "Simpan", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            conn.close();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Simpan", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int simpanTanpaPesan(String querySimpan){
        int hasil = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd); 
            java.sql.Statement save = conn.createStatement();
            hasil = save.executeUpdate(querySimpan);
            conn.close();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Simpan", JOptionPane.ERROR_MESSAGE);
        }
        return hasil;
    }

    public void ganti(String queryCek, String queryGanti){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd); 
            java.sql.Statement stat = conn.createStatement();
            ResultSet data = stat.executeQuery(queryCek);
            if(data.next()){
                java.sql.Statement modify = conn.createStatement();
                int jumlah = modify.executeUpdate(queryGanti);
                if(jumlah == 1){
                    JOptionPane.showMessageDialog(null, "Data tersimpan", "Ganti", JOptionPane.INFORMATION_MESSAGE);                
                }else{
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan", "Simpan", JOptionPane.WARNING_MESSAGE);
                }
            }
            conn.close();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e, "Ganti", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void gantiTanpaPesan(String queryGanti){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd); 
            java.sql.Statement modify = conn.createStatement();
            modify.executeUpdate(queryGanti); 
            
            modify.close();
            conn.close();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Ganti", JOptionPane.ERROR_MESSAGE);
        }
}

    public void hapus(String queryCek, String queryHapus){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd); 
            java.sql.Statement stat = conn.createStatement(); 
            ResultSet data = stat.executeQuery(queryCek);
            if(data.next()){
                java.sql.Statement del = conn.createStatement();
                int jumlah = del.executeUpdate(queryHapus);
                if(jumlah == 1){
                    JOptionPane.showMessageDialog(null, "Data terhapus", "Hanti", JOptionPane.INFORMATION_MESSAGE);                 
                }else{
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan", "Simpan", JOptionPane.WARNING_MESSAGE);
                }
            }
            conn.close();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Data gagal dihapus", "Hapus", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int hapusTanpaPesan(String queryHapus){
        int hasil = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd);
            java.sql.Statement del = conn.createStatement();
            hasil = del.executeUpdate(queryHapus);
            
            conn.close();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal dihapus", "Hapus", JOptionPane.ERROR_MESSAGE);
        }
        return hasil;
    }

    public int getJumlahColom(String queryGetJumlah){
        int intNum = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver"); // load mysql driver
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd);
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(queryGetJumlah);
            ResultSetMetaData rsmd =(ResultSetMetaData) rs.getMetaData(); //mendapatkan meta data dari database
            intNum = rsmd.getColumnCount(); // hitung jumlah kolom
            
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Jumlah Kolom", JOptionPane.ERROR_MESSAGE);
        }
        return intNum;
    }

    public int getNilaiCell(String query, int kolom){
        int nilai = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd); 
            java.sql.Statement stat = conn.createStatement(); 
            ResultSet data = stat.executeQuery(query);
            if (data.next()) {
                nilai = Integer.parseInt(data.getString(kolom));
            }else{
                nilai = 0;
            } 
            conn.close();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return nilai;
    }

    public String getStringCell(String query, int kolom){
        String nilai = "";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd);
            java.sql.Statement stat = conn.createStatement(); 
            ResultSet data = stat.executeQuery(query);
            if (data.next()) {
                nilai = data.getString(kolom);
            }else{
                nilai = "";
            }
            conn.close();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return nilai;
    }

    public void TabelView(String query, String [] HeaderKolom, JTable tabel){
        try {
            resetTable(tabel);
            Class.forName("com.mysql.jdbc.Driver"); // load mysql driver
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd); 
            java.sql.Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd =(ResultSetMetaData) rs.getMetaData(); //mendapatkan meta data dari database
            int intnum = rsmd.getColumnCount(); // hitung jumlah kolom
            
            //DefaultTableModel model = new DefaultTableModel();
            DefaultTableModel model = (DefaultTableModel) tabel.getModel();
            model.setColumnIdentifiers(HeaderKolom); // set header kolom
            while (rs.next()){ // lakukan perulangan sampai akhir data
                String[] data= new String[intnum];
                for (int i=0;i<intnum;i++){
                    data[i] = rs.getString(i+1); // tampung isi field database ke array data
                }
                model.addRow(data); //tambahkan baris table sesuai data
            }                    
            tabel.setModel(model);
            rs.close();
            stmt.close();
            conn.close();            
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Load Data", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void isiList(String query, JList list){
        try {
            list.removeAll();
            
            DefaultListModel lm = new DefaultListModel();
            Class.forName("com.mysql.jdbc.Driver"); // load mysql driver
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd); 
            java.sql.Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){ // lakukan perulangan sampai akhir data
                lm.addElement(rs.getString(1));
            }
            list.setModel(lm);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "List", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void isiCombo(String query, JComboBox combobox){
        try {
            combobox.removeAllItems();
            Class.forName("com.mysql.jdbc.Driver"); // load mysql driver
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd); 
            java.sql.Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd =(ResultSetMetaData) rs.getMetaData(); //mendapatkan meta data dari database
            int intnum = rsmd.getColumnCount(); // hitung jumlah kolom
            while (rs.next()){ // lakukan perulangan sampai akhir data
                //String[] data= new String[intnum];
                for (int i=0;i<intnum;i++){
                    combobox.addItem(rs.getString(i+1));
                }
            }
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ComboBox", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ComboBoxModel getIsiComboTable(){
        DefaultComboBoxModel model1 = new DefaultComboBoxModel();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            java.sql.Connection conn = DriverManager.getConnection(getUrl(), user, pswd); 
            java.sql.Statement stat = conn.createStatement(); 
            ResultSet data = stat.executeQuery("select no_stok from stok");
            while(data.next()){
                model1.addElement(data.getString(1));
            }   
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
        }
        return model1;
    }
    
    public boolean createSQL_mySQL(String query){
        boolean hasil;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd);
            Statement st = (Statement) conn.createStatement();
            hasil = st.execute(query);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            hasil = false;
            JOptionPane.showMessageDialog(null, e);
        }
        return hasil;
    }
    
    public String cari(String query){
        String temp = "";
        try {
            Class.forName("com.mysql.jdbc.Driver"); // load mysql driver
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd); 
            java.sql.Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                temp = rs.getString(1);
            }
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Cari", JOptionPane.ERROR_MESSAGE);
        }
        return temp;
    }
    
    public String[] KembaliArray(int baris, String query){
        String data[] = new String[1];
        try {
            Class.forName("com.mysql.jdbc.Driver"); // load mysql driver
            java.sql.Connection conn = DriverManager.getConnection(url, user, pswd); 
            java.sql.Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            
            data = new String[baris];
            int awal = 0;
            while (rs.next()){ // lakukan perulangan sampai akhir data
                data[awal] = rs.getString(1);
                awal++;
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Kembali Array", JOptionPane.ERROR_MESSAGE);
        }
        return data;
    }

    public void tulisTxt(String namaFile, String isi){
        try {
            FileWriter fstream = new FileWriter(namaFile);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(isi);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public static String bacaTxt(String stringfile){
        String isipath = "";
        
        try {
            FileInputStream fstream = new FileInputStream(stringfile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null){
                isipath += strLine;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Setting path belum dilakukan", "Path File", JOptionPane.ERROR_MESSAGE);
        }
        return  isipath;
    }

    public void copyFile(String src, String dst){
        try {
            OutputStream out;
            InputStream in = new FileInputStream(src);
            out = new FileOutputStream(dst);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
            }
            out.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public String getCurtimeFormat(){
        Calendar c = Calendar.getInstance(); 
        int jam = c.get(Calendar.HOUR);
        int menit = c.get(Calendar.MINUTE);
        int detik = c.get(Calendar.SECOND);
        return jam + ":" + menit + ":" + detik;
    }
    
    public String getCurdateFormat(Date tanggal){
        SimpleDateFormat Format = new SimpleDateFormat("dd-MM-yyyy");
        String formatTgl = Format.format(tanggal);
        return formatTgl;
    }
    
    public String inputDateFormat(Date tanggal){
        SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
        String formatTgl = Format.format(tanggal);
        return formatTgl;
    }
    
    public Date StringToDate(String input) throws Exception{
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date date = (Date)formater.parse(input);
        return date;
    }
    
    public String FormatMataUang(double input){
        String hasil = "";
        try {
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            hasil = nf.format(input);
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            return hasil;
        }
    }
    
    public String pembulatan2Koma(String input){
        String a = FormatMataUang(Double.parseDouble(input));
        String b = a.substring(2, a.length());
        String c = b.replace(',', '.');
        return c;
    }
    
    public String pembulatan(double x){
        DecimalFormat df = new DecimalFormat("#.####");
        return df.format(x);
    }
    
    public void buatKolomSesuai(JTable t){
    	TableColumnModel modelKolom = t.getColumnModel();
    	
    	for(int kol=0; kol < modelKolom.getColumnCount(); kol++){
            int lebarKolomMax=0;
            for(int baris=0;baris<t.getRowCount();baris++){
                TableCellRenderer rend=t.getCellRenderer(baris,kol);
                Object nilaiTablel=t.getValueAt(baris,kol);
                Component comp=rend.getTableCellRendererComponent(t,nilaiTablel,false,false,baris,kol);
                lebarKolomMax = Math.max(comp.getPreferredSize().width,lebarKolomMax);
            }//akhir for baris
            TableColumn kolom=modelKolom.getColumn(kol);
            kolom.setPreferredWidth(lebarKolomMax);
        }//akhir for kolom
    }
    
    public String PasswordToString(char[] cPasswordNya){
        String Isinya = "";
        for (int i = 0; i < cPasswordNya.length; i++){
            Isinya += cPasswordNya[i];
        }
        return Isinya;
    }
    
    public boolean validasiAngka(String input){
        boolean hasil;
        if(input.matches("[0-9]*")){
            hasil = true;
        }else{
            hasil = false;
        }
        return hasil;
    }
    
    public float pembulatanKoma(float input, int belakangKoma){
        BigDecimal bd = new BigDecimal(input);
        bd = bd.setScale(belakangKoma, BigDecimal.ROUND_UP);
        input = bd.floatValue();
        return input;
    }
    
    public void resetTable(JTable namaTable){
        while(namaTable.getRowCount() > 0) {
            ((DefaultTableModel) namaTable.getModel()).removeRow(0);
        }
    }
    
    final Color alphaZero = new Color(0, true);
    
    public void BuatTableTransparant(JTable tb, JScrollPane scroll){
        tb.setOpaque(true);
        tb.setBackground(alphaZero);
        scroll.getViewport().setOpaque(true);
        scroll.getViewport().setBackground(alphaZero);
    }
    
    public String nilaiUang(String input){
        String hasil;
        if(input.contains(",")){
            hasil = input.replaceAll(",", "");
        }else{
            hasil = input;
        }
        return hasil;
    }
    
    public void resetAutoIncrement(String namaTable){
        createSQL_mySQL("ALTER TABLE "+namaTable+" AUTO_INCREMENT = 1");
    }
    
    public String terbilang(int satuan){ 
        String[] huruf ={"","Satu","Dua","Tiga","Empat","Lima","Enam","Tujuh","Delapan","Sembilan","Sepuluh","Sebelas"}; 

        String hasil=""; 
        if(satuan < 12) {
            hasil = hasil + huruf[satuan]; 
        }else if(satuan < 20) {
            hasil = hasil + terbilang(satuan-10)+" belas"; 
        }else if(satuan < 100) {
            hasil= hasil + terbilang(satuan/10)+" puluh " + terbilang(satuan%10); 
        }else if(satuan < 200) {
            hasil= hasil + "Seratus " + terbilang(satuan-100); 
        }else if(satuan < 1000) {
            hasil = hasil + terbilang(satuan/100)+ " Ratus " + terbilang(satuan%100); 
        }else if(satuan < 2000){
            hasil = hasil + "Seribu " + terbilang(satuan-1000); 
        }else if(satuan < 1000000){
            hasil = hasil + terbilang(satuan/1000) + " ribu rupiah" + terbilang(satuan % 1000); 
        }else if(satuan < 1000000000) {
            hasil = hasil + terbilang(satuan/1000000) + " juta " + terbilang(satuan % 1000000); 
        }else if(satuan >= 1000000000){
            hasil = "Angka terlalu besar, harus kurang dari 1 milyar!"; 
        }
        return hasil; 
    } 
}
