package py.fpuna.is2.proyectos.alpha.service.exceptionmappers;

import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {

	@Override
	public Response toResponse(EJBException e) {
		if (e instanceof EJBTransactionRolledbackException) {
			return ErrorEntity.build(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					"Error durante la confirmación de los datos", e.getCause()
							.toString());
		} else {
			return ErrorEntity.build(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					"Error interno del servidor", e.getMessage());
		}
	}
}
