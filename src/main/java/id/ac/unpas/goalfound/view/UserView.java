/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.view;

import id.ac.unpas.goalfound.DAO.UserDAO;
import id.ac.unpas.goalfound.Model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

/**
 * @author NNDAAA
 */
public class UserView extends JPanel {
    private JTable tableUser;
    private DefaultTableModel tableModel;
    private JTextField txtUsername, txtNamaLengkap, txtEmail, txtPassword;
    private JComboBox<String> cmbRole;
    private JButton btnTambah, btnUpdate, btnHapus, btnReset, btnResetPassword;
    private final UserDAO userDAO;
    private int selectedUserId = -1;

    public UserView() {
        userDAO = new UserDAO();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
       setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(320, 0));

        JLabel lblSubTitle = new JLabel("Form User");
        lblSubTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSubTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(lblSubTitle);
        formPanel.add(Box.createVerticalStrut(15));

        addInputGroup(formPanel, "Username", txtUsername = new JTextField());
        addInputGroup(formPanel, "Password", txtPassword = new JTextField());
        addInputGroup(formPanel, "Nama Lengkap", txtNamaLengkap = new JTextField());
        addInputGroup(formPanel, "Email", txtEmail = new JTextField());

        JLabel lblRole = new JLabel("Role:");
        lblRole.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(lblRole);
        cmbRole = new JComboBox<>(new String[]{"admin", "staff"});
        cmbRole.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        cmbRole.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(cmbRole);
        formPanel.add(Box.createVerticalStrut(15));

        btnTambah = createWideButton("Tambah");
        btnUpdate = createWideButton("Update");
        btnHapus = createWideButton("Hapus");
        btnReset = createWideButton("Reset Form");
        btnResetPassword = createWideButton("Reset Password");

        formPanel.add(btnTambah);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(btnUpdate);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(btnHapus);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(btnReset);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(btnResetPassword);

        add(formPanel, BorderLayout.WEST);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Data User"));

        String[] columns = {"No", "Username", "Nama Lengkap", "Email", "Role"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tableUser = new JTable(tableModel);
        tableUser.setRowHeight(20);
        JScrollPane scrollPane = new JScrollPane(tableUser);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        btnTambah.addActionListener(e -> tambahUser());
        btnUpdate.addActionListener(e -> updateUser());
        btnHapus.addActionListener(e -> hapusUser());
        btnReset.addActionListener(e -> resetForm());
        btnResetPassword.addActionListener(e -> resetPassword());

        tableUser.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableUser.getSelectedRow() != -1) {
                loadFormFromTable();
            }
        });
    }

    private void addInputGroup(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText + ":");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(textField);
        panel.add(Box.createVerticalStrut(10));
    }

    private JButton createWideButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
    try {
        ResultSet rs = userDAO.getAll();
        int no = 1; 
        while (rs.next()) {
            Object[] row = {
                no++,
                    rs.getString("username"),
                    rs.getString("nama_lengkap"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getInt("id_user")
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void loadFormFromTable() {
        int row = tableUser.getSelectedRow();
        if (row >= 0) {
            selectedUserId = (int) tableModel.getValueAt(row, 0);
            txtUsername.setText(tableModel.getValueAt(row, 1).toString());
            txtNamaLengkap.setText(tableModel.getValueAt(row, 2).toString());
            txtEmail.setText(tableModel.getValueAt(row, 3).toString());
            cmbRole.setSelectedItem(tableModel.getValueAt(row, 4).toString());
            txtPassword.setText("");
            txtPassword.setEnabled(false);
            btnTambah.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnHapus.setEnabled(true);
        }
    }

    private void tambahUser() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String namaLengkap = txtNamaLengkap.getText().trim();
        String email = txtEmail.getText().trim();
        String role = cmbRole.getSelectedItem().toString();

        if (username.isEmpty() || password.isEmpty() || namaLengkap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username, Password, dan Nama Lengkap wajib diisi!");
            return;
        }
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password minimal 6 karakter!");
            return;
        }

        try {
            if (userDAO.isUsernameExist(username)) {
                JOptionPane.showMessageDialog(this, "Username sudah digunakan!");
                return;
            }
            User user = new User(username, password, namaLengkap, email, role);
            userDAO.insert(user);
            JOptionPane.showMessageDialog(this, "User berhasil ditambahkan!");
            resetForm();
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void updateUser() {
        if (selectedUserId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih user yang ingin diupdate!");
            return;
        }

        String username = txtUsername.getText().trim();
        String namaLengkap = txtNamaLengkap.getText().trim();
        String email = txtEmail.getText().trim();
        String role = cmbRole.getSelectedItem().toString();

        if (username.isEmpty() || namaLengkap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan Nama Lengkap wajib diisi!");
            return;
        }

        try {
            User user = new User(selectedUserId, username, "", namaLengkap, email, role);
            userDAO.update(user);
            
            String newPassword = txtPassword.getText().trim();
            if (!newPassword.isEmpty()) {
                if (newPassword.length() < 6) {
                    JOptionPane.showMessageDialog(this, "Password minimal 6 karakter!");
                    return;
                }
                userDAO.updatePassword(selectedUserId, newPassword);
            }
            
            JOptionPane.showMessageDialog(this, "User berhasil diupdate!");
            resetForm();
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void hapusUser() {
        if (selectedUserId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih user yang ingin dihapus!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Yakin ingin menghapus user ini?", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                userDAO.delete(selectedUserId);
                JOptionPane.showMessageDialog(this, "User berhasil dihapus!");
                resetForm();
                loadData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }


    private void resetForm() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtNamaLengkap.setText("");
        txtEmail.setText("");
        cmbRole.setSelectedIndex(0);
        txtPassword.setEnabled(true);
        btnTambah.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnHapus.setEnabled(false);
        selectedUserId = -1;
        tableUser.clearSelection();
    }

    private void resetPassword() {
        if (selectedUserId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih user terlebih dahulu!");
            return;
        }

        String newPassword = JOptionPane.showInputDialog(this, "Masukkan password baru (min 6 karakter):");
        if (newPassword == null) {
            return; 
        }
        newPassword = newPassword.trim();
        if (newPassword.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password minimal 6 karakter!");
            return;
        }

        String confirmPassword = JOptionPane.showInputDialog(this, "Konfirmasi password baru:");
        if (confirmPassword == null) {
            return; 
        }
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Password dan konfirmasi tidak sama!");
            return;
        }

        try {
            userDAO.updatePassword(selectedUserId, newPassword);
            JOptionPane.showMessageDialog(this, "Password berhasil direset!");
            txtPassword.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
