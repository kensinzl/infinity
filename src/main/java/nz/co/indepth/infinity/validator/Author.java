package nz.co.indepth.infinity.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Target (value = {ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
@Constraint (validatedBy = AuthorValidator.class)
@Documented
public @interface Author {
    String message() default "Author is not allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
