package net.therap.leavemanagement.command;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author rumi.dipto
 * @since 11/24/21
 */
public class LoginCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String username;

    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
