/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.view;

import id.ac.unpas.goalfound.util.UserSession;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 *
 * @author Rzaaa 
 */
public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private final JPanel sidebar;
    private JLabel lblTitle;
    
    private TimView timView;
    private PemainView pemainView;
    private JadwalPertandinganView jadwalView;
    private UserView userView;

    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SIDEBAR_COLOR = new Color(52, 73, 94);
    private final Color BG_COLOR = new Color(236, 240, 241);
    private final Color TEXT_COLOR = Color.WHITE;

    public MainFrame() {
        setTitle("GoalFound - Futsal Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel logo = new JLabel("GOALFOUND");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(logo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBackground(new Color(44, 62, 80));
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        userInfoPanel.setMaximumSize(new Dimension(230, 80));
        
        JLabel lblUserName = new JLabel(UserSession.getInstance().getNamaLengkap());
        lblUserName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUserName.setForeground(Color.WHITE);
        lblUserName.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblUserRole = new JLabel("(" + UserSession.getInstance().getRole().toUpperCase() + ")");
        lblUserRole.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblUserRole.setForeground(new Color(149, 165, 166));
        lblUserRole.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        userInfoPanel.add(lblUserName);
        userInfoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        userInfoPanel.add(lblUserRole);
        
        sidebar.add(userInfoPanel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton btnTim = createMenuButton("Manajemen Tim");
        JButton btnPemain = createMenuButton("Manajemen Pemain");
        JButton btnJadwal = createMenuButton("Jadwal Tanding");
        JButton btnUser = createMenuButton("Manajemen User");
        JButton btnKeluar = createMenuButton("Keluar");

        sidebar.add(btnTim);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnPemain);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnJadwal);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        
        if (UserSession.getInstance().isAdmin()) {
            sidebar.add(btnUser);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnKeluar);

        add(sidebar, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new BorderLayout());
        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(getWidth(), 60));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        
        lblTitle = new JLabel(" Dashboard ");
        lblTitle.setBorder(new EmptyBorder(0, 20, 0, 0));
        header.add(lblTitle, BorderLayout.WEST);
        
        rightPanel.add(header, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        timView = new TimView();
        pemainView = new PemainView();
        jadwalView = new JadwalPertandinganView();
        userView = new UserView();

        mainPanel.add(timView, "TIM");
        mainPanel.add(pemainView, "PEMAIN");
        mainPanel.add(jadwalView, "JADWAL");
        mainPanel.add(userView, "USER");

        rightPanel.add(mainPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.CENTER);

        btnTim.addActionListener(e -> {
            lblTitle.setText(" Manajemen Tim");
            cardLayout.show(mainPanel, "TIM");
            timView.loadTable();
        });

        btnPemain.addActionListener(e -> {
            lblTitle.setText(" Manajemen Pemain");
            pemainView.loadDataPemain(); 
            pemainView.loadComboTim();
            cardLayout.show(mainPanel, "PEMAIN");
        });

        btnJadwal.addActionListener(e -> {
            lblTitle.setText(" Jadwal Pertandingan");
            jadwalView.loadData();
            cardLayout.show(mainPanel, "JADWAL");
        });
        
        btnUser.addActionListener(e -> {
            lblTitle.setText(" Manajemen User");
            cardLayout.show(mainPanel, "USER");
        });

        btnKeluar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin logout?", 
                "Konfirmasi Logout", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                UserSession.getInstance().logout();
                dispose();
                SwingUtilities.invokeLater(() -> {
                    LoginView loginView = new LoginView();
                    loginView.setVisible(true);
                });
            }
        });
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setBackground(SIDEBAR_COLOR);
        btn.setForeground(TEXT_COLOR);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMargin(new Insets(0, 20, 0, 0));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(SIDEBAR_COLOR);
            }
        });

        return btn;
    }
    
}
