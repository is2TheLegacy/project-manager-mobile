/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.services.solicitudes;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.fpuna.is2.proyectos.alpha.business.model.Hito;
import py.fpuna.is2.proyectos.alpha.business.model.SolicitudColaboracion;
import py.fpuna.is2.proyectos.alpha.business.services.AbstractFacade;

/**
 *
 * @author rafae
 */
@Stateless
public class SolicitudColaboracionService extends AbstractFacade<SolicitudColaboracion>{
    
    @PersistenceContext(unitName = "py.fpuna.is2_proyectos-alpha_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public SolicitudColaboracionService() {
        super(SolicitudColaboracion.class);
    }
    
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void create(SolicitudColaboracion entity) {
        if(entity!=null) {
            entity.setFechaSolitud(new Date());
            entity.setEstado(EstadosSolicitudColaboracion.PENDIENTE);
        }
        super.create(entity);
    }
    
    public void edit(UUID id, SolicitudColaboracion entity) { 
        if(id != null && entity != null) {
            entity.setIdSolicitudColaboracion(id);
            super.edit(entity);
        }
    }
    
    public void remove(UUID id) {
        super.remove(super.find(id));
    }
    
    public SolicitudColaboracion find(UUID id) {
        return super.find(id);
    }
    
    public List<SolicitudColaboracion> getAllPendingColaborationRequests(UUID idProyecto) {
        Query q = em.createNamedQuery("SolicitudColaboracion.findByProyecto", SolicitudColaboracion.class);
        q.setParameter("idProyecto", idProyecto);
        q.setParameter("estado", EstadosSolicitudColaboracion.PENDIENTE);
        return q.getResultList();
    }
}