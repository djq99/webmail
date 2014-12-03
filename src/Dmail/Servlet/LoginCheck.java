package Dmail.Servlet;


import Dmail.Utils.DbFactory;
import Dmail.dao.UserDao;
import Dmail.model.User;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginCheck extends HttpServlet{
    PrintWriter out;
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("req").equals("login"))
        {

            String username = req.getParameter("username");
            String password = req.getParameter("password");
            UserDao userDao = new UserDao();
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            boolean result = false;
            Connection conn = DbFactory.getConnection();
            try {
                result = userDao.findUser(user,conn);
                DbFactory.closeConn(conn);
                JSONObject jo =new JSONObject();
                jo.put("result",result);
                out = resp.getWriter();
                out.print(jo);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
