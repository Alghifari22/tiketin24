/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.ruteDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.rute;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "ruteCtr", urlPatterns = {"/ruteCtr"})
public class ruteCtr extends HttpServlet {

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
        ruteDao ruteDao = new ruteDao();

        if (page == null) {
            List<rute> listRute = ruteDao.getAllRute();
            String jsonRute = gson.toJson(listRute);
            out.println(jsonRute);
        } else if ("tambah".equals(page)) {
            rute rute = new rute();
            try {
                rute.setIdTransportasi(Integer.parseInt(request.getParameter("id_transportasi")));
                rute.setKotaAsal(request.getParameter("kota_asal"));
                rute.setKotaTujuan(request.getParameter("kota_tujuan"));
                rute.setDurasiPerjalanan(Integer.parseInt(request.getParameter("durasi_perjalanan")));
                rute.setJarakPerjalanan(Integer.parseInt(request.getParameter("jarak_perjalanan")));

                String resultMessage = ruteDao.simpanData(rute, page);
                response.setContentType("text/plain");
                out.println(resultMessage);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Status 400 Bad Request
                out.println("Tipe data tidak valid. Pastikan input berupa angka."); // Pesan kesalahan
            }

        } else if ("tampil".equals(page)) {
            rute rute = ruteDao.getRecordById(request.getParameter("id"));
            String jsonRute = gson.toJson(rute);
            response.setContentType("text/html;charset=UTF-8");
            out.println(jsonRute);
        } else if ("edit".equals(page)) {
            rute rute = new rute();
            try {
                rute.setId(request.getParameter("id")); // Set ID untuk edit
                rute.setIdTransportasi(Integer.parseInt(request.getParameter("id_transportasi")));
                rute.setKotaAsal(request.getParameter("kota_asal"));
                rute.setKotaTujuan(request.getParameter("kota_tujuan"));
                rute.setDurasiPerjalanan(Integer.parseInt(request.getParameter("durasi_perjalanan")));
                rute.setJarakPerjalanan(Integer.parseInt(request.getParameter("jarak_perjalanan")));

                String resultMessage = ruteDao.simpanData(rute, page);
                response.setContentType("text/plain");
                out.println(resultMessage);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Tipe data tidak valid. Pastikan input berupa angka.");
            }
        } else if ("hapus".equals(page)) {
            ruteDao.hapusData(request.getParameter("id"));
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
