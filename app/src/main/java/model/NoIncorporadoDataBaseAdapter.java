package model;

import clases.MedidorNoIncorporado;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;


public class NoIncorporadoDataBaseAdapter
{
    private Context context;
    private AppDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public static NoIncorporadoDataBaseAdapter instance;

    public NoIncorporadoDataBaseAdapter(Context context)
    {
        this.context = context;
    }

    public static NoIncorporadoDataBaseAdapter getInstance(Context context)
    {
        if (instance==null)
        {
            instance=new NoIncorporadoDataBaseAdapter(context);
        }
        return instance;
    }

    public NoIncorporadoDataBaseAdapter abrir() throws SQLiteException
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
    public void agregar(MedidorNoIncorporado noincorporado)
    {
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_NOINCORPORADO_RUTA, noincorporado.getRuta());
        valores.put(AppDataBaseHelper.CAMPO_NOINCORPORADO_MEDIDOR, noincorporado.getMedidor());
        valores.put(AppDataBaseHelper.CAMPO_NOINCORPORADO_ESTADO, noincorporado.getEstado());
        valores.put(AppDataBaseHelper.CAMPO_NOINCORPORADO_DIRECCION, noincorporado.getDireccion());
        valores.put(AppDataBaseHelper.CAMPO_NOINCORPORADO_FOLIO, noincorporado.getFolio());
        valores.put(AppDataBaseHelper.CAMPO_NOINCORPORADO_DS, noincorporado.getDs());

        db.insert(AppDataBaseHelper.TABLE_NOINCORPORADO, null, valores);
        Toast.makeText(context, "Se agregó un registro.", Toast.LENGTH_LONG).show();


    }

    public void modificar(MedidorNoIncorporado noincorporado)
    {
        String[] argumentos = new String[]{String.valueOf(noincorporado.get_id())};
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_NOINCORPORADO_RUTA, noincorporado.getRuta());
        valores.put(AppDataBaseHelper.CAMPO_NOINCORPORADO_MEDIDOR, noincorporado.getMedidor());
        valores.put(AppDataBaseHelper.CAMPO_NOINCORPORADO_ESTADO, noincorporado.getEstado());
        valores.put(AppDataBaseHelper.CAMPO_NOINCORPORADO_DIRECCION, noincorporado.getDireccion());
        valores.put(AppDataBaseHelper.CAMPO_NOINCORPORADO_FOLIO, noincorporado.getFolio());
        valores.put(AppDataBaseHelper.CAMPO_NOINCORPORADO_DS, noincorporado.getDs());

        db.update(AppDataBaseHelper.TABLE_NOINCORPORADO, valores, AppDataBaseHelper.CAMPO_ID_NOINCORPORADO + " = ?", argumentos);
    }


    public void eliminarTodos()
    {
        db.delete(AppDataBaseHelper.TABLE_NOINCORPORADO, null, null);

    }

    public Cursor obtenerTodos()
    {
        String[] campos = {AppDataBaseHelper.CAMPO_ID_NOINCORPORADO,
                AppDataBaseHelper.CAMPO_NOINCORPORADO_RUTA,
                AppDataBaseHelper.CAMPO_NOINCORPORADO_MEDIDOR,
                AppDataBaseHelper.CAMPO_NOINCORPORADO_ESTADO,
                AppDataBaseHelper.CAMPO_NOINCORPORADO_DIRECCION,
                AppDataBaseHelper.CAMPO_NOINCORPORADO_FOLIO,
                AppDataBaseHelper.CAMPO_NOINCORPORADO_DS
        };

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_NOINCORPORADO, campos, null, null, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();
        }

        return resultado;
    }

    public MedidorNoIncorporado buscar(String id)
    {
        MedidorNoIncorporado noincorporado = null;

        String[] campos = {AppDataBaseHelper.CAMPO_ID_NOINCORPORADO,
                AppDataBaseHelper.CAMPO_NOINCORPORADO_RUTA,
                AppDataBaseHelper.CAMPO_NOINCORPORADO_MEDIDOR,
                AppDataBaseHelper.CAMPO_NOINCORPORADO_ESTADO,
                AppDataBaseHelper.CAMPO_NOINCORPORADO_DIRECCION,
                AppDataBaseHelper.CAMPO_NOINCORPORADO_FOLIO,
                AppDataBaseHelper.CAMPO_NOINCORPORADO_DS
        };


        String[] argumentos = {id};

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_NOINCORPORADO, campos, AppDataBaseHelper.CAMPO_NOINCORPORADO_DIRECCION + " = ?", argumentos, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();

            noincorporado = new MedidorNoIncorporado();
            noincorporado.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_NOINCORPORADO)));
            noincorporado.setRuta(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_RUTA)));
            noincorporado.setMedidor(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_MEDIDOR)));
            noincorporado.setEstado(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_ESTADO)));
            noincorporado.setDireccion(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_DIRECCION)));
            noincorporado.setFolio(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_FOLIO)));
            noincorporado.setDs(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_DS)));
        }
        return noincorporado;
    }

    public Cursor obtenerPorRuta(String ruta)
    {
        String[] campos =
                {
                        AppDataBaseHelper.CAMPO_ID_NOINCORPORADO,
                        AppDataBaseHelper.CAMPO_NOINCORPORADO_RUTA,
                        AppDataBaseHelper.CAMPO_NOINCORPORADO_MEDIDOR,
                        AppDataBaseHelper.CAMPO_NOINCORPORADO_ESTADO,
                        AppDataBaseHelper.CAMPO_NOINCORPORADO_DIRECCION,
                        AppDataBaseHelper.CAMPO_NOINCORPORADO_FOLIO,
                        AppDataBaseHelper.CAMPO_NOINCORPORADO_DS

                };

        String[] argumentos = {ruta};
        Cursor resultado = db.query(AppDataBaseHelper.TABLE_NOINCORPORADO, campos, AppDataBaseHelper.CAMPO_NOINCORPORADO_RUTA + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }

        return resultado;
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