package Dmail.Servlet;


import Dmail.Utils.DbFactory;
import Dmail.dao.MailDao;
import Dmail.model.Email;
import Dmail.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

public class Folder extends HttpServlet {
    PrintWriter out;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userinfo") == null) {
            response.sendRedirect("login");
        } else {
            User user = (User) session.getAttribute("userinfo");
            Connection conn = DbFactory.getConnection();
            MailDao mailDao = new MailDao();
            int pages;
            int pageRecords=20;
            int records = 0;
            if(request.getParameter("req").equals("movetofolder"))
            {
                String mailId = request.getParameter("mailId");
                String folderName = request.getParameter("folderName");
                try {
                    mailDao.changeFolder(mailId,folderName,conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DbFactory.closeConn(conn);
            }

            else if (request.getParameter("req").equals("changefolderpage"))
            {
                String folderName = request.getParameter("folderName");
                int userId = user.getUserid();
                try {
                    records = mailDao.totalRecords(folderName,userId,conn);
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
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DbFactory.closeConn(conn);

            }
            else if(request.getParameter("req").equals("changefolder"))
            {
                ArrayList<Email> mail;
                String folderName = request.getParameter("folderName");
                try {
                    mail = mailDao.getCurrentPage(folderName,user.getUserid(),1,pageRecords,conn);
                    JSONArray jsonArray = JSONArray.fromObject(mail);
                    out = response.getWriter();
                    out.print(jsonArray);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DbFactory.closeConn(conn);
            }

            else if(request.getParameter("req").equals("createFolder"))
            {
                out = response.getWriter();
                String folderName = request.getParameter("folder");
                try {
                   boolean result = mailDao.createFolder(user.getUserid(),folderName,conn);
                    String r = String.valueOf(result);
                    if(r!= null)
                    {
                        out.print("create success!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DbFactory.closeConn(conn);
            }
            else if(request.getParameter("req").equals("deleteFolder"))
            {
                out = response.getWriter();
                String folderName = request.getParameter("folder");
                try {
                    mailDao.deleteFolder(user.getUserid(),folderName,conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DbFactory.closeConn(conn);
            }
            else if(request.getParameter("req").equals("cleantrash"))
            {

                try {
                    mailDao.cleanTrash(user.getUserid(),conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DbFactory.closeConn(conn);
            }
        }
    }

}
