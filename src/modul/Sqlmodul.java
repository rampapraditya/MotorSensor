package modul;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Rampa Praditya <https://pramediaenginering.com/>
 */
public class Sqlmodul {

    public Connection getKoneksi() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            //conn = DriverManager.getConnection("jdbc:sqlite:" + curdir() + "sigmont.db");
            conn = DriverManager.getConnection("jdbc:sqlite:motor.db");
            conn.setAutoCommit(true);

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return conn;
    }

    public String baca(String query) {
        String hasil = "";
        try {
            Statement stat = getKoneksi().createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                hasil = rs.getString(1);
            }
            rs.close();
            getKoneksi().close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return hasil;
    }

    public String[][] baca_array(String query, int baris, int kolom) {
        String[][] data = new String[baris][kolom];
        try {
            Statement stat = getKoneksi().createStatement();
            ResultSet rs = stat.executeQuery(query);

            int baris_loop = 0;
            while (rs.next()) {
                for (int j = 0; j < kolom; j++) {
                    data[baris_loop][j] = rs.getString(j + 1);
                }
                baris_loop++;
            }
            rs.close();
            getKoneksi().close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return data;
    }

    public String autokode(String depan, String kolom, String table, int awal, int akhir) {
        String data = baca("select ifnull(MAX(substr(" + kolom + "," + awal + "," + akhir + ")),0) + 1 as jml from " + table + ";");
        int panjang = data.length();
        int pnjng_nol = (akhir - panjang) - awal;
        String nol = "";
        for (int i = 1; i <= pnjng_nol; i++) {
            nol += "0";
        }
        String hasil = depan + nol + data;
        return hasil;
    }

    public int dml(String query) {
        int hasil = 0;
        try {
            Statement stmt = getKoneksi().createStatement();
            hasil = stmt.executeUpdate(query);

            stmt.close();
            getKoneksi().close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return hasil;
    }

    public void createTable(Connection conn, String query) {
        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate(query);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public String[] cekAllowBackup() {
        String[] setting = new String[2];

        int cek = Integer.parseInt(baca("select count(*) as jml from users"));
        if (cek > 0) {
            String data[][] = baca_array("select allow_backup, path from users", 1, 2);
            setting[0] = data[0][0];
            setting[1] = data[0][1];

        } else {
            setting[0] = "0";
            setting[1] = "";
        }

        return setting;
    }
}
