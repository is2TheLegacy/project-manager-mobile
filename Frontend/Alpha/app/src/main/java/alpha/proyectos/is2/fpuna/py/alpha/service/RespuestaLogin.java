package alpha.proyectos.is2.fpuna.py.alpha.service;

import java.util.List;

/**
 *
 * @author federico.torres
 */

public class RespuestaLogin {

    private int status;
    private String header;
    private List<String> messages;

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
}
