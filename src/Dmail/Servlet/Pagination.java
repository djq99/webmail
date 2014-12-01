package Dmail.Servlet;

import Dmail.Utils.DbFactory;
import Dmail.dao.MailDao;
import Dmail.model.Email;
import Dmail.model.User;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class Pagination extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userinfo") == null) {
            response.sendRedirect("login");
        }
        else {
            User user = (User) session.getAttribute("userinfo");
            Connection conn = DbFactory.getConnection();
            MailDao mailDao = new MailDao();
            ArrayList<Email> mail;
            if (request.getParameter("req").equals("previous"))
            {
                String currentPage = request.getParameter("current");
                int current = Integer.valueOf(currentPage);
                String currentFolder = request.getParameter("currentFolder");
                try {
                    mail = mailDao.getCurrentPage(currentFolder,user.getUserid(),current,10,conn);
                    JSONArray jsonArray = JSONArray.fromObject(mail);
                    out = response.getWriter();
                    out.print(jsonArray);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DbFactory.closeConn(conn);
            }
            else if (request.getParameter("req").equals("nextpage"))
            {
                String currentPage = request.getParameter("current");
                String currentFolder = request.getParameter("currentFolder");
                int current = Integer.valueOf(currentPage);
                try {
                    mail = mailDao.getCurrentPage(currentFolder,user.getUserid(),current,10,conn);
                    JSONArray jsonArray = JSONArray.fromObject(mail);
                    out = response.getWriter();
                    out.print(jsonArray);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DbFactory.closeConn(conn);
        }
    }
}
