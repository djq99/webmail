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

public class Sort extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userinfo") == null) {
            response.sendRedirect("login");
        } else {
            User user = (User) session.getAttribute("userinfo");
            Connection conn = DbFactory.getConnection();
            MailDao mailDao = new MailDao();
            ArrayList<Email> mail;
            if (request.getParameter("req").equals("from")) {
                int userId = user.getUserid();
                try {
                    mail = mailDao.returnSortByFrom(userId, conn);
                    JSONArray jsonArray = JSONArray.fromObject(mail);
                    out = response.getWriter();
                    out.print(jsonArray);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (request.getParameter("req").equals("subject")) {
                System.out.println("subject");
                int userId = user.getUserid();
                try {
                    mail = mailDao.returnSortBySubject(userId, conn);
                    JSONArray jsonArray = JSONArray.fromObject(mail);
                    out = response.getWriter();
                    out.print(jsonArray);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (request.getParameter("req").equals("date")) {
                System.out.println("date");
                int userId = user.getUserid();
                try {
                    mail = mailDao.returnSortByDate(userId, conn);
                    JSONArray jsonArray = JSONArray.fromObject(mail);
                    out = response.getWriter();
                    out.print(jsonArray);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (request.getParameter("req").equals("size")) {
                System.out.println("size");
                int userId = user.getUserid();
                try {
                    mail = mailDao.returnSortBySize(userId, conn);
                    JSONArray jsonArray = JSONArray.fromObject(mail);
                    out = response.getWriter();
                    out.print(jsonArray);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
