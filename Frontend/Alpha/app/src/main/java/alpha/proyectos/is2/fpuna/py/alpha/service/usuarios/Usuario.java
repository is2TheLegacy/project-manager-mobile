package alpha.proyectos.is2.fpuna.py.alpha.service.usuarios;

import java.util.UUID;

/**
 * Created by rafae on 3/31/2018.
 */

public class Usuario {

    private String idUsuario;
    private String alias;
    private String email;
    private String nombre;
    private String apellido;
    private String sexo;

    public Usuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(String idUsuario, String alias, String email, String nombre, String apellido, String sexo) {
        this.idUsuario = idUsuario;
        this.alias = alias;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
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
}
