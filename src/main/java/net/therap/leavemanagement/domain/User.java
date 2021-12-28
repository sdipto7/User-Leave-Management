package net.therap.leavemanagement.domain;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

import static net.therap.leavemanagement.domain.Designation.*;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
@Entity
@Table(name = "lm_user")
@NamedQueries({
        @NamedQuery(name = "User.findHrExecutive",
                query = "SELECT u FROM User u WHERE u.designation = 'HR_EXECUTIVE'"),

        @NamedQuery(name = "User.findByUsername",
                query = "SELECT u FROM User u WHERE u.username = :username"),

        @NamedQuery(name = "User.findAllTeamLead",
                query = "SELECT u FROM User u WHERE u.designation = 'TEAM_LEAD' ORDER BY u.id ASC"),

        @NamedQuery(name = "User.findAllDeveloper",
                query = "SELECT u FROM User u WHERE u.designation = 'DEVELOPER' ORDER BY u.id ASC"),

        @NamedQuery(name = "User.findAllTester",
                query = "SELECT u FROM User u WHERE u.designation = 'TESTER' ORDER BY u.id ASC"),

        @NamedQuery(name = "User.findAll",
                query = "SELECT u FROM User u ORDER BY u.id ASC"),

        @NamedQuery(name = "User.countTeamLead",
                query = "SELECT COUNT(u) FROM User u WHERE u.designation = 'TEAM_LEAD'"),

        @NamedQuery(name = "User.countDeveloper",
                query = "SELECT COUNT(u) FROM User u WHERE u.designation = 'DEVELOPER'"),

        @NamedQuery(name = "User.countTester",
                query = "SELECT COUNT(u) FROM User u WHERE u.designation = 'TESTER'"),

        @NamedQuery(name = "User.countAll",
                query = "SELECT COUNT(u) FROM User u")
})
public class User extends Persistent {

    private static final long serialVersionUID = 1L;

    @Column(name = "first_name")
    @Size(min = 2, max = 100)
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 2, max = 100)
    @NotNull
    private String lastName;

    @Size(min = 2, max = 100)
    @NotNull
    private String username;

    @Size(min = 5, max = 100)
    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Designation designation;

    @DecimalMin(value = "5000.00")
    @Digits(integer = 10, fraction = 2)
    @NotNull
    private BigDecimal salary;

    private boolean activated;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isHrExecutive() {
        return this.designation.equals(HR_EXECUTIVE);
    }

    public boolean isTeamLead() {
        return this.designation.equals(TEAM_LEAD);
    }

    public boolean isDeveloper() {
        return this.designation.equals(DEVELOPER);
    }

    public boolean isTester() {
        return this.designation.equals(TESTER);
    }
}
