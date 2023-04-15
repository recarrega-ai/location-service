package ai.recarrega.locationservice.infra.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueConstraintValidator implements ConstraintValidator<Unique, Object> {
	private final EntityManager entityManager;
	private Class<?> entity;
	private String field;
	private String entityName;

	@Autowired
	public UniqueConstraintValidator(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void initialize(Unique constraintAnnotation) {
		entity = constraintAnnotation.entity();
		field = constraintAnnotation.field();
		String[] fullyQualifiedNameSplit = entity.getName().split("\\.");
		entityName = fullyQualifiedNameSplit[fullyQualifiedNameSplit.length-1];
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
		String jpql = "SELECT e FROM " + entity.getName() + " e WHERE e." + field + " = :value";
		List<?> resultList = entityManager.createQuery(jpql)
			.setParameter("value", value)
			.getResultList();
		
        String message = String.format("%s with %s equal to %s already exists.", entityName, field, value);
        Assert.isTrue(resultList.size() < 1, message);
		return true;
	}
}
