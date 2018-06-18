/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rafae
 */
@Entity
@Table(name = "rol_proyecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolProyecto.findAll", query = "SELECT r FROM RolProyecto r")
    , @NamedQuery(name = "RolProyecto.findByIdRolProyecto", query = "SELECT r FROM RolProyecto r WHERE r.idRolProyecto = :idRolProyecto")
    , @NamedQuery(name = "RolProyecto.findByNombre", query = "SELECT r FROM RolProyecto r WHERE r.nombre = :nombre")})
public class RolProyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Short ADMINISTRADOR = 1;
    public static final Short COLABORADOR = 2;
    public static final Short SEGUIDOR = 3;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rol_proyecto")
    private Short idRolProyecto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rol")
    private Collection<MiembroProyecto> miembroProyectoCollection;

    public RolProyecto() {
    }

    public RolProyecto(Short idRolProyecto) {
        this.idRolProyecto = idRolProyecto;
    }

    public RolProyecto(Short idRolProyecto, String nombre) {
        this.idRolProyecto = idRolProyecto;
        this.nombre = nombre;
    }

    public Short getIdRolProyecto() {
        return idRolProyecto;
    }

    public void setIdRolProyecto(Short idRolProyecto) {
        this.idRolProyecto = idRolProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<MiembroProyecto> getMiembroProyectoCollection() {
        return miembroProyectoCollection;
    }

    public void setMiembroProyectoCollection(Collection<MiembroProyecto> miembroProyectoCollection) {
        this.miembroProyectoCollection = miembroProyectoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolProyecto != null ? idRolProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolProyecto)) {
            return false;
        }
        RolProyecto other = (RolProyecto) object;
        if ((this.idRolProyecto == null && other.idRolProyecto != null) || (this.idRolProyecto != null && !this.idRolProyecto.equals(other.idRolProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.fpuna.is2.proyectos.alpha.model.RolProyecto[ idRolProyecto=" + idRolProyecto + " ]";
    }
    
}
