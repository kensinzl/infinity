package nz.co.indepth.infinity.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Inverse Table(Main)
 */
@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "employee_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "employee_name")
    private String employeeName;

//    /**
//     * Test case:
//     *  1. Only fetch Employee should not see the emails. If want to see email, it should use employee.getEmails
//     *  2. Detach employee, employee.getEmails will invoke the proxy lost issue
//     *  3. should see the SQL
//     */
//    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Email> emails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }


//    public Set<Email> getEmails() {
//        return emails;
//    }
//
//    public void setEmails(Set<Email> emails) {
//        this.emails = emails;
//    }


}

