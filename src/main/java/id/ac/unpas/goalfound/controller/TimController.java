/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.controller;

/**
 *
 * @author Muhammad Fauzan nur
 */

import id.ac.unpas.goalfound.DAO.TimDAO;
import id.ac.unpas.goalfound.Model.Tim;
import id.ac.unpas.goalfound.view.TimView;

import javax.swing.JOptionPane;
import java.sql.ResultSet;

public class TimController {

    private TimView view;
    private TimDAO dao;

    public TimController(TimView view) {
        this.view = view;
        this.dao = new TimDAO();

       
        loadTable();
    }

    
    public void tambahTim() {
        try {
            String nama = view.getNamaTim();
            String fakultas = view.getFakultas();

            
            if (nama.isEmpty()) {
                throw new Exception("Nama tim tidak boleh kosong");
            }
            if (fakultas == null) {
                throw new Exception("Fakultas wajib dipilih");
            }

            Tim t = new Tim();
            t.setNamaTim(nama);
            t.setFakultas(fakultas);

            dao.insert(t);

            JOptionPane.showMessageDialog(view, "Tim berhasil ditambahkan");
            loadTable();
            view.clearForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    
    public void ubahTim() {
        try {
            int id = view.getIdTim();
            String nama = view.getNamaTim();
            String fakultas = view.getFakultas();

            if (id == 0) {
                throw new Exception("Pilih tim terlebih dahulu");
            }

            Tim t = new Tim();
            t.setIdTim(id);
            t.setNamaTim(nama);
            t.setFakultas(fakultas);

            dao.update(t);

            JOptionPane.showMessageDialog(view, "Data tim berhasil diubah");
            loadTable();
            view.clearForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    
    public void hapusTim() {
        try {
            int id = view.getIdTim();

            if (id == 0) {
                throw new Exception("Pilih tim yang akan dihapus");
            }

            boolean punyaJadwal = dao.cekJadwalTerkait(id);
            boolean punyaPemain = dao.cekPemainTerkait(id);

            if (punyaJadwal || punyaPemain) {
                StringBuilder info = new StringBuilder("Tim ini memiliki: \n");
                if (punyaJadwal) info.append("- Jadwal pertandingan\n");
                if (punyaPemain) info.append("- Pemain terdaftar\n");

                int konfirmasiCascade = JOptionPane.showConfirmDialog(
                        view,
                        info + "\nApakah Anda yakin ingin menghapus tim beserta seluruh jadwal dan pemain terkait?",
                        "Peringatan - Hapus Beserta Relasi",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (konfirmasiCascade == JOptionPane.YES_OPTION) {
                    dao.deleteCascade(id);
                    JOptionPane.showMessageDialog(view, "Tim, jadwal, dan pemain terkait berhasil dihapus");
                    loadTable();
                    view.clearForm();
                }
            } else {
                int konfirmasi = JOptionPane.showConfirmDialog(
                        view,
                        "Yakin ingin menghapus tim ini?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION
                );

                if (konfirmasi == JOptionPane.YES_OPTION) {
                    dao.delete(id);
                    JOptionPane.showMessageDialog(view, "Tim berhasil dihapus");
                    loadTable();
                    view.clearForm();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }



    public void loadTable() {
        try {
            ResultSet rs = dao.getAll();
            view.tampilkanData(rs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }
}

