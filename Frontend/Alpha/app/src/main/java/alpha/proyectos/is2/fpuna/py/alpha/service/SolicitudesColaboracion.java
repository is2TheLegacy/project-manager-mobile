package alpha.proyectos.is2.fpuna.py.alpha.service;

import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;

public class SolicitudesColaboracion {

    private UUID idSolicitudColaboracion;
    private Proyecto proyecto;
    private Usuario usuarioOrigen;
    private Usuario usuarioDestino;
    private String mensaje;
    private String estado;
    private Long fechaSolitud;

    public SolicitudesColaboracion(UUID idSolicitudColaboracion, Proyecto proyecto,
                                   Usuario usuarioOrigen, Usuario usuarioDestino,
                                   String mensaje) {
        this.idSolicitudColaboracion = idSolicitudColaboracion;
        this.proyecto = proyecto;
        this.usuarioOrigen = usuarioOrigen;
        this.usuarioDestino = usuarioDestino;
        this.mensaje = mensaje;
    }

    public UUID getIdSolicitudColaboracion() {
        return idSolicitudColaboracion;
    }

    public void setIdSolicitudColaboracion(UUID idSolicitudColaboracion) {
        this.idSolicitudColaboracion = idSolicitudColaboracion;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Usuario getUsuarioOrigen() {
        return usuarioOrigen;
    }

    public void setUsuarioOrigen(Usuario usuarioOrigen) {
        this.usuarioOrigen = usuarioOrigen;
    }

    public Usuario getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(Usuario usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getFechaSolitud() {
        return fechaSolitud;
    }

    public void setFechaSolitud(Long fechaSolitud) {
        this.fechaSolitud = fechaSolitud;
    }
}
