/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.services.security;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author rafae
 */
@Provider
@PreMatching
public class AuthInterceptor implements ContainerRequestFilter {

    private static final Logger LOG = Logger.getLogger(AuthInterceptor.class.getName());
    
    @Inject
    private TokenAutorizacionService tokens;
    
    @Override
    public void filter(ContainerRequestContext requestCtx) throws IOException {
        
        String path = requestCtx.getUriInfo().getPath();
        LOG.log(Level.INFO, "Filtrando path: {0}", path);
 
        //evita metodo OPTIONS
        if ( requestCtx.getRequest().getMethod().equals( "OPTIONS" ) ) {
            requestCtx.abortWith(Response.status(Response.Status.OK).build());
            return;
        }
 
        // Then check is the service key exists and is valid.
        String authToken = requestCtx.getHeaderString(HttpHeaders.AUTHORIZATION);
 
        // For any pther methods besides login, the authToken must be verified
        if ( !path.startsWith( "/sessions/" ) ) {
            if (!tokens.esTokenValido(authToken)) {
                requestCtx.abortWith( Response.status( Response.Status.UNAUTHORIZED ).build() );
                return;
            }
        }
    }
   
}
