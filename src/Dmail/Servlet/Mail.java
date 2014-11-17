package Dmail.Servlet;

import Dmail.Servers.WebServer;
import Dmail.Utils.DbFactory;
import Dmail.dao.MailDao;
import Dmail.mail.SslPopClient;

import javax.jms.Session;
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

import Dmail.dao.UserDao;
import Dmail.model.Email;
import Dmail.model.User;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

/**
 * Created by jiaqi on 11/2/14.
 */
public class Mail extends HttpServlet {

    STGroup templates = new STGroupDir(WebServer.WEBMAIL_TEMPLATES_ROOT); // call unload() to wack the cache
    {
        templates.setListener(WebServer.stListener);
        templates.delimiterStartChar = '$';
        templates.delimiterStopChar = '$';
    }

    HttpServletRequest request;
    HttpServletResponse response;
    PrintWriter out;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login");
        } else {
            User user = (User) session.getAttribute("userinfo");
            Connection conn = DbFactory.getConnection();
            MailDao mailDao = new MailDao();
            ArrayList<Email> mail;
            try {
                mail = mailDao.returnMailHeader(user.getUserid(),conn);
                ST mailST = templates.getInstanceOf("mailPage");
                mailST.add("mails",mail);
                String mailPage = mailST.render();
                out = response.getWriter();
                out.print(mailPage);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DbFactory.closeConn(conn);
            }



          /*  int emailNumber = SslPopClient.returnEmailNumber(user);

            Email[] mail = SslPopClient.returnEmail(user, emailNumber);
            for (int i = 0; i < mail.length; i++) {
                try {
                    if (!mailDao.checkMail(mail[i], conn)) {
                    try {
                         mailDao.createMail(mail[i], user, conn);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
          }*/






        }

    }

    /*    User user = new User();
        user.setEmailaddress("imdjq1990@gmail.com");
        user.setEmailPassword("djq199031415926");
        int emailNumber = SslPopClient.returnEmailNumber(user);

        Email []mail = SslPopClient.returnEmail(user,emailNumber);
       // PrintWriter out = response.getWriter();
       // response.setContentType("application/octet-stream;");
        response.setContentType("text/plain");
        //System.out.println(mail[0].getContent());

       // out.println(emailNumber);
        response.getOutputStream().print(mail[5].getContent());*/
       // out.println(mail[0].getContent());

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        User user = (User) request.getSession().getAttribute("userinfo");
        String clientRequest = request.getParameter("request");
        System.out.println(clientRequest);
        //return how many emails pulled from pop3
        int mailNumber;
        if(clientRequest.equals("mailNumber"))
        {
            mailNumber = SslPopClient.returnEmailNumber(user);
            System.out.println(mailNumber);
            String rs = String.valueOf(mailNumber);
            response.getOutputStream().print(rs);
        }


    }
}
