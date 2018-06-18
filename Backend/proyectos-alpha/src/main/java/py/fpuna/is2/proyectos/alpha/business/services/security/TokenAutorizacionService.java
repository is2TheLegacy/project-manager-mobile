/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.services.security;

import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.PathParam;
import py.fpuna.is2.proyectos.alpha.business.model.Hito;
import py.fpuna.is2.proyectos.alpha.business.model.TokenAutorizacion;
import py.fpuna.is2.proyectos.alpha.business.model.Usuario;
import py.fpuna.is2.proyectos.alpha.business.services.AbstractFacade;

/**
 *
 * @author rafae
 */
@Stateless
public class TokenAutorizacionService extends AbstractFacade<TokenAutorizacion> {

    @PersistenceContext(unitName = "py.fpuna.is2_proyectos-alpha_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public TokenAutorizacionService() {
        super(TokenAutorizacion.class);
    }

    @Override
    public void create(TokenAutorizacion entity) {
        super.create(entity);
    }
    
    public void edit(@PathParam("id") UUID id, TokenAutorizacion entity) {
        super.edit(entity);
    }
    
    public void remove(@PathParam("id") UUID id) {
        super.remove(super.find(id));
    }

    public TokenAutorizacion find(@PathParam("id") UUID id) {
        return super.find(id);
    }

    public List<TokenAutorizacion> findAll() {
        return super.findAll();
    }

    public List<TokenAutorizacion> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<TokenAutorizacion> findActiveSessions(Usuario user) {
        Query q = em.createNamedQuery("TokenAutorizacion.findActiveSessionsOfUser", TokenAutorizacion.class);
        q.setParameter("idUsuario", user.getIdUsuario());
        return q.getResultList();
    }

    public boolean esTokenValido(String authToken) {
        try {
            UUID t = UUID.fromString(authToken);
            Query q = em.createNamedQuery("TokenAutorizacion.findActiveToken", TokenAutorizacion.class);
            q.setParameter("token", t);
            TokenAutorizacion token = (TokenAutorizacion)q.getSingleResult();
            return token != null;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }
}
