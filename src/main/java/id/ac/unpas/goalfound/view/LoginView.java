/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.view;

import id.ac.unpas.goalfound.DAO.UserDAO;
import id.ac.unpas.goalfound.Model.User;
import id.ac.unpas.goalfound.util.UserSession;
import javax.swing.*;
import java.awt.*;

/**
 * Login View - Minimal UI for Backend
 * @author NNDAAA
 */
public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblMessage;
    private UserDAO userDAO;
    
    public LoginView() {
        userDAO = new UserDAO();
        initComponents();
        
        try {
            userDAO.createDefaultAdmin();
        } catch (Exception e) {
            System.err.println("Error creating default admin: " + e.getMessage());
        }
    }
    
    private void initComponents() {
        setTitle("Login - GoalFound");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel lblTitle = new JLabel("LOGIN SISTEM", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(lblTitle, gbc);
        
        JLabel lblSubtitle = new JLabel("GoalFound - Futsal Management", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 11));
        gbc.gridy = 1;
        mainPanel.add(lblSubtitle, gbc);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 5, 15, 5);
        mainPanel.add(new JSeparator(), gbc);
        
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1;
        
        JLabel lblUsername = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(lblUsername, gbc);
        
        txtUsername = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(txtUsername, gbc);
        
        JLabel lblPassword = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(lblPassword, gbc);
        
        txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        mainPanel.add(txtPassword, gbc);
        
        lblMessage = new JLabel(" ", SwingConstants.CENTER);
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 11));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(lblMessage, gbc);
        
        btnLogin = new JButton("LOGIN");
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 5, 5, 5);
        mainPanel.add(btnLogin, gbc);
                
        add(mainPanel);
        
        btnLogin.addActionListener(e -> handleLogin());
        txtPassword.addActionListener(e -> handleLogin());
        txtUsername.addActionListener(e -> txtPassword.requestFocus());
    }
    
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Username dan password wajib diisi!", Color.RED);
            return;
        }
        
        try {
            User user = userDAO.login(username, password);
            
            if (user != null) {
                UserSession.getInstance().setCurrentUser(user);
                dispose();
                SwingUtilities.invokeLater(() -> {
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.setVisible(true);
                });
            } else {
                showMessage("Username atau password salah!", Color.RED);
                txtPassword.setText("");
                txtPassword.requestFocus();
            }
            
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        }
    }
    
    private void showMessage(String message, Color color) {
        lblMessage.setText(message);
        lblMessage.setForeground(color);
    }
}
