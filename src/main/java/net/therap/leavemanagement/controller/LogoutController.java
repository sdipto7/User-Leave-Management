package net.therap.leavemanagement.controller;

import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author rumi.dipto
 * @since 11/29/21
 */
@Controller
public class LogoutController {

    private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");
        logger.info("[logout] {} logged out successfully", sessionUser.getFullName());

        session.invalidate();
        redirectAttributes.addFlashAttribute("logoutMessage", messageSourceAccessor.getMessage("msg.success.logout"));

        return "redirect:/" + Constant.LOGIN_URL;
    }
}
