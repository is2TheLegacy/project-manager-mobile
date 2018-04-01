package alpha.proyectos.is2.fpuna.py.alpha.service.login;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;

/**
 * Created by konecta on 20/03/18.
 */
public class RespuestaLogin {

    private int status = 200;//default is OK
    private String header;
    private List<String> messages;
    private UUID authToken;
    private Usuario user;

    public RespuestaLogin() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public UUID getAuthToken() {
        return authToken;
    }

    public void setAuthToken(UUID authToken) {
        this.authToken = authToken;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
