package net.therap.leavemanagement.filter;

import net.therap.leavemanagement.controller.HomeController;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author rumi.dipto
 * @since 12/6/21
 */
public class ExceptionFilter implements Filter {

    private static final Logger logger = Logger.getLogger(HomeController.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try {
            chain.doFilter(request, response);
        } catch (Exception exception) {
            logger.error(exception.getCause());

            httpServletRequest.setAttribute("errorMessage", exception.getMessage());
            RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("/error");
            dispatcher.forward(httpServletRequest, httpServletResponse);
        }
    }
}
