package clases;

import java.io.Serializable;

public class MedidorNoCoincidente implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int _id;
    String ruta;
    String medidorAnterior;
    String medidorActual;
    int estado;
    String direccion;
    String folio;
    String ds;


    public MedidorNoCoincidente() {
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
    public String getMedidorAnterior() {
        return medidorAnterior;
    }
    public void setMedidorAnterior(String medidorAnterior) {
        this.medidorAnterior = medidorAnterior;
    }
    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
    public String getDireccion()
    {
        return direccion;
    }
    public void setDireccion(String direccion)
    {
        this.direccion=direccion;
    }
    public String getMedidorActual() {
        return medidorActual;
    }
    public void setMedidorActual(String medidorActual) {
        this.medidorActual = medidorActual;
    }
    public void setFolio(String folio) {
        this.folio=folio;

    }
    public String getFolio() {
        return folio;
    }
    public String getDs() {
        return ds;
    }
    public void setDs(String ds) {
        this.ds = ds;
    }


}