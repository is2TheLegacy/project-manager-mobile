/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.services.notificaciones;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author rafae
 */
@Path("/fcm")
public interface Firebase {
    
    public static final String AUTHORIZATION_KEY = "key=AAAAt8D98Dc:APA91bGdMBQD119Pe2Xl2MyQXgmHpZSnRo9TT50aZJemuPgzSANKTdBsped8lHeyIw5qa6JOV450S7U1o3lecIDUeuYnyG4xVNvT4DpZ_rzEfWAcI0Wcqml6O8xoVymEQnaM63LUbWrk";
    
    @POST
    @Path("/send")
    @Consumes("application/json")
    @Produces("application/json")
    public Response send(@HeaderParam("Authorization") String authorization, FirebaseSendBody body);
}
