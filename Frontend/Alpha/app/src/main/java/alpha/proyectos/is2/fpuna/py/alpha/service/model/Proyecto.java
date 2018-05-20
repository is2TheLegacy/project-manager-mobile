package alpha.proyectos.is2.fpuna.py.alpha.service.model;

import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;

/**
 * Created by konecta on 13/05/18.
 */

public class Proyecto {

    private UUID idProyecto;
    private String nombre;
    private String estado;
    private String descripcion;
    private Long fechaCreacion;
    private Long fechaFinalizacion;
    private Usuario propietario;

    public Proyecto() {
    }

    public Proyecto(UUID idProyecto, String nombre, String descripcion,
                    Long fechaFinalizacion, Usuario usuario) {

        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaFinalizacion = fechaFinalizacion;
        this.propietario = usuario;
    }

    public UUID getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(UUID idProyecto) {
        this.idProyecto = idProyecto;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Long fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

}
