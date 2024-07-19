package DTO;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface NotNull {
    String message() default "This value cannot be null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
