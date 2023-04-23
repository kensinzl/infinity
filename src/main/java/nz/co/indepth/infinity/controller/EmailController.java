package nz.co.indepth.infinity.controller;

import nz.co.indepth.infinity.po.EmailPO;
import nz.co.indepth.infinity.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<List<EmailPO>> createMovie(@RequestBody List<EmailPO> emailPOs) {
        List<EmailPO> createdEmailPOs = emailService.createEmail (emailPOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmailPOs);
    }

    /**
     * @RequestParam and @PathVariable
     * https://www.baeldung.com/spring-request-param
     *
     * 1. @RequestParam:
     *      email?address=value, the default required attribute value is true.
     *          true  -> User have to assign the value.
     *          false -> There is no need to assign address value and then the address value would be null.
     *
     * 2. @PathVariable:
     *      This works like @RequestParam, but the URI is different.
     *          i)  the path is email/addressValue.
     *          ii) GetMapping("/{address}") you need explicitly define the path, no matter required is true or false.
     *
     *  Conclusion:
     *      @RequestParam is to ? param assign or not assign
     *      @PathVariable is for the explicitly define the value
     **/
    @GetMapping("/{address}")
    public ResponseEntity<EmailPO> fetchMovie(@PathVariable(name="address", required = true) String address) {
        EmailPO result = emailService.findEmailUsingFetch (address);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping
    public ResponseEntity<List<EmailPO>> fetchAllEmails() {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.fetchAllEmails ());
    }

    @DeleteMapping
    public String deleteMovie(@RequestBody EmailPO emailPO) {
        return emailService.deleteEmail (emailPO);
    }

//    @DeleteMapping
//    public String deleteMovies() {
//        return emailService.deleteEmails ();
//    }


}
