package net.therap.leavemanagement.controller;

import net.therap.leavemanagement.helper.AuthorizationHelper;
import net.therap.leavemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

import static net.therap.leavemanagement.controller.UserController.USER_COMMAND;

/**
 * @author rumi.dipto
 * @since 11/25/21
 */
@Controller
@RequestMapping("/user")
@SessionAttributes(USER_COMMAND)
public class UserController {

    public static final String USER_COMMAND = "user";
    public static final String USER_LIST = "userList";

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationHelper authorizationHelper;

    @InitBinder(USER_COMMAND)
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);

        binder.setDisallowedFields("id", "activated", "created", "updated", "version");
        binder.setAllowedFields("firstName", "lastName", "username", "password", "designation", "salary");
    }

    @RequestMapping("/teamleadList")
    public String showTeamleadList(HttpSession session, ModelMap model) {
        authorizationHelper.checkHumanResourceAccess(session);

        model.addAttribute(USER_LIST, userService.findAllTeamlead());

        return "user/list";
    }

    @RequestMapping("/developerList")
    public String showDeveloperList(HttpSession session, ModelMap model) {
        authorizationHelper.checkHumanResourceAccess(session);

        model.addAttribute(USER_LIST, userService.findAllDeveloper());

        return "user/list";
    }

    @RequestMapping("/testerList")
    public String showTesterList(HttpSession session, ModelMap model) {
        authorizationHelper.checkHumanResourceAccess(session);

        model.addAttribute(USER_LIST, userService.findAllTester());

        return "user/list";
    }
}
