package nz.co.indepth.infinity.entity;

import javax.persistence.*;

/**
 * Owning Table(Store the foreign keys), Child Table
 */
@Entity
@Table(name = "EMAIL")
public class Email {

    @Id
    @Column(name = "email_id")
    @GeneratedValue
    private Long id;

    @Column(name = "email_address")
    private String emailAddress;

    /**
     * If do not introduce fieldName employeeId here, I have to use email.getEmployee().getId() to get employee id.
     * It would drag down the performance because have to load the employee instance.
     */
    @Column(name = "employee_id")
    private Long employeeId;

    /**
     * Note: @ManyToOne can not be existed lonely.
     *
     * @ManyToOne's
     *  1. fetch mode default value is eager, because just one record row
     *  2. optional default is true, true that means works like left join, false works like inner join.
     *
     * @JoinColumn
     *  1. name                 -> Owing table employee_id column
     *  2. referenceColumnName  -> Inverse table employee_id column
     *
     * Test case:
     *  1. Fetch Email should always gets Employee.
     *      -> Yes, fetch email will also fetch employee. Using findEmailBy.. or JPQL
     *  2. try delete email, to see employee.
     *      -> Delete email will also delete employee because of if add cascade all via using deleteEmailBy..
     *      -> Delete email will not delete employee if using JPQL "delete from email"
     *
     *  Summary for insertable:
     *      1. fieldName employeeId is set value, employee.id also set value, cascade all and make insertable true
     *         This situation will complain the repeated exception
     *
     *      2. fieldName employeeId is 9L, employee.id is 10L.
     *         employee is 10L record would be changed if employee changed the original value.
     *
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "employee_id",
                referencedColumnName = "employee_id",
                unique = true,
                nullable = false,
                insertable = false,
                updatable = false)
    private Employee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
