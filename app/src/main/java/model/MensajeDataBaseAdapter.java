package model;

import clases.Mensaje;
import clases.Ubicacion;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


public class MensajeDataBaseAdapter
{
    private Context context;
    private AppDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public static MensajeDataBaseAdapter instance;

    public MensajeDataBaseAdapter(Context context)
    {
        this.context = context;
    }

    public static MensajeDataBaseAdapter getInstance(Context context)
    {
        if (instance==null)
        {
            instance = new MensajeDataBaseAdapter(context);
        }
        return instance;
    }

    public MensajeDataBaseAdapter abrir() throws SQLiteException
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

    public long agregar(Mensaje mensaje)
    {
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_MENSAJECOD,mensaje.getmsj());
        valores.put(AppDataBaseHelper.CAMPO_MENSAJE,mensaje.getMensaje());

        return db.insert(AppDataBaseHelper.TABLE_MENSAJE, null, valores);
    }

    public void modificar(Ubicacion ubicacion)
    {
        String[] argumentos = new String[]{String.valueOf(ubicacion.get_id())};
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_CODUBICACION,ubicacion.getcodubi());
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

        db.execSQL("DELETE from " + AppDataBaseHelper.TABLE_MENSAJE + "");

    }

    public Cursor obtenerTodos()
    {
        String[] campos = {AppDataBaseHelper.CAMPO_ID_MENSAJE,
                AppDataBaseHelper.CAMPO_MENSAJECOD,
                AppDataBaseHelper.CAMPO_MENSAJE

        };

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_MENSAJE, campos, null, null, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();
        }

        return resultado;
    }

    public Mensaje buscar(String id)
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
    }

    public void limpiar()
    {
        db.delete(AppDataBaseHelper.TABLE_MENSAJE, null, null);
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