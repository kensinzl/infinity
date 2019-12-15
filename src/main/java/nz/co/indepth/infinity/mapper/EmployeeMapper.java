package nz.co.indepth.infinity.mapper;

import nz.co.indepth.infinity.entity.Employee;
import nz.co.indepth.infinity.entity.Movie;
import nz.co.indepth.infinity.po.EmployeePO;
import nz.co.indepth.infinity.po.MoviePO;
import nz.co.indepth.infinity.repository.EmployeeRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR//,
        //uses = MovieMapper.class
        )
public abstract class EmployeeMapper {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MovieMapper movieMapper;

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

    //@Mapping (target = "movies", source = "moviePOs")
    @Mapping (target = "movies", ignore = true)
    public abstract Employee checkPOIdHasEntity(@MappingTarget Employee employee, EmployeePO employeePO);


    @AfterMapping
    private void projectEmployeeIntoMovie(@MappingTarget Employee employee, EmployeePO employeePO) {
        List<Movie> movies = movieMapper.moviePOListToEntity (employeePO.getMoviePOs ());
        employee.setMovies (movies);
        employee.getMovies ().stream ().forEach (movie -> movie.setEmployee (employee));
    }


    //@Mapping (target = "moviePOs", source = "movies")
    @Mapping (target = "moviePOs", ignore = true)
    public abstract EmployeePO employeeToPo(Employee employee);

    @AfterMapping
    private void projectEmployeePOIntoMoviePO(@MappingTarget EmployeePO employeePO, Employee employee) {
        List<MoviePO> moviePOs = movieMapper.movieListToPo (employee.getMovies ());
        employeePO.setMoviePOs (moviePOs);
        employeePO.getMoviePOs ().stream ().forEach (movie -> movie.setEmployeePO (employeePO));
    }

    public abstract List<EmployeePO> employeeListToPo(List<Employee> employees);
}
