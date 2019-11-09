package nz.co.indepth.infinity.serviceimpl;

import nz.co.indepth.infinity.entity.Email;
import nz.co.indepth.infinity.entity.Employee;
import nz.co.indepth.infinity.mapper.EmailMapper;
import nz.co.indepth.infinity.mapper.EmployeeMapper;
import nz.co.indepth.infinity.po.EmailPO;
import nz.co.indepth.infinity.po.EmployeePO;
import nz.co.indepth.infinity.repository.EmailRepository;
import nz.co.indepth.infinity.repository.EmployeeRepository;
import nz.co.indepth.infinity.service.EmailService;
import nz.co.indepth.infinity.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailMapper emailMapper;

    @Autowired
    private EmailRepository emailRepository;

    @Override
    public EmailPO createEmail(EmailPO po) {
        Email email = emailMapper.emailPOToEntity (po);
        /**
         * SpringBoot Data repository.save()
         *  1. PK is null, persist
         *  2. PK is not null(even 0), fetch from db then merge
         *  Some people think PK is null or 0 is persist, but on the SpringBoot Data not.
         */
        return emailMapper.emailToPo (emailRepository.save (email));
    }

    @Override
    public EmailPO findEmailByAddress(String address) {
        Optional<Email> email = emailRepository.findByEmailAddress (address);
        return emailMapper.emailToPo (email.get ());

    }

    @Override
    public String deleteEmail(EmailPO emailPO) {
        /**
         * No need to pass PO, because the source code of delete is still use ID.
         * In this instance, just use MovieId is enough
         */
        Email email = emailMapper.emailPOToEntity (emailPO);
        emailRepository.delete (email);
        return "Successfully deleted. ";
    }
}
