package model;

import clases.Ubicacion;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


public class UbicacionDataBaseAdapter
{
    private Context context;
    private AppDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public static UbicacionDataBaseAdapter instance;

    public UbicacionDataBaseAdapter(Context context)
    {
        this.context = context;
    }

    public static UbicacionDataBaseAdapter getInstance(Context context)
    {
        if (instance==null)
        {
            instance = new UbicacionDataBaseAdapter(context);
        }
        return instance;
    }

    public UbicacionDataBaseAdapter abrir() throws SQLiteException
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

    public long agregar(Ubicacion ubicacion)
    {
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_COD_UBICACION,ubicacion.getcodubi());
        valores.put(AppDataBaseHelper.CAMPO_UBICACION_MEDIDOR,ubicacion.getUbicacion());

        return db.insert(AppDataBaseHelper.TABLE_UBICACION, null, valores);
    }

    public void modificar(Ubicacion ubicacion)
    {
        String[] argumentos = new String[]{String.valueOf(ubicacion.get_id())};
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_COD_UBICACION,ubicacion.getcodubi());
        valores.put(AppDataBaseHelper.CAMPO_UBICACION_MEDIDOR,ubicacion.getUbicacion());

        db.update(AppDataBaseHelper.TABLE_UBICACION, valores, AppDataBaseHelper.CAMPO_ID_UBICACION + " = ?", argumentos);
    }

    public void eliminar(Ubicacion ubicacion)
    {
        String[] argumentos = new String[]{String.valueOf(ubicacion.get_id())};


        db.delete(AppDataBaseHelper.TABLE_UBICACION, AppDataBaseHelper.CAMPO_ID_UBICACION + " = ?", argumentos);
    }

    public void eliminarTodos()
    {

        db.execSQL("DELETE from " + AppDataBaseHelper.TABLE_UBICACION + "");

    }

    public Cursor obtenerTodos()
    {
        String[] campos = {AppDataBaseHelper.CAMPO_ID_UBICACION,
                AppDataBaseHelper.CAMPO_UBICACION_MEDIDOR,
                AppDataBaseHelper.CAMPO_COD_UBICACION

        };

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_UBICACION, campos, null, null, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();
        }

        return resultado;
    }

    public Ubicacion buscar(String id)
    {
        Ubicacion ubicacion = null;

        String[] campos = {AppDataBaseHelper.CAMPO_ID_UBICACION,
                AppDataBaseHelper.CAMPO_COD_UBICACION,
                AppDataBaseHelper.CAMPO_UBICACION_MEDIDOR

        };

        String[] argumentos = {String.valueOf(id)};

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_UBICACION, campos, AppDataBaseHelper.CAMPO_COD_UBICACION + " = ?", argumentos, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();

            ubicacion = new Ubicacion();
            ubicacion.setUbicacion(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_UBICACION_MEDIDOR)));
        }

        return ubicacion;
    }

    public void limpiar()
    {
        db.delete(AppDataBaseHelper.TABLE_UBICACION, null, null);
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