package nz.co.indepth.infinity.service;

import nz.co.indepth.infinity.po.EmailPO;
import java.util.Set;

public interface EmailService {
    public EmailPO createEmail(EmailPO po);

    public EmailPO findEmailByAddress(String address);

    public String deleteEmail(EmailPO emailPO);

}
