/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rafae
 */
@Entity
@Table(name = "hito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hito.findAll", query = "SELECT h FROM Hito h")
    , @NamedQuery(name = "Hito.findByNombre", query = "SELECT h FROM Hito h WHERE h.nombre = :nombre")
    , @NamedQuery(name = "Hito.findByDescripcion", query = "SELECT h FROM Hito h WHERE h.descripcion = :descripcion")
    , @NamedQuery(name = "Hito.findByFechaInicio", query = "SELECT h FROM Hito h WHERE h.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Hito.findByFechaEstimadaFin", query = "SELECT h FROM Hito h WHERE h.fechaEstimadaFin = :fechaEstimadaFin")
    , @NamedQuery(name = "Hito.findByFechaRealFin", query = "SELECT h FROM Hito h WHERE h.fechaRealFin = :fechaRealFin")
    , @NamedQuery(name = "Hito.findByProyecto", query = "SELECT h FROM Hito h WHERE h.proyecto.idProyecto = :idProyecto")})
public class Hito implements Serializable {

    @JsonIgnore
    @OneToMany(mappedBy = "hito")
    private Collection<Tarea> tareaCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_hito")
    private UUID idHito;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_estimada_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaEstimadaFin;
    @Column(name = "fecha_real_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaRealFin;
    @JoinColumn(name = "proyecto", referencedColumnName = "id_proyecto")
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @JoinColumn(name = "usuario_creador", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuarioCreador;

    public Hito() {
    }

    public Hito(UUID idHito) {
        this.idHito = idHito;
    }

    public Hito(UUID idHito, String nombre, Date fechaInicio, Date fechaEstimadaFin) {
        this.idHito = idHito;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaEstimadaFin = fechaEstimadaFin;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaEstimadaFin() {
        return fechaEstimadaFin;
    }

    public void setFechaEstimadaFin(Date fechaEstimadaFin) {
        this.fechaEstimadaFin = fechaEstimadaFin;
    }

    public Date getFechaRealFin() {
        return fechaRealFin;
    }

    public void setFechaRealFin(Date fechaRealFin) {
        this.fechaRealFin = fechaRealFin;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHito != null ? idHito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hito)) {
            return false;
        }
        Hito other = (Hito) object;
        if ((this.idHito == null && other.idHito != null) || (this.idHito != null && !this.idHito.equals(other.idHito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.fpuna.is2.proyectos.alpha.model.Hito[ idHito=" + idHito + " ]";
    }

    @XmlTransient
    public Collection<Tarea> getTareaCollection() {
        return tareaCollection;
    }

    public void setTareaCollection(Collection<Tarea> tareaCollection) {
        this.tareaCollection = tareaCollection;
    }
    
}
