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
     * @ManyToOne's fetch mode default is eager
     *              optional true non-null relationship must not exist
     *
     * TODO: Test case:
     *  1. Fetch Email should always gets Employee
     *  2. try delte email, to see employee and vise versa
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
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
