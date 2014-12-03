package Dmail.Servlet;

import Dmail.Servers.WebServer;
import Dmail.Utils.DbFactory;
import Dmail.dao.UserDao;
import Dmail.model.User;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;


public class Account extends HttpServlet {

    STGroup templates = new STGroupDir(WebServer.WEBMAIL_TEMPLATES_ROOT); // call unload() to wack the cache
    {
        templates.setListener(WebServer.stListener);
        templates.delimiterStartChar = '$';
        templates.delimiterStopChar = '$';
    }

    HttpServletRequest request;
    HttpServletResponse response;
    PrintWriter out;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null||session.getAttribute("userinfo") ==null) {
            response.sendRedirect("login");
        }
        else {
            ST accountST = templates.getInstanceOf("account");
            String account = accountST.render();
            out = response.getWriter();
            out.print(account);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        Connection conn = DbFactory.getConnection();
        UserDao userDao = new UserDao();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userinfo") == null) {
            response.sendRedirect("login");
        }
        else
        {
            User user = (User) session.getAttribute("userinfo");
            String newPassword = request.getParameter("newPassword");
            user.setPassword(newPassword);
            try {
                userDao.updatePassword(user,conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            session.removeAttribute("userinfo");
            session.setAttribute("userinfo",user);
            response.sendRedirect("mail");

        }
    }

}
