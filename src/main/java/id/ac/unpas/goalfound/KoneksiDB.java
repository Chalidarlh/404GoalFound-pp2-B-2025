/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Muhammad Fauzan nur
 */
public class KoneksiDB {

    private static Connection conn;

    public static Connection configDB() throws Exception {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                Properties env = loadLocalEnv();

                String host = firstNonBlank(env.getProperty("DB_HOST"));
                String port = firstNonBlank(env.getProperty("DB_PORT"));
                String db   = firstNonBlank(env.getProperty("DB_NAME"));
                String user = firstNonBlank(env.getProperty("DB_USER"));
                String pass = firstNonBlank(env.getProperty("DB_PASS"));

                String url = "jdbc:mysql://" + host + ":" + port + "/" + db
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&connectTimeout=5000";

                conn = DriverManager.getConnection(url, user, pass);
            }
            return conn;
            
        } catch (Exception e) {
            System.err.println("Koneksi Gagal: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal Konek ke Database: \n" + e.getMessage());
            throw e;
        }
    }

    private static Properties loadLocalEnv() {
        Properties p = new Properties();
        Path path = Path.of(".env.local");
        if (Files.exists(path)) {
            try (var reader = Files.newBufferedReader(path)) {
                p.load(reader);
            } catch (IOException ignored) {
            }
        }
        return p;
    }

    private static String firstNonBlank(String... values) {
        for (String v : values) {
            if (v != null && !v.isBlank()) {
                return v;
            }
        }
        return "";
    }
}