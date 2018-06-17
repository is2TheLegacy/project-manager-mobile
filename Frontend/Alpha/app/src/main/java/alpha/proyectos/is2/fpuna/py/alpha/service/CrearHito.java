package alpha.proyectos.is2.fpuna.py.alpha.service;

import java.util.Date;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;

public class CrearHito {

    private UUID idHito;
    private String nombre;
    private String descripcion;
    private Long fechaInicio;
    private Long fechaEstimadaFin;
    private Usuario usuarioCreador;
    private Proyecto proyecto;

    public CrearHito(UUID id, String nombre, String descripcion, Long fechaInicio,
                     Long fechaEstimadaFin, Usuario usuarioCreador, Proyecto proyecto) {
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

    public Long getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Long getFechaEstimadaFin() {
        return fechaEstimadaFin;
    }

    public void setFechaEstimadaFin(Long fechaEstimadaFin) {
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
}
