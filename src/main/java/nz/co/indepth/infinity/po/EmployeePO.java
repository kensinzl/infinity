package nz.co.indepth.infinity.po;


import java.util.List;
import java.util.Set;

public class EmployeePO {
    private Long id;
    private String employeeName;
    private List<MoviePO> moviePOs;


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

    public List<MoviePO> getMoviePOs() {
        return moviePOs;
    }

    public void setMoviePOs(List<MoviePO> moviePOS) {
        this.moviePOs = moviePOS;
    }
}
