package net.therap.leavemanagement.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author rumi.dipto
 * @since 12/6/21
 */
public class ExceptionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try {
            chain.doFilter(request, response);
        } catch (Exception exception) {
            httpServletRequest.setAttribute("errorMessage", exception.getMessage());
            RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("/error");
            dispatcher.forward(httpServletRequest, httpServletResponse);
        }
    }
}
