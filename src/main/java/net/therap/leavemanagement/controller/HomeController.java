package net.therap.leavemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author rumi.dipto
 * @since 11/21/21
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String showStartPage() {
        return "index";
    }

    @RequestMapping("/success")
    public String showSuccessPage() {
        return "success";
    }
}
