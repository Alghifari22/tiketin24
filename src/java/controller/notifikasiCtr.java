/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.notifikasiDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.notifikasi;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "notifikasiCtr", urlPatterns = {"/notifikasiCtr"})
public class notifikasiCtr extends HttpServlet {

    private final notifikasiDao notifikasiDao = new notifikasiDao();
    private final Gson gson = new Gson();
    
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String page = request.getParameter("page");
        PrintWriter out = response.getWriter();

        if (page == null) {
            List<notifikasi> listNotifikasi = notifikasiDao.getAllNotifikasi();
            String jsonNotifikasi = gson.toJson(listNotifikasi);
            out.println(jsonNotifikasi);
        } else if ("tambah".equals(page)) {
            notifikasi notifikasi = new notifikasi();
            try {
                notifikasi.setIdUser(Integer.parseInt(request.getParameter("id_user")));
                notifikasi.setPesan(request.getParameter("pesan"));
                notifikasi.setCreatedAt(request.getParameter("created_at")); // Get created_at from request

                String resultMessage = notifikasiDao.simpanData(notifikasi, page);
                response.setContentType("text/plain");
                out.println(resultMessage);

            } catch (NumberFormatException | NullPointerException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Invalid input data. Please check your input (id_user must be a number).");
            }
        } else if ("tampil".equals(page)) {
            String jsonNotifikasi = gson.toJson(notifikasiDao.getRecordById(request.getParameter("id")));
            response.setContentType("text/html;charset=UTF-8");
            out.println(jsonNotifikasi);
        } else if ("edit".equals(page)) {
            notifikasi notifikasi = new notifikasi();
            try {
                notifikasi.setId(request.getParameter("id")); // Ensure notifikasi has an 'id' field/setter
                notifikasi.setIdUser(Integer.parseInt(request.getParameter("id_user")));
                notifikasi.setPesan(request.getParameter("pesan"));
                notifikasi.setCreatedAt(request.getParameter("created_at"));

                String resultMessage = notifikasiDao.simpanData(notifikasi, page);
                response.setContentType("text/plain");
                out.println(resultMessage);

            } catch (NumberFormatException | NullPointerException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Invalid input data. Please check your input (id_user must be a number).");
            }
        } else if ("hapus".equals(page)) {
            notifikasiDao.hapusData(request.getParameter("id"));
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
