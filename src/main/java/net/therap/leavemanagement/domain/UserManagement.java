package net.therap.leavemanagement.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
@Entity
@Table(name = "lm_user_management")
@NamedQueries({
        @NamedQuery(name = "UserManagement.findUserManagementByUserId",
                query = "SELECT um FROM UserManagement um WHERE um.user.id = :userId"),

        @NamedQuery(name = "UserManagement.findAllUserManagementByTeamLeadId",
                query = "SELECT um FROM UserManagement um WHERE um.teamLead.id = :teamLeadId"),

        @NamedQuery(name = "UserManagement.findTeamLead",
                query = "SELECT um.teamLead FROM UserManagement um WHERE um.user.id = :id"),

        @NamedQuery(name = "UserManagement.findAllDeveloperUnderTeamLead",
                query = "SELECT um.user FROM UserManagement um " +
                        "WHERE um.teamLead.id = :id AND um.user.designation = 'DEVELOPER' ORDER BY um.id"),

        @NamedQuery(name = "UserManagement.findAllTesterUnderTeamLead",
                query = "SELECT um.user FROM UserManagement um " +
                        "WHERE um.teamLead.id = :id AND um.user.designation = 'TESTER' ORDER BY um.id"),

        @NamedQuery(name = "UserManagement.countDeveloperUnderTeamLead",
                query = "SELECT COUNT(um) FROM UserManagement um WHERE um.teamLead.id = :id AND um.user.designation = 'DEVELOPER'"),

        @NamedQuery(name = "UserManagement.countTesterUnderTeamLead",
                query = "SELECT COUNT(um) FROM UserManagement um WHERE um.teamLead.id = :id AND um.user.designation = 'TESTER'")
})
public class UserManagement extends Persistent {

    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "team_lead_id")
    @NotNull
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
