package net.therap.leavemanagement.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
@Entity
@Table(name = "user")
public class User extends Persistent {

    private static final long serialVersionUID = 1L;

    @Column(name = "first_name")
    @Size(min = 2, max = 100, message = "{validation.size.msg}")
    @NotNull(message = "{validation.notNull.msg}")
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 2, max = 100, message = "{validation.size.msg}")
    @NotNull(message = "{validation.notNull.msg}")
    private String lastName;

    @Size(min = 2, max = 100, message = "{validation.size.msg}")
    @NotNull(message = "{validation.notNull.msg}")
    private String username;

    @Size(min = 5, max = 255, message = "{validation.size.msg}")
    @NotNull(message = "{validation.notNull.msg}")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{validation.notNull.select.msg}")
    private Designation designation;

    @NotNull(message = "{validation.notNull.msg}")
    private BigDecimal salary;

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
}
