package net.therap.leavemanagement.filter;

import org.apache.log4j.Logger;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.util.NestedServletException;

import javax.persistence.OptimisticLockException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceException;
import java.io.IOException;

/**
 * @author rumi.dipto
 * @since 12/6/21
 */
public class ExceptionFilter implements Filter {

    private static final Logger logger = Logger.getLogger(ExceptionFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try {
            chain.doFilter(request, response);
        } catch (NestedServletException nestedServletException) {
            try {
                throw nestedServletException.getRootCause();
            } catch (NullPointerException nullPointerException) {
                logger.error("[NullPointerException] The Requested data is not available");
                httpServletRequest.setAttribute("errorMessage", "The Requested data is not available");
                forward(httpServletRequest, httpServletResponse);
            } catch (WebServiceException webServiceException) {
                logger.error("[WebServiceException] You are not authorized to look into this page");
                httpServletRequest.setAttribute("errorMessage", "You are not authorized to look into this page");
                forward(httpServletRequest, httpServletResponse);
            } catch (HttpSessionRequiredException httpSessionRequiredException) {
                logger.error("[HttpSessionRequiredException] Invalid session! Please Reload the page");
                httpServletRequest.setAttribute("errorMessage", "Invalid session! Please Reload the page");
                forward(httpServletRequest, httpServletResponse);
            } catch (OptimisticLockException optimisticLockException) {
                logger.error("[OptimisticLockException] The current state of data you are trying to modify is already modified! Please reload the page");
                httpServletRequest.setAttribute("errorMessage", "The current state of data you are trying to modify is already modified! Please reload the page");
                forward(httpServletRequest, httpServletResponse);
            } catch (Throwable exception) {
                logger.error("[Exception] Unexpected error occured! Contact us for more detailed information");
                httpServletRequest.setAttribute("errorMessage", exception.getMessage());
                forward(httpServletRequest, httpServletResponse);
            }
        }
    }

    public void forward(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("/error");
        dispatcher.forward(httpServletRequest, httpServletResponse);
    }
}
