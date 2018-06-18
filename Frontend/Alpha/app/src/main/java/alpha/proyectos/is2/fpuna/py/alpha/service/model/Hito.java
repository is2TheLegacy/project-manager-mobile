package alpha.proyectos.is2.fpuna.py.alpha.service.model;

import java.util.Date;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;

public class Hito {

    private UUID idHito;
	private String nombre;
	private String descripcion;
    private String fechaInicio;
	private String fechaEstimadaFin;
    private Usuario usuarioCreador;
    private Proyecto proyecto;
	
	public Hito() {
	}

    public Hito(UUID id) {
	    this.idHito = id;
    }

    public Hito(UUID id, String nombre, String descripcion, String fechaInicio,
                String fechaEstimadaFin, Usuario usuarioCreador, Proyecto proyecto) {
        this.idHito = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaEstimadaFin = fechaEstimadaFin;
        this.usuarioCreador = usuarioCreador;
        this.proyecto = proyecto;
    }

    public UUID getIdHito() {
        return idHito;
    }

    public void setIdHito(UUID idHito) {
        this.idHito = idHito;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaEstimadaFin() {
        return fechaEstimadaFin;
    }

    public void setFechaEstimadaFin(String fechaEstimadaFin) {
        this.fechaEstimadaFin = fechaEstimadaFin;
    }
}