package net.therap.leavemanagement.domain;

/**
 * @author rumi.dipto
 * @since 12/6/21
 */
public enum Day {

    THU("Thursday"),
    FRI("Friday"),
    SAT("Saturday"),
    SUN("Sunday"),
    MON("Monday"),
    WED("Wednesday"),
    TUE("Tuesday");

    private String naturalName;

    Day(String naturalName) {
        this.naturalName = naturalName;
    }

    public String getNaturalName() {
        return this.naturalName;
    }
}
