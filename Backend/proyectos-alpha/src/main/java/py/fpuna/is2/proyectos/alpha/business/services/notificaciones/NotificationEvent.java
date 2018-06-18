/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.services.notificaciones;

import java.io.Serializable;
import java.util.UUID;
import py.fpuna.is2.proyectos.alpha.business.model.Usuario;

/**
 *
 * @author rafae
 */
public class NotificationEvent implements Serializable {
    private Usuario usuarioDestino;
    private Notification notification;

    public Usuario getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(Usuario usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
