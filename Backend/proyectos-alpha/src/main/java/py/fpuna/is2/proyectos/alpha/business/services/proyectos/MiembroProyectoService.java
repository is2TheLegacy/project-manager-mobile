/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.services.proyectos;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import py.fpuna.is2.proyectos.alpha.business.model.MiembroProyecto;
import py.fpuna.is2.proyectos.alpha.business.services.AbstractFacade;
import py.fpuna.is2.proyectos.alpha.business.services.solicitudes.SolicitudColaboracionService;

/**
 *
 * @author rafae
 */
@Stateless
public class MiembroProyectoService extends AbstractFacade<MiembroProyecto>{
    
    @PersistenceContext(unitName = "py.fpuna.is2_proyectos-alpha_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @EJB
    private SolicitudColaboracionService solicitudesColaboracion;

    public MiembroProyectoService() {
        super(MiembroProyecto.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}