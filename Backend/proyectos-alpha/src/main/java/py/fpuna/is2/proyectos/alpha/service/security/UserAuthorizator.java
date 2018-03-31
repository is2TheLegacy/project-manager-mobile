/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.service.security;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.fpuna.is2.proyectos.alpha.business.model.TokenAutorizacion;
import py.fpuna.is2.proyectos.alpha.business.model.Usuario;
import py.fpuna.is2.proyectos.alpha.service.TokenAutorizacionService;
import py.fpuna.is2.proyectos.alpha.service.UsuarioService;
import py.fpuna.is2.proyectos.alpha.service.assertions.ServiceAssertions;
import py.fpuna.is2.proyectos.alpha.service.exceptions.ApplicationException;
import py.fpuna.is2.proyectos.alpha.utils.SecurityException;
import py.fpuna.is2.proyectos.alpha.utils.SecurityUtil;

@Singleton
public class UserAuthorizator {
    
    private static final Logger LOG = Logger.getLogger(UserAuthorizator.class.getName());
    
    @PersistenceContext(unitName = "py.fpuna.is2_proyectos-alpha_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Inject
    private UsuarioService userStorage;

    @Inject
    private TokenAutorizacionService authorizationTokensStorage;

    public LoginEntity login(String username, String password) throws LoginException, ApplicationException {

        try {
            Query q = em.createNamedQuery("Usuario.findByAlias", Usuario.class);
            q.setParameter("alias", username);
            Usuario user = null;
            try {
                user = (Usuario) q.getSingleResult();
            } catch(NoResultException e) {
                //ignore
            }
            
            ServiceAssertions.assertNotNullOrEmpty(user, "El usuario no existe");
            ServiceAssertions.assertTrue("ACTIVO".equalsIgnoreCase(user.getEstado()), "La cuenta está inactivada");

            String valueToCheck = password+user.getPasswordSalt();
            String match = SecurityUtil.digestText(valueToCheck);
            ServiceAssertions.assertTrue(match.equals(user.getPassword()), "Contraseña incorrecta.");
            UUID authToken = UUID.randomUUID();

            TokenAutorizacion token = new TokenAutorizacion(authToken);
            token.setUsuario(user);
            token.setFechaCreacion(new Date());
            
            authorizationTokensStorage.create(token);

            return new LoginEntity(token.getToken(), user);
            
        } catch (SecurityException ex) {
            throw new LoginException(ex.getMessage());
        }
    }

    public boolean isAuthTokenValid(UUID authToken, UUID idUsuario) {
        TokenAutorizacion token
                = authorizationTokensStorage.find(authToken);
        if (token != null) {
            if(token.getUsuario().getIdUsuario().equals(idUsuario)) {
                return !token.getExpirado();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    public void logout(UUID authToken) {
        TokenAutorizacion token
                = authorizationTokensStorage.find(authToken);
        if (token != null) {
            token.setExpirado(true);
            em.merge(token);
        }
    }
}
