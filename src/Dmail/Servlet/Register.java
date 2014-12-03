package Dmail.Servlet;

/**
 * Created by Jiaqi on 10/21/14.
 */
import Dmail.Servers.WebServer;
import Dmail.Utils.DbFactory;
import Dmail.dao.UserDao;
import Dmail.model.User;
import net.sf.json.JSONObject;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

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
        UserDao userDao = new UserDao();
        Connection conn = DbFactory.getConnection();
            user.setUsername(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));
            user.setEmailaddress(request.getParameter("emailaddress"));
            user.setEmailPassword(request.getParameter("emailpassword"));
            try {
                userDao.createUser(user,conn);

                int uid = userDao.returnUserID(user,conn);
                DbFactory.closeConn(conn);
                user.setUserid(uid);
                HttpSession session = request.getSession(true);
                session.setAttribute("userinfo",user);
                File saveDir = new File("web/userResource/"+user.getUserid());
                saveDir.mkdirs();
                response.sendRedirect("mail");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

 }

