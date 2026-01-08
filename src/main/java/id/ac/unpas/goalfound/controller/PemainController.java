/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.controller;

/**
 *
 * @author Asus
 */
import id.ac.unpas.goalfound.DAO.PemainDAO;
import id.ac.unpas.goalfound.Model.Pemain;
import id.ac.unpas.goalfound.view.PemainView;

import javax.swing.*;
import java.sql.ResultSet;

public class PemainController {

    private PemainView view;
    private PemainDAO dao;

    public PemainController(PemainView view) {
        this.view = view;
        this.dao = new PemainDAO();

        view.btnTambah.addActionListener(e -> tambahDataPemain());
        view.btnUbah.addActionListener(e -> ubahDataPemain());
        view.btnHapus.addActionListener(e -> hapusDataPemain());
       

        loadDataPemain();
    }

    // load data pemain
    public void loadDataPemain() {
        try {
            view.model.setRowCount(0);
            ResultSet rs = dao.loadDataPemain();
            int no = 1;

            while (rs.next()) {
                view.model.addRow(new Object[]{
                    no++,
                    rs.getString("nama_tim"),
                    rs.getString("nama_pemain"),
                    rs.getString("npm"),
                    rs.getInt("no_punggung")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }
    
    // validasi huruf saja (nama)
    private boolean isOnlyLetters(String text) {
        return text.matches("[a-zA-Z ]+");
    }

    // validasi angka saja
    private boolean isOnlyNumbers(String text) {
        return text.matches("\\d+");
    }


    // tambah data pemain
    public void tambahDataPemain() {
    try {
        String nama = view.txtNama.getText().trim();
        String npm = view.txtNpm.getText().trim();
        String no = view.txtNo.getText().trim();

        // ================= CEK KOSONG =================
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama pemain tidak boleh kosong");
            return;
        }

        if (npm.isEmpty()) {
            JOptionPane.showMessageDialog(view, "NPM tidak boleh kosong");
            return;
        }

        if (no.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No punggung tidak boleh kosong");
            return;
        }

        // ================= VALIDASI NAMA =================
        if (!isOnlyLetters(nama)) {
            JOptionPane.showMessageDialog(view, "Nama pemain hanya boleh berisi huruf");
            return;
        }

        // ================= VALIDASI NPM =================
        if (!isOnlyNumbers(npm)) {
            JOptionPane.showMessageDialog(view, "NPM harus berupa angka");
            return;
        }

        if (npm.length() > 10) {
            JOptionPane.showMessageDialog(view, "NPM tidak boleh lebih dari 10 angka");
            return;
        }

        if (dao.cekNpmPemain(npm)) {
            JOptionPane.showMessageDialog(view, "NPM sudah terdaftar");
            return;
        }

        // ================= VALIDASI NO PUNGGUNG =================
        if (!isOnlyNumbers(no)) {
            JOptionPane.showMessageDialog(view, "No punggung harus berupa angka");
            return;
        }

        if (view.getSelectedIdTim() == 0) {
            JOptionPane.showMessageDialog(view, "Tim wajib dipilih");
            return;
        }

        Pemain p = new Pemain(
            view.getSelectedIdTim(),
            nama,
            npm,
            Integer.parseInt(no)
        );

        dao.tambahDataPemain(p);
        loadDataPemain();
        view.resetForm();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(view, e.getMessage());
    }
}



    // update data pemain
    public void ubahDataPemain() {
        try {
            if (view.getSelectedIdTim() == 0) {
                JOptionPane.showMessageDialog(view, "Tim wajib dipilih");
                return;
            }

            Pemain p = new Pemain(
                view.getSelectedIdTim(),
                view.txtNama.getText(),
                view.txtNpm.getText(),
                Integer.parseInt(view.txtNo.getText())
            );

            dao.ubahDataPemain(p);
            loadDataPemain();
            view.resetForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }


    // delete data pemain
    public void hapusDataPemain() {
        try {
            String npm = view.txtNpm.getText();
            
            if (npm.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Pilih pemain yang akan dihapus");
                return;
            }

            dao.hapusDataPemain(npm);
            JOptionPane.showMessageDialog(view, "Pemain berhasil dihapus");
            loadDataPemain();
            view.resetForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }
    
    // load data pemain berdasarkan tim
    public void loadDataPemainByTim(int idTim) {
        try {
            view.model.setRowCount(0);
            ResultSet rs = dao.loadDataPemainByTim(idTim);
            int no = 1;

            while (rs.next()) {
                view.model.addRow(new Object[]{
                    no++,
                    rs.getString("nama_tim"),
                    rs.getString("nama_pemain"),
                    rs.getString("npm"),
                    rs.getInt("no_punggung")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
    }
       
    }
}

    


