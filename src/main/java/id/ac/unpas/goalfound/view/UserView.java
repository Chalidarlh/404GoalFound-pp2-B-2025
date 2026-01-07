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
    private UserDAO userDAO;
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

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel("MANAJEMEN USER");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);
        JPanel mainPanel = new JPanel(new BorderLayout(10, 0));
        mainPanel.setBackground(Color.WHITE);
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Form User"));
        formPanel.setPreferredSize(new Dimension(320, 0)); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.anchor = GridBagConstraints.WEST; 
        
        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; 
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtUsername = new JTextField();
        formPanel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 2; 
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; 
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtPassword = new JTextField();
        formPanel.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 4; 
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Nama Lengkap:"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; 
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNamaLengkap = new JTextField();
        formPanel.add(txtNamaLengkap, gbc);

        gbc.gridx = 0; gbc.gridy = 6; 
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 7; 
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtEmail = new JTextField();
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 8; 
        gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Role:"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 9; 
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbRole = new JComboBox<>(new String[]{"admin", "staff"});
        formPanel.add(cmbRole, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        buttonPanel.setBackground(Color.WHITE);
        
        btnTambah = new JButton("Tambah");
        btnUpdate = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Clear");
        btnResetPassword = new JButton("Ubah Pass");
        
        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnReset);
        buttonPanel.add(btnResetPassword);
        
        gbc.gridx = 0; gbc.gridy = 10;
        gbc.weighty = 0; 
        gbc.insets = new Insets(15, 5, 5, 5);
        formPanel.add(buttonPanel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 11;
        gbc.weighty = 1.0;
        formPanel.add(Box.createVerticalGlue(), gbc);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Data User"));

        String[] columns = {"ID", "Username", "Nama Lengkap", "Email", "Role"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableUser = new JTable(tableModel);
        tableUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableUser.getTableHeader().setReorderingAllowed(false);
        tableUser.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableUser.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableUser.getColumnModel().getColumn(2).setPreferredWidth(150);
        tableUser.getColumnModel().getColumn(3).setPreferredWidth(150);
        tableUser.getColumnModel().getColumn(4).setPreferredWidth(70);

        JScrollPane scrollPane = new JScrollPane(tableUser);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(formPanel, BorderLayout.WEST);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

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

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            ResultSet rs = userDAO.getAll();
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id_user"),
                    rs.getString("username"),
                    rs.getString("nama_lengkap"),
                    rs.getString("email"),
                    rs.getString("role")
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
