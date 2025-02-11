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
import model.passenger;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
public class passengerDao {
    
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public passengerDao() {
        this.conn = koneksi.getKoneksi();
    }

    public ArrayList<passenger> getAllPassenger() {
        ArrayList<passenger> listPassenger = new ArrayList<>();
        try {
            String query = "SELECT * FROM passenger ORDER BY id";

            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                passenger passenger = new passenger();
                passenger.setNama(rs.getString("nama"));
                passenger.setNIK(rs.getInt("NIK"));
                passenger.setNoPasport(rs.getInt("no_pasport"));
                passenger.setNoTelepon(rs.getInt("no_telepon"));
                passenger.setEmail(rs.getString("email"));
                listPassenger.add(passenger);
            }
        } catch (SQLException se) {
            System.out.println("Ada Kesalahan: " + se);
        }
        return listPassenger;
    }

    public String simpanData(passenger passenger, String page) {
        String sqlSimpan = null;
        String message = "Berhasil Menambah Data";
        int parameterIndex = 1;

        switch (page) {
            case "edit":
                sqlSimpan = "UPDATE passenger SET nama=?, NIK=?, no_pasport=?, no_telepon=?, email=? WHERE id=?";
                break;
            case "tambah":
                sqlSimpan = "INSERT INTO passenger (nama, NIK, no_pasport, no_telepon, email) VALUES (?, ?, ?, ?, ?)";
                break;
            default:
                return "Invalid page value";
        }

        try (PreparedStatement ps = conn.prepareStatement(sqlSimpan)) {
            ps.setString(parameterIndex++, passenger.getNama());
            ps.setInt(parameterIndex++, passenger.getNIK());
            ps.setInt(parameterIndex++, passenger.getNoPasport());
            ps.setInt(parameterIndex++, passenger.getNoTelepon());
            ps.setString(parameterIndex++, passenger.getEmail());

            if (page.equals("edit")) {
                ps.setString(parameterIndex, passenger.getId());
            }

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected <= 0 && page.equals("edit")) {
                message = "No passenger found with that ID to update.";
            } else if (rowsAffected <= 0 && page.equals("tambah")) {
                message = "Failed to insert passenger.";
            } else if (rowsAffected > 0 && page.equals("tambah")) {
                message = "Passenger successfully added.";
            } else if (rowsAffected > 0 && page.equals("edit")) {
                message = "Passenger successfully updated.";
            }

        } catch (SQLException e) {
            System.err.println("Ada kesalahan saat penyimpanan data: " + e.getMessage());
            message = "Terjadi kesalahan saat menyimpan data: " + e.getMessage();
        }
        return message;
    }

    public passenger getRecordById(String id) {
        passenger passenger = new passenger();
        String sqlSearch = "SELECT * FROM passenger WHERE id=?";

        try {
            ps = conn.prepareStatement(sqlSearch);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                passenger.setId(rs.getString("id"));
                passenger.setNama(rs.getString("nama"));
                passenger.setNIK(rs.getInt("NIK"));
                passenger.setNoPasport(rs.getInt("no_pasport"));
                passenger.setNoTelepon(rs.getInt("no_telepon"));
                passenger.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            System.err.println("ada kesalahan pada getrecord" + e.getMessage());
        }
        return passenger;
    }

    public void hapusData(String id) {
        String sqlHapus = "DELETE FROM passenger WHERE id=?";
        try {
            ps = conn.prepareStatement(sqlHapus);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        passengerDao passengerDao = new passengerDao();
        System.out.println(passengerDao.getAllPassenger());
    }
}
