/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import model.user;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class loginDao {
    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;   
    
    public loginDao(){
        conDB = koneksi.getKoneksi();
    }
    
    public user getLoginByUsername (String email, String password) 
    {
        user us = new user();
        String sqlSearch = "SELECT * FROM users WHERE email=? AND password=?";
        try
        {
            ps = conDB. prepareStatement(sqlSearch);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()) 
            {
                us.setEmail(rs.getString("email"));
                us.setPassword(rs.getString("password"));
                us.setRole(rs.getString("role"));
            }
        }
        catch (SQLException e) 
        {
            System.out.println("There some problem when occured login"+e.getMessage());
        }
        return us;
        
    }
    
    public boolean registerUser(user newUser){
        String sqlInsert = "INSERT INTO users (username, email, password, role, created_at, updated_at) VALUES (?,?,?,?,?,?)";
        try {
            ps = conDB.prepareStatement(sqlInsert);
            ps.setString(1, newUser.getUsername());
            ps.setString(2, newUser.getEmail());
            ps.setString(3, newUser.getPassword()); 
            ps.setString(4, "User"); 
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(5, currentTime);
            ps.setTimestamp(6, currentTime);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }
}
