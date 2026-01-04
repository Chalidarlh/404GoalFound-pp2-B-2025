/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.DAO;

import id.ac.unpas.goalfound.Model.JadwalPertandingan;
import id.ac.unpas.goalfound.KoneksiDB;
import java.sql.*;

/**
 *
 * @author NNDAAA
 */
public class JadwalPertandinganDAO {
    
    public void tambahJadwal(JadwalPertandingan jadwal) throws Exception {
        String sql = """
            INSERT INTO jadwal_pertandingan(id_tim_tuan, id_tim_tamu, tanggal_pertandingan, 
                                            waktu_pertandingan, lokasi, status)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setInt(1, jadwal.getIdTimTuan());
        ps.setInt(2, jadwal.getIdTimTamu());
        ps.setDate(3, jadwal.getTanggalPertandingan());
        ps.setTime(4, jadwal.getWaktuPertandingan());
        ps.setString(5, jadwal.getLokasi());
        ps.setString(6, jadwal.getStatus());
        ps.executeUpdate();
    }
    
    public ResultSet loadSemuaJadwal() throws Exception {
        String sql = """
            SELECT 
                jp.id_jadwal,
                t1.nama_tim AS tim_tuan,
                t2.nama_tim AS tim_tamu,
                jp.tanggal_pertandingan,
                jp.waktu_pertandingan,
                jp.lokasi,
                jp.status
            FROM jadwal_pertandingan jp
            JOIN tim t1 ON jp.id_tim_tuan = t1.id_tim
            JOIN tim t2 ON jp.id_tim_tamu = t2.id_tim
            ORDER BY jp.tanggal_pertandingan, jp.waktu_pertandingan
        """;
        Statement st = KoneksiDB.configDB().createStatement();
        return st.executeQuery(sql);
    }
    
    public ResultSet loadJadwalByStatus(String status) throws Exception {
        String sql = """
            SELECT 
                jp.id_jadwal,
                t1.nama_tim AS tim_tuan,
                t2.nama_tim AS tim_tamu,
                jp.tanggal_pertandingan,
                jp.waktu_pertandingan,
                jp.lokasi,
                jp.status
            FROM jadwal_pertandingan jp
            JOIN tim t1 ON jp.id_tim_tuan = t1.id_tim
            JOIN tim t2 ON jp.id_tim_tamu = t2.id_tim
            WHERE jp.status = ?
            ORDER BY jp.tanggal_pertandingan, jp.waktu_pertandingan
        """;
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setString(1, status);
        return ps.executeQuery();
    }
    
    public ResultSet loadJadwalByTim(int idTim) throws Exception {
        String sql = """
            SELECT 
                jp.id_jadwal,
                t1.nama_tim AS tim_tuan,
                t2.nama_tim AS tim_tamu,
                jp.tanggal_pertandingan,
                jp.waktu_pertandingan,
                jp.lokasi,
                jp.status
            FROM jadwal_pertandingan jp
            JOIN tim t1 ON jp.id_tim_tuan = t1.id_tim
            JOIN tim t2 ON jp.id_tim_tamu = t2.id_tim
            WHERE jp.id_tim_tuan = ? OR jp.id_tim_tamu = ?
            ORDER BY jp.tanggal_pertandingan, jp.waktu_pertandingan
        """;
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setInt(1, idTim);
        ps.setInt(2, idTim);
        return ps.executeQuery();
    }
    
    public void ubahJadwal(JadwalPertandingan jadwal) throws Exception {
        String sql = """
            UPDATE jadwal_pertandingan
            SET id_tim_tuan = ?, id_tim_tamu = ?, tanggal_pertandingan = ?, 
                waktu_pertandingan = ?, lokasi = ?, status = ?
            WHERE id_jadwal = ?
        """;
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setInt(1, jadwal.getIdTimTuan());
        ps.setInt(2, jadwal.getIdTimTamu());
        ps.setDate(3, jadwal.getTanggalPertandingan());
        ps.setTime(4, jadwal.getWaktuPertandingan());
        ps.setString(5, jadwal.getLokasi());
        ps.setString(6, jadwal.getStatus());
        ps.setInt(7, jadwal.getIdJadwal());
        ps.executeUpdate();
    }
    
    public void ubahStatus(int idJadwal, String statusBaru) throws Exception {
        String sql = "UPDATE jadwal_pertandingan SET status = ? WHERE id_jadwal = ?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setString(1, statusBaru);
        ps.setInt(2, idJadwal);
        ps.executeUpdate();
    }
    
    public void hapusJadwal(int idJadwal) throws Exception {
        String sql = "DELETE FROM jadwal_pertandingan WHERE id_jadwal = ?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setInt(1, idJadwal);
        ps.executeUpdate();
    }
    
    public boolean cekJadwalAda(int idJadwal) throws Exception {
        String sql = "SELECT id_jadwal FROM jadwal_pertandingan WHERE id_jadwal = ?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setInt(1, idJadwal);
        return ps.executeQuery().next();
    }
    
    public boolean cekKonflikJadwal(int idTim, Date tanggal, Time waktu) throws Exception {
        String sql = """
            SELECT id_jadwal FROM jadwal_pertandingan 
            WHERE (id_tim_tuan = ? OR id_tim_tamu = ?) 
            AND tanggal_pertandingan = ? 
            AND waktu_pertandingan = ?
        """;
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setInt(1, idTim);
        ps.setInt(2, idTim);
        ps.setDate(3, tanggal);
        ps.setTime(4, waktu);
        return ps.executeQuery().next();
    }
    
    public boolean cekKonflikLokasi(String lokasi, Date tanggal, Time waktu) throws Exception {
        String sql = """
            SELECT id_jadwal FROM jadwal_pertandingan 
            WHERE lokasi = ? AND tanggal_pertandingan = ? AND waktu_pertandingan = ?
        """;
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setString(1, lokasi);
        ps.setDate(2, tanggal);
        ps.setTime(3, waktu);
        return ps.executeQuery().next();
    }
}
