/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rafae
 */
@Entity
@Table(name = "solicitud_colaboracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitudColaboracion.findAll", query = "SELECT s FROM SolicitudColaboracion s")
    , @NamedQuery(name = "SolicitudColaboracion.findByFechaSolitud", query = "SELECT s FROM SolicitudColaboracion s WHERE s.fechaSolitud = :fechaSolitud")
    , @NamedQuery(name = "SolicitudColaboracion.findByEstado", query = "SELECT s FROM SolicitudColaboracion s WHERE s.estado = :estado")
    , @NamedQuery(name = "SolicitudColaboracion.findByMensaje", query = "SELECT s FROM SolicitudColaboracion s WHERE s.mensaje = :mensaje")})
public class SolicitudColaboracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_solicitud_colaboracion")
    private UUID idSolicitudColaboracion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_solitud")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolitud;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "mensaje")
    private String mensaje;
    @JoinColumn(name = "proyecto", referencedColumnName = "id_proyecto")
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @JoinColumn(name = "usuario_origen", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuarioOrigen;
    @JoinColumn(name = "usuario_destino", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuarioDestino;

    public SolicitudColaboracion() {
    }

    public SolicitudColaboracion(UUID idSolicitudColaboracion) {
        this.idSolicitudColaboracion = idSolicitudColaboracion;
    }

    public SolicitudColaboracion(UUID idSolicitudColaboracion, Date fechaSolitud, String estado, String mensaje) {
        this.idSolicitudColaboracion = idSolicitudColaboracion;
        this.fechaSolitud = fechaSolitud;
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public UUID getIdSolicitudColaboracion() {
        return idSolicitudColaboracion;
    }

    public void setIdSolicitudColaboracion(UUID idSolicitudColaboracion) {
        this.idSolicitudColaboracion = idSolicitudColaboracion;
    }

    public Date getFechaSolitud() {
        return fechaSolitud;
    }

    public void setFechaSolitud(Date fechaSolitud) {
        this.fechaSolitud = fechaSolitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicitudColaboracion != null ? idSolicitudColaboracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudColaboracion)) {
            return false;
        }
        SolicitudColaboracion other = (SolicitudColaboracion) object;
        if ((this.idSolicitudColaboracion == null && other.idSolicitudColaboracion != null) || (this.idSolicitudColaboracion != null && !this.idSolicitudColaboracion.equals(other.idSolicitudColaboracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.fpuna.is2.proyectos.alpha.model.SolicitudColaboracion[ idSolicitudColaboracion=" + idSolicitudColaboracion + " ]";
    }
    
}
