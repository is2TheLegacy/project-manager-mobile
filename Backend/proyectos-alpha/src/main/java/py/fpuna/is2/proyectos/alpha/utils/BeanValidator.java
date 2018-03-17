package py.fpuna.is2.proyectos.alpha.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import py.fpuna.is2.proyectos.alpha.service.exceptions.BusinessException;

public class BeanValidator {

    @Inject
    Validator validator;

    public <T> T validate(T object) throws BusinessException, IllegalArgumentException {
        if (object == null) {
            throw new IllegalArgumentException("El objeto a validar no puede ser nulo");
        }

        Set<ConstraintViolation<T>> violations = validator.validate(object);

        // Si no existen violaciones se retorna el objeto
        if (violations != null && violations.isEmpty()) {
            return object;
        } else {
            List<String> messages = extractMessagesList(violations);
            throw new BusinessException.ModelValidationException(
                    "Ocurrieron errores de validacion para el objeto " + object.getClass().getSimpleName(), messages);
        }
    }

    public <T> T validate(T object, Class<?>... groups) throws BusinessException, IllegalArgumentException {

        if (object == null) {
            throw new IllegalArgumentException("El objeto a validar no puede ser nulo");
        }

        Set<ConstraintViolation<T>> violations = validator.validate(object, groups);

        // Si no existen violaciones se retorna el objeto
        if (violations != null && violations.isEmpty()) {
            return object;
        } else {
            List<String> messages = extractMessagesList(violations);
            throw new BusinessException.ModelValidationException(
                    "Ocurrieron errores de validacion para el objeto " + object.getClass().getSimpleName(), messages);
        }
    }

    private <T> List<String> extractMessagesList(Set<ConstraintViolation<T>> violations)
            throws IllegalArgumentException {
        if (violations == null) {
            throw new IllegalArgumentException("El conjunto de violaciones no puede ser nulo");
        }

        List<String> messages = new ArrayList<>();
        for (ConstraintViolation<T> constraintViolation : violations) {
            messages.add(constraintViolation.getMessage());
        }

        return messages;
    }
}
