package clases;

import java.io.Serializable;

public class Ordenativo implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int _id;
    int ord;
    String ordenativo;
    public Ordenativo() {
        super();
    }


    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public int getord() {
        return ord;
    }
    public void setord(int ord) {
        this.ord = ord;
    }
    public String getOrdenativo() {
        return ordenativo;
    }
    public void setOrdenativo(String ordenativo) {
        this.ordenativo = ordenativo;
    }



}