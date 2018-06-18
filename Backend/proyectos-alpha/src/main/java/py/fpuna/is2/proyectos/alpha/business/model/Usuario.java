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
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByAlias", query = "SELECT u FROM Usuario u WHERE u.alias = :alias")
    , @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email")
    , @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuario.findByApellido", query = "SELECT u FROM Usuario u WHERE u.apellido = :apellido")
    , @NamedQuery(name = "Usuario.findBySexo", query = "SELECT u FROM Usuario u WHERE u.sexo = :sexo")
    , @NamedQuery(name = "Usuario.findByFechaCreacion", query = "SELECT u FROM Usuario u WHERE u.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Usuario.findByPasswordSalt", query = "SELECT u FROM Usuario u WHERE u.passwordSalt = :passwordSalt")
    , @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password")
    , @NamedQuery(name = "Usuario.findByEstado", query = "SELECT u FROM Usuario u WHERE u.estado = :estado")})
public class Usuario implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<TokenAutorizacion> tokenAutorizacionCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_usuario")
    private UUID idUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "alias")
    private String alias;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "email")
    private String email;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "apellido")
    private String apellido;
    @Size(max = 1)
    @Column(name = "sexo")
    private String sexo;
    @JsonIgnore
    @Column(name = "fecha_creacion", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "password_salt", insertable = false, updatable = false)
    @JsonIgnore
    private String passwordSalt;
    @Basic(optional = false)
    @JsonIgnore
    @Column(name = "password", insertable = false, updatable = false)
    private String password;
    @Size(min = 1, max = 10)
    @Column(name = "estado", insertable = false)
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioCreador")
    private Collection<Tarea> tareaCollection;
    @OneToMany(mappedBy = "usuarioAsignado")
    private Collection<Tarea> tareaCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propietario")
    private Collection<Proyecto> proyectoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioCreador")
    private Collection<Comentario> comentarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioCreador")
    private Collection<Hito> hitoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioOrigen")
    private Collection<SolicitudColaboracion> solicitudColaboracionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioDestino")
    private Collection<SolicitudColaboracion> solicitudColaboracionCollection1;

    public Usuario() {
    }

    public Usuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(UUID idUsuario, String alias, String email, Date fechaCreacion, String passwordSalt, String password, String estado) {
        this.idUsuario = idUsuario;
        this.alias = alias;
        this.email = email;
        this.fechaCreacion = fechaCreacion;
        this.passwordSalt = passwordSalt;
        this.password = password;
        this.estado = estado;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Tarea> getTareaCollection() {
        return tareaCollection;
    }

    public void setTareaCollection(Collection<Tarea> tareaCollection) {
        this.tareaCollection = tareaCollection;
    }

    @XmlTransient
    public Collection<Tarea> getTareaCollection1() {
        return tareaCollection1;
    }

    public void setTareaCollection1(Collection<Tarea> tareaCollection1) {
        this.tareaCollection1 = tareaCollection1;
    }

    @XmlTransient
    public Collection<Proyecto> getProyectoCollection() {
        return proyectoCollection;
    }

    public void setProyectoCollection(Collection<Proyecto> proyectoCollection) {
        this.proyectoCollection = proyectoCollection;
    }

    @XmlTransient
    public Collection<Comentario> getComentarioCollection() {
        return comentarioCollection;
    }

    public void setComentarioCollection(Collection<Comentario> comentarioCollection) {
        this.comentarioCollection = comentarioCollection;
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
    public Collection<SolicitudColaboracion> getSolicitudColaboracionCollection1() {
        return solicitudColaboracionCollection1;
    }

    public void setSolicitudColaboracionCollection1(Collection<SolicitudColaboracion> solicitudColaboracionCollection1) {
        this.solicitudColaboracionCollection1 = solicitudColaboracionCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.fpuna.is2.proyectos.alpha.model.Usuario[ idUsuario=" + idUsuario + " ]";
    }

    @XmlTransient
    public Collection<TokenAutorizacion> getTokenAutorizacionCollection() {
        return tokenAutorizacionCollection;
    }

    public void setTokenAutorizacionCollection(Collection<TokenAutorizacion> tokenAutorizacionCollection) {
        this.tokenAutorizacionCollection = tokenAutorizacionCollection;
    }
    
}
