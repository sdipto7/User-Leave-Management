package net.therap.leavemanagement.validator;

import net.therap.leavemanagement.command.UserSaveCommand;
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
public class UserSaveCommandValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserSaveCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserSaveCommand userSaveCommand = (UserSaveCommand) target;
        User user = userSaveCommand.getUser();
        User duplicateUser = userService.findByUsername(user.getUsername());

        if (Objects.nonNull(duplicateUser) && !user.equals(duplicateUser)) {
            errors.rejectValue("user.username", "validation.duplicate.username");
        }
    }
}
