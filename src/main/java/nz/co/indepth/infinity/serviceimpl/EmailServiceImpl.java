package nz.co.indepth.infinity.serviceimpl;

import nz.co.indepth.infinity.entity.Email;
import nz.co.indepth.infinity.mapper.EmailMapper;
import nz.co.indepth.infinity.po.EmailPO;
import nz.co.indepth.infinity.repository.EmailRepository;
import nz.co.indepth.infinity.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailMapper emailMapper;

    @Autowired
    private EmailRepository emailRepository;

    @Override
    public List<EmailPO> createEmail(List<EmailPO> emailPOs) {
        /**
         * SpringBoot Data repository.save()
         *  1. PK is null, persist
         *  2. PK is not null(even 0), fetch from db then merge
         *  Some people think PK is null or 0 is persist, but on the SpringBoot Data not.
         */
        List<Email> emails = emailMapper.emailPOListToEntityList (emailPOs);
        return emailMapper.emailPoListToEmails (emailRepository.saveAll (emails));
    }

    @Override
    public EmailPO findEmailByAddress(String address) {
        Optional<Email> emailOpt = emailRepository.findByEmailAddress (address);
        return emailOpt.map (email -> emailMapper.emailToPo (email)).orElse (new EmailPO ());
    }


    /**
     * These are several delete methods
     *
     *  1. JpaRepository::deleteAllInBatch
     *      Using JPQL to delete the entity in bunch
     *
     *  2. CrudRepository::delete(entity)
     *      Using Id to fetch it, then em.remove(fetchedEntity)
     *      See the source code, using id to delete
     *
     *  3. CrudRepository::deleteAll(), deleteAll(entities)
     *      Just loop entities for delete(entity)
     *
     *  Note:
     *   If you using deleteAll to delete emails(gmail, qq and yahoo), when loop the first one email to delete.
     *   After delete, it should be invoked to delete the employee because if we added the Cascade.All on the @ManyToOne Email side.
     *   So, the exception will happen.
     *   If the only employee is deleted, how about the remaining qq and yahoo's FK, their reference from employee is gone.
     *   Plus, we also add FK nullable is false, and qq/yahoo's FK is gone and can not become null.
     *   FK from concept can not be null.
     *   In this instance, recommend to use deleteAllInBatch with where condition. That is also using JPQL.
     *   It seems you just using SQL to delete the kids on the DB.
     */
    @Override
    public String deleteEmail(EmailPO emailPO) {
        Email email = emailRepository.findByEmailAddress (emailPO.getEmailAddress ()).orElse (new Email ());
        emailRepository.delete (email);
        return "Successfully deleted. ";
    }

    /**
     *  This one just for test learning.
     *  deleteAll or deleteAll(Iterable) will loop the delete items.
     *
     *  Eg: I have three emails on the DataBase, so you would see three times delete SQL when you executing deleteAll.
     *  delete from email where email_id=?
     *  delete from email where email_id=?
     *  delete from email where email_id=?
     *
     *  In this instance, if you can assume this delete operation would manipulate huge items.
     *  It is better to use @Query("delete from entity from where condition").
     *  This only execute one SQL statement.
     */
    @Deprecated
    public String deleteEmails() {
        //emailRepository.deleteAll ();
        emailRepository.deleteAllEmail();
        return "Successfully deleted. ";
    }

    @Override
    public List<EmailPO> fetchAllEmails() {
        List<Email> emails = emailRepository.fetchAllEmails ();
        return emailMapper.emailPoListToEmails (emails);
    }


}
