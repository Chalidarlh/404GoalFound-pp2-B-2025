/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.util;

import id.ac.unpas.goalfound.Model.User;

/**
 * @author NNDAAA
 */
public class UserSession {
    private static UserSession instance;
    private User currentUser;
    
    private UserSession() {}
    
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public String getUsername() {
        return currentUser != null ? currentUser.getUsername() : "";
    }
    
    public String getNamaLengkap() {
        return currentUser != null ? currentUser.getNamaLengkap() : "";
    }
    
    public String getRole() {
        return currentUser != null ? currentUser.getRole() : "";
    }
    
    public boolean isAdmin() {
        return currentUser != null && "admin".equalsIgnoreCase(currentUser.getRole());
    }
    
    public boolean isStaff() {
        return currentUser != null && "staff".equalsIgnoreCase(currentUser.getRole());
    }
}
