package model;

import clases.Ruta;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


public class RutaDataBaseAdapter
{
    private static Context context;
    private AppDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public static RutaDataBaseAdapter instance;



    public RutaDataBaseAdapter(Context context)
    {
        RutaDataBaseAdapter.context = context;
    }

    public static RutaDataBaseAdapter getInstance(Context context)
    {
        if(instance==null)
        {
            instance= new RutaDataBaseAdapter(context);
        }
        return instance;
    }

    public RutaDataBaseAdapter abrir() throws SQLiteException
    {
        dbHelper= AppDataBaseHelper.getInstance(context);
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

    public long agregar(Ruta ruta)
    {
        ContentValues valores = new ContentValues();


        valores.put(AppDataBaseHelper.CAMPO_RUTA,ruta.getRuta());
        valores.put(AppDataBaseHelper.CAMPO_PLAN, ruta.getPlan());
        valores.put(AppDataBaseHelper.CAMPO_PARAM, ruta.getParam());
        valores.put(AppDataBaseHelper.CAMPO_CANTCLIENTES, ruta.getCantclientes());
        valores.put(AppDataBaseHelper.CAMPO_TOTALMEDIDORES, ruta.getTotalmedidores());
        valores.put(AppDataBaseHelper.CAMPO_MEDIDORESTOMADOS, ruta.getMedidorestomados());
        valores.put(AppDataBaseHelper.CAMPO_REGINICMEDIDORES, ruta.getReginicmedidores());
        valores.put(AppDataBaseHelper.CAMPO_ULTSEC_FOL_DS_SENTIDOTE, ruta.getUltsec_fol_ds_sentidote());
        valores.put(AppDataBaseHelper.CAMPO_RUTAINT, ruta.getrutaInt());

        return db.insert(AppDataBaseHelper.TABLE_RUTA, null, valores);
    }

    public void modificar(Ruta ruta)
    {
        String[] argumentos = new String[]{String.valueOf(ruta.get_id())};
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_RUTA,ruta.getRuta());
        valores.put(AppDataBaseHelper.CAMPO_PLAN, ruta.getPlan());
        valores.put(AppDataBaseHelper.CAMPO_PARAM, ruta.getParam());
        valores.put(AppDataBaseHelper.CAMPO_CANTCLIENTES, ruta.getCantclientes());
        valores.put(AppDataBaseHelper.CAMPO_TOTALMEDIDORES, ruta.getTotalmedidores());
        valores.put(AppDataBaseHelper.CAMPO_MEDIDORESTOMADOS, ruta.getMedidorestomados());
        valores.put(AppDataBaseHelper.CAMPO_REGINICMEDIDORES, ruta.getReginicmedidores());
        valores.put(AppDataBaseHelper.CAMPO_ULTSEC_FOL_DS_SENTIDOTE, ruta.getUltsec_fol_ds_sentidote());
        valores.put(AppDataBaseHelper.CAMPO_RUTAINT, ruta.getrutaInt());

        db.update(AppDataBaseHelper.TABLE_RUTA, valores, AppDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }

    public void eliminar(Ruta ruta)
    {
        String[] argumentos = new String[]{String.valueOf(ruta.get_id())};


        db.delete(AppDataBaseHelper.TABLE_RUTA, AppDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }

    public void eliminarTodos()
    {

        db.execSQL("DELETE from " + AppDataBaseHelper.TABLE_RUTA + "");

    }

    public Cursor obtenerTodos()
    {

        String[] campos = {AppDataBaseHelper.CAMPO_ID,
                AppDataBaseHelper.CAMPO_RUTA,
                AppDataBaseHelper.CAMPO_PLAN,
                AppDataBaseHelper.CAMPO_PARAM,
                AppDataBaseHelper.CAMPO_CANTCLIENTES,
                AppDataBaseHelper.CAMPO_TOTALMEDIDORES,
                AppDataBaseHelper.CAMPO_MEDIDORESTOMADOS,
                AppDataBaseHelper.CAMPO_REGINICMEDIDORES,
                AppDataBaseHelper.CAMPO_ULTSEC_FOL_DS_SENTIDOTE,
                AppDataBaseHelper.CAMPO_RUTAINT
        };

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_RUTA, campos, null, null, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public Ruta buscar(int id)
    {
        Ruta ruta = null;

        String[] campos = {AppDataBaseHelper.CAMPO_ID,
                AppDataBaseHelper.CAMPO_RUTA,
                AppDataBaseHelper.CAMPO_PLAN,
                AppDataBaseHelper.CAMPO_PARAM,
                AppDataBaseHelper.CAMPO_CANTCLIENTES,
                AppDataBaseHelper.CAMPO_TOTALMEDIDORES,
                AppDataBaseHelper.CAMPO_MEDIDORESTOMADOS,
                AppDataBaseHelper.CAMPO_REGINICMEDIDORES,
                AppDataBaseHelper.CAMPO_ULTSEC_FOL_DS_SENTIDOTE,
                AppDataBaseHelper.CAMPO_RUTAINT
        };

        String[] argumentos = {String.valueOf(id)};

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_RUTA, campos, AppDataBaseHelper.CAMPO_ID + " = ?", argumentos, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();

            ruta = new Ruta();
            ruta.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID)));
            ruta.setRuta(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_RUTA)));
            ruta.setPlan(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_PLAN)));
            ruta.setParam(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_PARAM)));
            ruta.setCantclientes(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CANTCLIENTES)));
            ruta.setTotalmedidores(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_TOTALMEDIDORES)));
            ruta.setMedidorestomados(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_MEDIDORESTOMADOS)));
            ruta.setReginicmedidores(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_REGINICMEDIDORES)));
            ruta.setUltsec_fol_ds_sentidote(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ULTSEC_FOL_DS_SENTIDOTE)));
            ruta.setrutaInt(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_RUTAINT)));
        }

        return ruta;
    }

    public void limpiar()
    {
        db.delete(AppDataBaseHelper.TABLE_RUTA, null, null);
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

    public static Context getAppContext() {
        return RutaDataBaseAdapter.context;
    }


}