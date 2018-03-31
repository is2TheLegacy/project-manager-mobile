/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.service.security;

import java.util.UUID;
import py.fpuna.is2.proyectos.alpha.business.model.Usuario;

/**
 *
 * @author rafae
 */
public class LoginEntity {

    private UUID authToken;
    private Usuario user;

    LoginEntity(UUID token, Usuario user) {
        this.authToken = token;
        this.user = user;
    }

    public UUID getAuthToken() {
        return authToken;
    }

    public void setAuthToken(UUID authToken) {
        this.authToken = authToken;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
