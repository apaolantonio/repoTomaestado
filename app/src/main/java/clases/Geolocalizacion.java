package clases;

import java.io.Serializable;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


public class Geolocalizacion implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int _id;
    Double latitud;
    Double longitud;
    private Context context;
    private LocationManager lm;
    private String proveedor;


    public Geolocalizacion(Context c)
    {


        lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);




    }




    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public Double getLatitud() {
        return latitud;
    }
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
    public Double getLongitud() {
        return longitud;
    }
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }


    public Location getUltimaPosicionConocida()
    {
        return lm.getLastKnownLocation(proveedor);
    }


    public boolean setUpProveedor(String proveedor, LocationListener listener)
    {
        if ( lm.isProviderEnabled(proveedor) == true )
        {
            this.proveedor = proveedor;

            lm.requestLocationUpdates(proveedor, 1000, 0, listener);

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean habilitado()
    {

        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        }

        else return false;
    }


}