package net.therap.leavemanagement.controller;

import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.helper.DataSetupHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author rumi.dipto
 * @since 11/25/21
 */
@Controller
public class DashboardController {

    @Autowired
    private DataSetupHelper dataSetupHelper;

    @RequestMapping("/dashboard")
    private String showDashboard(HttpSession session, ModelMap model) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");
        dataSetupHelper.addSessionUserData(sessionUser, model);

        return "dashboard";
    }
}
