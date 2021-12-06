package net.therap.leavemanagement.helper;

import net.therap.leavemanagement.domain.Designation;
import net.therap.leavemanagement.domain.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceException;
import java.util.List;
import java.util.Objects;

/**
 * @author rumi.dipto
 * @since 11/26/21
 */
@Component
public class AuthorizationHelper {

    public void checkAccess(List<Designation> designationList, HttpSession session) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if (!designationList.contains(sessionUser.getDesignation())) {
            throw new WebServiceException();
        }
    }

    public void checkAccess(User user, HttpSession session) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if (!user.equals(sessionUser)) {
            throw new WebServiceException();
        }
    }

    public void checkTeamLead(User teamLead, HttpSession session) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if (Objects.nonNull(teamLead) && sessionUser.getDesignation().equals(Designation.TEAM_LEAD)) {
            if (!teamLead.equals(sessionUser)) {
                throw new WebServiceException();
            }
        }
    }
}
