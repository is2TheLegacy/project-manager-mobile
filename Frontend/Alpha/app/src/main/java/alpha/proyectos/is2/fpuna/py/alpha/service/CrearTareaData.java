package alpha.proyectos.is2.fpuna.py.alpha.service;

import java.util.Date;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.service.model.Hito;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Tarea;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;

public class CrearTareaData {

    private UUID idTarea;
    private String nombre;
    private String descripcion;
    private String prioridad;
    private String estado;
    private Long fechaInicio;
    private Long fechaFin;
    private Long fechaEstimadaInicio;
    private Long fechaEstimadaFin;
    private short porcentajeRealizado;
    private Usuario usuarioAsignado;
    private Usuario usuarioCreador;
    private Proyecto proyecto;
    private Hito hito;
    private Long fechaCreacion;

    public CrearTareaData() {
    }

    public CrearTareaData(Tarea tarea) {
        this.idTarea = tarea.getIdTarea();
        this.nombre = tarea.getNombre();
        this.descripcion = tarea.getDescripcion();
        this.fechaEstimadaInicio = tarea.getFechaEstimadaInicio().getTime();
        this.fechaEstimadaFin = tarea.getFechaEstimadaFin().getTime();
        this.prioridad = tarea.getPrioridad();
        this.usuarioAsignado = tarea.getUsuarioAsignado();
        this.estado = tarea.getEstado();
        this.prioridad = tarea.getPrioridad();
        this.proyecto = tarea.getProyecto();
        this.usuarioCreador = tarea.getUsuarioCreador();
        this.fechaCreacion = tarea.getFechaCreacion();
    }

    public CrearTareaData(UUID id, String nombre, String descripcion, Date fechaEstimadaInicio,
                          Date fechaEstimadaFin, String prioridad, Usuario usuarioAsignado,
                          Proyecto proyecto, Hito hito, Usuario usuarioCreador) {

        this.idTarea = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaEstimadaInicio = fechaEstimadaInicio.getTime();
        this.fechaEstimadaFin = fechaEstimadaFin.getTime();
        this.prioridad = prioridad;
        this.usuarioAsignado = usuarioAsignado;
        this.estado = "PENDIENTE";
        this.proyecto = proyecto;
        this.porcentajeRealizado = 0;
        this.hito = hito;
        this.usuarioCreador = usuarioCreador;
    }

    public CrearTareaData(UUID id, String nombre, String descripcion, Long fechaInicio, Long fechaFin,
                          String estado, Short porcentaje, Hito hito, String prioridad, Long fechaEstimadaInicio,
                          Long fechaEstimadaFin, Long fechaCreacion, Proyecto proyecto, Usuario usuarioCreador) {

        this.idTarea = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.porcentajeRealizado = porcentaje;
        this.hito = hito;
        this.prioridad = prioridad;
        this.fechaEstimadaInicio = fechaEstimadaInicio;
        this.fechaEstimadaFin = fechaEstimadaFin;
        this.fechaCreacion = fechaCreacion;
        this.proyecto = proyecto;
        this.usuarioCreador = usuarioCreador;
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

    public Long getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Long getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Long fechaFin) {
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

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
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
