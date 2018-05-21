package py.fpuna.is2.proyectos.alpha.business.services.exceptionmappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class RuntimeExceptionMapper implements
        ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException e) {
        if (e instanceof IllegalStateException) {
            return ErrorEntity.build(500, "Error interno del servidor.", e.getMessage());
        } else {
            return ErrorEntity.build(500, "Error interno del servidor.", e.getMessage());
        }
    }

}
