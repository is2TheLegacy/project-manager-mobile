/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.service.tareas;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import py.fpuna.is2.proyectos.alpha.business.model.Tarea;
import py.fpuna.is2.proyectos.alpha.service.AbstractFacade;
import py.fpuna.is2.proyectos.alpha.service.security.UserAuthorizator;

/**
 *
 * @author rafae
 */
@Stateless
@Path("tareas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TareaService extends AbstractFacade<Tarea> {

    @PersistenceContext(unitName = "py.fpuna.is2_proyectos-alpha_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Inject
    private UserAuthorizator authorizator;

    public TareaService() {
        super(Tarea.class);
    }

    @POST
    @Override
    public void create(Tarea entity) {
        if(entity!=null) {
            entity.setFechaCreacion(new Date());
            
            if(entity.getEstado()==null) {
                entity.setEstado(EstadosTarea.PENDIENTE);
            }
            
            if(entity.getPrioridad()==null) {
                entity.setPrioridad("NORMAL");
            }
        }
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    public void edit(@PathParam("id") UUID id, Tarea entity) { 
        if(id != null && entity != null) {
            entity.setIdTarea(id);
            super.edit(entity);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") UUID id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    public Tarea find(@PathParam("id") UUID id) {
        return super.find(id);
    }

    @GET
    @Override
    public List<Tarea> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    public List<Tarea> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }    
}