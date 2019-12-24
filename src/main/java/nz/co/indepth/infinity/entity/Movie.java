package nz.co.indepth.infinity.entity;

import javax.persistence.*;
import java.math.BigDecimal;

// TODO: search does need implements Serializable
@Entity
@Table(name = "MOVIE")
public class Movie {
    @Id
    @GeneratedValue
    @Column(name = "movie_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "movie_price")
    private BigDecimal price;

    @Column(name = "author")
    private String author;

    /**
     * Fetch Only.
     * If do not explicitly define insetable and updatable, you would cause repeated issue.
     * Employee:employee_id and employee_id
     */
    @Column(name = "employee_id", insertable = false, updatable = false)
    private Long employeeId;

    /**
     * Only for fetching
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id",
            insertable = true, updatable = false, nullable = false)
    private Employee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
