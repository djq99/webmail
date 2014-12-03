package Dmail.Servlet;

/**
 * Created by jiaqi on 10/25/14.
 */
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import Dmail.Servers.WebServer;
import Dmail.dao.MailDao;
import Dmail.dao.UserDao;
import Dmail.model.Email;
import Dmail.model.User;
import Dmail.Utils.DbFactory;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import Dmail.mail.SslPopClient;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

public class Login extends HttpServlet {

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
            ST loginST = templates.getInstanceOf("login");
            String login = loginST.render();
            out = response.getWriter();
            out.print(login);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        Connection conn = DbFactory.getConnection();
        UserDao userDao = new UserDao();
        int uid = 0;
        try {

            uid = userDao.returnUserID(user, conn);
            if (uid != -1)
            user.setUserid(uid);
            user = userDao.returnUserInfo(user.getUsername(), user.getPassword(), conn);
            HttpSession session = request.getSession();
            session.setAttribute("userinfo", user);
            response.sendRedirect("mail");
            File saveDir = new File("web/userResource/" + user.getUserid());
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            DbFactory.closeConn(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

