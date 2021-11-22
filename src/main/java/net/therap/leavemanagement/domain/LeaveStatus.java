package net.therap.leavemanagement.domain;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
public enum LeaveStatus {

    APPROVED_BY_TEAMLEAD("approvedByTeamlead"),
    PENDING_BY_TEAMLEAD("pendingByTeamlead"),
    DENIED_BY_TEAMLEAD("deniedByTeamlead"),
    APPROVED_BY_HUMAN_RESOURCE("approvedByHumanResource"),
    PENDING_BY_HUMAN_RESOURCE("pendingByHumanResource"),
    DENIED_BY_HUMAN_RESOURCE("deniedByHumanResource");

    private String naturalName;

    LeaveStatus(String naturalName) {
        this.naturalName = naturalName;
    }

    public String getNaturalName() {
        return this.naturalName;
    }
}
