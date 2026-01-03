/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.DAO;
import id.ac.unpas.goalfound.Model.Tim;
import id.ac.unpas.goalfound.KoneksiDB;
import id.ac.unpas.goalfound.KoneksiDB;
/**
 *
 * @author Muhammad Fauzan nur
 */


import java.sql.*;

public class TimDAO {

    public void insert(Tim t) throws Exception {
        String sql = "INSERT INTO tim(nama_tim, fakultas) VALUES (?,?)";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setString(1, t.getNamaTim());
        ps.setString(2, t.getFakultas());
        ps.executeUpdate();
    }

    public ResultSet getAll() throws Exception {
        String sql = "SELECT * FROM tim";
        Statement st = KoneksiDB.configDB().createStatement();
        return st.executeQuery(sql);
    }

    public void update(Tim t) throws Exception {
        String sql = "UPDATE tim SET nama_tim=?, fakultas=? WHERE id_tim=?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setString(1, t.getNamaTim());
        ps.setString(2, t.getFakultas());
        ps.setInt(3, t.getIdTim());
        ps.executeUpdate();
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM tim WHERE id_tim=?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}

