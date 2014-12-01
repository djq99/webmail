package Dmail.Servlet;

/**
 * Created by Jiaqi on 10/21/14.
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import Dmail.Servers.WebServer;
import Dmail.dao.UserDao;
import Dmail.model.User;
import Dmail.Utils.DbFactory;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import Dmail.mail.SslPopClient;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

public class Register extends HttpServlet {
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
        if(session!=null && session.getAttribute("userinfo")!=null)
        {
            response.sendRedirect("mail");
        }
        else{
            ST loginST = templates.getInstanceOf("register");
            String register = loginST.render();
            out = response.getWriter();
            out.print(register);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setEmailaddress(request.getParameter("emailAddress"));
        user.setEmailPassword(request.getParameter("emailPassword"));
        Connection conn = DbFactory.getConnection();
        UserDao userDao = new UserDao();
        try {
            if(userDao.createUser(user,conn))
            {
                boolean check = SslPopClient.verifyEmail(user.getEmailaddress(),user.getEmailPassword());
                if(check == true)
                {
                    request.getSession().setAttribute("userinfo",user);
                    response.sendRedirect("mail");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
