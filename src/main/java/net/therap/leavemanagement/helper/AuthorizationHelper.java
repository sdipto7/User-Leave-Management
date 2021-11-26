package net.therap.leavemanagement.helper;

import net.therap.leavemanagement.domain.Designation;
import net.therap.leavemanagement.domain.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceException;

/**
 * @author rumi.dipto
 * @since 11/26/21
 */
@Component
public class AuthorizationHelper {

    public void checkHumanResourceAccess(HttpSession session) {
        User user = (User) session.getAttribute("SESSION_USER");
        if (!Designation.HUMAN_RESOURCE.equals(user.getDesignation())) {
            throw new WebServiceException();
        }
    }
}
