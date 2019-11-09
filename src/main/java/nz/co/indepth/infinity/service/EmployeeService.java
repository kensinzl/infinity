package nz.co.indepth.infinity.service;

import nz.co.indepth.infinity.po.EmployeePO;

import java.util.List;

public interface EmployeeService {
    public EmployeePO createEmployee(EmployeePO po);

    public List<EmployeePO> getEmployees();

    public EmployeePO updateEmployee(EmployeePO po);

    public EmployeePO findByEmployeeName(String name);

    public String deleteEmployee(EmployeePO employeePO);
}
