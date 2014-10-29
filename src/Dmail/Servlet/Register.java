package Dmail.Servlet;

/**
 * Created by Jiaqi on 10/21/14.
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
public class Register extends HttpServlet {

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
        if(userDao.createUser(user,conn))
        {
            request.getSession().setAttribute("userinfo",user);
            response.sendRedirect("Dmail.html");
        }

    }
}