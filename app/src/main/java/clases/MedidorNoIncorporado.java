package clases;

import java.io.Serializable;

public class MedidorNoIncorporado implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int _id;
    String ruta;
    int estado;
    String medidor;
    String direccion;
    String folio;
    String ds;


    public MedidorNoIncorporado() {
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
    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
    public String getMedidor() {
        return medidor;
    }
    public void setMedidor(String medidor) {
        this.medidor = medidor;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getFolio() {
        return folio;
    }
    public void setFolio(String folio) {
        this.folio = folio;
    }
    public String getDs() {
        return ds;
    }
    public void setDs(String ds) {
        this.ds = ds;
    }



}