package Dmail.Servlet;


import Dmail.model.User;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class CheckPassword extends HttpServlet{
    PrintWriter out;
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null||session.getAttribute("userinfo") ==null) {
            resp.sendRedirect("login");
        }
        else
        {
            User user = (User) session.getAttribute("userinfo");
            JSONObject jo = new JSONObject();
            if(req.getParameter("req").equals("checkPassword"))
            {

                String password = req.getParameter("password");
                boolean check = false;
                check = password.equals(user.getPassword());
                jo.put("check",check);
            }
            out = resp.getWriter();
            out.print(jo);
        }
    }
}
