/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.view;

import id.ac.unpas.goalfound.controller.PemainController;
import id.ac.unpas.goalfound.DAO.TimDAO;
import java.awt.*;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author 
 */

public class PemainView extends JPanel {
    public JTextField txtNama, txtNpm, txtNo;
    public JComboBox<String> cbTim;
    public JButton btnTambah, btnUbah, btnHapus, btnExport, btnClear;
    public JTable tablePemain;
    public DefaultTableModel model;
    
    private Map<String, Integer> timMap = new HashMap<>();
    private PemainController controller;

    public PemainView() {
        initComponents();

        controller = new PemainController(this);
        loadComboTim();
        loadDataPemain();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        //Panel Form
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Form Data Pemain"));

        panelForm.add(new JLabel("Pilih Tim:"));
        cbTim = new JComboBox<>();
        panelForm.add(cbTim);

        panelForm.add(new JLabel("Nama Pemain:"));
        txtNama = new JTextField();
        panelForm.add(txtNama);

        panelForm.add(new JLabel("NPM:"));
        txtNpm = new JTextField();
        panelForm.add(txtNpm);

        panelForm.add(new JLabel("No Punggung:"));
        txtNo = new JTextField();
        panelForm.add(txtNo);

        //Panel ButtoN
        JPanel panelAction = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnExport = new JButton("Export PDF");
        btnClear = new JButton("Clear");

        panelAction.add(btnTambah);
        panelAction.add(btnUbah);
        panelAction.add(btnHapus);
        panelAction.add(btnExport);
        panelAction.add(btnClear);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(panelForm, BorderLayout.CENTER);
        northPanel.add(panelAction, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        //Panel Tabel
        String[] columns = {"No", "Nama Tim", "Nama Pemain", "NPM", "No Punggung"};
        model = new DefaultTableModel(columns, 0);
        tablePemain = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tablePemain);
        add(scrollPane, BorderLayout.CENTER);

        btnClear.addActionListener(e -> resetForm());
        
        tablePemain.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablePemain.getSelectedRow() != -1) {
                int row = tablePemain.getSelectedRow();
                cbTim.setSelectedItem(model.getValueAt(row, 1).toString());
                txtNama.setText(model.getValueAt(row, 2).toString());
                txtNpm.setText(model.getValueAt(row, 3).toString());
                txtNo.setText(model.getValueAt(row, 4).toString());
                txtNpm.setEditable(false); 
            }
        });
    }

    //ambil data tim untuk ComboBox
    public void loadComboTim() {
        cbTim.removeAllItems();
        timMap.clear();
        try {
            TimDAO timDao = new TimDAO();
            ResultSet rs = timDao.getAll();
            while (rs.next()) {
                String namaTim = rs.getString("nama_tim");
                int idTim = rs.getInt("id_tim");
                cbTim.addItem(namaTim);
                timMap.put(namaTim, idTim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSelectedIdTim() {
        String selected = (String) cbTim.getSelectedItem();
        return timMap.getOrDefault(selected, 0);
    }

    public void loadDataPemain() {
        model.setRowCount(0);
        if (controller != null) {
            controller.loadDataPemain();
        }
    }

    public void resetForm() {
        txtNama.setText("");
        txtNpm.setText("");
        txtNo.setText("");
        txtNpm.setEditable(true);
        if (cbTim.getItemCount() > 0) cbTim.setSelectedIndex(0);
        tablePemain.clearSelection();
    }
}
