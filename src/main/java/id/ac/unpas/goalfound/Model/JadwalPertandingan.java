/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.Model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author NNDAAA
 */
public class JadwalPertandingan {
    
    private int idJadwal;
    private int idTimTuan;
    private int idTimTamu;
    private Date tanggalPertandingan;
    private Time waktuPertandingan;
    private String lokasi;
    private String status;
    
    public JadwalPertandingan() {}
    
    public JadwalPertandingan(int idTimTuan, int idTimTamu, Date tanggalPertandingan, 
                             Time waktuPertandingan, String lokasi, String status) {
        this.idTimTuan = idTimTuan;
        this.idTimTamu = idTimTamu;
        this.tanggalPertandingan = tanggalPertandingan;
        this.waktuPertandingan = waktuPertandingan;
        this.lokasi = lokasi;
        this.status = status;
    }
    
    public JadwalPertandingan(int idJadwal, int idTimTuan, int idTimTamu, 
                             Date tanggalPertandingan, Time waktuPertandingan, 
                             String lokasi, String status) {
        this.idJadwal = idJadwal;
        this.idTimTuan = idTimTuan;
        this.idTimTamu = idTimTamu;
        this.tanggalPertandingan = tanggalPertandingan;
        this.waktuPertandingan = waktuPertandingan;
        this.lokasi = lokasi;
        this.status = status;
    }

    public int getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(int idJadwal) {
        this.idJadwal = idJadwal;
    }

    public int getIdTimTuan() {
        return idTimTuan;
    }

    public void setIdTimTuan(int idTimTuan) {
        this.idTimTuan = idTimTuan;
    }

    public int getIdTimTamu() {
        return idTimTamu;
    }

    public void setIdTimTamu(int idTimTamu) {
        this.idTimTamu = idTimTamu;
    }

    public Date getTanggalPertandingan() {
        return tanggalPertandingan;
    }

    public void setTanggalPertandingan(Date tanggalPertandingan) {
        this.tanggalPertandingan = tanggalPertandingan;
    }

    public Time getWaktuPertandingan() {
        return waktuPertandingan;
    }

    public void setWaktuPertandingan(Time waktuPertandingan) {
        this.waktuPertandingan = waktuPertandingan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
