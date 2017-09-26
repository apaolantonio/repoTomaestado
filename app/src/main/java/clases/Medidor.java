package clases;

import java.io.Serializable;


public class Medidor implements Serializable{

    private static final long serialVersionUID = 1L;
    int _id;
    String ruta;
    String folio;
    String digseg;
    String medidor;
    String direccion;
    int facmul;
    String tarifa;
    String estant;
    String promedio;
    String codubicacion;
    String codmensaje;
    int validacion;
    int intentos;



    public Medidor() {
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

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getDigseg() {
        return digseg;
    }

    public void setDigseg(String digseg) {
        this.digseg = digseg;
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

    public int getFacmul() {
        return facmul;
    }

    public void setFacmul(int facmul) {
        this.facmul = facmul;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public String getEstant() {
        return estant;
    }

    public void setEstant(String estant) {
        this.estant = estant;
    }

    public String getPromedio() {
        return promedio;
    }

    public void setPromedio(String promedio) {
        this.promedio = promedio;
    }

    public String getCodubicacion() {
        return codubicacion;
    }

    public void setCodubicacion(String codubicacion) {
        this.codubicacion = codubicacion;
    }

    public String getCodmensaje() {
        return codmensaje;
    }

    public void setCodmensaje(String codmensaje) {
        this.codmensaje = codmensaje;
    }

    public int getValidacion() {
        return validacion;
    }

    public void setValidacion(int validacion) {
        this.validacion = validacion;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }



}