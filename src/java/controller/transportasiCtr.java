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
 * @author Alghifari Ramadhan
 */
@WebServlet(name = "transportasiCtr", urlPatterns = {"/transportasiCtr"})
public class transportasiCtr extends HttpServlet {

    private final transportasiDao transportDao = new transportasiDao();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String page = request.getParameter("page");
        PrintWriter out = response.getWriter();

        if (page == null) {
            List<transportasi> listTransportasi = transportDao.getAllTransportasi();
            String jsonTransportasi = gson.toJson(listTransportasi);
            out.println(jsonTransportasi);
        } else if ("tambah".equals(page)) {
            transportasi transport = new transportasi();
            transport.setNamaTransportasi(request.getParameter("nama_transportasi"));
            transport.setJenisTransportasi(request.getParameter("jenis_transportasi"));
            transport.setNamaPerusahaan(request.getParameter("nama_perusahaan"));
            transport.setKodePerusahaan(request.getParameter("kode_perusahaan"));
            transport.setKapasitasKursi(Integer.parseInt(request.getParameter("kapasitas")));

            String resultMessage = transportDao.simpanData(transport, page);
            response.setContentType("text/plain");
            out.println(resultMessage);
        } else if ("tampil".equals(page)) {
            String jsonTransportasi = gson.toJson(transportDao.getRecordById(request.getParameter("id")));
            response.setContentType("text/html;charset=UTF-8");
            out.println(jsonTransportasi);
        } else if ("edit".equals(page)) {
            transportasi transport = new transportasi();
            transport.setId(request.getParameter("id"));
            transport.setNamaTransportasi(request.getParameter("nama_transportasi"));
            transport.setJenisTransportasi(request.getParameter("jenis_transportasi"));
            transport.setNamaPerusahaan(request.getParameter("nama_perusahaan"));
            transport.setKodePerusahaan(request.getParameter("kode_perusahaan"));
            transport.setKapasitasKursi(Integer.parseInt(request.getParameter("kapasitas")));

            String resultMessage = transportDao.simpanData(transport, page);
            response.setContentType("text/plain");
            out.println(resultMessage);
        } else if ("hapus".equals(page)) {
            transportDao.hapusData(request.getParameter("id"));
            response.setContentType("text/html;charset=UTF-8");
            out.println("Data berhasil dihapus, OK...");
        }
    }
}
