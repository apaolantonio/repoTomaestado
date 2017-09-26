package clases;

import java.io.Serializable;

public class Ruta implements Serializable {

    private static final long serialVersionUID = 1L;
    int _id;
    String ruta;
    String plan;
    int param;
    int cantclientes;
    int totalmedidores;
    int medidorestomados;
    int reginicmedidores;
    int ultsec_fol_ds_sentidote;
    String rutaInt;

    public Ruta() {
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

    public void setrutaInt(String rutaInt) {
        this.rutaInt = rutaInt;
    }

    public String getrutaInt() {
        return rutaInt;
    }



    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public int getParam() {
        return param;
    }

    public void setParam(int param) {
        this.param = param;
    }

    public int getCantclientes() {
        return cantclientes;
    }

    public void setCantclientes(int cantclientes) {
        this.cantclientes = cantclientes;
    }

    public int getTotalmedidores() {
        return totalmedidores;
    }

    public void setTotalmedidores(int totalmedidores) {
        this.totalmedidores = totalmedidores;
    }

    public int getMedidorestomados() {
        return medidorestomados;
    }

    public void setMedidorestomados(int medidorestomados) {
        this.medidorestomados = medidorestomados;
    }

    public int getReginicmedidores() {
        return reginicmedidores;
    }

    public void setReginicmedidores(int reginicmedidores) {
        this.reginicmedidores = reginicmedidores;
    }

    public int getUltsec_fol_ds_sentidote() {
        return ultsec_fol_ds_sentidote;
    }

    public void setUltsec_fol_ds_sentidote(int ultsec_fol_ds_sentidote) {
        this.ultsec_fol_ds_sentidote = ultsec_fol_ds_sentidote;
    }







}
