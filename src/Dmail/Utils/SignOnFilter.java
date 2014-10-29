package Dmail.Utils;

/**
 * Created by jiaqi on 10/28/14.
 */
/*
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;
public class SignOnFilter implements Filter {
    FilterConfig fc;
    public void destroy() {

    }
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hreq=(HttpServletRequest)request;
        HttpServletResponse hres=(HttpServletResponse) response;
        HttpSession session = hreq.getSession();
        if(session!=null&&session.getAttribute("user")!=null)
            chain.doFilter(request, response);
        else if(hreq.getRequestURI().equals(hreq.getContextPath()+"/Dmail.html")){
            if(valid(hreq,hres))
                hres.sendRedirect(hreq.getContextPath()+"/Dmail.html");
            else chain.doFilter(request, response);
        }
        else hres.sendRedirect(hreq.getContextPath()+"/Home.html");
    }
    public Boolean valid(HttpServletRequest hreq, ServletResponse hres){
        String uName=hreq.getParameter("username");
        String uPassword=hreq.getParameter("password");
        if(uName!=null){
            HttpSession session=hreq.getSession();
            session.setAttribute("user", uName);
            return true;
        }else return false;

    }

    public void init(FilterConfig fc) throws ServletException {
        // TODO Auto-generated method stub
        this.fc=fc;
    }
}
*/