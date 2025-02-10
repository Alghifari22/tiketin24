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
import model.rute;

/**
 *
 * @author Alghifari Ramadhan
 */
public class ruteDao {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public ruteDao() {
        this.conn = koneksi.getKoneksi();
    }

    public ArrayList<rute> getAllRute() {
        ArrayList<rute> listRute = new ArrayList<>();
        try {
            String query = "SELECT * FROM rute ORDER BY id";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                rute r = new rute();
                r.setIdTransportasi(rs.getInt("id_transportasi"));
                r.setKotaAsal(rs.getString("kota_asal"));
                r.setKotaTujuan(rs.getString("kota_tujuan"));
                r.setDurasiPerjalanan(rs.getInt("durasi_perjalanan"));
                r.setJarakPerjalanan(rs.getInt("jarak_perjalanan"));
                listRute.add(r);
            }
        } catch (SQLException se) {
            System.err.println("Ada Kesalahan: " + se.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return listRute;
    }

    public String simpanData(rute r, String page) {
        String sqlSimpan = null;
        String message = "Berhasil Menambah Data";
        int parameterIndex = 1;

        switch (page) {
            case "edit":
                sqlSimpan = "UPDATE rute SET id_transportasi=?, kota_asal=?, kota_tujuan=?, durasi_perjalanan=?, jarak_perjalanan=? WHERE id=?";
                break;
            case "tambah":
                sqlSimpan = "INSERT INTO rute (id_transportasi, kota_asal, kota_tujuan, durasi_perjalanan, jarak_perjalanan) VALUES (?, ?, ?, ?, ?)";
                break;
            default:
                return "Invalid page value";
        }

        try (PreparedStatement ps = conn.prepareStatement(sqlSimpan)) {
            ps.setInt(parameterIndex++, r.getIdTransportasi());
            ps.setString(parameterIndex++, r.getKotaAsal());
            ps.setString(parameterIndex++, r.getKotaTujuan());
            ps.setInt(parameterIndex++, r.getDurasiPerjalanan());
            ps.setInt(parameterIndex++, r.getJarakPerjalanan());

            if (page.equals("edit")) {
                ps.setString(parameterIndex, r.getId());
            }

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected <= 0 && page.equals("edit")) {
                message = "No route found with that ID to update.";
            } else if (rowsAffected <= 0 && page.equals("tambah")) {
                message = "Failed to insert route.";
            } else if (rowsAffected > 0 && page.equals("tambah")) {
                message = "Route successfully added.";
            } else if (rowsAffected > 0 && page.equals("edit")) {
                message = "Route successfully updated.";
            }

        } catch (SQLException e) {
            System.err.println("Ada kesalahan saat penyimpanan data: " + e.getMessage());
            message = "Terjadi kesalahan saat menyimpan data: " + e.getMessage();
        }
        return message;
    }

    public rute getRecordById(String id) {
        rute r = new rute();
        String sqlSearch = "SELECT * FROM rute WHERE id=?";

        try {
            ps = conn.prepareStatement(sqlSearch);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                r.setId(rs.getString("id"));
                r.setIdTransportasi(rs.getInt("id_transportasi"));
                r.setKotaAsal(rs.getString("kota_asal"));
                r.setKotaTujuan(rs.getString("kota_tujuan"));
                r.setDurasiPerjalanan(rs.getInt("durasi_perjalanan"));
                r.setJarakPerjalanan(rs.getInt("jarak_perjalanan"));
            }
        } catch (SQLException e) {
            System.err.println("ada kesalahan pada getrecord " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return r;
    }

    public void hapusData(String id) {
        String sqlHapus = "DELETE FROM rute WHERE id=?";
        try {
            ps = conn.prepareStatement(sqlHapus);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public static void main(String args[]) {
        ruteDao ruteDao = new ruteDao();
        System.out.println(ruteDao.getAllRute());
    }
}
