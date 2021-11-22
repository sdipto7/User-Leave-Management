package net.therap.leavemanagement.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
@Entity
@Table(name = "user_leave_stat")
public class UserLeaveStat extends Persistent {

    private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "{validation.notNull.msg}")
    private User user;

    @Column(name = "sick_leave_count")
    @Max(value = 10, message = "{validation.max.msg}")
    private int sickLeaveCount;

    @Column(name = "casual_leave_count")
    @Max(value = 10, message = "{validation.max.msg}")
    private int casualLeaveCount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSickLeaveCount() {
        return sickLeaveCount;
    }

    public void setSickLeaveCount(int sickLeaveCount) {
        this.sickLeaveCount = sickLeaveCount;
    }

    public int getCasualLeaveCount() {
        return casualLeaveCount;
    }

    public void setCasualLeaveCount(int casualLeaveCount) {
        this.casualLeaveCount = casualLeaveCount;
    }
}
