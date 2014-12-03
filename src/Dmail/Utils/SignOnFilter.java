package Dmail.Utils;

/**
 * Created by jiaqi on 10/28/14.
 */

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
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        if (session.getAttribute("userinfo") != null) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect("login");
        }
    }


    public void init(FilterConfig fc) throws ServletException {
        // TODO Auto-generated method stub
        this.fc=fc;
    }
}
