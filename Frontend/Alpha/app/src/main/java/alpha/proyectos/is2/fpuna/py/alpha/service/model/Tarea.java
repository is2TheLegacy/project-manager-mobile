package alpha.proyectos.is2.fpuna.py.alpha.service.model;

import java.util.Date;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;

public class Tarea {

    private UUID idTarea;
	private String nombre;
	private String descripcion;
	private String prioridad;
	private String estado;
    private Date fechaInicio;
    private Date fechaFin;
	private Date fechaEstimadaInicio;
	private Date fechaEstimadaFin;
	private Long fechaCreacion;
    private short porcentajeRealizado;
    private Usuario usuarioAsignado;
    private Usuario usuarioCreador;
    private Proyecto proyecto;
    private Hito hito;
	
	public Tarea() {
	}

    public Tarea(UUID id, String nombre, String descripcion, Date fechaEstimadaInicio,
                 Date fechaEstimadaFin, String prioridad, Usuario usuarioAsignado) {
        this.idTarea = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        //this.fechaEstimadaInicio = fechaEstimadaInicio;
        //this.fechaEstimadaFin = fechaEstimadaFin;
        this.prioridad = prioridad;
        this.usuarioAsignado = usuarioAsignado;
    }

    public Tarea(UUID id, String nombre, String descripcion, Date fechaEstimadaInicio,
                 Date fechaEstimadaFin, String estado) {
        this.idTarea = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaEstimadaInicio = fechaEstimadaInicio;
        this.fechaEstimadaFin = fechaEstimadaFin;
        this.prioridad = prioridad;
        this.usuarioAsignado = usuarioAsignado;
        this.estado = estado;
    }

    public UUID getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(UUID idTarea) {
        this.idTarea = idTarea;
    }

    public Usuario getUsuarioAsignado() {
        return usuarioAsignado;
    }

    public void setUsuarioAsignado(Usuario usuarioAsignado) {
        this.usuarioAsignado = usuarioAsignado;
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

    public short getPorcentajeRealizado() {
        return porcentajeRealizado;
    }

    public void setPorcentajeRealizado(short porcentajeRealizado) {
        this.porcentajeRealizado = porcentajeRealizado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaEstimadaInicio() {
        return fechaEstimadaInicio;
    }

    public void setFechaEstimadaInicio(Date fechaEstimadaInicio) {
        this.fechaEstimadaInicio = fechaEstimadaInicio;
    }

    public Date getFechaEstimadaFin() {
        return fechaEstimadaFin;
    }

    public void setFechaEstimadaFin(Date fechaEstimadaFin) {
        this.fechaEstimadaFin = fechaEstimadaFin;
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

    public Hito getHito() {
        return hito;
    }

    public void setHito(Hito hito) {
        this.hito = hito;
    }

    public Long getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}