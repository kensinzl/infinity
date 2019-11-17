package nz.co.indepth.infinity.serviceimpl;

import nz.co.indepth.infinity.entity.Employee;
import nz.co.indepth.infinity.entity.Movie;
import nz.co.indepth.infinity.mapper.EmployeeMapper;
import nz.co.indepth.infinity.mapper.MovieMapper;
import nz.co.indepth.infinity.po.EmployeePO;
import nz.co.indepth.infinity.po.MoviePO;
import nz.co.indepth.infinity.repository.EmployeeRepository;
import nz.co.indepth.infinity.repository.MovieRepository;
import nz.co.indepth.infinity.service.EmployeeService;
import nz.co.indepth.infinity.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeePO createEmployee(EmployeePO po) {
        Employee employee = employeeMapper.employeePOToEntity (po);
        return employeeMapper.employeeToPo (employeeRepository.save (employee));
    }

    @Override
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

    @Override
    public EmployeePO findByEmployeeName(String name) {
        Optional<Employee> employee = employeeRepository.findByEmployeeName (name);
        return employeeMapper.employeeToPo (employee.get ());

    }

    @Override
    public String deleteEmployee(EmployeePO employeePO) {
        /**
         * No need to pass PO, because the source code of delete is still use ID.
         * In this instance, just use MovieId is enough
         */
        Employee employee = employeeMapper.employeePOToEntity (employeePO);
        employeeRepository.delete (employee);
        return "Successfully deleted. ";
    }

    @Override
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
