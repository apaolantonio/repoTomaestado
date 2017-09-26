package model;

import clases.Estado;
import clases.Geolocalizacion;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


public class GeolocalizacionDataBaseAdapter
{
    private Context context;
    private AppDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public static GeolocalizacionDataBaseAdapter instance;

    public GeolocalizacionDataBaseAdapter(Context context)
    {
        this.context = context;
    }

    public static GeolocalizacionDataBaseAdapter getInstance(Context context)
    {
        if (instance==null)
        {
            instance = new GeolocalizacionDataBaseAdapter(context);
        }
        return instance;
    }

    public GeolocalizacionDataBaseAdapter abrir() throws SQLiteException
    {
        dbHelper = AppDataBaseHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();

        return this;
    }

    public void cerrar()
    {
        if ( db != null )
        {
            db.close();
        }
    }

    public long agregar(Geolocalizacion geo)
    {
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_GEOLOCALIZACION_ID,geo.get_id());
        valores.put(AppDataBaseHelper.CAMPO_GEO_LATITUD,geo.getLatitud());
        valores.put(AppDataBaseHelper.CAMPO_GEO_LONGITUD,geo.getLongitud());

        return db.insert(AppDataBaseHelper.TABLE_GEOLOCALIZACION, null, valores);
    }

    public void modificar(Geolocalizacion geo)
    {
        String[] argumentos = new String[]{String.valueOf(geo.get_id())};
        ContentValues valores = new ContentValues();


        valores.put(AppDataBaseHelper.CAMPO_GEO_LATITUD,geo.getLatitud());
        valores.put(AppDataBaseHelper.CAMPO_GEO_LONGITUD,geo.getLongitud());

        db.update(AppDataBaseHelper.TABLE_GEOLOCALIZACION, valores, AppDataBaseHelper.CAMPO_GEOLOCALIZACION_ID + " = ?", argumentos);
    }

    public void eliminar(Geolocalizacion geo)
    {
        String[] argumentos = new String[]{String.valueOf(geo.get_id())};
        db.delete(AppDataBaseHelper.TABLE_GEOLOCALIZACION, AppDataBaseHelper.CAMPO_GEOLOCALIZACION_ID + " = ?", argumentos);
    }
    public void eliminarTodos()
    {

        db.execSQL("DELETE from " + AppDataBaseHelper.TABLE_GEOLOCALIZACION + "");

    }

    public Cursor obtenerTodos()
    {
        String[] campos = {AppDataBaseHelper.CAMPO_GEOLOCALIZACION_ID,
                AppDataBaseHelper.CAMPO_GEO_LATITUD,
                AppDataBaseHelper.CAMPO_GEO_LONGITUD

        };

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_GEOLOCALIZACION, campos, null, null, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();
        }

        return resultado;
    }
	
	/*public Mensaje buscar(String id)
	{
		Mensaje mensaje = null;
		
		String[] campos = {AppDataBaseHelper.CAMPO_ID_MENSAJE,
				AppDataBaseHelper.CAMPO_MENSAJECOD,
				AppDataBaseHelper.CAMPO_MENSAJE
				
		};
		
		String[] argumentos = {String.valueOf(id)};
		
		Cursor resultado = db.query(AppDataBaseHelper.TABLE_MENSAJE, campos, AppDataBaseHelper.CAMPO_MENSAJECOD + " = ?", argumentos, null, null, null);
		
		if (resultado != null)
		{
			resultado.moveToFirst();
			
			mensaje = new Mensaje();
			mensaje.setMensaje(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_MENSAJE)));
		}
		
		return mensaje;
	}*/

    public void limpiar()
    {
        db.delete(AppDataBaseHelper.TABLE_GEOLOCALIZACION, null, null);
    }

    public void beginTransaction()
    {
        if ( db != null )
        {
            db.beginTransaction();
        }
    }

    public void flush()
    {
        if ( db != null )
        {
            db.setTransactionSuccessful();
        }
    }

    public void commit()
    {
        if ( db != null )
        {
            db.endTransaction();
        }
    }

}