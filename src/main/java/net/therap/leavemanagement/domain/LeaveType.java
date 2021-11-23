package net.therap.leavemanagement.domain;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
public enum LeaveType {

    Sick("sick"),
    Casual("casual");

    private String naturalName;

    LeaveType(String naturalName) {
        this.naturalName = naturalName;
    }

    public String getNaturalName() {
        return this.naturalName;
    }
}
