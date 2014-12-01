package Dmail.Servlet;


import Dmail.Utils.DbFactory;
import Dmail.dao.MailDao;
import Dmail.model.Email;
import Dmail.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.stringtemplate.v4.ST;

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

public class MailList extends HttpServlet {
    PrintWriter out;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null||session.getAttribute("userinfo") ==null) {
            response.sendRedirect("login");
        }
        else {
            User user = (User) session.getAttribute("userinfo");
            Connection conn = DbFactory.getConnection();
            MailDao mailDao = new MailDao();
            int pages;
            int pageRecords=10;
            int records = 0;
            if (request.getParameter("req").equals("pages"))
            {
                int userId = user.getUserid();

                try {
                    records = mailDao.totalRecords("inbox",userId,conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if(records % pageRecords == 0)
                {
                    pages = records/pageRecords;
                }
                else pages = records/pageRecords+1;
                Map map = new HashMap();
                map.put("totalPages",pages);
                map.put("currentPage",1);
                JSONObject jo = JSONObject.fromObject(map);
                out = response.getWriter();
                out.print(jo);
                DbFactory.closeConn(conn);

            }
            else if (request.getParameter("req").equals("maillist"))
            {
                ArrayList<Email> mail;
                try {
                    mail = mailDao.getCurrentPage("inbox",user.getUserid(),1,pageRecords,conn);
                    JSONArray jsonArray = JSONArray.fromObject(mail);
                    out = response.getWriter();
                    out.print(jsonArray);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DbFactory.closeConn(conn);

            }
            else if(request.getParameter("req").equals("folders"))
            {
                int userId = user.getUserid();
                ArrayList<String>folder;
                try {
                    folder = mailDao.getFolders(userId, conn);
                    JSONArray jsonArray = JSONArray.fromObject(folder);
                    out = response.getWriter();
                    out.print(jsonArray);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DbFactory.closeConn(conn);

            }
            else if(request.getParameter("req").equals("markRead"))
            {
                String mailId = request.getParameter("mailId");
                try {
                     mailDao.markRead(mailId,conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DbFactory.closeConn(conn);

            }
            else if(request.getParameter("req").equals("markUnread"))
            {

                String mailId = request.getParameter("mailId");
                try {
                    mailDao.markUnread(mailId,conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DbFactory.closeConn(conn);

            }

        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null||session.getAttribute("userinfo") ==null) {
            response.sendRedirect("login");
        }
        else
        {
            User user = (User) session.getAttribute("userinfo");
            Connection conn = DbFactory.getConnection();
            MailDao mailDao = new MailDao();
            if(request.getParameter("req").equals("delete"))
            {
                String mailId = request.getParameter("mailId");
                try {
                    mailDao.deleteMail(mailId,conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DbFactory.closeConn(conn);
        }
    }
}
