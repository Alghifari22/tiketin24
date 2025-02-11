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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.pembayaran;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class pembayaranDao {
    
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public pembayaranDao() {
        this.conn = koneksi.getKoneksi();
    }

    public ArrayList<pembayaran> getAllPembayaran() {
        ArrayList<pembayaran> listPembayaran = new ArrayList<>();
        try {
            String query = "SELECT * FROM pembayaran ORDER BY id";

            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                pembayaran pembayaran = new pembayaran();
                pembayaran.setIdUser(rs.getInt("id_user"));
                pembayaran.setIdPemesanan(rs.getInt("id_pemesanan"));
                pembayaran.setMetodePembayaran(rs.getString("metode_pembayaran"));
                pembayaran.setTanggalPembayaran(sdf.format(rs.getDate("tanggal_pembayaran"))); // Format tanggal
                pembayaran.setStatusPembayaran(rs.getString("status_pembayaran"));
                listPembayaran.add(pembayaran);
            }
        } catch (SQLException se) {
            System.out.println("Ada Kesalahan: " + se);
        }
        return listPembayaran;
    }

    public String simpanData(pembayaran pembayaran, String page) {
        String sqlSimpan = null;
        String message = "Berhasil Menambah Data";
        int parameterIndex = 1;

        switch (page) {
            case "edit":
                sqlSimpan = "UPDATE pembayaran SET id_user=?, id_pemesanan=?, metode_pembayaran=?, tanggal_pembayaran=?, status_pembayaran=? WHERE id=?";
                break;
            case "tambah":
                sqlSimpan = "INSERT INTO pembayaran (id_user, id_pemesanan, metode_pembayaran, tanggal_pembayaran, status_pembayaran) VALUES (?, ?, ?, ?, ?)";
                break;
            default:
                return "Invalid page value";
        }

        try (PreparedStatement ps = conn.prepareStatement(sqlSimpan)) {
            ps.setInt(parameterIndex++, pembayaran.getIdUser());
            ps.setInt(parameterIndex++, pembayaran.getIdPemesanan());
            ps.setString(parameterIndex++, pembayaran.getMetodePembayaran());
            ps.setString(parameterIndex++, pembayaran.getTanggalPembayaran()); // Simpan tanggal sebagai String
            ps.setString(parameterIndex++, pembayaran.getStatusPembayaran());

            if (page.equals("edit")) {
                ps.setString(parameterIndex, pembayaran.getId());
            }

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected <= 0 && page.equals("edit")) {
                message = "No pembayaran found with that ID to update.";
            } else if (rowsAffected <= 0 && page.equals("tambah")) {
                message = "Failed to insert pembayaran.";
            } else if (rowsAffected > 0 && page.equals("tambah")) {
                message = "Pembayaran successfully added.";
            } else if (rowsAffected > 0 && page.equals("edit")) {
                message = "Pembayaran successfully updated.";
            }

        } catch (SQLException e) {
            System.err.println("Ada kesalahan saat penyimpanan data: " + e.getMessage());
            message = "Terjadi kesalahan saat menyimpan data: " + e.getMessage();
        }
        return message;
    }

    public pembayaran getRecordById(String id) {
        pembayaran pembayaran = new pembayaran();
        String sqlSearch = "SELECT * FROM pembayaran WHERE id=?";

        try {
            ps = conn.prepareStatement(sqlSearch);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                pembayaran.setId(rs.getString("id"));
                pembayaran.setIdUser(rs.getInt("id_user"));
                pembayaran.setIdPemesanan(rs.getInt("id_pemesanan"));
                pembayaran.setMetodePembayaran(rs.getString("metode_pembayaran"));
                pembayaran.setTanggalPembayaran(rs.getString("tanggal_pembayaran"));
                pembayaran.setStatusPembayaran(rs.getString("status_pembayaran"));
            }
        } catch (SQLException e) {
            System.err.println("ada kesalahan pada getrecord" + e.getMessage());
        }
        return pembayaran;
    }

    public void hapusData(String id) {
        String sqlHapus = "DELETE FROM pembayaran WHERE id=?";
        try {
            ps = conn.prepareStatement(sqlHapus);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        pembayaranDao pembayaranDao = new pembayaranDao();
        System.out.println(pembayaranDao.getAllPembayaran());
    }
}
