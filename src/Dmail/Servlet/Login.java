package Dmail.Servlet;

/**
 * Created by tomjacky on 10/25/14.
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;

import Dmail.dao.UserDao;
import Dmail.model.User;
import Dmail.Utils.DbFactory;
import java.sql.Connection;
public class Login extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        int uid = userDao.returnUserID(user,conn);
        if(uid!=-1)
        user.setUserid(uid);
        if(userDao.findUser(user,conn))
        {
            request.getSession().setAttribute("userinfo",user);
            response.sendRedirect("Dmail.html");
        }

    }
}

