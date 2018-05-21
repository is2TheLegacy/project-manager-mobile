/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.services.notificaciones;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import py.fpuna.is2.proyectos.alpha.business.model.TokenAutorizacion;
import py.fpuna.is2.proyectos.alpha.business.services.security.TokenAutorizacionService;

/**
 *
 * @author rafae
 */
@Singleton
public class NotificationObserver {
    
    @Inject
    private TokenAutorizacionService tokens;
    @Inject
    private Instance<Firebase> firebase;
    
    public void handleNotification(@Observes NotificationEvent event) {
        TokenAutorizacion token = tokens.find(event.getSessionToken());
        FirebaseSendBody sendBody = new FirebaseSendBody();
        sendBody.setNotification(event.getNotification());
        sendBody.setTo(token.getGcmRegistrationId());
        Response resp = firebase.get().send(Firebase.AUTHORIZATION_KEY, sendBody);
        if(resp != null) {
            System.out.println("RESP="+resp.getStatus() + resp.getEntity());
        }
    }
}
