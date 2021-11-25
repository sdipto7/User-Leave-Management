package net.therap.leavemanagement.helper;

import net.therap.leavemanagement.domain.Designation;
import net.therap.leavemanagement.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

/**
 * @author rumi.dipto
 * @since 11/25/21
 */
@Component
public class DataSetupHelper {

    public void addSessionUserData(User user, ModelMap model) {
        model.addAttribute("isSessionUserHumanResource", isHumanResource(user));
        model.addAttribute("isSessionUserTeamlead", isTeamlead(user));
        model.addAttribute("isSessionUserDeveloper", isDeveloper(user));
        model.addAttribute("isSessionUserTester", isTester(user));
    }

    private boolean isHumanResource(User user) {
        return user.getDesignation().equals(Designation.HUMAN_RESOURCE);
    }

    private boolean isTeamlead(User user) {
        return user.getDesignation().equals(Designation.TEAMLEAD);
    }

    private boolean isDeveloper(User user) {
        return user.getDesignation().equals(Designation.DEVELOPER);
    }

    private boolean isTester(User user) {
        return user.getDesignation().equals(Designation.TESTER);
    }
}
