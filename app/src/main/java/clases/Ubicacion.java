package clases;

import java.io.Serializable;

public class Ubicacion implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int _id;
    int codubi;
    String ubicacion;
    public Ubicacion() {
        super();
    }




    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }

    public int getcodubi() {
        return codubi;
    }
    public void setcodubi(int codubi) {
        this.codubi = codubi;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }



}