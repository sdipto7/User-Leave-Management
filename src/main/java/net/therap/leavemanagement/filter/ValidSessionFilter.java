package net.therap.leavemanagement.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * @author rumi.dipto
 * @since 12/6/21
 */
public class ValidSessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = ((HttpServletRequest) request).getSession();

        if (Objects.nonNull(session.getAttribute("SESSION_USER"))) {
            httpServletResponse.sendRedirect("/dashboard");
        } else {
            chain.doFilter(request, response);
        }
    }
}
