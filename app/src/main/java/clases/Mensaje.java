package clases;

import java.io.Serializable;

public class Mensaje implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int _id;
    int msj;
    String mensaje;

    public Mensaje() {
        super();
    }


    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public int getmsj() {
        return msj;
    }
    public void setmsj(int msj) {
        this.msj = msj;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }



}