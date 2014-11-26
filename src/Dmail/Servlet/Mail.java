package Dmail.Servlet;

import Dmail.Servers.WebServer;
import Dmail.Utils.DbFactory;
import Dmail.dao.MailDao;
import Dmail.mail.SslPopClient;
import Dmail.model.Email;
import Dmail.model.User;
import net.sf.json.JSONArray;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

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
        if(session == null||session.getAttribute("userinfo") ==null) {
           response.sendRedirect("login");
        } else {
            User user = (User) session.getAttribute("userinfo");
            Connection conn = DbFactory.getConnection();
            MailDao mailDao = new MailDao();
            ArrayList<Email> mail;

            try {
                mail = mailDao.returnMailHeader(user.getUserid(),conn);
                ST mailST = templates.getInstanceOf("mailPage");
              //  mailST.add("mails",mail);
                JSONArray jsonArray = JSONArray.fromObject(mail);
                String mailPage = mailST.render();
                out = response.getWriter();
                out.print(mailPage);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                DbFactory.closeConn(conn);
            }
        }
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

    }



}
