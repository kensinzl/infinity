package nz.co.indepth.infinity.repository;

import nz.co.indepth.infinity.entity.Email;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    /**
     * Derived Queries
     * Derived Intelligence Hitting Using IntelliJ to find using the field name
     *
     * https://thoughts-on-java.org/ultimate-guide-derived-queries-with-spring-data-jpa/
     * perform like this select e from Email e where e.address = address
     */
    public Optional<Email> findByEmailAddress(String address);


    @Query("select e from Email e where e.emailAddress like %:address%")
    public List<Email> fetchEmailByAddress(@Param("address") String address, Pageable pageable);


    /**
     * delete from Email where address = address
     *
     * Note:
     *  If using derived query, we have to obey the full property name,
     *  otherwise you will get the following issue -> No property address found for type Email!
     *
     *  derived query using field name, and you also can get this from the above exception
     */
    public void deleteByEmailAddress(String address);


    /**
     * https://codar.club/blogs/5cd7f06bec80a.html
     *
     * @Modifying     replace default fetchExecute into executeUpdate
     *
     * @Transactional
     * See SimpleJpaRepository implements JpaRepositoryImplementation extends JpaRepository
     * The readOnly attribute default value is false, so it is for update, insert and delete operation.
     * If you see the SimpleJpaRepository, you will find all fetch methods are readOnly,
     *      because @Transactional(readOnly = true) is located on the class level.
     * Other changing DB operations should be explicitly @Transactional again on the function level.
     * In this instance, deleteAllEmail function should use the non-readOnly default attribute
     *
     * Note: @Modifying is not necessary to be added for the derived method and inherit method like save
     *
     * On the other hand,
     * Think function public List<Email> fetchAllEmails() together
     * Using JPQL to delete Email would not invoke Cascade.all to delete the employee
     * Image it just like you using the SQL to delete the emails on DB side, does the referred employee disappear? No
     */
    @Transactional
    @Modifying
    @Query("delete from Email e where e.employeeId=1")
    public void deleteAllEmail();


    /**
     * Using JPQL to fetch Email still attach the employee.
     *
     * I assume that it is because the @JoinColumn annotation reason.
     *
     */
    @Query("select e from Email e")
    public List<Email> fetchAllEmails();

}
