/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "categoria_proyecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaProyecto.findAll", query = "SELECT c FROM CategoriaProyecto c")
    , @NamedQuery(name = "CategoriaProyecto.findByIdCategoriaProyecto", query = "SELECT c FROM CategoriaProyecto c WHERE c.idCategoriaProyecto = :idCategoriaProyecto")
    , @NamedQuery(name = "CategoriaProyecto.findByNombre", query = "SELECT c FROM CategoriaProyecto c WHERE c.nombre = :nombre")})
public class CategoriaProyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_categoria_proyecto")
    private Short idCategoriaProyecto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "categoria")
    private Collection<Proyecto> proyectoCollection;

    public CategoriaProyecto() {
    }

    public CategoriaProyecto(Short idCategoriaProyecto) {
        this.idCategoriaProyecto = idCategoriaProyecto;
    }

    public CategoriaProyecto(Short idCategoriaProyecto, String nombre) {
        this.idCategoriaProyecto = idCategoriaProyecto;
        this.nombre = nombre;
    }

    public Short getIdCategoriaProyecto() {
        return idCategoriaProyecto;
    }

    public void setIdCategoriaProyecto(Short idCategoriaProyecto) {
        this.idCategoriaProyecto = idCategoriaProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Proyecto> getProyectoCollection() {
        return proyectoCollection;
    }

    public void setProyectoCollection(Collection<Proyecto> proyectoCollection) {
        this.proyectoCollection = proyectoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoriaProyecto != null ? idCategoriaProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaProyecto)) {
            return false;
        }
        CategoriaProyecto other = (CategoriaProyecto) object;
        if ((this.idCategoriaProyecto == null && other.idCategoriaProyecto != null) || (this.idCategoriaProyecto != null && !this.idCategoriaProyecto.equals(other.idCategoriaProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.fpuna.is2.proyectos.alpha.model.CategoriaProyecto[ idCategoriaProyecto=" + idCategoriaProyecto + " ]";
    }
    
}
