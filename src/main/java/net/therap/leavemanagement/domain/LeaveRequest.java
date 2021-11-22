package net.therap.leavemanagement.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
@Entity
@Table(name = "leave_request")
public class LeaveRequest extends Persistent {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(message = "{validation.notNull.msg}")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type")
    @NotNull(message = "{validation.notNull.msg}")
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_status")
    @NotNull(message = "{validation.notNull.msg}")
    private LeaveStatus leaveStatus;

    @Size(min = 5, max = 100, message = "{validation.size.msg}")
    @NotNull(message = "{validation.notNull.msg}")
    private String note;

    @Column(name = "start_date")
    @NotNull(message = "{validation.notNull.msg}")
    private Date startDate;

    @Column(name = "end_date")
    @NotNull(message = "{validation.notNull.msg}")
    private Date endDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
