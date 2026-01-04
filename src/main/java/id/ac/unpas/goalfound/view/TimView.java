/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.view;

import id.ac.unpas.goalfound.controller.TimController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

/**
 *
 * @author Rezaaa
 */

public class TimView extends JPanel {

    private JTextField txtId, txtNama;
    private JComboBox<String> cbFakultas;
    private JButton btnTambah, btnUbah, btnHapus, btnClear;
    private JTable table;
    private DefaultTableModel model;

    private TimController controller;

    public TimView() {
        initComponent();
        
        controller = new TimController(this);
        
        initEvent();
    }

    private void initComponent() {
        setLayout(new BorderLayout());

        // Form Input
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Form Data Tim"));

        panelForm.add(new JLabel("ID Tim"));
        txtId = new JTextField();
        txtId.setEditable(false); 
        panelForm.add(txtId);

        panelForm.add(new JLabel("Nama Tim"));
        txtNama = new JTextField();
        panelForm.add(txtNama);

        panelForm.add(new JLabel("Fakultas"));
        cbFakultas = new JComboBox<>(new String[]{
                "-- Pilih Fakultas --", "Teknik", "FEB", "FK", "FKIP", "FH", "FISIP", "SASTRA"
        });
        panelForm.add(cbFakultas);

        add(panelForm, BorderLayout.NORTH);

        JPanel panelButton = new JPanel(new FlowLayout());
        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");

        panelButton.add(btnTambah);
        panelButton.add(btnUbah);
        panelButton.add(btnHapus);
        panelButton.add(btnClear);

        add(panelButton, BorderLayout.CENTER);

        model = new DefaultTableModel(new String[]{"ID", "Nama Tim", "Fakultas"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        add(scroll, BorderLayout.SOUTH);
    }

    private void initEvent() {
        btnTambah.addActionListener(e -> controller.tambahTim());
        btnUbah.addActionListener(e -> controller.ubahTim());
        btnHapus.addActionListener(e -> controller.hapusTim());
        btnClear.addActionListener(e -> clearForm());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtNama.setText(model.getValueAt(row, 1).toString());
                cbFakultas.setSelectedItem(model.getValueAt(row, 2).toString());
            }
        });
    }

    public String getNamaTim() { 
        return txtNama.getText().trim(); 
    }

    public String getFakultas() {
        if (cbFakultas.getSelectedIndex() == 0) return null;
        return cbFakultas.getSelectedItem().toString();
    }

    public int getIdTim() {
        if (txtId.getText().isEmpty()) return 0;
        return Integer.parseInt(txtId.getText());
    }
    
    public void loadTable() {
        controller.loadTable();
    }

    public void clearForm() {
        txtId.setText("");
        txtNama.setText("");
        cbFakultas.setSelectedIndex(0);
        table.clearSelection();
    }

    public void tampilkanData(ResultSet rs) {
        try {
            if (model != null) {
                model.setRowCount(0);
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id_tim"),
                            rs.getString("nama_tim"),
                            rs.getString("fakultas")
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}

