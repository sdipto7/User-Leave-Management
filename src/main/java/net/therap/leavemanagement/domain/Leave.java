package net.therap.leavemanagement.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

import static net.therap.leavemanagement.domain.LeaveStatus.*;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
@Entity
@Table(name = "lm_leave_request")
@NamedQueries({
        @NamedQuery(name = "Leave.findAllLeave",
                query = "SELECT l FROM Leave l " +
                        "WHERE l.leaveStatus = 'APPROVED_BY_HR_EXECUTIVE' OR l.leaveStatus = 'DENIED_BY_HR_EXECUTIVE' ORDER BY l.id"),

        @NamedQuery(name = "Leave.findAllPendingLeave",
                query = "SELECT l FROM Leave l " +
                        "WHERE l.leaveStatus = 'PENDING_BY_TEAM_LEAD' OR l.leaveStatus = 'PENDING_BY_HR_EXECUTIVE' ORDER BY l.id"),

        @NamedQuery(name = "Leave.findAllLeaveUnderTeamLead",
                query = "SELECT l FROM Leave l " +
                        "WHERE (l.leaveStatus = 'APPROVED_BY_HR_EXECUTIVE' OR l.leaveStatus = 'DENIED_BY_HR_EXECUTIVE' " +
                        "OR l.leaveStatus = 'DENIED_BY_TEAM_LEAD') AND EXISTS (SELECT 1 FROM UserManagement um WHERE " +
                        "l.user.id = um.user.id AND um.teamLead.id = :teamLeadId) ORDER BY l.id"),

        @NamedQuery(name = "Leave.findAllPendingLeaveUnderTeamLead",
                query = "SELECT l FROM Leave l " +
                        "WHERE (l.leaveStatus = 'PENDING_BY_HR_EXECUTIVE' OR l.leaveStatus = 'PENDING_BY_TEAM_LEAD') AND " +
                        "EXISTS (SELECT 1 FROM UserManagement um WHERE " +
                        "l.user.id = um.user.id AND um.teamLead.id = :teamLeadId) ORDER BY l.id"),

        @NamedQuery(name = "Leave.findUserLeaveList",
                query = "SELECT l FROM Leave l WHERE l.user.id = :id AND " +
                        "(l.leaveStatus = 'APPROVED_BY_HR_EXECUTIVE' OR l.leaveStatus = 'DENIED_BY_HR_EXECUTIVE') ORDER BY l.id"),

        @NamedQuery(name = "Leave.findUserPendingLeaveList",
                query = "SELECT l FROM Leave l " +
                        "WHERE l.user.id = :id " +
                        "AND (l.leaveStatus = 'PENDING_BY_TEAM_LEAD' OR l.leaveStatus = 'PENDING_BY_HR_EXECUTIVE') ORDER BY l.id"),

        @NamedQuery(name = "Leave.countAllLeave",
                query = "SELECT COUNT(l) FROM Leave l " +
                        "WHERE l.leaveStatus = 'APPROVED_BY_HR_EXECUTIVE' OR l.leaveStatus = 'DENIED_BY_HR_EXECUTIVE'"),

        @NamedQuery(name = "Leave.countAllPendingLeave",
                query = "SELECT COUNT(l) FROM Leave l " +
                        "WHERE l.leaveStatus = 'PENDING_BY_TEAM_LEAD' OR l.leaveStatus = 'PENDING_BY_HR_EXECUTIVE'"),

        @NamedQuery(name = "Leave.countAllLeaveUnderTeamLead",
                query = "SELECT COUNT(l) FROM Leave l " +
                        "WHERE (l.leaveStatus = 'APPROVED_BY_HR_EXECUTIVE' OR l.leaveStatus = 'DENIED_BY_HR_EXECUTIVE' " +
                        "OR l.leaveStatus = 'DENIED_BY_TEAM_LEAD') AND EXISTS (SELECT 1 FROM UserManagement um WHERE " +
                        "l.user.id = um.user.id AND um.teamLead.id = :teamLeadId)"),

        @NamedQuery(name = "Leave.countAllPendingLeaveUnderTeamLead",
                query = "SELECT COUNT(l) FROM Leave l " +
                        "WHERE (l.leaveStatus = 'PENDING_BY_HR_EXECUTIVE' OR l.leaveStatus = 'PENDING_BY_TEAM_LEAD') AND " +
                        "EXISTS (SELECT 1 FROM UserManagement um WHERE " +
                        "l.user.id = um.user.id AND um.teamLead.id = :teamLeadId)"),

        @NamedQuery(name = "Leave.countUserLeave",
                query = "SELECT COUNT(l) FROM Leave l " +
                        "WHERE l.user.id = :id AND (l.leaveStatus = 'APPROVED_BY_HR_EXECUTIVE' OR l.leaveStatus = 'DENIED_BY_HR_EXECUTIVE')"),

        @NamedQuery(name = "Leave.countUserPendingLeave",
                query = "SELECT COUNT(l) FROM Leave l " +
                        "WHERE l.user.id = :id AND (l.leaveStatus = 'PENDING_BY_TEAM_LEAD' OR l.leaveStatus = 'PENDING_BY_HR_EXECUTIVE')")
})
public class Leave extends Persistent {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type")
    @NotNull
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_status")
    @NotNull
    private LeaveStatus leaveStatus;

    @Size(min = 2, max = 100)
    @NotNull
    private String note;

    @Column(name = "start_date")
    @NotNull
    private Date startDate;

    @Column(name = "end_date")
    @NotNull
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

    public boolean isPendingByHrExecutive() {
        return this.leaveStatus.equals(PENDING_BY_HR_EXECUTIVE);
    }

    public boolean isApprovedByHrExecutive() {
        return this.leaveStatus.equals(APPROVED_BY_HR_EXECUTIVE);
    }

    public boolean isDeniedByHrExecutive() {
        return this.leaveStatus.equals(DENIED_BY_HR_EXECUTIVE);
    }

    public boolean isPendingByTeamLead() {
        return this.leaveStatus.equals(PENDING_BY_TEAM_LEAD);
    }

    public boolean isDeniedByTeamLead() {
        return this.leaveStatus.equals(DENIED_BY_TEAM_LEAD);
    }
}
