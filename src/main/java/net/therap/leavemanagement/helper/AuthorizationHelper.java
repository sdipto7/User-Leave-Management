package net.therap.leavemanagement.helper;

import net.therap.leavemanagement.domain.Designation;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.util.ServletUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.xml.ws.WebServiceException;
import java.util.Arrays;
import java.util.List;

/**
 * @author rumi.dipto
 * @since 11/26/21
 */
@Component
public class AuthorizationHelper {

    public void checkAccess(Designation... designations) {
        User sessionUser = (User) WebUtils.getSessionAttribute(ServletUtil.getHttpServletRequest(), "SESSION_USER");

        List<Designation> designationList = Arrays.asList(designations);
        if (!designationList.contains(sessionUser.getDesignation())) {
            throw new WebServiceException();
        }
    }

    public void checkAccess(User user) {
        User sessionUser = (User) WebUtils.getSessionAttribute(ServletUtil.getHttpServletRequest(), "SESSION_USER");

        if (!user.equals(sessionUser)) {
            throw new WebServiceException();
        }
    }
}
