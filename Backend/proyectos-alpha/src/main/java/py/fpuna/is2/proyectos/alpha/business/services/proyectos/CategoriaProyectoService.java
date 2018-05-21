/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.services.proyectos;

import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import py.fpuna.is2.proyectos.alpha.business.model.CategoriaProyecto;
import py.fpuna.is2.proyectos.alpha.business.services.AbstractFacade;

/**
 *
 * @author rafae
 */
@Stateless
@Path("categorias")
@Produces("application/json")
@Consumes("application/json")
public class CategoriaProyectoService extends AbstractFacade<CategoriaProyecto> {
    
    @PersistenceContext(unitName = "py.fpuna.is2_proyectos-alpha_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public CategoriaProyectoService() {
        super(CategoriaProyecto.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Path("{id}")
    public CategoriaProyecto find(@PathParam("id") UUID id) {
        return super.find(id);
    }

    @GET
    @Override
    public List<CategoriaProyecto> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    public List<CategoriaProyecto> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
}