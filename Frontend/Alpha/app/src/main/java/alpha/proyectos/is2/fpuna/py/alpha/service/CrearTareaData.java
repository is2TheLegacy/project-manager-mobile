package alpha.proyectos.is2.fpuna.py.alpha.service;

import java.util.Date;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;

public class CrearTareaData {

    private UUID idTarea;
    private String nombre;
    private String descripcion;
    private String prioridad;
    private String estado;
    private Date fechaInicio;
    private Date fechaFin;
    private Long fechaEstimadaInicio;
    private Long fechaEstimadaFin;
    private short porcentajeRealizado;
    private Usuario usuarioAsignado;
    private Usuario usuarioCreador;
    private Proyecto proyecto;

    public CrearTareaData() {
    }

    public CrearTareaData(UUID id, String nombre, String descripcion, Date fechaEstimadaInicio,
                          Date fechaEstimadaFin, String prioridad, Usuario usuarioAsignado,
                          Proyecto proyecto) {

        this.idTarea = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaEstimadaInicio = fechaEstimadaInicio.getTime();
        this.fechaEstimadaFin = fechaEstimadaFin.getTime();
        this.prioridad = prioridad;
        this.usuarioAsignado = usuarioAsignado;
        this.usuarioCreador = usuarioAsignado;
        this.estado = "PENDIENTE";
        this.proyecto = proyecto;
        this.porcentajeRealizado = 0;
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

    public Long getFechaEstimadaInicio() {
        return fechaEstimadaInicio;
    }

    public void setFechaEstimadaInicio(Long fechaEstimadaInicio) {
        this.fechaEstimadaInicio = fechaEstimadaInicio;
    }

    public Long getFechaEstimadaFin() {
        return fechaEstimadaFin;
    }

    public void setFechaEstimadaFin(Long fechaEstimadaFin) {
        this.fechaEstimadaFin = fechaEstimadaFin;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

}
