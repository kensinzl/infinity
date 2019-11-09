package nz.co.indepth.infinity.repository;

import nz.co.indepth.infinity.entity.Email;
import nz.co.indepth.infinity.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    /**
     * Derived Queries
     * https://thoughts-on-java.org/ultimate-guide-derived-queries-with-spring-data-jpa/
     * perform like this select e from Email e where e.address = address
     */
    public Optional<Email> findByEmailAddress(String address);


    @Query("select e from Email e where e.emailAddress like %:address%")
    public List<Email> fetchEmailByAddress(@Param("address") String address, Pageable pageable);


    /**
     * delete from Email where address = address
     *
     * Note: if using derived query, we have to obey the full property name, otherwise you will get the following issue
     *       No property address found for type Email!
     */
    public void deleteByEmailAddress(String address);

}
