package net.therap.leavemanagement.domain;

import javax.persistence.*;

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
    private User user;

    @Column(name = "sick_leave_count")
    private int sickLeaveCount;

    @Column(name = "casual_leave_count")
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
