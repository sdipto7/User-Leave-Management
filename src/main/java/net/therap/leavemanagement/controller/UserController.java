package net.therap.leavemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author rumi.dipto
 * @since 11/21/21
 */
@Controller
public class UserController {

    @RequestMapping("/")
    public String show() {
        return "index";
    }
}
