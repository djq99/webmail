package Dmail.Servlet;

import Dmail.Utils.DbFactory;
import Dmail.mail.SslPopClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;

import Dmail.dao.UserDao;
import Dmail.model.Email;
import Dmail.model.User;

/**
 * Created by jiaqi on 11/2/14.
 */
public class Mail extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = new User();
        user.setEmailaddress("imdjq1990@gmail.com");
        user.setEmailPassword("djq199031415926");
        int emailNumber = SslPopClient.returnEmailNumber(user);

        Email []mail = SslPopClient.returnEmail(user,emailNumber);
       // PrintWriter out = response.getWriter();
       // response.setContentType("application/octet-stream;");
        response.setContentType("text/plain");
        //System.out.println(mail[0].getContent());

       // out.println(emailNumber);
        response.getOutputStream().print(mail[5].getContent());
       // out.println(mail[0].getContent());

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {/*
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

*/
    }
}
