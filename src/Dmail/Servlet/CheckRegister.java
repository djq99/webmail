package Dmail.Servlet;


import Dmail.Utils.DbFactory;
import Dmail.dao.UserDao;
import Dmail.mail.SslPopClient;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

public class CheckRegister extends HttpServlet{
    PrintWriter out;
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("req").equals("checkmail"))
        {
            String email = req.getParameter("email");
            String emailPassword = req.getParameter("password");
            boolean result = false;
            try
            {
                result = SslPopClient.verifyEmail(email, emailPassword);
            }
            catch (Exception e){
                result = false;
            }
            out = resp.getWriter();
            JSONObject jo = new JSONObject();
            jo.put("result",result);
            out.print(jo);

        }
        else if(req.getParameter("req").equals("checkname"))
        {

            String username = req.getParameter("username");
            UserDao userDao = new UserDao();
            Connection conn = DbFactory.getConnection();
            try {
               boolean check = userDao.checkUser(username,conn);
                JSONObject jo = new JSONObject();
                jo.put("check",check);
                out = resp.getWriter();
                out.print(jo);
                DbFactory.closeConn(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
}
