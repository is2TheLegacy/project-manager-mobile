package alpha.proyectos.is2.fpuna.py.alpha.service.model;

import java.util.Date;

public class Tarea {

    private String id;
	private String nombre;
	private String descripcion;
	private String prioridad;
	private String estado;
    private Date fechaInicio;
    private Date fechaFin;
	private Date fechaEstimadaInicio;
	private Date fechaEstimadaFin;
    private short porcentajeRealizado;
	
	public Tarea() {
	}

    public Tarea(String id, String nombre, String descripcion, Date fechaEstimadaInicio,
                 Date fechaEstimadaFin, String prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaEstimadaInicio = fechaEstimadaInicio;
        this.fechaEstimadaFin = fechaEstimadaFin;
        this.prioridad = prioridad;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}