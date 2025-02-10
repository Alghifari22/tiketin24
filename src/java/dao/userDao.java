package dao;

import connection.koneksi;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.user;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class userDao {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public userDao(){
        this.conn = koneksi.getKoneksi();
    }
    
    public ArrayList<user> getAllUser(){
        ArrayList<user> ListUser = new ArrayList<>();
        try{
            String query = "SELECT * FROM users ORDER BY id";

            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                user usr = new user();
                usr.setUsername(rs.getString("username"));
                usr.setEmail(rs.getString("email"));
                usr.setRole(rs.getString("role"));
                usr.setcreatedAt(sdf.format(rs.getDate("created_at")));
                ListUser.add(usr);
            }
        }catch(SQLException se){
            System.out.println("Ada Kesalahan: "+se);
        }
        return ListUser;
    }
    
    public String simpanData(user usr, String page) {
        String sqlSimpan = null;
        String message = "Berhasil Menambah Data";
        int parameterIndex = 1;

        switch (page) {
            case "edit":
                sqlSimpan = "UPDATE users SET username=?, email=?, role=? WHERE id=?";
                break;
            case "tambah":
                sqlSimpan = "INSERT INTO users (username, email, role) VALUES (?, ?, ?)"; 
                break;
            default:
                return "Invalid page value";
        }

        try (PreparedStatement ps = conn.prepareStatement(sqlSimpan)) {
            ps.setString(parameterIndex++, usr.getUsername());
            ps.setString(parameterIndex++, usr.getEmail());
            ps.setString(parameterIndex++, usr.getRole());

            if (page.equals("edit")) {
                ps.setString(parameterIndex, usr.getId());
            }

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected <= 0 && page.equals("edit")) {
                message = "No user found with that ID to update.";
            } else if (rowsAffected <= 0 && page.equals("tambah")) {
                message = "Failed to insert user.";
            } else if (rowsAffected > 0 && page.equals("tambah")) {
                message = "User successfully added.";
            } else if (rowsAffected > 0 && page.equals("edit")) {
                message = "User successfully updated.";
            }

        } catch (SQLException e) {
            System.err.println("Ada kesalahan saat penyimpanan data: " + e.getMessage());
            message = "Terjadi kesalahan saat menyimpan data: " + e.getMessage();
        }
        return message;
    }
    
    public user getRecordById(String id){
        user usr = new user();
        String sqlSearch = "SELECT * FROM users WHERE id=?";
        
        try{
            ps = conn.prepareStatement(sqlSearch);
            ps.setString(1, id);
            rs = ps.executeQuery();
            
            if(rs.next()){
                usr.setUsername(rs.getString("username"));
                usr.setEmail(rs.getString("email"));
                usr.setRole(rs.getString("role"));
                usr.setcreatedAt(rs.getString("created_at")); 
            }
        }catch(SQLException e){
            System.err.println("ada kesalahan pada getrecord"+e.getMessage());
        }
        return usr;
    }
    
    public void hapusData(String id) {
        String sqlHapus = "DELETE FROM users WHERE id=?";
        try {
            ps = conn.prepareStatement(sqlHapus);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error " + e.getMessage());
        }
    }
    
    public static void main(String args[]){
        userDao usrDao = new userDao();
        System.out.println(usrDao.getAllUser());
    }
}
