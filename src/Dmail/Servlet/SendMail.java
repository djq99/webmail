package Dmail.Servlet;

import Dmail.mail.SslSmtpClient;
import Dmail.model.Email;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


import Dmail.model.User;


/**
 * Created by jiaqi on 11/22/14.
 */
public class SendMail  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session ==null || session.getAttribute("userinfo")==null)
        {
            response.sendRedirect("login");
        }
        User user = (User) session.getAttribute("userinfo");
        Email email = new Email();
        //has attachment
        if(session.getAttribute("email")!=null)
        {
            email = (Email) session.getAttribute("email");
        }
        email.setSendTo(request.getParameter("email_to"));
        email.setSendCC(request.getParameter("email_cc"));
        email.setTitle(request.getParameter("subject"));
        email.setContent(request.getParameter("message"));

        //send email
        SslSmtpClient.sendEMail(user,email);

        response.sendRedirect("mail");
    }
}
