package alpha.proyectos.is2.fpuna.py.alpha.service.model;

import java.util.Date;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;

public class Comentario {

    private UUID idComentario;
    private String contenido;
    private Date fechaCreacion;
    private Usuario usuarioCreador;

    public Comentario() {
    }

    public Comentario(String contenido) {
        this.contenido = contenido;
    }

    public Comentario(UUID idComentario, String contenido) {
        this.idComentario = idComentario;
        this.contenido = contenido;
    }

    public UUID getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(UUID idComentario) {
        this.idComentario = idComentario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }
}
