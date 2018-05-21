package py.fpuna.is2.proyectos.alpha.business.services.exceptionmappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import py.fpuna.is2.proyectos.alpha.business.services.exceptions.BusinessException;

@Provider
public class BusinessExceptionMapper implements
        ExceptionMapper<BusinessException> {

    @Override
    public Response toResponse(BusinessException e) {
        if (e instanceof BusinessException.ModelValidationException) {

            return ErrorEntity.build(Status.NOT_ACCEPTABLE.getStatusCode(), e
                    .getMessage(),
                    ((BusinessException.ModelValidationException) e)
                            .getViolationMessages());

        } else {

            return ErrorEntity.build(Status.NOT_ACCEPTABLE.getStatusCode(),
                    "Regla de negocio violada", e.getMessage());

        }
    }

}
