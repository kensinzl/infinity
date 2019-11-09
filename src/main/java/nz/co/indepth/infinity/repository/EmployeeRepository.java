package nz.co.indepth.infinity.repository;

import nz.co.indepth.infinity.entity.Employee;
import nz.co.indepth.infinity.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Derived Queries
     * https://thoughts-on-java.org/ultimate-guide-derived-queries-with-spring-data-jpa/
     * perform like this select e from Employee e where e.employeeName = employeeName
     */
    public Optional<Employee> findByEmployeeName(String employeeName);


    @Query("select e from Employee e where e.employeeName like %:employeeName%")
    public List<Employee> fetchEmployeeByName(@Param("employeeName") String employeeName, Pageable pageable);


    /**
     * delete from Employee where name = name
     */
    public void deleteByEmployeeName(String employeeName);

}
