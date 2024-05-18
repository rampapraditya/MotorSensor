/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Rampa
 */
public class Global {

    public void tulisTxt(String namaFile, String isi) {
        try {
            FileWriter fstream = new FileWriter(namaFile);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(isi);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static String bacaTxt(String stringfile) {
        String isipath = "";

        try {
            FileInputStream fstream = new FileInputStream(stringfile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                isipath += strLine;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Setting path belum dilakukan", "Path File", JOptionPane.ERROR_MESSAGE);
        }
        return isipath;
    }

    public void copyFile(String src, String dst) {
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
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void copyFileUsingChannel(File source, File dest) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } finally {
            sourceChannel.close();
            destChannel.close();
        }
    }

    public String getCurtimeFormat() {
        Calendar c = Calendar.getInstance();
        int jam = c.get(Calendar.HOUR);
        int menit = c.get(Calendar.MINUTE);
        int detik = c.get(Calendar.SECOND);
        return jam + ":" + menit + ":" + detik;
    }

    public String getCurdateFormat(Date tanggal) {
        SimpleDateFormat Format = new SimpleDateFormat("dd-MM-yyyy");
        String formatTgl = Format.format(tanggal);
        return formatTgl;
    }

    public String inputDateFormat(Date tanggal) {
        SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
        String formatTgl = Format.format(tanggal);
        return formatTgl;
    }

    public String curTime(Date tanggal) {
        SimpleDateFormat Format = new SimpleDateFormat("HH:m:ss");
        String formatTgl = Format.format(tanggal);
        return formatTgl;
    }

    public Date StringToDate(String input) throws Exception {
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date date = (Date) formater.parse(input);
        return date;
    }

    public String FormatMataUang(double input) {
        String hasil = "";
        try {
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            hasil = nf.format(input);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            return hasil;
        }
    }

    public String pembulatan2Koma(String input) {
        String a = FormatMataUang(Double.parseDouble(input));
        String b = a.substring(2, a.length());
        String c = b.replace(',', '.');
        return c;
    }

    public String pembulatan(double x) {
        DecimalFormat df = new DecimalFormat("#.####");
        return df.format(x);
    }

    public void buatKolomSesuai(JTable t) {
        TableColumnModel modelKolom = t.getColumnModel();

        for (int kol = 0; kol < modelKolom.getColumnCount(); kol++) {
            int lebarKolomMax = 0;
            for (int baris = 0; baris < t.getRowCount(); baris++) {
                TableCellRenderer rend = t.getCellRenderer(baris, kol);
                Object nilaiTablel = t.getValueAt(baris, kol);
                Component comp = rend.getTableCellRendererComponent(t, nilaiTablel, false, false, baris, kol);
                lebarKolomMax = Math.max(comp.getPreferredSize().width, lebarKolomMax);
            }//akhir for baris
            TableColumn kolom = modelKolom.getColumn(kol);
            kolom.setPreferredWidth(lebarKolomMax);
        }//akhir for kolom
    }

    public String PasswordToString(char[] cPasswordNya) {
        String Isinya = "";
        for (int i = 0; i < cPasswordNya.length; i++) {
            Isinya += cPasswordNya[i];
        }
        return Isinya;
    }

    public boolean validasiAngka(String input) {
        boolean hasil;
        if (input.matches("[0-9]*")) {
            hasil = true;
        } else {
            hasil = false;
        }
        return hasil;
    }

    public float pembulatanKoma(float input, int belakangKoma) {
        BigDecimal bd = new BigDecimal(input);
        bd = bd.setScale(belakangKoma, BigDecimal.ROUND_UP);
        input = bd.floatValue();
        return input;
    }

    public void resetTable(JTable namaTable) {
        while (namaTable.getRowCount() > 0) {
            ((DefaultTableModel) namaTable.getModel()).removeRow(0);
        }
    }

    final Color alphaZero = new Color(0, true);

    public void BuatTableTransparant(JTable tb, JScrollPane scroll) {
        tb.setOpaque(true);
        tb.setBackground(alphaZero);
        scroll.getViewport().setOpaque(true);
        scroll.getViewport().setBackground(alphaZero);
    }

    public String nilaiUang(String input) {
        String hasil;
        if (input.contains(",")) {
            hasil = input.replaceAll(",", "");
        } else {
            hasil = input;
        }
        return hasil;
    }

    public String terbilang(int satuan) {
        String[] huruf = {"", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"};

        String hasil = "";
        if (satuan < 12) {
            hasil = hasil + huruf[satuan];
        } else if (satuan < 20) {
            hasil = hasil + terbilang(satuan - 10) + " belas";
        } else if (satuan < 100) {
            hasil = hasil + terbilang(satuan / 10) + " puluh " + terbilang(satuan % 10);
        } else if (satuan < 200) {
            hasil = hasil + "Seratus " + terbilang(satuan - 100);
        } else if (satuan < 1000) {
            hasil = hasil + terbilang(satuan / 100) + " Ratus " + terbilang(satuan % 100);
        } else if (satuan < 2000) {
            hasil = hasil + "Seribu " + terbilang(satuan - 1000);
        } else if (satuan < 1000000) {
            hasil = hasil + terbilang(satuan / 1000) + " ribu rupiah" + terbilang(satuan % 1000);
        } else if (satuan < 1000000000) {
            hasil = hasil + terbilang(satuan / 1000000) + " juta " + terbilang(satuan % 1000000);
        } else if (satuan >= 1000000000) {
            hasil = "Angka terlalu besar, harus kurang dari 1 milyar!";
        }
        return hasil;
    }

    public String cwdPath() {
        String cwd = System.getProperty("user.dir");
        return cwd;
    }
}
