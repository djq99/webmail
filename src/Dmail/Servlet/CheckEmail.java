package Dmail.Servlet;

import Dmail.Servers.WebServer;
import Dmail.Utils.DbFactory;
import Dmail.dao.MailDao;
import Dmail.mail.SslPopClient;
import Dmail.model.Email;
import Dmail.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import java.util.HashMap;
import java.util.Map;


public class CheckEmail extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null||session.getAttribute("userinfo") ==null) {
            response.sendRedirect("login");
        } else {
            User user = (User) session.getAttribute("userinfo");

            if (request.getParameter("req").equals("checmail"))
            {
                int emailNumber = SslPopClient.returnEmailNumber(user);
                SslPopClient.returnEmail(user, emailNumber);
            }
        }
    }
}
