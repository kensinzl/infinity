package nz.co.indepth.infinity.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

@Component
public class BeanValidators {

    @Autowired
    private Validator validator;

    public Map<String, String> validateBeanMayWithException(Object object, Class<?>... groups) {
        Map<String, String> errorMessages = new HashMap();
        //Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate (object, groups);
        if(!constraintViolations.isEmpty ()) {
            constraintViolations.stream ().forEach (violation -> {
                errorMessages.put (violation.getPropertyPath ().toString (), violation.getMessage ());
            });

        }
        return errorMessages;
    }

}
