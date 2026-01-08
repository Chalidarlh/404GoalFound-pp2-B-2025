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

    private JTextField txtNama;
    private JComboBox<String> cbFakultas;
    private JButton btnTambah, btnUbah, btnHapus, btnClear;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private JButton btnSearch;

    private int selectedId = 0;

    private TimController controller;

    public TimView() {
        initComponent();
        controller = new TimController(this);
        initEvent();
    }

    private void initComponent() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.Y_AXIS));

        JPanel panelForm = new JPanel(new GridLayout(2, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Form Data Tim"));

        panelForm.add(new JLabel("Nama Tim"));
        txtNama = new JTextField();
        panelForm.add(txtNama);

        panelForm.add(new JLabel("Fakultas"));
        cbFakultas = new JComboBox<>(new String[]{
                "-- Pilih Fakultas --", "Teknik", "FEB", "FK", "FKIP", "FH", "FISIP", "SASTRA"
        });
        panelForm.add(cbFakultas);

        panelHeader.add(panelForm);

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");

        panelButton.add(btnTambah);
        panelButton.add(btnUbah);
        panelButton.add(btnHapus);
        panelButton.add(btnClear);

        panelHeader.add(panelButton);

        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.setBorder(BorderFactory.createTitledBorder("Cari Tim"));

        panelSearch.add(new JLabel("Nama Tim / Fakultas:"));
        txtSearch = new JTextField(20);
        panelSearch.add(txtSearch);
        btnSearch = new JButton("Cari");
        panelSearch.add(btnSearch);

        panelHeader.add(panelSearch);

        add(panelHeader, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"No", "Nama Tim", "Fakultas"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        add(scroll, BorderLayout.CENTER);
    }

    private void initEvent() {
        btnTambah.addActionListener(e -> controller.tambahTim());
        btnUbah.addActionListener(e -> controller.ubahTim());
        btnHapus.addActionListener(e -> controller.hapusTim());
        btnClear.addActionListener(e -> clearForm());
        btnSearch.addActionListener(e -> {
            controller.searchTim(txtSearch.getText().trim());
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                try {
                    selectedId = Integer.parseInt(model.getValueAt(row, 0).toString());
                } catch (NumberFormatException ex) {
                    selectedId = 0;
                }
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
        return selectedId;
    }
    
    public void loadTable() {
        controller.loadTable();
    }

    public void clearForm() {
        selectedId = 0;
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
    
    public JTable getTableTim() {
        return table;
    }
}

