package net.therap.leavemanagement.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
@Entity
@Table(name = "user_management")
public class UserManagement extends Persistent {

    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(message = "{validation.notNull.msg}")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id")
    @NotNull(message = "{validation.notNull.msg}")
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
