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
import model.transportasi;

/**
 *
 * @author Alghifari Ramadhan
 */
public class transportasiDao {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public transportasiDao() {
        this.conn = koneksi.getKoneksi();
    }

    public ArrayList<transportasi> getAllTransportasi() {
        ArrayList<transportasi> listTransportasi = new ArrayList<>();
        try {
            String query = "SELECT * FROM transportasi ORDER BY id";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                transportasi transport = new transportasi();
                transport.setNamaTransportasi(rs.getString("nama_transportasi"));
                transport.setJenisTransportasi(rs.getString("jenis_transportasi"));
                transport.setNamaPerusahaan(rs.getString("nama_perusahaan"));
                transport.setKodePerusahaan(rs.getString("kode_perusahaan"));
                transport.setKapasitasKursi(rs.getInt("kapasitas"));
                listTransportasi.add(transport);
            }
        } catch (SQLException se) {
            System.err.println("Ada Kesalahan: " + se.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return listTransportasi;
    }

    public String simpanData(transportasi transport, String page) {
        String sqlSimpan = null;
        String message = "Berhasil Menambah Data";
        int parameterIndex = 1;

        switch (page) {
            case "edit":
                sqlSimpan = "UPDATE transportasi SET nama_transportasi=?, jenis_transportasi=?, nama_perusahaan=?, kode_perusahaan=?, kapasitas=? WHERE id=?";
                break;
            case "tambah":
                sqlSimpan = "INSERT INTO transportasi (nama_transportasi, jenis_transportasi, nama_perusahaan, kode_perusahaan, kapasitas) VALUES (?, ?, ?, ?, ?)";
                break;
            default:
                return "Invalid page value";
        }

        try (PreparedStatement ps = conn.prepareStatement(sqlSimpan)) {
            ps.setString(parameterIndex++, transport.getNamaTransportasi());
            ps.setString(parameterIndex++, transport.getJenisTransportasi());
            ps.setString(parameterIndex++, transport.getNamaPerusahaan());
            ps.setString(parameterIndex++, transport.getKodePerusahaan());
            ps.setInt(parameterIndex++, transport.getKapasitasKursi());

            if (page.equals("edit")) {
                ps.setString(parameterIndex, transport.getId());
            }

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected <= 0 && page.equals("edit")) {
                message = "No transport found with that ID to update.";
            } else if (rowsAffected <= 0 && page.equals("tambah")) {
                message = "Failed to insert transport.";
            } else if (rowsAffected > 0 && page.equals("tambah")) {
                message = "Transport successfully added.";
            } else if (rowsAffected > 0 && page.equals("edit")) {
                message = "Transport successfully updated.";
            }

        } catch (SQLException e) {
            System.err.println("Ada kesalahan saat penyimpanan data: " + e.getMessage());
            message = "Terjadi kesalahan saat menyimpan data: " + e.getMessage();
        }
        return message;
    }

    public transportasi getRecordById(String id) {
        transportasi transport = new transportasi();
        String sqlSearch = "SELECT * FROM transportasi WHERE id=?";

        try {
            ps = conn.prepareStatement(sqlSearch);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                transport.setId(rs.getString("id"));
                transport.setNamaTransportasi(rs.getString("nama_transportasi"));
                transport.setJenisTransportasi(rs.getString("jenis_transportasi"));
                transport.setNamaPerusahaan(rs.getString("nama_perusahaan"));
                transport.setKodePerusahaan(rs.getString("kode_perusahaan"));
                transport.setKapasitasKursi(rs.getInt("kapasitas"));
            }
        } catch (SQLException e) {
            System.err.println("ada kesalahan pada getrecord " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return transport;
    }

    public void hapusData(String id) {
        String sqlHapus = "DELETE FROM transportasi WHERE id=?";
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
        transportasiDao transportDao = new transportasiDao();
        System.out.println(transportDao.getAllTransportasi());
    }
}
