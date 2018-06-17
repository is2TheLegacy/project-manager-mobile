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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "proyecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p")
    , @NamedQuery(name = "Proyecto.findByNombre", query = "SELECT p FROM Proyecto p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Proyecto.findByDescripcion", query = "SELECT p FROM Proyecto p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Proyecto.findByUrImagenPortada", query = "SELECT p FROM Proyecto p WHERE p.urImagenPortada = :urImagenPortada")
    , @NamedQuery(name = "Proyecto.findByFechaCreacion", query = "SELECT p FROM Proyecto p WHERE p.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Proyecto.findByFechaFinalizacion", query = "SELECT p FROM Proyecto p WHERE p.fechaFinalizacion = :fechaFinalizacion")
    , @NamedQuery(name = "Proyecto.findByEstado", query = "SELECT p FROM Proyecto p WHERE p.estado = :estado")})
public class Proyecto implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto")
    private Collection<Tarea> tareaCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_proyecto")
    private UUID idProyecto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 2083)
    @Column(name = "ur_imagen_portada")
    private String urImagenPortada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fecha_finalizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinalizacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "categoria", referencedColumnName = "id_categoria_proyecto")
    @ManyToOne
    private CategoriaProyecto categoria;
    @JoinColumn(name = "propietario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario propietario;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto", fetch = FetchType.EAGER)
    private Collection<Hito> hitoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto")
    @JsonIgnore
    private Collection<SolicitudColaboracion> solicitudColaboracionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto1")
    @JsonIgnore
    private Collection<MiembroProyecto> miembroProyectoCollection;

    public Proyecto() {
    }

    public Proyecto(UUID idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Proyecto(UUID idProyecto, String nombre, String descripcion, Date fechaCreacion, String estado) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
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

    public String getUrImagenPortada() {
        return urImagenPortada;
    }

    public void setUrImagenPortada(String urImagenPortada) {
        this.urImagenPortada = urImagenPortada;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public CategoriaProyecto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProyecto categoria) {
        this.categoria = categoria;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    @XmlTransient
    public Collection<Hito> getHitoCollection() {
        return hitoCollection;
    }

    public void setHitoCollection(Collection<Hito> hitoCollection) {
        this.hitoCollection = hitoCollection;
    }

    @XmlTransient
    public Collection<SolicitudColaboracion> getSolicitudColaboracionCollection() {
        return solicitudColaboracionCollection;
    }

    public void setSolicitudColaboracionCollection(Collection<SolicitudColaboracion> solicitudColaboracionCollection) {
        this.solicitudColaboracionCollection = solicitudColaboracionCollection;
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
        hash += (idProyecto != null ? idProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.idProyecto == null && other.idProyecto != null) || (this.idProyecto != null && !this.idProyecto.equals(other.idProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.fpuna.is2.proyectos.alpha.model.Proyecto[ idProyecto=" + idProyecto + " ]";
    }

    @XmlTransient
    public Collection<Tarea> getTareaCollection() {
        return tareaCollection;
    }

    public void setTareaCollection(Collection<Tarea> tareaCollection) {
        this.tareaCollection = tareaCollection;
    }
}
