package clases;

import java.io.Serializable;

public class MedidorConexionDirecta implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    int _id;
    String ruta;
    String direccion;
    String responsable;
    int tipo;

    public MedidorConexionDirecta() {
        super();
    }


    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public String getRuta() {
        return ruta;
    }
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getResponsable() {
        return responsable;
    }
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }


    public int getTipo() {
        return tipo;
    }


    public void setTipo(int tipo) {
        this.tipo = tipo;
    }





}
