/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.view;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Rzaaa 
 */
public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    private TimView timView;
    private PemainView pemainView;
    private JadwalPertandinganView jadwalView;

    public MainFrame() {
        setTitle("GoalFound - Aplikasi Manajemen Futsal");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        navPanel.setBackground(new Color(44, 62, 80)); 

        JButton btnTim = new JButton("Manajemen Tim");
        JButton btnPemain = new JButton("Manajemen Pemain");
        JButton btnJadwal = new JButton("Jadwal Pertandingan");
        JButton btnKeluar = new JButton("Keluar");

        styleButton(btnTim);
        styleButton(btnPemain);
        styleButton(btnJadwal);
        styleButton(btnKeluar);

        navPanel.add(btnTim);
        navPanel.add(btnPemain);
        navPanel.add(btnJadwal);
        navPanel.add(btnKeluar);

        add(navPanel, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        timView = new TimView();
        pemainView = new PemainView();
        jadwalView = new JadwalPertandinganView();

        mainPanel.add(timView, "TIM");
        mainPanel.add(pemainView, "PEMAIN");
        mainPanel.add(jadwalView, "JADWAL");

        add(mainPanel, BorderLayout.CENTER);

        btnTim.addActionListener(e -> {
            cardLayout.show(mainPanel, "TIM");
            timView.loadTable();
        });

        btnPemain.addActionListener(e -> {
            pemainView.loadDataPemain(); 
            pemainView.loadComboTim();
            cardLayout.show(mainPanel, "PEMAIN");
        });

        btnJadwal.addActionListener(e -> {
            jadwalView.loadData();
            cardLayout.show(mainPanel, "JADWAL");
        });

        btnKeluar.addActionListener(e -> System.exit(0));
    }
    
    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(44, 62, 80));
        btn.setPreferredSize(new Dimension(180, 40));
    }
    
}
