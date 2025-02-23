/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.detail_pemesananDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.detail_pemesanan;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "detail_pemesananCtr", urlPatterns = {"/detail_pemesananCtr"})
public class detail_pemesananCtr extends HttpServlet {

     private final detail_pemesananDao detailPemesananDao = new detail_pemesananDao();
    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String page = request.getParameter("page");
        PrintWriter out = response.getWriter();

        if (page == null) {
            List<detail_pemesanan> listDetailPemesanan = detailPemesananDao.getAllDetailPemesanan();
            String jsonDetailPemesanan = gson.toJson(listDetailPemesanan);
            out.println(jsonDetailPemesanan);
        } else if ("tambah".equals(page)) {
            detail_pemesanan detail_pemesanan = new detail_pemesanan();
            try {
                detail_pemesanan.setIdPassenger(Integer.parseInt(request.getParameter("id_passenger")));
                detail_pemesanan.setIdPemesanan(Integer.parseInt(request.getParameter("id_pemesanan")));

                String resultMessage = detailPemesananDao.simpanData(detail_pemesanan, page);
                response.setContentType("text/plain");
                out.println(resultMessage);
            } catch (NumberFormatException | NullPointerException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Invalid input data. Please check your input (id_passenger and id_pemesanan must be numbers).");
            }
        } else if ("tampil".equals(page)) {
            String jsonDetailPemesanan = gson.toJson(detailPemesananDao.getRecordById(request.getParameter("id")));
            response.setContentType("text/html;charset=UTF-8");
            out.println(jsonDetailPemesanan);
        } else if ("edit".equals(page)) {
            detail_pemesanan detail_pemesanan = new detail_pemesanan();
            try {
                detail_pemesanan.setId(request.getParameter("id")); // Get ID for edit
                detail_pemesanan.setIdPassenger(Integer.parseInt(request.getParameter("id_passenger")));
                detail_pemesanan.setIdPemesanan(Integer.parseInt(request.getParameter("id_pemesanan")));

                String resultMessage = detailPemesananDao.simpanData(detail_pemesanan, page);
                response.setContentType("text/plain");
                out.println(resultMessage);
            } catch (NumberFormatException | NullPointerException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Invalid input data. Please check your input (id, id_passenger and id_pemesanan must be numbers).");
            }
        } else if ("hapus".equals(page)) {
            detailPemesananDao.hapusData(request.getParameter("id"));
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
