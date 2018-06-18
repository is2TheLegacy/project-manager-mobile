/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.services.notificaciones;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 *
 * @author rafae
 */
public class FirebaseClientProducer {
    
    private static final ResteasyClient CLIENT = new ResteasyClientBuilder()
        .connectionPoolSize(10)
        .maxPooledPerRoute(5)
        .build();
    
    @Produces
    public Firebase createFirebaseClient() {
        ResteasyWebTarget target = CLIENT.target("https://fcm.googleapis.com");
        return target.proxy(Firebase.class);
    }
}
