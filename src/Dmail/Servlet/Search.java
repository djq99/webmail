package Dmail.Servlet;

import Dmail.Utils.LuceneSearch;
import Dmail.model.Email;
import Dmail.model.User;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class Search extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userinfo") == null) {
            response.sendRedirect("login");
        }
        else
        {
            User user = (User) session.getAttribute("userinfo");
            int userId = user.getUserid();
            if(request.getParameter("req").equals("searchfrom"))
            {
                ArrayList<Email> emailHeader = null;
                String folder = request.getParameter("folderName");
                String searchField = "from";
                String searchWord = request.getParameter("content");
                try {
                    String indexPath = "indexPath"+"/"+userId;
                    emailHeader = LuceneSearch.Search(indexPath,folder,userId,searchField,searchWord);
                    JSONArray jsonArray = JSONArray.fromObject(emailHeader);
                    out = response.getWriter();
                    out.print(jsonArray);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(request.getParameter("req").equals("searchsubject"))
            {
                ArrayList<Email> emailHeader = null;
                String folder = request.getParameter("folderName");
                String searchField = "subject";
                String searchWord = request.getParameter("content");
                try {
                    String indexPath = "indexPath"+"/"+userId;
                    emailHeader = LuceneSearch.Search(indexPath,folder,userId,searchField,searchWord);
                    JSONArray jsonArray = JSONArray.fromObject(emailHeader);
                    out = response.getWriter();
                    out.print(jsonArray);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
