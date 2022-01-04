package net.therap.leavemanagement.formatter;

import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author rumi.dipto
 * @since 11/29/21
 */
@Component
public class UserFormatter implements Formatter<User> {

    @Autowired
    private UserService userService;

    @Override
    public User parse(String idText, Locale locale) throws ParseException {
        return userService.find(Integer.parseInt(idText));
    }

    @Override
    public String print(User object, Locale locale) {
        return null;
    }
}
