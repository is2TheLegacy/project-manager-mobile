/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.services.security;

import java.util.UUID;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import py.fpuna.is2.proyectos.alpha.business.services.exceptions.ApplicationException;

/**
 *
 * @author rafae
 */
@Path("/sessions")
public class LoginService {

    @Inject
    private UserAuthorizator authenticator;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("user") String username, @FormParam("password") String password) throws ApplicationException {
        try {
            LoginEntity loginResult = authenticator.login(username, password);

            CacheControl cc = new CacheControl();
            cc.setNoCache(true);
            cc.setMaxAge(-1);
            cc.setMustRevalidate(true);

            return Response.ok(loginResult).cacheControl(cc).build();
        } catch (LoginException ex) {
            throw new ApplicationException.IllegalArgument(ex.getMessage());
        }
    }

    @DELETE
    @Path("{token}")
    public Response logout(@PathParam("token") UUID token) {
        authenticator.logout(token);
        return Response.ok().build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{token}/registrationid")
    public Response setRegistrationId(@PathParam("token") UUID token, @FormParam("gcm-registration-id") String gcmRegistrationId) {
        authenticator.setRegistrationId(token, gcmRegistrationId);
        return Response.ok().build();
    }  
}
