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

    public void checkSessionUser(User user, HttpSession session) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if (!user.equals(sessionUser)) {
            throw new WebServiceException();
        }
    }

    public void checkHumanResourceAccess(HttpSession session) {
        User user = (User) session.getAttribute("SESSION_USER");
        if (!Designation.HUMAN_RESOURCE.equals(user.getDesignation())) {
            throw new WebServiceException();
        }
    }

    public void checkTeamLeadAccess(HttpSession session) {
        User user = (User) session.getAttribute("SESSION_USER");
        if (!Designation.TEAM_LEAD.equals(user.getDesignation())) {
            throw new WebServiceException();
        }
    }

    public void denyTeamLeadAccess(HttpSession session) {
        User user = (User) session.getAttribute("SESSION_USER");
        if (Designation.TEAM_LEAD.equals(user.getDesignation())) {
            throw new WebServiceException();
        }
    }

    public void checkDeveloperTesterAccess(HttpSession session) {
        User user = (User) session.getAttribute("SESSION_USER");
        if (!Designation.DEVELOPER.equals(user.getDesignation())
                || !Designation.TESTER.equals(user.getDesignation())) {
            throw new WebServiceException();
        }
    }

    public void denyDeveloperTesterAccess(HttpSession session) {
        User user = (User) session.getAttribute("SESSION_USER");
        if (Designation.DEVELOPER.equals(user.getDesignation())
                || Designation.TESTER.equals(user.getDesignation())) {
            throw new WebServiceException();
        }
    }
}
