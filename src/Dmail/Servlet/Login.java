package Dmail.Servlet;

/**
 * Created by jiaqi on 10/25/14.
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import Dmail.dao.MailDao;
import Dmail.dao.UserDao;
import Dmail.model.Email;
import Dmail.model.User;
import Dmail.Utils.DbFactory;
import java.sql.Connection;

import Dmail.mail.SslPopClient;
public class Login extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session!=null)
        {
            response.sendRedirect("Dmail.html");
        }
        else{
            response.sendRedirect("Home.html");
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
        MailDao mailDao = new MailDao();
        int uid = userDao.returnUserID(user,conn);
        if(uid!=-1)
        user.setUserid(uid);
        if(userDao.findUser(user,conn))
        {
            System.out.println("登录成功");
            user = userDao.returnUserInfo(user.getUsername(),user.getPassword(),conn);
            HttpSession session = request.getSession();
            session.setAttribute("userinfo",user);
            //deal with email headers
            int emailNumber = SslPopClient.returnEmailNumber(user);

            Email[] mailInfo= SslPopClient.returnEmailInfo(user,emailNumber);

            for(int i=0;i < mailInfo.length;i++)
            {
                if(!mailDao.checkMail(mailInfo[i],user,conn))
                {
                    mailDao.createMailHeader(mailInfo[i],user,conn);
                }
            }
            response.sendRedirect("Dmail.html");

        }

    }
}

