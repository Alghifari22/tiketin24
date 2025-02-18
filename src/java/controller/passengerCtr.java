/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.passengerDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.passenger;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "passengerCtr", urlPatterns = {"/passengerCtr"})
public class passengerCtr extends HttpServlet {

    
    private final passengerDao passengerDao = new passengerDao();
    private final Gson gson = new Gson();
   
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String page = request.getParameter("page");
        PrintWriter out = response.getWriter();

        if (page == null) {
            List<passenger> listPassenger = passengerDao.getAllPassenger();
            String jsonPassenger = gson.toJson(listPassenger);
            out.println(jsonPassenger);
        } else if ("tambah".equals(page)) {
            passenger passenger = new passenger();
            try {
                passenger.setNama(request.getParameter("nama"));
                passenger.setNIK(Integer.parseInt(request.getParameter("NIK")));
                passenger.setNoPasport(Integer.parseInt(request.getParameter("no_pasport")));
                passenger.setNoTelepon(Integer.parseInt(request.getParameter("no_telepon")));
                passenger.setEmail(request.getParameter("email"));

                String resultMessage = passengerDao.simpanData(passenger, page);
                response.setContentType("text/plain");
                out.println(resultMessage);
            } catch (NumberFormatException | NullPointerException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Invalid input data. Please check your input (NIK, no_pasport, no_telepon must be numbers).");
            }

        } else if ("tampil".equals(page)) {
            String jsonPassenger = gson.toJson(passengerDao.getRecordById(request.getParameter("id")));
            response.setContentType("text/html;charset=UTF-8");
            out.println(jsonPassenger);
        } else if ("edit".equals(page)) {
            passenger passenger = new passenger();
            try {
                passenger.setId(request.getParameter("id")); // Make sure passenger has an 'id' field and setter
                passenger.setNama(request.getParameter("nama"));
                passenger.setNIK(Integer.parseInt(request.getParameter("NIK")));
                passenger.setNoPasport(Integer.parseInt(request.getParameter("no_pasport")));
                passenger.setNoTelepon(Integer.parseInt(request.getParameter("no_telepon")));
                passenger.setEmail(request.getParameter("email"));

                String resultMessage = passengerDao.simpanData(passenger, page);
                response.setContentType("text/plain");
                out.println(resultMessage);
            } catch (NumberFormatException | NullPointerException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Invalid input data. Please check your input (NIK, no_pasport, no_telepon must be numbers).");
            }
        } else if ("hapus".equals(page)) {
            passengerDao.hapusData(request.getParameter("id"));
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
