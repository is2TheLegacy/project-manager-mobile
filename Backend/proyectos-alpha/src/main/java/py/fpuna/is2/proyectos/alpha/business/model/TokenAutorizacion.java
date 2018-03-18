/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rafae
 */
@Entity
@Table(name = "token_autorizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TokenAutorizacion.findAll", query = "SELECT t FROM TokenAutorizacion t")
    , @NamedQuery(name = "TokenAutorizacion.findByExpirado", query = "SELECT t FROM TokenAutorizacion t WHERE t.expirado = :expirado")
    , @NamedQuery(name = "TokenAutorizacion.findByFechaCreacion", query = "SELECT t FROM TokenAutorizacion t WHERE t.fechaCreacion = :fechaCreacion")})
public class TokenAutorizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "token")
    private UUID token;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expirado")
    private boolean expirado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public TokenAutorizacion() {
    }

    public TokenAutorizacion(UUID token) {
        this.token = token;
    }

    public TokenAutorizacion(UUID token, boolean expirado, Date fechaCreacion) {
        this.token = token;
        this.expirado = expirado;
        this.fechaCreacion = fechaCreacion;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public boolean getExpirado() {
        return expirado;
    }

    public void setExpirado(boolean expirado) {
        this.expirado = expirado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.token);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TokenAutorizacion other = (TokenAutorizacion) obj;
        if (!Objects.equals(this.token, other.token)) {
            return false;
        }
        return true;
    }
}
