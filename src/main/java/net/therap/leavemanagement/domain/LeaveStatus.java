package net.therap.leavemanagement.domain;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
public enum LeaveStatus {

    APPROVED_BY_TEAM_LEAD("Approved by Team Lead"),
    PENDING_BY_TEAM_LEAD("Pending by Team Lead"),
    DENIED_BY_TEAM_LEAD("Denied by Team Lead"),
    APPROVED_BY_HR_EXECUTIVE("Approved by HR Executive"),
    PENDING_BY_HR_EXECUTIVE("Pending by HR Executive"),
    DENIED_BY_HR_EXECUTIVE("Denied by HR Executive");

    private String naturalName;

    LeaveStatus(String naturalName) {
        this.naturalName = naturalName;
    }

    public String getNaturalName() {
        return this.naturalName;
    }
}
