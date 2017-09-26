package clases;

import java.io.Serializable;

public class Estado implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int _id;
    String estado;
    int id_Medidor;
    String hora;
    int secuencia;
    int ordenativo1;
    int ordenativo2;
    int ordenativo3;
    int ordenativo4;
    int ordenativo5;
    int ordenativo6;
    int geolocalizacion;
    boolean conexiondirecta;


    public Estado() {
        super();
    }


    public int get_id() {
        return _id;
    }


    public void setGeo(int geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    public int getGeo() {
        return geolocalizacion;
    }


    public void set_id(int _id) {
        this._id = _id;
    }


    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }


    public int getId_Medidor() {
        return id_Medidor;
    }


    public void setId_Medidor(int id_Medidor) {
        this.id_Medidor = id_Medidor;
    }


    public String getHora() {
        return hora;
    }


    public void setHora(String hora) {
        this.hora = hora;
    }


    public int getSecuencia() {
        return secuencia;
    }


    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }


    public int getOrdenativo1() {
        return ordenativo1;
    }


    public void setOrdenativo1(int ordenativo1) {
        this.ordenativo1 = ordenativo1;
    }


    public int getOrdenativo2() {
        return ordenativo2;
    }


    public void setOrdenativo2(int ordenativo2) {
        this.ordenativo2 = ordenativo2;
    }


    public int getOrdenativo3() {
        return ordenativo3;
    }


    public void setOrdenativo3(int ordenativo3) {
        this.ordenativo3 = ordenativo3;
    }


    public int getOrdenativo4() {
        return ordenativo4;
    }


    public void setOrdenativo4(int ordenativo4) {
        this.ordenativo4 = ordenativo4;
    }


    public int getOrdenativo5() {
        return ordenativo5;
    }


    public void setOrdenativo5(int ordenativo5) {
        this.ordenativo5 = ordenativo5;
    }


    public int getOrdenativo6() {
        return ordenativo6;
    }


    public void setOrdenativo6(int ordenativo6) {
        this.ordenativo6 = ordenativo6;
    }


    public boolean isConexiondirecta() {
        return conexiondirecta;
    }


    public void setConexiondirecta(boolean conexiondirecta) {
        this.conexiondirecta = conexiondirecta;
    }





}