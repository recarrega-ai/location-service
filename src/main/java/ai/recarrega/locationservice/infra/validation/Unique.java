package ai.recarrega.locationservice.infra.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UniqueConstraintValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
	String message() default "This entry already exists";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	Class<?> entity();
	String field();
}
