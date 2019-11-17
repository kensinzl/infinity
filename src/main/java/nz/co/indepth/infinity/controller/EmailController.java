package nz.co.indepth.infinity.controller;

import nz.co.indepth.infinity.po.EmailPO;
import nz.co.indepth.infinity.po.EmployeePO;
import nz.co.indepth.infinity.po.MoviePO;
import nz.co.indepth.infinity.serviceimpl.EmailServiceImpl;
import nz.co.indepth.infinity.serviceimpl.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    private EmailServiceImpl emailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailController.class);

    /**
     * @RequestParam and @PathVariable
     * https://www.baeldung.com/spring-request-param
     *
     * 1. @RequestParam:
     *      email?address=value, the default required attribute value is true that means user have to assign the value,
     *      otherwise, required value is false. There is no need to assign address value and then the address value would be null.
     *
     * 2. @PathVariable:
     *      This works like @RequestParam, but the URI is different.
     *          i)  the path is email/addressValue.
     *          ii) GetMapping("/{address}") you need explicitly define the path, when required=true
     *      Of course, required is equal true or false which need you assign the value or not.
     *
     *  Conclusion:
     *      @RequestParam is to ? param assign or not assign
     *      @PathVariable is for the explicitly define the value
     **/
    @GetMapping("/{address}")
    public ResponseEntity<EmailPO> fetchMovie(@PathVariable(name="address") String address) {
        EmailPO result = emailService.findEmailByAddress (address);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<EmailPO> createMovie(@RequestBody EmailPO emailPO) {
        EmailPO createdEmailPO = emailService.createEmail (emailPO);
        LOGGER.debug ("-------- created email: " + createdEmailPO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmailPO);
    }

    @DeleteMapping
    public String deleteMovie(@RequestBody EmailPO emailPO) {
        return emailService.deleteEmail (emailPO);
    }

    @GetMapping
    public ResponseEntity<List<EmailPO>> fetchAllEmails() {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.fetchAllEmails ());
    }
}
