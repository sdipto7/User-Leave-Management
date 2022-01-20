package net.therap.leavemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author rumi.dipto
 * @since 11/25/21
 */
@Controller
public class DashboardController {

    public static final String DASHBOARD_PAGE = "/dashboard";

    @RequestMapping("/dashboard")
    private String showDashboard() {
        return DASHBOARD_PAGE;
    }
}
