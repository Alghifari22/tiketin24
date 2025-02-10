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
import model.jadwal;

/**
 *
 * @author Alghifari Ramadhan
 */
public class jadwalDao {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public jadwalDao() {
        this.conn = koneksi.getKoneksi();
    }

    public ArrayList<jadwal> getAllJadwal() {
        ArrayList<jadwal> listJadwal = new ArrayList<>();
        try {
            String query = "SELECT * FROM jadwal ORDER BY id";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                jadwal j = new jadwal();
                j.setId(rs.getInt("id"));
                j.setIdRute(rs.getInt("id_rute"));
                j.setWaktuKeberangkatan(rs.getString("waktu_keberangkatan"));
                j.setWaktuKedatangan(rs.getString("waktu_kedatangan"));
                j.setHargaTiket(rs.getDouble("harga_tiket"));
                j.setKapasitas(rs.getInt("kapasitas"));
                listJadwal.add(j);
            }
        } catch (SQLException se) {
            System.err.println("Ada Kesalahan: " + se.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return listJadwal;
    }

    public String simpanData(jadwal j, String page) {
        String sqlSimpan = null;
        String message = "Berhasil Menambah Data";
        int parameterIndex = 1;

        switch (page) {
            case "edit":
                sqlSimpan = "UPDATE jadwal SET id_rute=?, waktu_keberangkatan=?, waktu_kedatangan=?, harga_tiket=?, kapasitas=? WHERE id=?";
                break;
            case "tambah":
                sqlSimpan = "INSERT INTO jadwal (id_rute, waktu_keberangkatan, waktu_kedatangan, harga_tiket, kapasitas) VALUES (?, ?, ?, ?, ?)";
                break;
            default:
                return "Invalid page value";
        }

        try (PreparedStatement ps = conn.prepareStatement(sqlSimpan)) {
            ps.setInt(parameterIndex++, j.getIdRute());
            ps.setString(parameterIndex++, j.getWaktuKeberangkatan());
            ps.setString(parameterIndex++, j.getWaktuKedatangan());
            ps.setDouble(parameterIndex++, j.getHargaTiket());
            ps.setInt(parameterIndex++, j.getKapasitas());

            if (page.equals("edit")) {
                ps.setInt(parameterIndex, j.getId());
            }

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected <= 0 && page.equals("edit")) {
                message = "No schedule found with that ID to update.";
            } else if (rowsAffected <= 0 && page.equals("tambah")) {
                message = "Failed to insert schedule.";
            } else if (rowsAffected > 0 && page.equals("tambah")) {
                message = "Schedule successfully added.";
            } else if (rowsAffected > 0 && page.equals("edit")) {
                message = "Schedule successfully updated.";
            }

        } catch (SQLException e) {
            System.err.println("Ada kesalahan saat penyimpanan data: " + e.getMessage());
            message = "Terjadi kesalahan saat menyimpan data: " + e.getMessage();
        }
        return message;
    }

    public jadwal getRecordById(int id) { // Menggunakan int karena id bertipe Integer
        jadwal j = new jadwal();
        String sqlSearch = "SELECT * FROM jadwal WHERE id=?";

        try {
            ps = conn.prepareStatement(sqlSearch);
            ps.setInt(1, id); // Set parameter dengan int
            rs = ps.executeQuery();

            if (rs.next()) {
                j.setId(rs.getInt("id"));
                j.setIdRute(rs.getInt("id_rute"));
                j.setWaktuKeberangkatan(rs.getString("waktu_keberangkatan"));
                j.setWaktuKedatangan(rs.getString("waktu_kedatangan"));
                j.setHargaTiket(rs.getDouble("harga_tiket"));
                j.setKapasitas(rs.getInt("kapasitas"));
            }
        } catch (SQLException e) {
            System.err.println("ada kesalahan pada getrecord " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return j;
    }

    public void hapusData(int id) { // Menggunakan int karena id bertipe Integer
        String sqlHapus = "DELETE FROM jadwal WHERE id=?";
        try {
            ps = conn.prepareStatement(sqlHapus);
            ps.setInt(1, id); // Set parameter dengan int
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public static void main(String args[]) {
        jadwalDao jadwalDao = new jadwalDao();
        System.out.println(jadwalDao.getAllJadwal());
    }
}
