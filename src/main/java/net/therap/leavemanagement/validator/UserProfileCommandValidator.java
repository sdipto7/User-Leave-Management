package net.therap.leavemanagement.validator;

import net.therap.leavemanagement.command.UserProfileCommand;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.util.HashGenerator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * @author rumi.dipto
 * @since 11/30/21
 */
@Component
public class UserProfileCommandValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserProfileCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserProfileCommand userProfileCommand = (UserProfileCommand) target;

        String currentPassword = userProfileCommand.getCurrentPassword();
        String newPassword = userProfileCommand.getNewPassword();
        String confirmedNewPassword = userProfileCommand.getConfirmedNewPassword();

        if (Objects.nonNull(currentPassword) && Objects.nonNull(newPassword) && Objects.nonNull(confirmedNewPassword)) {
            User user = userProfileCommand.getUser();
            String currentPasswordHashed = HashGenerator.getMd5(currentPassword);

            if (!currentPasswordHashed.equals(user.getPassword())) {
                errors.rejectValue("currentPassword", "validation.noMatch.currentPassword");
            }

            if (!newPassword.equals(confirmedNewPassword)) {
                errors.rejectValue("confirmedNewPassword", "validation.noMatch.confirmedPassword");
            }
        }
    }
}
