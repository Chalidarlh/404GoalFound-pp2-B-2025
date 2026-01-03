/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Muhammad Fauzan nur
 */



public class KoneksiDB {

    private static Connection conn;

    public static Connection configDB() throws Exception {
        if (conn == null || conn.isClosed()) {

            String url = "jdbc:mysql://localhost:3306/db_goalfound";
            String user = "root";
            String pass = ""; // sesuaikan

            conn = DriverManager.getConnection(url, user, pass);
        }
        return conn;
    }
}

