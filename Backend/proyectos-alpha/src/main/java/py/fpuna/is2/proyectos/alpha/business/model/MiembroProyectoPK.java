/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.model;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

/**
 *
 * @author rafae
 */
@Embeddable
public class MiembroProyectoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "proyecto")
    private UUID proyecto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usuario")
    private UUID usuario;

    public MiembroProyectoPK() {
    }

    public MiembroProyectoPK(UUID proyecto, UUID usuario) {
        this.proyecto = proyecto;
        this.usuario = usuario;
    }

    public UUID getProyecto() {
        return proyecto;
    }

    public void setProyecto(UUID proyecto) {
        this.proyecto = proyecto;
    }

    public UUID getUsuario() {
        return usuario;
    }

    public void setUsuario(UUID usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proyecto != null ? proyecto.hashCode() : 0);
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MiembroProyectoPK)) {
            return false;
        }
        MiembroProyectoPK other = (MiembroProyectoPK) object;
        if ((this.proyecto == null && other.proyecto != null) || (this.proyecto != null && !this.proyecto.equals(other.proyecto))) {
            return false;
        }
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.fpuna.is2.proyectos.alpha.model.MiembroProyectoPK[ proyecto=" + proyecto + ", usuario=" + usuario + " ]";
    }
    
}
