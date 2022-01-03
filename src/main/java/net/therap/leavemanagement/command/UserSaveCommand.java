package net.therap.leavemanagement.command;

import net.therap.leavemanagement.domain.User;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * @author rumi.dipto
 * @since 11/29/21
 */
public class UserSaveCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Valid
    private User user;

    private User teamLead;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(User teamLead) {
        this.teamLead = teamLead;
    }
}
