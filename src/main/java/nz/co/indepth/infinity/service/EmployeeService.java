package nz.co.indepth.infinity.service;

import nz.co.indepth.infinity.entity.Employee;
import nz.co.indepth.infinity.mapper.EmployeeMapper;
import nz.co.indepth.infinity.po.EmployeePO;
import nz.co.indepth.infinity.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeePO createEmployee(EmployeePO po) {
        Employee employee = employeeMapper.employeePOToEntity (po);
        return employeeMapper.employeeToPo (employeeRepository.save (employee));
    }

    public EmployeePO updateEmployee(EmployeePO po) {
        Employee employee = employeeMapper.employeePOToEntity (po);
        if(Objects.nonNull (employee.getId ()) && employee.getId () > 0) {
            boolean exist = employeeRepository.findById (employee.getId ()).isPresent ();
            if(!exist) {
                // TODO: throw 400 to client
            }
        }
        return employeeMapper.employeeToPo (employeeRepository.save (employee));
    }

    public EmployeePO findByEmployeeName(String name) {
        Optional<Employee> employee = employeeRepository.findByEmployeeName (name);
        return employeeMapper.employeeToPo (employee.get ());

    }

    public String deleteEmployee(EmployeePO employeePO) {
        /**
         * No need to pass PO, because the source code of delete is still use ID.
         * In this instance, just use MovieId is enough
         *
         * NOTICE: Here is a negative example.
         * In reality, you should fetch PO.Id to get entity then em.remove entity. See the source code
         *
         * Although, in the common DB, we can not delete the main table if kid table has the related value
         * because if delete the main table first, then the FK of kid tables's value would be null. Invalid
         *
         * But in the Spring Data, it seems fine, only using Cascade.ALL
         * I think it combine the amazing orphan, but I already for the optional and orphan for the Spring Boot
         */
        Employee employee = employeeMapper.employeePOToEntity (employeePO);
        employeeRepository.delete (employee);
        return "Successfully deleted. ";
    }

    public List<EmployeePO> getEmployees() {
        /**
         * sort for all records, then only pick first page and first three rows
         */
        Pageable sortedByEmployeeNameDesc =
                PageRequest.of(0, 3, Sort.by("employeeName").descending());
        List<Employee> employees = employeeRepository.findAll (sortedByEmployeeNameDesc).getContent ();
        return employeeMapper.employeeListToPo (employees);
    }

}
