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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "miembro_proyecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MiembroProyecto.findAll", query = "SELECT m FROM MiembroProyecto m")
    , @NamedQuery(name = "MiembroProyecto.findByFechaMembresia", query = "SELECT m FROM MiembroProyecto m WHERE m.fechaMembresia = :fechaMembresia")
    , @NamedQuery(name = "MiembroProyecto.findByEstado", query = "SELECT m FROM MiembroProyecto m WHERE m.estado = :estado")
    , @NamedQuery(name = "MiembroProyecto.findByUsuario", query = "SELECT m FROM MiembroProyecto m WHERE m.membresia.usuario.idUsuario = :idUsuario and m.estado = :estado")})
public class MiembroProyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MiembroProyectoPK membresia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_membresia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMembresia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "estado")
    private String estado;    
    @JoinColumn(name = "rol", referencedColumnName = "id_rol_proyecto")
    @ManyToOne(optional = false)
    private RolProyecto rol;

    public MiembroProyecto() {
    }

    public MiembroProyecto(MiembroProyectoPK membresia) {
        this.membresia = membresia;
    }

    public MiembroProyecto(MiembroProyectoPK membresia, Date fechaMembresia, String estado) {
        this.membresia = membresia;
        this.fechaMembresia = fechaMembresia;
        this.estado = estado;
    }

    public MiembroProyectoPK getMembresia() {
        return membresia;
    }

    public void setMembresia(MiembroProyectoPK membresia) {
        this.membresia = membresia;
    }

    public Date getFechaMembresia() {
        return fechaMembresia;
    }

    public void setFechaMembresia(Date fechaMembresia) {
        this.fechaMembresia = fechaMembresia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public RolProyecto getRol() {
        return rol;
    }

    public void setRol(RolProyecto rol) {
        this.rol = rol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (membresia != null ? membresia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MiembroProyecto)) {
            return false;
        }
        MiembroProyecto other = (MiembroProyecto) object;
        if ((this.membresia == null && other.membresia != null) || (this.membresia != null && !this.membresia.equals(other.membresia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.fpuna.is2.proyectos.alpha.model.MiembroProyecto[ membresia=" + membresia + " ]";
    }
    
}
