package model;

import clases.Captor;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;


public class CaptorDataBaseAdapter
{
    private Context context;
    private AppDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public static CaptorDataBaseAdapter instance;

    public CaptorDataBaseAdapter(Context context)
    {
        this.context = context;
    }

    public static CaptorDataBaseAdapter getInstance(Context context)
    {
        if (instance==null)
        {
            instance=new CaptorDataBaseAdapter(context);
        }
        return instance;
    }

    public CaptorDataBaseAdapter abrir() throws SQLiteException
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
    public void agregar(Captor captor)
    {
        ContentValues valores = new ContentValues();



        valores.put(AppDataBaseHelper.CAMPO_NROCAPTOR,captor.getNroCaptor());
        valores.put(AppDataBaseHelper.CAMPO_IDCARGA, captor.getIdCarga());
        valores.put(AppDataBaseHelper.CAMPO_NROLEGAJO, captor.getLegajo());
        valores.put(AppDataBaseHelper.CAMPO_NOMBRE, captor.getNombre());
        valores.put(AppDataBaseHelper.CAMPO_PASS, captor.getPass());
        db.insert(AppDataBaseHelper.TABLE_CAPTOR, null, valores);


    }



    public void eliminarTodos()
    {
        db.delete(AppDataBaseHelper.TABLE_CAPTOR, null, null);


    }



    public Captor buscar()
    {
        Captor captor = null;


        String[] campos = {AppDataBaseHelper.CAMPO_ID_CAPTOR,
                AppDataBaseHelper.CAMPO_IDCARGA,
                AppDataBaseHelper.CAMPO_NROCAPTOR,
                AppDataBaseHelper.CAMPO_NROLEGAJO,
                AppDataBaseHelper.CAMPO_NOMBRE,
                AppDataBaseHelper.CAMPO_PASS
        };




        Cursor resultado = db.query(AppDataBaseHelper.TABLE_CAPTOR, campos, null, null, null, null, null);

        if (resultado.getCount()==1)
        {
            resultado.moveToFirst();

            captor = new Captor();
            captor.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_CAPTOR)));
            captor.setIdCarga(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_IDCARGA)));
            captor.setNroCaptor(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NROCAPTOR)));
            captor.setLegajo(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NROLEGAJO)));
            captor.setNombre(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOMBRE)));
            captor.setPass(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_PASS)));


        }
        return captor;
    }

    public void limpiar()
    {
        db.delete(AppDataBaseHelper.TABLE_ESTADO, null, null);
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