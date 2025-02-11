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
import model.pemesanan;

/**
 *
 * @author Lenovo
 */
public class pemesananDao {
    
     private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public pemesananDao() {
        this.conn = koneksi.getKoneksi();
    }

    public ArrayList<pemesanan> getAllPemesanan() {
        ArrayList<pemesanan> listPemesanan = new ArrayList<>();
        try {
            String query = "SELECT * FROM pemesanan ORDER BY id";

            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                pemesanan pemesanan = new pemesanan();
                pemesanan.setId(rs.getString("id"));
                pemesanan.setIdJadwal(rs.getInt("id_jadwal"));
                pemesanan.setTotalHarga(rs.getDouble("total_harga"));
                pemesanan.setStatusPembayaran(rs.getString("status_pembayaran"));
                pemesanan.setJarakPerjalanan(rs.getInt("jarak_perjalanan"));
                listPemesanan.add(pemesanan);
            }
        } catch (SQLException se) {
            System.out.println("Ada Kesalahan: " + se);
        }
        return listPemesanan;
    }

    public String simpanData(pemesanan pemesanan, String page) {
        String sqlSimpan = null;
        String message = "Berhasil Menambah Data";
        int parameterIndex = 1;

        switch (page) {
            case "edit":
                sqlSimpan = "UPDATE pemesanan SET id_jadwal=?, total_harga=?, status_pembayaran=?, jarak_perjalanan=? WHERE id=?";
                break;
            case "tambah":
                sqlSimpan = "INSERT INTO pemesanan (id_jadwal, total_harga, status_pembayaran, jarak_perjalanan) VALUES (?, ?, ?, ?)";
                break;
            default:
                return "Invalid page value";
        }

        try (PreparedStatement ps = conn.prepareStatement(sqlSimpan)) {
            ps.setInt(parameterIndex++, pemesanan.getIdJadwal());
            ps.setDouble(parameterIndex++, pemesanan.getTotalHarga());
            ps.setString(parameterIndex++, pemesanan.getStatusPembayaran());
            ps.setInt(parameterIndex++, pemesanan.getJarakPerjalanan());

            if (page.equals("edit")) {
                ps.setString(parameterIndex, pemesanan.getId());
            }

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected <= 0 && page.equals("edit")) {
                message = "No pemesanan found with that ID to update.";
            } else if (rowsAffected <= 0 && page.equals("tambah")) {
                message = "Failed to insert pemesanan.";
            } else if (rowsAffected > 0 && page.equals("tambah")) {
                message = "Pemesanan successfully added.";
            } else if (rowsAffected > 0 && page.equals("edit")) {
                message = "Pemesanan successfully updated.";
            }

        } catch (SQLException e) {
            System.err.println("Ada kesalahan saat penyimpanan data: " + e.getMessage());
            message = "Terjadi kesalahan saat menyimpan data: " + e.getMessage();
        }
        return message;
    }

    public pemesanan getRecordById(String id) {
        pemesanan pemesanan = new pemesanan();
        String sqlSearch = "SELECT * FROM pemesanan WHERE id=?";

        try {
            ps = conn.prepareStatement(sqlSearch);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                pemesanan.setId(rs.getString("id"));
                pemesanan.setIdJadwal(rs.getInt("id_jadwal"));
                pemesanan.setTotalHarga(rs.getDouble("total_harga"));
                pemesanan.setStatusPembayaran(rs.getString("status_pembayaran"));
                pemesanan.setJarakPerjalanan(rs.getInt("jarak_perjalanan"));
            }
        } catch (SQLException e) {
            System.err.println("ada kesalahan pada getrecord" + e.getMessage());
        }
        return pemesanan;
    }

    public void hapusData(String id) {
        String sqlHapus = "DELETE FROM pemesanan WHERE id=?";
        try {
            ps = conn.prepareStatement(sqlHapus);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        pemesananDao pemesananDao = new pemesananDao();
        System.out.println(pemesananDao.getAllPemesanan());
    }
}
