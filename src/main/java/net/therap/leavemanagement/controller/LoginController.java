package net.therap.leavemanagement.controller;

import net.therap.leavemanagement.command.LoginCommand;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.helper.AuthenticationHelper;
import net.therap.leavemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static net.therap.leavemanagement.controller.LoginController.LOGIN_COMMAND;

/**
 * @author rumi.dipto
 * @since 11/24/21
 */
@Controller
@RequestMapping("/login")
@SessionAttributes(LOGIN_COMMAND)
public class LoginController {

    public static final String LOGIN_COMMAND = "loginCommand";

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationHelper authenticationHelper;

    @InitBinder(LOGIN_COMMAND)
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @RequestMapping(method = RequestMethod.GET)
    private String show(ModelMap model) {
        model.addAttribute(LOGIN_COMMAND, new LoginCommand());

        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    private String process(@Valid @ModelAttribute(LOGIN_COMMAND) LoginCommand loginCommand,
                           Errors errors,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            return "login";
        }

        String username = loginCommand.getUsername();
        String password = loginCommand.getPassword();

        User user = userService.findByUsername(username);
        if (user != null && authenticationHelper.authCheck(user, password)) {
            session.setAttribute("SESSION_USER", user);

            return "redirect:/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Enter credential correctly");

            return "redirect:/login";
        }
    }
}
