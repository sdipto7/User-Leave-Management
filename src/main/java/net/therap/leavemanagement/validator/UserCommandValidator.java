package net.therap.leavemanagement.validator;

import net.therap.leavemanagement.command.UserCommand;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * @author rumi.dipto
 * @since 11/30/21
 */
@Component
public class UserCommandValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCommand userCommand = (UserCommand) target;
        User user = userCommand.getUser();
        User duplicateUser = userService.findByUsername(user.getUsername());

        if (Objects.nonNull(duplicateUser) && !user.equals(duplicateUser)) {
            errors.rejectValue("user.username", "validation.duplicate.username");
        }
    }
}
