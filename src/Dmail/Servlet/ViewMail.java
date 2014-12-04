package Dmail.Servlet;

import Dmail.Servers.WebServer;
import Dmail.Utils.DbFactory;
import Dmail.dao.MailDao;
import Dmail.mail.SslSmtpClient;
import Dmail.model.Attachment;
import Dmail.model.Email;
import Dmail.model.User;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by jiaqi on 11/16/14.
 */
public class ViewMail extends HttpServlet {
    private StringBuffer sb = new StringBuffer();
    private boolean hasAttachment = false;
    private boolean hasText = false;
    static ArrayList<Attachment> attachments = new ArrayList<Attachment>();
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
        int newNum = 0;
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
                //check if the email is new, change its state
                if(email.getIsNew().equals("true"))
                {
                    mailDao.changeState(mailId,conn);
                }
                User user = (User) session.getAttribute("userinfo");
               newNum = mailDao.returnNewMailNumber(user.getUserid(),conn);
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
                ViewMail p = new ViewMail();
                String s =  p.getEmailContent(o,user);

                ST mailST = templates.getInstanceOf("viewMail");
                String n = new String(s.getBytes(),"ISO-8859-1");
                if(newNum > 0)
                {
                    mailST.add("newNum",newNum);
                }
                mailST.add("content",n);
                mailST.add("header",email);
                session.setAttribute("viewEmail",content);
                session.setAttribute("viewEmailHeader",email);
                if(!attachments.isEmpty())
                {
                    mailST.add("attachment",attachments);

                }

                String viewMail = mailST.render();
                out = response.getWriter();
                out.print(viewMail);
                attachments.clear();
            } catch (MessagingException e) {
                e.printStackTrace();
            }


        }
    }
    public String getEmailContent(Object o, User user) throws MessagingException, IOException {
        String result = "";
        if(o instanceof String)
        {
            result = (String)o;
        }
        else if(o instanceof Part)
        {
            result = parseContent((Part)o,user);
        }
        else if(o instanceof Multipart)
        {
            Multipart multipart = (Multipart)o;
            result = parseMultipart(multipart,user);
        }
        return result;

    }

    public String parseMultipart(Multipart multipart, User user) throws MessagingException, IOException
    {
        String result = "";
        for (int i= 0,n = multipart.getCount(); i<n; i++)
        {
            Part part = multipart.getBodyPart(i);
            if(part.getContent() instanceof Multipart)
            {
                Multipart p = (Multipart)part.getContent();
                parseMultipart(p,user);
            }
            else
            {
                parseContent(part,user);
            }
        }
        return sb.toString();
    }

    public String parseContent(Part part, User user) throws MessagingException, IOException {
        sb.append(new String(""));
        if(part.getContentType().startsWith("text/plain"))
        {
            hasText = true;
            String s = (String) part.getContent();
            sb.append(s);
        }
        if(part.getContentType().startsWith("text/html"))
        {
            String s = (String) part.getContent();
            if(hasText == true)
            {
                sb.delete(0,sb.length());
                sb.append(s);
            }
            else
            {
                sb.append(s);
            }
        }
        if(part.getFileName()!=null)
        {
            String header[] = part.getHeader("Content-ID");
            if(header == null)
            {

                //is an attachment not in the html

                    hasAttachment = true;
                    String fileName = part.getFileName();
                    fileName = MimeUtility.decodeText(fileName);

                    InputStream is = part.getInputStream();
                    String path = getFilePath(user);
                    File dir = new File(path);
                    File f = new File(dir,fileName);
                    FileOutputStream fos = new FileOutputStream(f);
                    byte[] buf = new byte[4096];
                    int bytesRead;
                    while((bytesRead = is.read(buf))!=-1) {
                        fos.write(buf, 0, bytesRead);
                    }
                    Attachment a = new Attachment();
                    a.setAttachmentPath("userResource/"+user.getUserid()+"/"+fileName);
                    a.setAttachmentName(fileName);
                    attachments.add(a);


            }
            else
            {
                String imgPath ="";
                String cid = "";
                cid = getCid(part);
                String fileName = part.getFileName();
                InputStream is = part.getInputStream();
                fileName = MimeUtility.decodeText(fileName);
                String path = getFilePath(user);
                File dir = new File(path);
                dir.mkdirs();
                File f = new File(dir,fileName);
                FileOutputStream fos = new FileOutputStream(f);
                byte[] buf = new byte[4096];
                int bytesRead;
                while((bytesRead = is.read(buf))!=-1) {
                    fos.write(buf, 0, bytesRead);
                }
                imgPath = "userResource/"+user.getUserid()+"/"+fileName;
                String s = sb.toString();
                if(s.contains(cid))
                {
                    s = s.replaceAll(cid,imgPath);
                }
                sb.replace(0,sb.length(),s);
            }
        }
        return sb.toString();
    }
    private static String getFilePath(User user)
    {
        String path = String.valueOf(user.getUserid());
        path = "./web/userResource/"+path;
        return path;
    }
    private static String getCid(Part p) throws MessagingException {
        String cidraw = null, cid = null;
        String[] headers = p.getHeader("Content-id");
        if (headers != null && headers.length > 0) {
            cidraw = headers[0];
        } else {
            return null;
        }
        if (cidraw.startsWith("<") && cidraw.endsWith(">")) {
            cid = "cid:" + cidraw.substring(1, cidraw.length() - 1);
        } else {
            cid = "cid:" + cidraw;
        }
        return cid;

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession(false);
        if(session.getAttribute("viewEmailHeader")!=null && session.getAttribute("viewEmail")!=null)
        {
            Email email = (Email) session.getAttribute("viewEmailHeader");
            String content = (String) session.getAttribute("viewEmail");
            session.removeAttribute("viewEmail");
            session.removeAttribute("viewEmailHeader");
            User user = (User) session.getAttribute("userinfo");

            //if the request is forward
            if(request.getParameter("email_to")!=null)
            {
                email.setSendTo(request.getParameter("email_to"));
                email.setContent(content);
                SslSmtpClient.forwardEMail(user,email);
            }
            //if the request is reply
            else if(request.getParameter("message")!=null)
            {
                email.setContent(request.getParameter("message"));
                email.setTitle("reply: "+email.getTitle());
                String sendTo = email.getFrom();
                String[] ary = sendTo.split("&lt");
                String temp ="";
                for(int i=1;i < ary.length;i++) {
                    temp = temp+ary[i].substring(0, ary[i].indexOf("&gt"));
                }
                sendTo = temp;
                email.setSendTo(sendTo);
                SslSmtpClient.forwardEMail(user, email);
            }

        }
        response.sendRedirect("mail");
    }
}


