package Dmail.Servlet;


import Dmail.Utils.DbFactory;
import Dmail.dao.MailDao;
import Dmail.model.Email;
import Dmail.model.User;
import net.sf.json.JSONArray;
import org.stringtemplate.v4.ST;

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

public class MailList extends HttpServlet {
    HttpServletRequest request;
    HttpServletResponse response;
    PrintWriter out;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null||session.getAttribute("userinfo") ==null) {
            response.sendRedirect("login");
        }
        else
        {
            User user = (User) session.getAttribute("userinfo");
            Connection conn = DbFactory.getConnection();
            MailDao mailDao = new MailDao();
            ArrayList<Email> mail;
            try {
                mail = mailDao.returnMailHeader(user.getUserid(),conn);
                JSONArray jsonArray = JSONArray.fromObject(mail);
               // System.out.println(jsonArray);
                out = response.getWriter();
                out.print(jsonArray);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null||session.getAttribute("userinfo") ==null) {
            response.sendRedirect("login");
        }
        else
        {
            User user = (User) session.getAttribute("userinfo");
            Connection conn = DbFactory.getConnection();
            MailDao mailDao = new MailDao();
            if(request.getParameter("req").equals("delete"))
            {
                String mailId = request.getParameter("mailId");
                try {
                    mailDao.deleteMail(mailId,conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
