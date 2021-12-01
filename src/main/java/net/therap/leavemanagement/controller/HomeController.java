package net.therap.leavemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author rumi.dipto
 * @since 11/21/21
 */
@Controller
public class HomeController {

    public static final String INDEX_PAGE = "/index";
    public static final String SUCCESS_PAGE = "/success";

    @RequestMapping("/")
    public String showStartPage() {
        return INDEX_PAGE;
    }

    @RequestMapping("/success")
    public String showSuccessPage(@ModelAttribute("doneMessage") String msg, ModelMap model) {
        model.addAttribute("message", msg);

        return SUCCESS_PAGE;
    }
}
