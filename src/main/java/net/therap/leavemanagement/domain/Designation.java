package net.therap.leavemanagement.domain;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
public enum Designation {

    HUMAN_RESOURCE("humanResourse"),
    TEAMLEAD("teamlead"),
    DEVELOPER("developer"),
    TESTER("tester");

    private String naturalName;

    Designation(String naturalName) {
        this.naturalName = naturalName;
    }

    public String getNaturalName() {
        return this.naturalName;
    }
}
