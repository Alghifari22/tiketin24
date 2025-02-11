/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.transportasiDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.transportasi;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "transportasiCtr", urlPatterns = {"/transportasiCtr"})
public class transportasiCtr extends HttpServlet {

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
        transportasiDao transportasiDao = new transportasiDao();

        if (page == null) {
            List<transportasi> listTransportasi = transportasiDao.getAllTransportasi();
            String jsonTransportasi = gson.toJson(listTransportasi);
            out.println(jsonTransportasi);
        } else if ("tambah".equals(page)) {
            transportasi transport = new transportasi();
            try {
                transport.setNamaTransportasi(request.getParameter("nama_transportasi"));
                transport.setJenisTransportasi(request.getParameter("jenis_transportasi"));
                transport.setNamaPerusahaan(request.getParameter("nama_perusahaan"));
                transport.setKodePerusahaan(request.getParameter("kode_perusahaan"));
                transport.setKapasitasKursi(Integer.parseInt(request.getParameter("kapasitas")));

                String resultMessage = transportasiDao.simpanData(transport, page);
                response.setContentType("text/plain");
                out.println(resultMessage);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Input kapasitas harus berupa angka.");
            }
        } else if ("tampil".equals(page)) {
            String jsonTransportasi = gson.toJson(transportasiDao.getRecordById(request.getParameter("id")));
            response.setContentType("text/html;charset=UTF-8");
            out.println(jsonTransportasi);
        } else if ("edit".equals(page)) {
            transportasi transport = new transportasi();
            try {
                transport.setId(request.getParameter("id")); // Set the ID for edit
                transport.setNamaTransportasi(request.getParameter("nama_transportasi"));
                transport.setJenisTransportasi(request.getParameter("jenis_transportasi"));
                transport.setNamaPerusahaan(request.getParameter("nama_perusahaan"));
                transport.setKodePerusahaan(request.getParameter("kode_perusahaan"));
                transport.setKapasitasKursi(Integer.parseInt(request.getParameter("kapasitas")));

                String resultMessage = transportasiDao.simpanData(transport, page);
                response.setContentType("text/plain");
                out.println(resultMessage);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Input kapasitas harus berupa angka.");
            }
        } else if ("hapus".equals(page)) {
            transportasiDao.hapusData(request.getParameter("id"));
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
