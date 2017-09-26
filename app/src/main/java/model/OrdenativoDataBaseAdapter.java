package model;

import clases.Ordenativo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


public class OrdenativoDataBaseAdapter
{
    private Context context;
    private AppDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public static OrdenativoDataBaseAdapter instance;

    public OrdenativoDataBaseAdapter(Context context)
    {
        this.context = context;
    }

    public static OrdenativoDataBaseAdapter getInstance(Context context)
    {
        if (instance==null)
        {
            instance=new OrdenativoDataBaseAdapter(context);
        }
        return instance;
    }

    public OrdenativoDataBaseAdapter abrir() throws SQLiteException
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

    public long agregar(Ordenativo ordenativo)
    {
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_ORD,ordenativo.getord());
        valores.put(AppDataBaseHelper.CAMPO_ORDENATIVO,ordenativo.getOrdenativo());

        return db.insert(AppDataBaseHelper.TABLE_ORDENATIVO, null, valores);
    }

    public void modificar(Ordenativo ordenativo)
    {
        String[] argumentos = new String[]{String.valueOf(ordenativo.get_id())};
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_ORDENATIVO,ordenativo.getOrdenativo());

        db.update(AppDataBaseHelper.TABLE_ORDENATIVO, valores, AppDataBaseHelper.CAMPO_ID_ORDENATIVO + " = ?", argumentos);
    }

    public void eliminar(Ordenativo ordenativo)
    {
        String[] argumentos = new String[]{String.valueOf(ordenativo.get_id())};


        db.delete(AppDataBaseHelper.TABLE_ORDENATIVO, AppDataBaseHelper.CAMPO_ORDENATIVO + " = ?", argumentos);
    }

    public void eliminarTodos()
    {

        db.execSQL("DELETE from " + AppDataBaseHelper.TABLE_ORDENATIVO + "");

    }

    public Cursor obtenerTodos()
    {
        String[] campos = {AppDataBaseHelper.CAMPO_ID_ORDENATIVO,
                AppDataBaseHelper.CAMPO_ORD,
                AppDataBaseHelper.CAMPO_ORDENATIVO

        };

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_ORDENATIVO, campos, null, null, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();
        }

        return resultado;
    }

    public Ordenativo buscar(int id)
    {
        Ordenativo ordenativo = null;

        String[] campos = {AppDataBaseHelper.CAMPO_ID_ORDENATIVO,
                AppDataBaseHelper.CAMPO_ORDENATIVO,

        };

        String[] argumentos = {String.valueOf(id)};

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_ORDENATIVO, campos, AppDataBaseHelper.CAMPO_ID_ORDENATIVO + " = ?", argumentos, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();

            ordenativo = new Ordenativo();
            ordenativo.setOrdenativo(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO)));
        }

        return ordenativo;
    }

    public void limpiar()
    {
        db.delete(AppDataBaseHelper.TABLE_ORDENATIVO, null, null);
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