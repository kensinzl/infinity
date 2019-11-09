package nz.co.indepth.infinity.mapper;


import nz.co.indepth.infinity.entity.Email;
import nz.co.indepth.infinity.po.EmailPO;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EmailMapper {
    @Mapping (target = "employeePO", source = "employee")
    public EmailPO emailToPo(Email email);

    @Mapping (target = "employee", source = "employeePO")
    public Email emailPOToEntity(EmailPO emailPO);
}
