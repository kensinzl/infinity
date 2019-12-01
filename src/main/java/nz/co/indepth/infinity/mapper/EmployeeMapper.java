package nz.co.indepth.infinity.mapper;

import nz.co.indepth.infinity.entity.Employee;
import nz.co.indepth.infinity.po.EmployeePO;
import nz.co.indepth.infinity.repository.EmployeeRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class EmployeeMapper {

    @Autowired
    private EmailMapper emailMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee employeePOToEntity(EmployeePO employeePO) {
        if(Objects.isNull (employeePO)) {
            return null;
        } else if(Objects.isNull (employeePO.getId ()) || employeePO.getId () == 0) {
            return checkPOIdHasEntity(new Employee (), employeePO);
        } else {
            Optional<Employee> mayEmployee = employeeRepository.findById (employeePO.getId ());
            return checkPOIdHasEntity(mayEmployee.orElse (new Employee ()), employeePO);
        }

    }

    public abstract Employee checkPOIdHasEntity(@MappingTarget Employee employee, EmployeePO employeePO);


    public abstract List<Employee> employeePOListToEntity(List<EmployeePO> employeePOs);

//    @AfterMapping
//    protected void addEmails(EmployeePO dto, @MappingTarget Employee target) {
//        Set<Email> emailsSet = emailMapper.emailPOSetToEntity (dto.getEmails ());
//        emailsSet.stream ().forEach (email -> email.setEmployee (target));
//        target.setEmails (emailsSet);
//    }



    //@Mapping (target = "emails", ignore = true)
    public abstract EmployeePO employeeToPo(Employee employee);

    public abstract List<EmployeePO> employeeListToPo(List<Employee> employees);

//    @AfterMapping
//    protected void addEmailPOs(Employee entity, @MappingTarget EmployeePO target) {
//        Set<EmailPO> emailPOsSet = emailMapper.emailSetToPo (entity.getEmails ());
//        emailPOsSet.stream ().forEach (emailPO -> emailPO.setEmployeePO (target));
//        target.setEmails (emailPOsSet);
//    }

}
