package nz.co.indepth.infinity.service;

import nz.co.indepth.infinity.po.EmailPO;

import java.util.List;
import java.util.Set;

public interface EmailService {
    public List<EmailPO> createEmail(List<EmailPO> emailPOs);

    public EmailPO findEmailByAddress(String address);

    public String deleteEmail(EmailPO emailPO);

    public List<EmailPO> fetchAllEmails();

}
