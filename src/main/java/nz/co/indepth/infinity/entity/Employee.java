package nz.co.indepth.infinity.entity;

import javax.persistence.*;
import java.util.List;
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

    /**
     * TODO:
     *  Test: orphan removal setting true, dis-connect the relationship
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movie> movies;

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

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}

