package clases;

import java.io.Serializable;

public class Captor implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int _id;
    String idCarga;
    int nroCaptor;
    int legajo;
    String nombre;
    String pass;



    public Captor() {
        super();
    }


    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public String getIdCarga() {
        return idCarga;
    }
    public void setIdCarga(String idCarga) {
        this.idCarga = idCarga;
    }
    public int getNroCaptor() {
        return nroCaptor;
    }
    public void setNroCaptor(int nroCaptor) {
        this.nroCaptor = nroCaptor;
    }
    public int getLegajo() {
        return legajo;
    }
    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }



}