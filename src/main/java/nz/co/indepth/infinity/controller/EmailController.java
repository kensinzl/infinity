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


@RestController
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    private EmailServiceImpl emailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailController.class);

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
}
