package Dmail.Servlet;

import Dmail.Servers.WebServer;
import Dmail.Utils.DbFactory;
import Dmail.Utils.ParseEmail;
import Dmail.dao.MailDao;
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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
        if (session == null) {
            response.sendRedirect("login");
        } else {
           String mailId = request.getParameter("mailinfo");
            String content ="";
            MailDao mailDao = new MailDao();
            Connection conn = DbFactory.getConnection();
            try {
                content = mailDao.returnMailContent(mailId,conn);
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
                ParseEmail p = new ParseEmail();
               String s =  p.getEmailContent(o);
                out=response.getWriter();
                out.print(s);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

    /*        Session.getDefaultInstance(properties);
            try {
                MimeMessage msg = new MimeMessage(Session.getDefaultInstance(properties),
                        new ByteArrayInputStream(content.getBytes()));
                Object o = msg.getContent();
                if (o instanceof Multipart)
                {
                    Multipart multipart = (Multipart)o;
                    parseMultipart(multipart,response);
                }
                else if(o instanceof Part)
                {
                    Part part =(Part) o;
                    parsePart(part,response);
                }
                else if(o instanceof String)
                {
                  //  System.out.println(header);
                    String str = (String)o;
                    parseString(str);
                }
               // System.out.println(content);
                out=response.getWriter();
                out.print(emailContent);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
*/
        }
    }
    /*
    private void parseString(String str) throws Exception{
        emailContent = str;
    }
    private void parseMultipart(Multipart multipart,HttpServletResponse response) throws Exception{
        for(int i = 0, n = multipart.getCount();i < n; i++)
        {
            Part part = multipart.getBodyPart(i);
            if(part.getContent() instanceof Multipart)
            {
                Multipart p = (Multipart) part.getContent();
                parseMultipart(p,response);
            }
            else
            {
                parsePart(part,response);
            }
        }
    }
    private void parsePart(Part part,HttpServletResponse response)throws Exception{
        String disposition = part.getDisposition() ;
        if(disposition != null && disposition.equals(part.ATTACHMENT))
        {
            String fileName = MimeUtility.decodeText(part.getFileName());
        }
        else if(part.getContentType().startsWith("text/html"))
        {
            emailContent = (String)part.getContent();
        }
        else
        {
            emailContent = (String)part.getContent();
        }
    }
*/
}


