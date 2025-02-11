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
import java.util.ArrayList;
import model.notifikasi;

/**
 *
 * @author Lenovo
 */
public class notifikasiDao {
    
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public notifikasiDao() {
        this.conn = koneksi.getKoneksi();
    }

    public ArrayList<notifikasi> getAllNotifikasi() {
        ArrayList<notifikasi> listNotifikasi = new ArrayList<>();
        try {
            String query = "SELECT * FROM notifikasi ORDER BY id";

            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                notifikasi notifikasi = new notifikasi();
                notifikasi.setId(rs.getString("id"));
                notifikasi.setIdUser(rs.getInt("id_user"));
                notifikasi.setPesan(rs.getString("pesan"));
                notifikasi.setCreatedAt(rs.getString("created_at"));
                listNotifikasi.add(notifikasi);
            }
        } catch (SQLException se) {
            System.out.println("Ada Kesalahan: " + se);
        }
        return listNotifikasi;
    }

    public String simpanData(notifikasi notifikasi, String page) {
        String sqlSimpan = null;
        String message = "Berhasil Menambah Data";
        int parameterIndex = 1;

        switch (page) {
            case "edit":
                sqlSimpan = "UPDATE notifikasi SET id_user=?, pesan=?, created_at=? WHERE id=?";
                break;
            case "tambah":
                sqlSimpan = "INSERT INTO notifikasi (id_user, pesan, created_at) VALUES (?, ?, ?)";
                break;
            default:
                return "Invalid page value";
        }

        try (PreparedStatement ps = conn.prepareStatement(sqlSimpan)) {
            ps.setInt(parameterIndex++, notifikasi.getIdUser());
            ps.setString(parameterIndex++, notifikasi.getPesan());
            ps.setString(parameterIndex++, notifikasi.getCreatedAt());

            if (page.equals("edit")) {
                ps.setString(parameterIndex, notifikasi.getId());
            }

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected <= 0 && page.equals("edit")) {
                message = "No notifikasi found with that ID to update.";
            } else if (rowsAffected <= 0 && page.equals("tambah")) {
                message = "Failed to insert notifikasi.";
            } else if (rowsAffected > 0 && page.equals("tambah")) {
                message = "Notifikasi successfully added.";
            } else if (rowsAffected > 0 && page.equals("edit")) {
                message = "Notifikasi successfully updated.";
            }

        } catch (SQLException e) {
            System.err.println("Ada kesalahan saat penyimpanan data: " + e.getMessage());
            message = "Terjadi kesalahan saat menyimpan data: " + e.getMessage();
        }
        return message;
    }

    public notifikasi getRecordById(String id) {
        notifikasi notifikasi = new notifikasi();
        String sqlSearch = "SELECT * FROM notifikasi WHERE id=?";

        try {
            ps = conn.prepareStatement(sqlSearch);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                notifikasi.setId(rs.getString("id"));
                notifikasi.setIdUser(rs.getInt("id_user"));
                notifikasi.setPesan(rs.getString("pesan"));
                notifikasi.setCreatedAt(rs.getString("created_at"));
            }
        } catch (SQLException e) {
            System.err.println("ada kesalahan pada getrecord" + e.getMessage());
        }
        return notifikasi;
    }

    public void hapusData(String id) {
        String sqlHapus = "DELETE FROM notifikasi WHERE id=?";
        try {
            ps = conn.prepareStatement(sqlHapus);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        notifikasiDao notifikasiDao = new notifikasiDao();
        System.out.println(notifikasiDao.getAllNotifikasi());
    }
}
