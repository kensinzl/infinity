package nz.co.indepth.infinity.po;



public class EmployeePO {
    private Long id;
    private String employeeName;


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

    @Override
    public String toString() {
        return "EmployeePO { " +
                "id=" + id +
                ", employeeName='" + employeeName + '}';
    }
}
