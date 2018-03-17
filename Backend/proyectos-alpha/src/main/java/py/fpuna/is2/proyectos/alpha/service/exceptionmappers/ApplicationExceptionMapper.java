package py.fpuna.is2.proyectos.alpha.service.exceptionmappers;

import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import py.fpuna.is2.proyectos.alpha.service.exceptions.ApplicationException;
import py.fpuna.is2.proyectos.alpha.service.exceptions.BusinessException;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {

    @SuppressWarnings("deprecation")
    @Override
    public Response toResponse(ApplicationException e) {
        if (e instanceof ApplicationException.IllegalArgument) {
            return ErrorEntity.build(400, "Argumento inválido.", e.getMessage());
        } else if (e instanceof ApplicationException.NotFound) {
            return ErrorEntity.build(404, "No encontrado.", e.getMessage());
        } else if (e instanceof ApplicationException.AbortException
                && e.getCause() != null && e.getCause() instanceof BusinessException.ModelValidationException) {

            return ErrorEntity.build(406, "Transación Abortada", ((BusinessException.ModelValidationException) e.getCause()).getViolationMessages());

        } else if (e instanceof ApplicationException.AbortException
                && e.getCause() != null && e.getCause() instanceof BusinessException.RuleValidationException) {

            return ErrorEntity.build(406, "Transación Abortada", e.getCause().getMessage());
        } else {

            if (e != null && e.getCause() != null
                    && (e.getCause() instanceof PersistenceException
                    || e instanceof ApplicationException.SQLException
                    || e.getCause() instanceof java.sql.SQLException)) {

                return ErrorEntity.build(500, "Error durante la operación con la base de datos.", e.getCause().getMessage());

            } else {
                return ErrorEntity.build(500, "Error interno del servidor.", e.getMessage());
            }
        }
    }

}
