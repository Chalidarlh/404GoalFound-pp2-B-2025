/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.view;

import id.ac.unpas.goalfound.Model.JadwalPertandingan;
import id.ac.unpas.goalfound.controller.JadwalPertandinganController;
import id.ac.unpas.goalfound.DAO.TimDAO;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

/**
 *
 * @author Rzaaa
 */
public class JadwalPertandinganView extends JPanel {

    private class TimItem {
        private int id;
        private String nama;
        public TimItem(int id, String nama) { this.id = id; this.nama = nama; }
        public int getId() { return id; }
        @Override public String toString() { return nama; }
    }

    private JComboBox<TimItem> cmbTimTuan, cmbTimTamu;
    private JDateChooser dateChooser; 
    private JSpinner spinnerWaktu;  
    private JTextField txtLokasi;
    private JComboBox<String> cmbStatus;
    
    private JButton btnTambah, btnUbah, btnHapus, btnClear, btnExportPdf;
    private JTable table;
    private DefaultTableModel model;
    private int selectedId = 0;
    private JadwalPertandinganController controller;

    public JadwalPertandinganView() {
        controller = new JadwalPertandinganController();
        initComponent();
        loadTim(); 
        initEvent();
        loadData();
    }

    private void initComponent() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.Y_AXIS));

        JPanel panelForm = new JPanel(new GridLayout(6, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Form Jadwal Pertandingan"));

        panelForm.add(new JLabel("Tim Tuan Rumah:"));
        cmbTimTuan = new JComboBox<>();
        panelForm.add(cmbTimTuan);

        panelForm.add(new JLabel("Tim Tamu:"));
        cmbTimTamu = new JComboBox<>();
        panelForm.add(cmbTimTamu);

        panelForm.add(new JLabel("Tanggal:"));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setDate(new java.util.Date());
        panelForm.add(dateChooser);

        panelForm.add(new JLabel("Waktu:"));
        SpinnerDateModel timeModel = new SpinnerDateModel();
        spinnerWaktu = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinnerWaktu, "HH:mm:ss");
        spinnerWaktu.setEditor(timeEditor);
        panelForm.add(spinnerWaktu);

        panelForm.add(new JLabel("Lokasi:"));
        txtLokasi = new JTextField();
        panelForm.add(txtLokasi);

        panelForm.add(new JLabel("Status:"));
        cmbStatus = new JComboBox<>(new String[]{"Dijadwalkan", "Berlangsung", "Selesai", "Dibatalkan"});
        panelForm.add(cmbStatus);

        panelHeader.add(panelForm);

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        btnTambah = new JButton("Buat Jadwal");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");
        btnExportPdf = new JButton("Export PDF");
        
        btnExportPdf.setBackground(new Color(46, 204, 113));
        btnExportPdf.setForeground(Color.WHITE);

        panelButton.add(btnTambah);
        panelButton.add(btnUbah);
        panelButton.add(btnHapus);
        panelButton.add(btnClear);
        panelButton.add(btnExportPdf);

        panelHeader.add(panelButton);

        add(panelHeader, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"No", "Tuan", "Tamu", "Tanggal", "Waktu", "Lokasi", "Status"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        add(scroll, BorderLayout.CENTER);
    }
    
    private void loadTim() {
        try {
            TimDAO timDao = new TimDAO();
            ResultSet rs = timDao.getAll();
            cmbTimTuan.removeAllItems();
            cmbTimTamu.removeAllItems();
            while (rs.next()) {
                TimItem item = new TimItem(rs.getInt("id_tim"), rs.getString("nama_tim"));
                cmbTimTuan.addItem(item);
                cmbTimTamu.addItem(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load tim: " + e.getMessage());
        }
    }

    private void initEvent() {
        btnTambah.addActionListener(e -> {
            try {
                JadwalPertandingan jp = new JadwalPertandingan();
                
                TimItem timTuan = (TimItem) cmbTimTuan.getSelectedItem();
                TimItem timTamu = (TimItem) cmbTimTamu.getSelectedItem();
                if(timTuan != null) jp.setIdTimTuan(timTuan.getId());
                if(timTamu != null) jp.setIdTimTamu(timTamu.getId());
                
                java.util.Date utilDate = dateChooser.getDate();
                if (utilDate == null) throw new Exception("Tanggal wajib diisi!");
                jp.setTanggalPertandingan(new java.sql.Date(utilDate.getTime()));
                
                java.util.Date utilTime = (java.util.Date) spinnerWaktu.getValue();
                jp.setWaktuPertandingan(new java.sql.Time(utilTime.getTime()));
                
                jp.setLokasi(txtLokasi.getText());
                jp.setStatus(cmbStatus.getSelectedItem().toString());
                
                String res = controller.tambahJadwal(jp);
                JOptionPane.showMessageDialog(this, res);
                if(res.startsWith("Berhasil")) { loadData(); clearForm(); }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
        });

        btnUbah.addActionListener(e -> {
             try {
                if(selectedId == 0) {
                    JOptionPane.showMessageDialog(this, "Pilih jadwal dari tabel dahulu!");
                    return;
                }
                JadwalPertandingan jp = new JadwalPertandingan();
                jp.setIdJadwal(selectedId);
                
                TimItem timTuan = (TimItem) cmbTimTuan.getSelectedItem();
                TimItem timTamu = (TimItem) cmbTimTamu.getSelectedItem();
                if(timTuan != null) jp.setIdTimTuan(timTuan.getId());
                if(timTamu != null) jp.setIdTimTamu(timTamu.getId());
                
                java.util.Date utilDate = dateChooser.getDate();
                if (utilDate == null) throw new Exception("Tanggal wajib diisi!");
                jp.setTanggalPertandingan(new java.sql.Date(utilDate.getTime()));
                
                java.util.Date utilTime = (java.util.Date) spinnerWaktu.getValue();
                jp.setWaktuPertandingan(new java.sql.Time(utilTime.getTime()));
                
                jp.setLokasi(txtLokasi.getText());
                jp.setStatus(cmbStatus.getSelectedItem().toString());
                
                String res = controller.ubahJadwal(jp);
                JOptionPane.showMessageDialog(this, res);
                loadData();
                clearForm();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
        });
        
        btnHapus.addActionListener(e -> {
            if(selectedId != 0) {
                if(JOptionPane.showConfirmDialog(this, "Hapus jadwal ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(this, controller.hapusJadwal(selectedId));
                    loadData();
                    clearForm();
                }
            } else {
                 JOptionPane.showMessageDialog(this, "Pilih jadwal dari tabel dahulu!");
            }
        });
        
        btnExportPdf.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Simpan PDF");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                if(!filePath.endsWith(".pdf")) filePath += ".pdf";
                String res = controller.exportJadwalKeFile(table, filePath);
                JOptionPane.showMessageDialog(this, res);
            }
        });
        
        btnClear.addActionListener(e -> clearForm());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                try {
                    selectedId = Integer.parseInt(model.getValueAt(row, 0).toString());
                } catch(Exception ex) { selectedId = 0; }
                
                String namaTuan = model.getValueAt(row, 1).toString();
                String namaTamu = model.getValueAt(row, 2).toString();
                setSelectedTim(cmbTimTuan, namaTuan);
                setSelectedTim(cmbTimTamu, namaTamu);
                
                try {
                    String dateStr = model.getValueAt(row, 3).toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    dateChooser.setDate(sdf.parse(dateStr));
                } catch(Exception ex) {}
                
                try {
                    String timeStr = model.getValueAt(row, 4).toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    spinnerWaktu.setValue(sdf.parse(timeStr));
                } catch(Exception ex) {}

                txtLokasi.setText(model.getValueAt(row, 5).toString());
                cmbStatus.setSelectedItem(model.getValueAt(row, 6).toString());
            }
        });
    }

    private void setSelectedTim(JComboBox<TimItem> cmb, String namaTim) {
        for (int i = 0; i < cmb.getItemCount(); i++) {
            TimItem item = cmb.getItemAt(i);
            if (item.toString().equals(namaTim)) {
                cmb.setSelectedIndex(i);
                break;
            }
        }
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
        selectedId = 0;
        if(cmbTimTuan.getItemCount() > 0) cmbTimTuan.setSelectedIndex(0);
        if(cmbTimTamu.getItemCount() > 0) cmbTimTamu.setSelectedIndex(0);
        dateChooser.setDate(new java.util.Date());
        spinnerWaktu.setValue(new java.util.Date());
        txtLokasi.setText("");
        cmbStatus.setSelectedIndex(0);
        table.clearSelection();
    }
}
