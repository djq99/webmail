package Dmail.Servlet;

import Dmail.Servers.WebServer;
import Dmail.Utils.DbFactory;
import Dmail.Utils.ParseEmail;
import Dmail.dao.MailDao;
import Dmail.model.Email;
import Dmail.model.User;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by jiaqi on 11/16/14.
 */
public class ViewMail extends HttpServlet {
    STGroup templates = new STGroupDir(WebServer.WEBMAIL_TEMPLATES_ROOT); // call unload() to wack the cache
    {
        templates.setListener(WebServer.stListener);
        templates.delimiterStartChar = '$';
        templates.delimiterStopChar = '$';
    }


    PrintWriter out;
    String emailContent;


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Email email = new Email();
        if (session == null || session.getAttribute("userinfo")==null) {
            response.sendRedirect("login");
        } else {
           String mailId = request.getParameter("mailinfo");
            String content ="";
            MailDao mailDao = new MailDao();
            Connection conn = DbFactory.getConnection();
            try {
                content = mailDao.returnMailContent(mailId,conn);
                email = mailDao.returnOneMailHeader(mailId,conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DbFactory.closeConn(conn);
            }


            //decode the email content///////////////////////
            Properties properties = new Properties();
            MimeMessage msg = null;
            try {
                msg = new MimeMessage(Session.getDefaultInstance(properties),
                        new ByteArrayInputStream(content.getBytes()));
                Object o = msg.getContent();

                User user = (User) request.getSession().getAttribute("userinfo");
                ParseEmail p = new ParseEmail(user);
                String s =  p.getEmailContent(o);

                ST mailST = templates.getInstanceOf("viewMail");
                String n = new String(s.getBytes(),"ISO-8859-1");
                mailST.add("content",n);
                mailST.add("header",email);
                String viewMail = mailST.render();
                out = response.getWriter();
                out.print(viewMail);

            } catch (MessagingException e) {
                e.printStackTrace();
            }


        }
    }
}


