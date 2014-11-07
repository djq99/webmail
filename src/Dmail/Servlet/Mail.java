package Dmail.Servlet;

import Dmail.Utils.DbFactory;
import Dmail.mail.SslPopClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import Dmail.dao.UserDao;
import Dmail.model.User;

/**
 * Created by jiaqi on 11/2/14.
 */
public class Mail extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        User user = (User) request.getSession().getAttribute("userinfo");
        UserDao userDao = new UserDao();
        Connection conn = DbFactory.getConnection();
        String clientRequest = request.getParameter("request");
        user = userDao.returnUserInfo(user.getUsername(),user.getPassword(),conn);
        //return how many emails pulled from pop3
        int mailNumber;
        if(clientRequest.equals("mailNumber"))
        {
            mailNumber = SslPopClient.returnEmailNumber(user);
            String rs = String.valueOf(mailNumber);
            response.getOutputStream().print(rs);
        }


    }
}
