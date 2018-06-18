/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.services.proyectos;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import py.fpuna.is2.proyectos.alpha.business.model.Hito;
import py.fpuna.is2.proyectos.alpha.business.model.MiembroProyecto;
import py.fpuna.is2.proyectos.alpha.business.model.MiembroProyectoPK;
import py.fpuna.is2.proyectos.alpha.business.model.Proyecto;
import py.fpuna.is2.proyectos.alpha.business.model.RolProyecto;
import py.fpuna.is2.proyectos.alpha.business.model.SolicitudColaboracion;
import py.fpuna.is2.proyectos.alpha.business.services.AbstractFacade;
import py.fpuna.is2.proyectos.alpha.business.services.assertions.ServiceAssertions;
import py.fpuna.is2.proyectos.alpha.business.services.exceptions.ApplicationException;
import py.fpuna.is2.proyectos.alpha.business.services.solicitudes.EstadosSolicitudColaboracion;
import py.fpuna.is2.proyectos.alpha.business.services.solicitudes.SolicitudColaboracionService;

/**
 *
 * @author rafae
 */
@Stateless
@Path("proyectos")
@Consumes("application/json")
@Produces("application/json")
public class ProyectoService extends AbstractFacade<Proyecto>{
    
    @PersistenceContext(unitName = "py.fpuna.is2_proyectos-alpha_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @EJB
    private SolicitudColaboracionService solicitudesColaboracion;
    @EJB
    private MiembroProyectoService miembrosProyecto;

    public ProyectoService() {
        super(Proyecto.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @POST
    @Override
    public void create(Proyecto entity) {
        if(entity!=null) {
            entity.setFechaCreacion(new Date());
            entity.setEstado(EstadosProyecto.ACTIVO);
        }
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    public void edit(@PathParam("id") UUID id, Proyecto entity) { 
        if(id != null && entity != null) {
            entity.setIdProyecto(id);
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
    public Proyecto find(@PathParam("id") UUID id) {
        return super.find(id);
    }

    @GET
    @Override
    public List<Proyecto> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    public List<Proyecto> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("{idProyecto}/hitos")
    public List<Hito> getAllMilestonesOfProject(@PathParam("idProyecto")UUID idProyecto) {
        Query q = em.createNamedQuery("Hito.findByProyecto", Hito.class);
        q.setParameter("idProyecto", idProyecto);
        return q.getResultList();
    }
    
    @POST
    @Path("solicitudes-colaboracion")
    public void createColaborationRequest(SolicitudColaboracion solicitud) throws ApplicationException {
        ServiceAssertions.assertNotNullOrEmpty(solicitud, "Se requiere la solicitud");
        solicitudesColaboracion.create(solicitud);
    }
    
    @GET
    @Path("{idProyecto}/solicitudes-colaboracion")
    public List<SolicitudColaboracion> getAllPendingColaborationRequests(@PathParam("idProyecto")UUID idProyecto) {
        return solicitudesColaboracion.getAllPendingColaborationRequests(idProyecto);
    }
    
    @PUT
    @Path("solicitudes-colaboracion/{idSolicitudColaboracion}/aceptar")
    public void acceptRequest(@PathParam("idSolicitudColaboracion")UUID idSolicitudColaboracion) throws ApplicationException {
        SolicitudColaboracion solicitud = solicitudesColaboracion.find(idSolicitudColaboracion);
        
        ServiceAssertions.assertFound(solicitud, "No existe la solicitud.");
        MiembroProyecto miembro = new MiembroProyecto();
        
        //se crea un miembro para el proyecto
        MiembroProyectoPK clave = new MiembroProyectoPK();
        clave.setProyecto(solicitud.getProyecto());
        clave.setUsuario(solicitud.getUsuarioOrigen());
        miembro.setMembresia(clave);
        miembro.setEstado(EstadosMembresia.ACTIVO);
        miembro.setFechaMembresia(new Date());
        miembro.setRol(new RolProyecto(RolProyecto.COLABORADOR));
        miembrosProyecto.create(miembro);
        
        //se cambia el estado de la solicitud
        solicitud.setEstado(EstadosSolicitudColaboracion.ACEPTADO);
        solicitudesColaboracion.edit(solicitud);
    }
    
    @PUT
    @Path("solicitudes-colaboracion/{idSolicitudColaboracion}/rechazar")
    public void rejectRequest(@PathParam("idProyecto")UUID idProyecto, @PathParam("idSolicitudColaboracion")UUID idSolicitudColaboracion) throws ApplicationException {
        SolicitudColaboracion solicitud = solicitudesColaboracion.find(idSolicitudColaboracion);
        ServiceAssertions.assertFound(solicitud, "No existe la solicitud.");
    
        //se cambia el estado de la solicitud
        solicitud.setEstado(EstadosSolicitudColaboracion.RECHAZADO);
        solicitudesColaboracion.edit(solicitud);
    } 
}