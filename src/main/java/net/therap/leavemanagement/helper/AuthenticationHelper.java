package net.therap.leavemanagement.helper;

import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.util.HashGenerator;
import org.springframework.stereotype.Component;

/**
 * @author rumi.dipto
 * @since 11/24/21
 */
@Component
public class AuthenticationHelper {

    public Boolean authCheck(User user, String password) {
        String hashedPassword = HashGenerator.getMd5(password);

        return hashedPassword.equals(user.getPassword());
    }
}
