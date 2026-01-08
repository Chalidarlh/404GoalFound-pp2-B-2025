/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.DAO;

import java.sql.PreparedStatement;
import id.ac.unpas.goalfound.Model.Pemain;
import id.ac.unpas.goalfound.KoneksiDB;
import java.sql.*;
/**
 *
 * @author Muhammad Fauzan nur
 */
public class PemainDAO {
    // tambah data pemain
    public void tambahDataPemain(Pemain p) throws Exception {
        String sql = """
            INSERT INTO pemain(id_tim, nama_pemain, npm, no_punggung)
            VALUES (?,?,?,?)
        """;
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setInt(1, p.getIdTim());
        ps.setString(2, p.getNamaPemain());
        ps.setString(3, p.getNpm());
        ps.setInt(4, p.getNoPunggung());
        ps.executeUpdate();
    }

    // read data pemain
    public ResultSet loadDataPemain() throws Exception {
        String sql = """
            SELECT p.id_pemain, t.nama_tim, p.nama_pemain, p.npm, p.no_punggung
            FROM pemain p
            JOIN tim t ON p.id_tim = t.id_tim
        """;
        Statement st = KoneksiDB.configDB().createStatement();
        return st.executeQuery(sql);
    }

    // update data pemain
    public void ubahDataPemain(Pemain p) throws Exception {
        String sql = """
            UPDATE pemain
            SET nama_pemain = ?, no_punggung = ?, id_tim = ?
            WHERE npm = ?
        """;
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setString(1, p.getNamaPemain());
        ps.setInt(2, p.getNoPunggung());
        ps.setInt(3, p.getIdTim());
        ps.setString(4, p.getNpm());
        ps.executeUpdate();
    }

    // hapus data pemain
    public void hapusDataPemain(String npm) throws Exception {
        String sql = "DELETE FROM pemain WHERE npm = ?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setString(1, npm);
        ps.executeUpdate();
    }

    //sorting
    public ResultSet loadDataPemainByTim(int idTim) throws Exception {
    String sql = """
        SELECT p.id_pemain, t.nama_tim, p.nama_pemain, p.npm, p.no_punggung
        FROM pemain p
        JOIN tim t ON p.id_tim = t.id_tim
        WHERE p.id_tim = ?
    """;

    PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
    ps.setInt(1, idTim);
    return ps.executeQuery();
}

    // validasi
    public void hapusPemainByTim(int idTim) throws Exception {
        String sql = "DELETE FROM pemain WHERE id_tim = ?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setInt(1, idTim);
        ps.executeUpdate();
    }

    // ================= VALIDASI =================
    public boolean cekNpmPemain(String npm) throws Exception {
        String sql = "SELECT npm FROM pemain WHERE npm = ?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setString(1, npm);
        return ps.executeQuery().next();
    }

}
