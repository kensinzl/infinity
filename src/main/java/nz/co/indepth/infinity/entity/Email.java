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
     * @ManyToOne's
     *  1. fetch mode default value is eager, because just one record row
     *  2. optional default is true, true that means works like left join, false works like inner join.
     *
     * Test case:
     *  1. Fetch Email should always gets Employee.
     *      -> Yes, fetch email will also fetch employee. Using findEmailBy.. or JPQL
     *  2. try delete email, to see employee.
     *      -> Delete email will also delete employee because of cascade all if using deleteEmailBy..
     *      -> Delete email will not delete employee if using JPQL "delete from email"
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
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
}
