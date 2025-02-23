package controller;

import com.google.gson.Gson;
import dao.userDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.user;

/**
 *
 * @author Kelompok 1 12 RPL 2
 */
@WebServlet(name = "userCtr", urlPatterns = {"/userCtr"})
public class userCtr extends HttpServlet {

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
        response.setContentType("application/json");
        String page = request.getParameter("page");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        userDao usrDao = new userDao();
        
        if(page == null){
            List<user> listuser = usrDao.getAllUser();
            String jsonUser = gson.toJson(listuser);
            out.println(jsonUser);
        }else if("tambah".equals(page)){
            user usr = new user();
            usr.setUsername(request.getParameter("username"));
            usr.setEmail(request.getParameter("email"));
            usr.setRole(request.getParameter("role"));
            usr.setPassword(request.getParameter("password"));
                
            String resultMessage = usrDao.simpanData(usr, page);
            response.setContentType("text/plain");
            out.println(resultMessage);
        }else if("tampil".equals(page)){
            String jsonUser = gson.toJson(usrDao.getRecordById(request.getParameter("id")));
            response.setContentType("text/html;charset=UTF-8");
            out.println(jsonUser);
        }else if("edit".equals(page)){
            user usr = new user();
            usr.setId(request.getParameter("id"));
            usr.setUsername(request.getParameter("username"));
            usr.setEmail(request.getParameter("email"));
            usr.setRole(request.getParameter("role"));
            
            String resultMessage = usrDao.simpanData(usr, page);
            response.setContentType("text/plain");
            out.println(resultMessage);
        }else if("hapus".equals(page)){
            usrDao.hapusData(request.getParameter("id"));
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
