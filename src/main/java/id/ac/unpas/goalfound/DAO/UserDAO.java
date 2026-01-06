/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.DAO;

import id.ac.unpas.goalfound.Model.User;
import id.ac.unpas.goalfound.KoneksiDB;
import java.security.MessageDigest;
import java.sql.*;

/**
 *
 * @author NNDAAA
 */
public class UserDAO {
    
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public User login(String username, String password) throws Exception {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, hashPassword(password));
        
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new User(
                rs.getInt("id_user"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("nama_lengkap"),
                rs.getString("email"),
                rs.getString("role")
            );
        }
        return null;
    }
    
    public boolean isUsernameExist(String username) throws Exception {
        String sql = "SELECT id_user FROM user WHERE username = ?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    
    public void insert(User user) throws Exception {
        String sql = "INSERT INTO user(username, password, nama_lengkap, email, role) VALUES (?,?,?,?,?)";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setString(1, user.getUsername());
        ps.setString(2, hashPassword(user.getPassword()));
        ps.setString(3, user.getNamaLengkap());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getRole());
        ps.executeUpdate();
    }
    
    public ResultSet getAll() throws Exception {
        String sql = "SELECT id_user, username, nama_lengkap, email, role FROM user ORDER BY id_user";
        Statement st = KoneksiDB.configDB().createStatement();
        return st.executeQuery(sql);
    }
    
    public void update(User user) throws Exception {
        String sql = "UPDATE user SET username=?, nama_lengkap=?, email=?, role=? WHERE id_user=?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getNamaLengkap());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getRole());
        ps.setInt(5, user.getIdUser());
        ps.executeUpdate();
    }
    
    public void updatePassword(int idUser, String newPassword) throws Exception {
        String sql = "UPDATE user SET password=? WHERE id_user=?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setString(1, hashPassword(newPassword));
        ps.setInt(2, idUser);
        ps.executeUpdate();
    }
    
    public void delete(int idUser) throws Exception {
        String sql = "DELETE FROM user WHERE id_user=?";
        PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
        ps.setInt(1, idUser);
        ps.executeUpdate();
    }
    
    public void createDefaultAdmin() throws Exception {
        if (!isUsernameExist("admin")) {
            User admin = new User("admin", "admin123", "Administrator", "admin@goalfound.com", "admin");
            insert(admin);
        }
    }
}
