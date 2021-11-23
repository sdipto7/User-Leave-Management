package net.therap.leavemanagement.domain;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
public enum Designation {

    HumanResource("humanResource"),
    Teamlead("teamlead"),
    Developer("developer"),
    Tester("tester");

    private String naturalName;

    Designation(String naturalName) {
        this.naturalName = naturalName;
    }

    public String getNaturalName() {
        return this.naturalName;
    }
}
