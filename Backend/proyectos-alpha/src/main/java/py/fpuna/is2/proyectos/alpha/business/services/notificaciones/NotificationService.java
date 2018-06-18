/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.services.notificaciones;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import py.fpuna.is2.proyectos.alpha.business.model.TokenAutorizacion;
import py.fpuna.is2.proyectos.alpha.business.services.security.TokenAutorizacionService;

/**
 *
 * @author rafae
 */
@Singleton
@Lock(LockType.READ)
public class NotificationService {

    private static final Logger LOG = Logger.getLogger(NotificationService.class.getName());
    
    @Inject
    private TokenAutorizacionService tokens;
    @Inject
    private Instance<Firebase> firebase;
    
    @Asynchronous
    public void handleNotification(NotificationEvent event) {
        List<TokenAutorizacion> sessions = tokens.findActiveSessions(event.getUsuarioDestino());
        final FirebaseSendBody sendBody = new FirebaseSendBody();
        sendBody.setNotification(event.getNotification());
        
        sessions.forEach(s -> {
            if(s!=null && s.getGcmRegistrationId() != null) {
                sendBody.setTo(s.getGcmRegistrationId());
                Response resp = firebase.get().send(Firebase.AUTHORIZATION_KEY, sendBody);
                if(resp != null) {
                    LOG.log(Level.INFO, "Push notification -> HTTP Status Code: {0} Body: {1}", new Object[]{resp.getStatus(), resp.getEntity()!=null?resp.getEntity():""});
                }
            }
        });
    }
}
