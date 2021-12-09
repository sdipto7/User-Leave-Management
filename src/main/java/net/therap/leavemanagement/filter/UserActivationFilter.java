package net.therap.leavemanagement.filter;

import net.therap.leavemanagement.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author rumi.dipto
 * @since 12/8/21
 */
public class UserActivationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = ((HttpServletRequest) request).getSession();

        User user = (User) session.getAttribute("SESSION_USER");

        if (!user.isActivated()) {
            httpServletResponse.sendRedirect("/user/profile?id=" + user.getId());
        } else {
            chain.doFilter(request, response);
        }
    }
}
