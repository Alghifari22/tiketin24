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
import model.detail_pemesanan;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class detail_pemesananDao {
    
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public detail_pemesananDao() {
        this.conn = koneksi.getKoneksi();
    }

    public ArrayList<detail_pemesanan> getAllDetailPemesanan() {
        ArrayList<detail_pemesanan> listDetailPemesanan = new ArrayList<>();
        try {
            String query = "SELECT * FROM detail_pemesanan ORDER BY id";

            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                detail_pemesanan detail_pemesanan = new detail_pemesanan();
                detail_pemesanan.setIdPassenger(rs.getInt("id_passenger"));
                detail_pemesanan.setIdPemesanan(rs.getInt("id_pemesanan"));
                listDetailPemesanan.add(detail_pemesanan);
            }
        } catch (SQLException se) {
            System.out.println("Ada Kesalahan: " + se);
        }
        return listDetailPemesanan;
    }

    public String simpanData(detail_pemesanan detail_pemesanan, String page) {
        String sqlSimpan = null;
        String message = "Berhasil Menambah Data";
        int parameterIndex = 1;

        switch (page) {
            case "edit":
                sqlSimpan = "UPDATE detail_pemesanan SET id_passenger=?, id_pemesanan=? WHERE id=?";
                break;
            case "tambah":
                sqlSimpan = "INSERT INTO detail_pemesanan (id_passenger, id_pemesanan) VALUES (?, ?)";
                break;
            default:
                return "Invalid page value";
        }

        try (PreparedStatement ps = conn.prepareStatement(sqlSimpan)) {
            ps.setInt(parameterIndex++, detail_pemesanan.getIdPassenger());
            ps.setInt(parameterIndex++, detail_pemesanan.getIdPemesanan());

            if (page.equals("edit")) {
                ps.setString(parameterIndex, detail_pemesanan.getId());
            }

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected <= 0 && page.equals("edit")) {
                message = "No detail_pemesanan found with that ID to update.";
            } else if (rowsAffected <= 0 && page.equals("tambah")) {
                message = "Failed to insert detail_pemesanan.";
            } else if (rowsAffected > 0 && page.equals("tambah")) {
                message = "Detail Pemesanan successfully added.";
            } else if (rowsAffected > 0 && page.equals("edit")) {
                message = "Detail Pemesanan successfully updated.";
            }

        } catch (SQLException e) {
            System.err.println("Ada kesalahan saat penyimpanan data: " + e.getMessage());
            message = "Terjadi kesalahan saat menyimpan data: " + e.getMessage();
        }
        return message;
    }

    public detail_pemesanan getRecordById(String id) {
        detail_pemesanan detail_pemesanan = new detail_pemesanan();
        String sqlSearch = "SELECT * FROM detail_pemesanan WHERE id=?";

        try {
            ps = conn.prepareStatement(sqlSearch);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                detail_pemesanan.setId(rs.getString("id"));
                detail_pemesanan.setIdPassenger(rs.getInt("id_passenger"));
                detail_pemesanan.setIdPemesanan(rs.getInt("id_pemesanan"));
            }
        } catch (SQLException e) {
            System.err.println("ada kesalahan pada getrecord" + e.getMessage());
        }
        return detail_pemesanan;
    }

    public void hapusData(String id) {
        String sqlHapus = "DELETE FROM detail_pemesanan WHERE id=?";
        try {
            ps = conn.prepareStatement(sqlHapus);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        detail_pemesananDao detail_pemesananDao = new detail_pemesananDao();
        System.out.println(detail_pemesananDao.getAllDetailPemesanan());
    }
}
