package net.therap.leavemanagement.command;

import net.therap.leavemanagement.domain.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author rumi.dipto
 * @since 11/30/21
 */
public class UserProfileCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Valid
    private User user;

    @NotNull
    @Size(min = 5, max = 100)
    private String currentPassword;

    @NotNull
    @Size(min = 5, max = 100)
    private String newPassword;

    @NotNull
    @Size(min = 5, max = 100)
    private String confirmedNewPassword;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmedNewPassword() {
        return confirmedNewPassword;
    }

    public void setConfirmedNewPassword(String confirmedNewPassword) {
        this.confirmedNewPassword = confirmedNewPassword;
    }
}
