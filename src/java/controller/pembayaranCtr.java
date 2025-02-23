/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.pembayaranDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.pembayaran;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "pembayaranCtr", urlPatterns = {"/pembayaranCtr"})
public class pembayaranCtr extends HttpServlet {
    
    private final pembayaranDao pembayaranDao = new pembayaranDao();
    private final Gson gson = new Gson();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
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
        PrintWriter out = response.getWriter();

        if (page == null) {
            List<pembayaran> listPembayaran = pembayaranDao.getAllPembayaran();
            String jsonPembayaran = gson.toJson(listPembayaran);
            out.println(jsonPembayaran);
        } else if ("tambah".equals(page)) {
            pembayaran pembayaran = new pembayaran();
            try {
                pembayaran.setIdUser(Integer.parseInt(request.getParameter("id_user")));
                pembayaran.setIdPemesanan(Integer.parseInt(request.getParameter("id_pemesanan")));
                pembayaran.setMetodePembayaran(request.getParameter("metode_pembayaran"));
                pembayaran.setTanggalPembayaran(request.getParameter("tanggal_pembayaran")); // Tanggal dari form
                pembayaran.setStatusPembayaran(request.getParameter("status_pembayaran"));

                String resultMessage = pembayaranDao.simpanData(pembayaran, page);
                response.setContentType("text/plain");
                out.println(resultMessage);
            } catch (NumberFormatException | NullPointerException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Invalid input data. Please check your input.");
            }


        } else if ("tampil".equals(page)) {
            String jsonPembayaran = gson.toJson(pembayaranDao.getRecordById(request.getParameter("id")));
            response.setContentType("text/html;charset=UTF-8");
            out.println(jsonPembayaran);
        } else if ("edit".equals(page)) {
            pembayaran pembayaran = new pembayaran();
            try {
                pembayaran.setId(request.getParameter("id"));
                pembayaran.setIdUser(Integer.parseInt(request.getParameter("id_user")));
                pembayaran.setIdPemesanan(Integer.parseInt(request.getParameter("id_pemesanan")));
                pembayaran.setMetodePembayaran(request.getParameter("metode_pembayaran"));
                pembayaran.setTanggalPembayaran(request.getParameter("tanggal_pembayaran"));
                pembayaran.setStatusPembayaran(request.getParameter("status_pembayaran"));

                String resultMessage = pembayaranDao.simpanData(pembayaran, page);
                response.setContentType("text/plain");
                out.println(resultMessage);
            } catch (NumberFormatException | NullPointerException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Invalid input data. Please check your input.");
            }
        } else if ("hapus".equals(page)) {
            pembayaranDao.hapusData(request.getParameter("id"));
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
