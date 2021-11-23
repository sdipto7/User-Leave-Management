package net.therap.leavemanagement.domain;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
public enum LeaveStatus {

    ApprovedByTeamlead("approvedByTeamlead"),
    PendingByTeamlead("pendingByTeamlead"),
    DeniedByTeamlead("deniedByTeamlead"),
    ApprovedByHumanResource("approvedByHumanResource"),
    PendingByHumanResource("pendingByHumanResource"),
    DeniedByHumanResource("deniedByHumanResource");

    private String naturalName;

    LeaveStatus(String naturalName) {
        this.naturalName = naturalName;
    }

    public String getNaturalName() {
        return this.naturalName;
    }
}
