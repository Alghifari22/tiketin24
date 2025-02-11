/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.riwayatDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.riwayat;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "riwayatCtr", urlPatterns = {"/riwayatCtr"})
public class riwayatCtr extends HttpServlet {

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
        riwayatDao riwayatDao = new riwayatDao();

        if (page == null) {
            List<riwayat> listRiwayat = riwayatDao.getAllRiwayat();
            String jsonRiwayat = gson.toJson(listRiwayat);
            out.println(jsonRiwayat);
        } else if ("tambah".equals(page)) {
            riwayat riwayat = new riwayat();
            try {
                riwayat.setIdPemesanan(Integer.parseInt(request.getParameter("id_pemesanan")));
                riwayat.setStatusPerjalanan(request.getParameter("status_perjalanan"));

                String resultMessage = riwayatDao.simpanData(riwayat, page);
                response.setContentType("text/plain");
                out.println(resultMessage);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Input id_pemesanan harus berupa angka.");
            }
        } else if ("tampil".equals(page)) {
            riwayat riwayat = riwayatDao.getRecordById(request.getParameter("id"));
            String jsonRiwayat = gson.toJson(riwayat);
            response.setContentType("text/html;charset=UTF-8");
            out.println(jsonRiwayat);
        } else if ("edit".equals(page)) {
            riwayat riwayat = new riwayat();
            try {
                riwayat.setId(request.getParameter("id")); // Set ID untuk edit
                riwayat.setIdPemesanan(Integer.parseInt(request.getParameter("id_pemesanan")));
                riwayat.setStatusPerjalanan(request.getParameter("status_perjalanan"));

                String resultMessage = riwayatDao.simpanData(riwayat, page);
                response.setContentType("text/plain");
                out.println(resultMessage);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Input id_pemesanan harus berupa angka.");
            }
        } else if ("hapus".equals(page)) {
            riwayatDao.hapusData(request.getParameter("id"));
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
