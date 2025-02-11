/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.pemesananDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.pemesanan;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "pemesananCtr", urlPatterns = {"/pemesananCtr"})
public class pemesananCtr extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String page = request.getParameter("page");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        pemesananDao pemesananDao = new pemesananDao();

        if (page == null) {
            List<pemesanan> listPemesanan = pemesananDao.getAllPemesanan();
            String jsonPemesanan = gson.toJson(listPemesanan);
            out.println(jsonPemesanan);
        } else if ("tambah".equals(page)) {
            pemesanan pemesanan = new pemesanan();
            try {
                pemesanan.setIdJadwal(Integer.parseInt(request.getParameter("id_jadwal")));
                pemesanan.setTotalHarga(Double.parseDouble(request.getParameter("total_harga")));
                pemesanan.setStatusPembayaran(request.getParameter("status_pembayaran"));
                pemesanan.setJarakPerjalanan(Integer.parseInt(request.getParameter("jarak_perjalanan")));

                String resultMessage = pemesananDao.simpanData(pemesanan, page);
                response.setContentType("text/plain");
                out.println(resultMessage);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Input tidak valid. Pastikan id_jadwal dan jarak_perjalanan berupa angka, dan total_harga berupa angka desimal.");
            }
        } else if ("tampil".equals(page)) {
            pemesanan pemesanan = pemesananDao.getRecordById(request.getParameter("id"));
            String jsonPemesanan = gson.toJson(pemesanan);
            response.setContentType("text/html;charset=UTF-8");
            out.println(jsonPemesanan);
        } else if ("edit".equals(page)) {
            pemesanan pemesanan = new pemesanan();
            try {
                pemesanan.setId(request.getParameter("id")); // Set ID untuk edit
                pemesanan.setIdJadwal(Integer.parseInt(request.getParameter("id_jadwal")));
                pemesanan.setTotalHarga(Double.parseDouble(request.getParameter("total_harga")));
                pemesanan.setStatusPembayaran(request.getParameter("status_pembayaran"));
                pemesanan.setJarakPerjalanan(Integer.parseInt(request.getParameter("jarak_perjalanan")));

                String resultMessage = pemesananDao.simpanData(pemesanan, page);
                response.setContentType("text/plain");
                out.println(resultMessage);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Input tidak valid. Pastikan id_jadwal dan jarak_perjalanan berupa angka, dan total_harga berupa angka desimal.");
            }
        } else if ("hapus".equals(page)) {
            pemesananDao.hapusData(request.getParameter("id"));
            response.setContentType("text/html;charset=UTF-8");
            out.println("Data berhasil dihapus, OK...");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
