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
import id.ac.unpas.goalfound.util.ExportPDF;

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
        view.btnExport.addActionListener(e -> exportPdfPemain());

        loadDataPemain();
    }

    // load data pemain
    public void loadDataPemain() {
        try {
            //view.model.setRowCount(0);
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

    // tambah data pemain
    public void tambahDataPemain() {
        try {
            if (view.txtNama.getText().isEmpty() ||
                view.txtNpm.getText().isEmpty() ||
                view.txtNo.getText().isEmpty()) {

                JOptionPane.showMessageDialog(view, "Data tidak boleh kosong");
                return;
            }

            if (dao.cekNpmPemain(view.txtNpm.getText())) {
                JOptionPane.showMessageDialog(view, "NPM sudah terdaftar");
                return;
            }

            Pemain p = new Pemain(
                view.getSelectedIdTim(),
                view.txtNama.getText(),
                view.txtNpm.getText(),
                Integer.parseInt(view.txtNo.getText())
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
            dao.hapusDataPemain(view.txtNpm.getText());
            loadDataPemain();
            view.resetForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    // export pdf
    public void exportPdfPemain() {
        try {
            ExportPDF.exportTable(view.tablePemain, "data_pemain.pdf");
            JOptionPane.showMessageDialog(view, "Export PDF berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }
}
