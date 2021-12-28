package net.therap.leavemanagement.filter;

import org.apache.log4j.Logger;
import org.springframework.web.HttpSessionRequiredException;

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
        } catch (Exception exception) {
            String cause;
            try {
                throw exception.getCause();
            } catch (NullPointerException nullPointerException) {
                cause = "The Requested data is not available";
                logger.error("[NullPointerException] {}", nullPointerException.fillInStackTrace());
            } catch (WebServiceException webServiceException) {
                cause = "You are not authorized to look into this page";
                logger.error("[WebServiceException] {}", webServiceException.fillInStackTrace());
            } catch (HttpSessionRequiredException httpSessionRequiredException) {
                cause = "Invalid session! Please Reload the page";
                logger.error("[HttpSessionRequiredException] {}", httpSessionRequiredException.fillInStackTrace());
            } catch (OptimisticLockException optimisticLockException) {
                cause = "The current state of data you are trying to modify is already modified! Please reload the page";
                logger.error("[OptimisticLockException] {}", optimisticLockException.fillInStackTrace());
            } catch (Throwable throwable) {
                cause = "Unexpected error occured! Contact us for more detailed information";
                logger.error("[Exception] {}", exception.fillInStackTrace());
            }
            httpServletRequest.setAttribute("errorMessage", cause);
            RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("/error");
            dispatcher.forward(httpServletRequest, httpServletResponse);
        }
    }
}
