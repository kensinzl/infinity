package nz.co.indepth.infinity.mapper;


import nz.co.indepth.infinity.entity.Email;
import nz.co.indepth.infinity.po.EmailPO;
import nz.co.indepth.infinity.repository.EmailRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class EmailMapper {

    @Autowired
    private EmailRepository emailRepository;

    /**
     *  -> if PO from UI is full payload, it is not need to fetch the entity from DB
     *  -> if PO from UI is partial, you have to fetch the latest entity from DB
     *
     *  Employee(id, name, createDate, modifyDate)
     *  Just id and name shown on the UI, createDate and modifyDate not shown on UI. They are null.
     *  If just directly transfer PO into Entity,
     *  this entity with null value which would also overwritten the createDate, modifyDate which may have the value.
     */
    public Email emailPOToEntity(EmailPO emailPO) {
        if(Objects.isNull (emailPO)) {
            return null;
        } else if (Objects.isNull (emailPO.getId ()) || emailPO.getId () == 0) {
            return checkPOIdHasEntity(new Email (), emailPO);
        } else {
            Optional<Email> mayEmail = emailRepository.findById (emailPO.getId ());
            if (mayEmail.isPresent ()) {
                return checkPOIdHasEntity(mayEmail.get (), emailPO);
            } else {
                return new Email ();
            }
        }
    }

    @Mapping (target = "employee", source = "employeePO")
    public abstract Email checkPOIdHasEntity(@MappingTarget Email email, EmailPO emailPO);

    public abstract List<Email> emailPOListToEntityList(List<EmailPO> emailPOs);

    @Mapping (target = "employeePO", source = "employee")
    public abstract EmailPO emailToPo(Email email);

    public abstract List<EmailPO> emailPoListToEmails(List<Email> emails);











// TODO: I do not why @ObjectFactory can not work among springboot.
//    @ObjectFactory
//    private Email checkPOIdHasEntity(EmailPO emailPO) {
//        if(Objects.isNull (emailPO) || Objects.isNull (emailPO.getId ())) {
//            System.out.println ("--------- The po must has the ID value. ");
//            return new Email ();
//        } else {
//            Optional<Email> mayEmail = emailRepository.findById (emailPO.getId ());
//            System.out.println (">>>>>>>>>>>>>>> " + mayEmail.get ());
//            return mayEmail.orElse (new Email ());
//        }
//    }
}
