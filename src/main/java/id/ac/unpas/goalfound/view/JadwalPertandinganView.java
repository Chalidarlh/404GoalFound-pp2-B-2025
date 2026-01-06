/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.view;

import id.ac.unpas.goalfound.controller.JadwalPertandinganController;
import id.ac.unpas.goalfound.Model.JadwalPertandingan;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;

/**
 *
 * @author Rzaaa
 */
public class JadwalPertandinganView extends JPanel {

    private JTextField txtIdJadwal, txtIdTuan, txtIdTamu, txtTanggal, txtWaktu, txtLokasi;
    private JComboBox<String> cmbStatus;
    private JButton btnTambah, btnUbah, btnHapus, btnClear;
    private JTable table;
    private DefaultTableModel model;
    
    private JadwalPertandinganController controller;

    public JadwalPertandinganView() {
        controller = new JadwalPertandinganController();
        initComponent();
        loadData();
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        
        JPanel panelForm = new JPanel(new GridLayout(7, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Form Jadwal"));
        
        panelForm.add(new JLabel("ID Jadwal (Auto/Pilih)"));
        txtIdJadwal = new JTextField(); 
        txtIdJadwal.setEditable(false);
        panelForm.add(txtIdJadwal);
        
        panelForm.add(new JLabel("ID Tim Tuan Rumah"));
        txtIdTuan = new JTextField();
        panelForm.add(txtIdTuan);
        
        panelForm.add(new JLabel("ID Tim Tamu"));
        txtIdTamu = new JTextField();
        panelForm.add(txtIdTamu);
        
        panelForm.add(new JLabel("Tanggal (YYYY-MM-DD)"));
        txtTanggal = new JTextField();
        panelForm.add(txtTanggal);
        
        panelForm.add(new JLabel("Waktu (HH:MM:SS)"));
        txtWaktu = new JTextField();
        panelForm.add(txtWaktu);
        
        panelForm.add(new JLabel("Lokasi"));
        txtLokasi = new JTextField();
        panelForm.add(txtLokasi);
        
        panelForm.add(new JLabel("Status"));
        cmbStatus = new JComboBox<>(new String[]{"Dijadwalkan", "Berlangsung", "Selesai", "Dibatalkan"});
        panelForm.add(cmbStatus);
        
        add(panelForm, BorderLayout.NORTH);
        
        JPanel panelBtn = new JPanel();
        btnTambah = new JButton("Buat Jadwal");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");
        
        panelBtn.add(btnTambah);
        panelBtn.add(btnUbah);
        panelBtn.add(btnHapus);
        panelBtn.add(btnClear);
        add(panelBtn, BorderLayout.CENTER);
        
        model = new DefaultTableModel(new String[]{"ID", "Tuan", "Tamu", "Tanggal", "Waktu", "Lokasi", "Status"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.SOUTH);
        
        initEvents();
    }
    
    private void initEvents() {
        btnTambah.addActionListener(e -> {
            try {
                JadwalPertandingan jp = new JadwalPertandingan();
                jp.setIdTimTuan(Integer.parseInt(txtIdTuan.getText()));
                jp.setIdTimTamu(Integer.parseInt(txtIdTamu.getText()));
                jp.setTanggalPertandingan(Date.valueOf(txtTanggal.getText()));
                jp.setWaktuPertandingan(Time.valueOf(txtWaktu.getText()));
                jp.setLokasi(txtLokasi.getText());
                jp.setStatus(cmbStatus.getSelectedItem().toString());
                
                String res = controller.tambahJadwal(jp);
                JOptionPane.showMessageDialog(this, res);
                if(res.startsWith("Berhasil")) { loadData(); clearForm(); }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Format Error: " + ex.getMessage()); }
        });

        btnUbah.addActionListener(e -> {
             try {
                if(txtIdJadwal.getText().isEmpty()) return;
                JadwalPertandingan jp = new JadwalPertandingan();
                jp.setIdJadwal(Integer.parseInt(txtIdJadwal.getText()));
                jp.setIdTimTuan(Integer.parseInt(txtIdTuan.getText()));
                jp.setIdTimTamu(Integer.parseInt(txtIdTamu.getText()));
                jp.setTanggalPertandingan(Date.valueOf(txtTanggal.getText()));
                jp.setWaktuPertandingan(Time.valueOf(txtWaktu.getText()));
                jp.setLokasi(txtLokasi.getText());
                jp.setStatus(cmbStatus.getSelectedItem().toString());
                
                JOptionPane.showMessageDialog(this, controller.ubahJadwal(jp));
                loadData();
                clearForm();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
        });
        
        btnHapus.addActionListener(e -> {
            if(!txtIdJadwal.getText().isEmpty()) {
                if(JOptionPane.showConfirmDialog(this, "Hapus jadwal ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(this, controller.hapusJadwal(Integer.parseInt(txtIdJadwal.getText())));
                    loadData();
                    clearForm();
                }
            }
        });
        
        btnClear.addActionListener(e -> clearForm());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtIdJadwal.setText(model.getValueAt(row, 0).toString());
                // Note: Mengambil ID tim agak sulit hanya dari nama di tabel tanpa query ulang
                // User harus memasukkan ID Tim Tuan/Tamu secara manual untuk edit jika tidak menggunakan combo box
                txtTanggal.setText(model.getValueAt(row, 3).toString());
                txtWaktu.setText(model.getValueAt(row, 4).toString());
                txtLokasi.setText(model.getValueAt(row, 5).toString());
                cmbStatus.setSelectedItem(model.getValueAt(row, 6).toString());
            }
        });
    }

    public void loadData() {
        model.setRowCount(0);
        ResultSet rs = controller.loadSemuaJadwal();
        try {
            while(rs != null && rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_jadwal"),
                    rs.getString("tim_tuan"),
                    rs.getString("tim_tamu"),
                    rs.getDate("tanggal_pertandingan"),
                    rs.getTime("waktu_pertandingan"),
                    rs.getString("lokasi"),
                    rs.getString("status")
                });
            }
        } catch(Exception e) { e.printStackTrace(); }
    }
    
    private void clearForm() {
        txtIdJadwal.setText(""); txtIdTuan.setText(""); txtIdTamu.setText("");
        txtTanggal.setText(""); txtWaktu.setText(""); txtLokasi.setText("");
        cmbStatus.setSelectedIndex(0);
        table.clearSelection();
    }
}
