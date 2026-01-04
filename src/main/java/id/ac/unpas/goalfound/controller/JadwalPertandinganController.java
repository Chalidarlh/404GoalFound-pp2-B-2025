/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.controller;

import id.ac.unpas.goalfound.DAO.JadwalPertandinganDAO;
import id.ac.unpas.goalfound.Model.JadwalPertandingan;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author NNDAAA
 */
public class JadwalPertandinganController {
    
    private JadwalPertandinganDAO dao;
    
    public JadwalPertandinganController() {
        this.dao = new JadwalPertandinganDAO();
    }
    
    public String tambahJadwal(JadwalPertandingan jadwal) {
        try {
            if (jadwal.getIdTimTuan() == jadwal.getIdTimTamu()) {
                return "Gagal: Tim tuan dan tim tamu tidak boleh sama!";
            }
            
            if (dao.cekKonflikJadwal(jadwal.getIdTimTuan(), 
                                     jadwal.getTanggalPertandingan(), 
                                     jadwal.getWaktuPertandingan())) {
                return "Gagal: Tim tuan sudah memiliki jadwal di waktu yang sama!";
            }
            
            if (dao.cekKonflikJadwal(jadwal.getIdTimTamu(), 
                                     jadwal.getTanggalPertandingan(), 
                                     jadwal.getWaktuPertandingan())) {
                return "Gagal: Tim tamu sudah memiliki jadwal di waktu yang sama!";
            }
            
            if (dao.cekKonflikLokasi(jadwal.getLokasi(), 
                                     jadwal.getTanggalPertandingan(), 
                                     jadwal.getWaktuPertandingan())) {
                return "Gagal: Lokasi sudah dipakai di waktu yang sama!";
            }
            
            dao.tambahJadwal(jadwal);
            return "Berhasil menambahkan jadwal pertandingan!";
        } catch (Exception e) {
            return "Gagal menambahkan jadwal: " + e.getMessage();
        }
    }
    
    public ResultSet loadSemuaJadwal() {
        try {
            return dao.loadSemuaJadwal();
        } catch (Exception e) {
            System.err.println("Error load semua jadwal: " + e.getMessage());
            return null;
        }
    }
    
    public ResultSet loadJadwalByStatus(String status) {
        try {
            return dao.loadJadwalByStatus(status);
        } catch (Exception e) {
            System.err.println("Error load jadwal by status: " + e.getMessage());
            return null;
        }
    }
    
    public ResultSet loadJadwalByTim(int idTim) {
        try {
            return dao.loadJadwalByTim(idTim);
        } catch (Exception e) {
            System.err.println("Error load jadwal by tim: " + e.getMessage());
            return null;
        }
    }
    
    public String ubahJadwal(JadwalPertandingan jadwal) {
        try {
            if (!dao.cekJadwalAda(jadwal.getIdJadwal())) {
                return "Gagal: Jadwal tidak ditemukan!";
            }
            
            if (jadwal.getIdTimTuan() == jadwal.getIdTimTamu()) {
                return "Gagal: Tim tuan dan tim tamu tidak boleh sama!";
            }
            
            dao.ubahJadwal(jadwal);
            return "Berhasil mengubah jadwal pertandingan!";
        } catch (Exception e) {
            return "Gagal mengubah jadwal: " + e.getMessage();
        }
    }
    
    public String ubahStatus(int idJadwal, String statusBaru) {
        try {
            if (!dao.cekJadwalAda(idJadwal)) {
                return "Gagal: Jadwal tidak ditemukan!";
            }
            
            if (!statusBaru.equals("Dijadwalkan") && 
                !statusBaru.equals("Berlangsung") && 
                !statusBaru.equals("Selesai") && 
                !statusBaru.equals("Dibatalkan")) {
                return "Gagal: Status tidak valid!";
            }
            
            dao.ubahStatus(idJadwal, statusBaru);
            return "Berhasil mengubah status jadwal!";
        } catch (Exception e) {
            return "Gagal mengubah status: " + e.getMessage();
        }
    }
    
    public String hapusJadwal(int idJadwal) {
        try {
            if (!dao.cekJadwalAda(idJadwal)) {
                return "Gagal: Jadwal tidak ditemukan!";
            }
            
            dao.hapusJadwal(idJadwal);
            return "Berhasil menghapus jadwal pertandingan!";
        } catch (Exception e) {
            return "Gagal menghapus jadwal: " + e.getMessage();
        }
    }
    
    public boolean cekKonflikJadwal(int idTim, Date tanggal, Time waktu) {
        try {
            return dao.cekKonflikJadwal(idTim, tanggal, waktu);
        } catch (Exception e) {
            System.err.println("Error cek konflik jadwal: " + e.getMessage());
            return false;
        }
    }
    
    public boolean cekKonflikLokasi(String lokasi, Date tanggal, Time waktu) {
        try {
            return dao.cekKonflikLokasi(lokasi, tanggal, waktu);
        } catch (Exception e) {
            System.err.println("Error cek konflik lokasi: " + e.getMessage());
            return false;
        }
    }
}
