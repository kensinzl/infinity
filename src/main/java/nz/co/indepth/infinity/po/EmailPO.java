package nz.co.indepth.infinity.po;



public class EmailPO {
    private Long id;
    private String emailAddress;
    private Long employeeId;
    private EmployeePO employeePO;

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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeePO getEmployeePO() {
        return employeePO;
    }

    public void setEmployeePO(EmployeePO employeePO) {
        this.employeePO = employeePO;
    }

    @Override
    public String toString() {
        return "EmailPO{" +
                "id=" + id +
                ", emailAddress='" + emailAddress + '\'' +
                ", employeeId=" + employeeId +
                ", employeePO=" + employeePO +
                '}';
    }
}
