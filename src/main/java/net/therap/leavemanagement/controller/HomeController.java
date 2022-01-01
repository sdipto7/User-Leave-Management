package net.therap.leavemanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author rumi.dipto
 * @since 11/21/21
 */
@Controller
public class HomeController {

    public static final String INDEX_PAGE = "/index";

    public static final String SUCCESS_PAGE = "/success";

    public static final String ERROR_PAGE = "/error";

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    public String showStartPage() {
        logger.info("[home_controller] Application started successfully! ");

        return INDEX_PAGE;
    }

    @RequestMapping("/success")
    public String showSuccessPage(@ModelAttribute("doneMessage") String msg, ModelMap modelMap) {
        modelMap.addAttribute("message", msg);

        return SUCCESS_PAGE;
    }

    @RequestMapping(value = "/error")
    private String showError(HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("message", request.getAttribute("errorMessage"));

        return ERROR_PAGE;
    }
}
