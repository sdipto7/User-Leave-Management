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
        @NamedQuery(name = "UserManagement.findTeamLead",
                query = "SELECT um.supervisor FROM UserManagement um WHERE um.user.id = :id"),

        @NamedQuery(name = "UserManagement.findAllDeveloperUnderTeamLead",
                query = "SELECT um.user FROM UserManagement um " +
                        "WHERE um.supervisor.id = :id AND um.user.designation = 'DEVELOPER'"),

        @NamedQuery(name = "UserManagement.findAllTesterUnderTeamLead",
                query = "SELECT um.user FROM UserManagement um " +
                        "WHERE um.supervisor.id = :id AND um.user.designation = 'TESTER'")
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
    @JoinColumn(name = "supervisor_id")
    @NotNull
    private User supervisor;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }
}
