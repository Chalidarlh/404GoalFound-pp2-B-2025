/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.DAO;
import id.ac.unpas.goalfound.Model.Tim;
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

    public void deleteCascade(int id) throws Exception {
        // Hapus jadwal pertandingan yang melibatkan tim (sebagai tuan atau tamu)
        String sqlJadwal = "DELETE FROM jadwal_pertandingan WHERE id_tim_tuan=? OR id_tim_tamu=?";
        PreparedStatement psJadwal = KoneksiDB.configDB().prepareStatement(sqlJadwal);
        psJadwal.setInt(1, id);
        psJadwal.setInt(2, id);
        psJadwal.executeUpdate();

        // Hapus seluruh pemain milik tim
        String sqlPemain = "DELETE FROM pemain WHERE id_tim=?";
        PreparedStatement psPemain = KoneksiDB.configDB().prepareStatement(sqlPemain);
        psPemain.setInt(1, id);
        psPemain.executeUpdate();

        // Terakhir, hapus tim
        delete(id);
    }

    public boolean cekJadwalTerkait(int id) throws Exception {
        String sql = "SELECT id_jadwal FROM jadwal_pertandingan WHERE id_tim_tuan=? OR id_tim_tamu=?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setInt(1, id);
        ps.setInt(2, id);
        return ps.executeQuery().next();
    }

    public boolean cekPemainTerkait(int id) throws Exception {
        String sql = "SELECT id_pemain FROM pemain WHERE id_tim=?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeQuery().next();
    }
}

