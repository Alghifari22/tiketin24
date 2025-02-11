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
import model.riwayat;

/**
 *
 * @author Lenovo
 */
public class riwayatDao {
    
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public riwayatDao() {
        this.conn = koneksi.getKoneksi();
    }

    public ArrayList<riwayat> getAllRiwayat() {
        ArrayList<riwayat> listRiwayat = new ArrayList<>();
        try {
            String query = "SELECT * FROM riwayat ORDER BY id";

            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                riwayat riwayat = new riwayat();
                riwayat.setId(rs.getString("id"));
                riwayat.setIdPemesanan(rs.getInt("id_pemesanan"));
                riwayat.setStatusPerjalanan(rs.getString("status_perjalanan"));
                listRiwayat.add(riwayat);
            }
        } catch (SQLException se) {
            System.out.println("Ada Kesalahan: " + se);
        }
        return listRiwayat;
    }

    public String simpanData(riwayat riwayat, String page) {
        String sqlSimpan = null;
        String message = "Berhasil Menambah Data";
        int parameterIndex = 1;

        switch (page) {
            case "edit":
                sqlSimpan = "UPDATE riwayat SET id_pemesanan=?, status_perjalanan=? WHERE id=?";
                break;
            case "tambah":
                sqlSimpan = "INSERT INTO riwayat (id_pemesanan, status_perjalanan) VALUES (?, ?)";
                break;
            default:
                return "Invalid page value";
        }

        try (PreparedStatement ps = conn.prepareStatement(sqlSimpan)) {
            ps.setInt(parameterIndex++, riwayat.getIdPemesanan());
            ps.setString(parameterIndex++, riwayat.getStatusPerjalanan());

            if (page.equals("edit")) {
                ps.setString(parameterIndex, riwayat.getId());
            }

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected <= 0 && page.equals("edit")) {
                message = "No riwayat found with that ID to update.";
            } else if (rowsAffected <= 0 && page.equals("tambah")) {
                message = "Failed to insert riwayat.";
            } else if (rowsAffected > 0 && page.equals("tambah")) {
                message = "Riwayat successfully added.";
            } else if (rowsAffected > 0 && page.equals("edit")) {
                message = "Riwayat successfully updated.";
            }

        } catch (SQLException e) {
            System.err.println("Ada kesalahan saat penyimpanan data: " + e.getMessage());
            message = "Terjadi kesalahan saat menyimpan data: " + e.getMessage();
        }
        return message;
    }

    public riwayat getRecordById(String id) {
        riwayat riwayat = new riwayat();
        String sqlSearch = "SELECT * FROM riwayat WHERE id=?";

        try {
            ps = conn.prepareStatement(sqlSearch);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                riwayat.setId(rs.getString("id"));
                riwayat.setIdPemesanan(rs.getInt("id_pemesanan"));
                riwayat.setStatusPerjalanan(rs.getString("status_perjalanan"));
            }
        } catch (SQLException e) {
            System.err.println("ada kesalahan pada getrecord" + e.getMessage());
        }
        return riwayat;
    }

    public void hapusData(String id) {
        String sqlHapus = "DELETE FROM riwayat WHERE id=?";
        try {
            ps = conn.prepareStatement(sqlHapus);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        riwayatDao riwayatDao = new riwayatDao();
        System.out.println(riwayatDao.getAllRiwayat());
    }
}
