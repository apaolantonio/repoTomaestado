package clases;

import java.io.Serializable;

public class Empleado implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int _id;
    String nombre;
    int legajo;
    String pass;
    boolean admin;


    public Empleado() {
        super();
    }


    public int get_id() {
        return _id;
    }


    public void set_id(int _id) {
        this._id = _id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getLegajo() {
        return legajo;
    }


    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }


    public String getPass() {
        return pass;
    }


    public void setPass(String pass) {
        this.pass = pass;
    }


    public boolean isAdmin() {
        return admin;
    }


    public void setAdmin(boolean admin) {
        this.admin = admin;
    }



}
