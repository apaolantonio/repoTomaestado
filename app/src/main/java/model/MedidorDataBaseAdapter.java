package model;

import clases.Medidor;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


public class MedidorDataBaseAdapter
{
    private Context context;
    private AppDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public static MedidorDataBaseAdapter instance;

    public MedidorDataBaseAdapter(Context context)
    {
        this.context = context;
    }

    public static MedidorDataBaseAdapter getInstance(Context context)
    {
        if (instance==null)
        {
            instance=new MedidorDataBaseAdapter(context);
        }
        return instance;
    }

    public MedidorDataBaseAdapter abrir() throws SQLiteException
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
    public long agregar(Medidor medidor)
    {
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_RUTA_MEDIDOR,medidor.getRuta());
        valores.put(AppDataBaseHelper.CAMPO_FOLIO, medidor.getFolio());
        valores.put(AppDataBaseHelper.CAMPO_DIGSEG, medidor.getDigseg());
        valores.put(AppDataBaseHelper.CAMPO_MEDIDOR, medidor.getMedidor());
        valores.put(AppDataBaseHelper.CAMPO_DIRECCION, medidor.getDireccion());
        valores.put(AppDataBaseHelper.CAMPO_FACMUL, medidor.getFacmul());
        valores.put(AppDataBaseHelper.CAMPO_TARIFA, medidor.getTarifa());
        valores.put(AppDataBaseHelper.CAMPO_ESTANT, medidor.getEstant());
        valores.put(AppDataBaseHelper.CAMPO_PROMEDIO, medidor.getPromedio());
        valores.put(AppDataBaseHelper.CAMPO_CODUBICACION, medidor.getCodubicacion());
        valores.put(AppDataBaseHelper.CAMPO_CODMENSAJE, medidor.getCodmensaje());
        valores.put(AppDataBaseHelper.CAMPO_VALIDACION, medidor.getValidacion());
        valores.put(AppDataBaseHelper.CAMPO_INTENTOS, medidor.getIntentos());

        return db.insert(AppDataBaseHelper.TABLE_MEDIDOR, null, valores);
    }

    public void modificar(Medidor medidor)
    {
        String[] argumentos = new String[]{String.valueOf(medidor.get_id())};
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_RUTA_MEDIDOR,medidor.getRuta());
        valores.put(AppDataBaseHelper.CAMPO_FOLIO, medidor.getFolio());
        valores.put(AppDataBaseHelper.CAMPO_DIGSEG, medidor.getDigseg());
        valores.put(AppDataBaseHelper.CAMPO_MEDIDOR, medidor.getMedidor());
        valores.put(AppDataBaseHelper.CAMPO_DIRECCION, medidor.getDireccion());
        valores.put(AppDataBaseHelper.CAMPO_FACMUL, medidor.getFacmul());
        valores.put(AppDataBaseHelper.CAMPO_TARIFA, medidor.getTarifa());
        valores.put(AppDataBaseHelper.CAMPO_ESTANT, medidor.getEstant());
        valores.put(AppDataBaseHelper.CAMPO_PROMEDIO, medidor.getPromedio());
        valores.put(AppDataBaseHelper.CAMPO_CODUBICACION, medidor.getCodubicacion());
        valores.put(AppDataBaseHelper.CAMPO_CODMENSAJE, medidor.getCodmensaje());
        valores.put(AppDataBaseHelper.CAMPO_VALIDACION, medidor.getValidacion());
        valores.put(AppDataBaseHelper.CAMPO_INTENTOS, medidor.getIntentos());

        db.update(AppDataBaseHelper.TABLE_MEDIDOR, valores, AppDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }

    public void eliminar(Medidor captor)
    {
        String[] argumentos = new String[]{String.valueOf(captor.get_id())};


        db.delete(AppDataBaseHelper.TABLE_MEDIDOR, AppDataBaseHelper.CAMPO_ID_MEDIDOR + " = ?", argumentos);
    }

    public void eliminarTodos()
    {

        db.execSQL("DELETE from " + AppDataBaseHelper.TABLE_MEDIDOR + "");

    }

    public Cursor obtenerTodos()
    {
        String[] campos = {AppDataBaseHelper.CAMPO_ID_MEDIDOR,
                AppDataBaseHelper.CAMPO_RUTA_MEDIDOR,
                AppDataBaseHelper.CAMPO_FOLIO,
                AppDataBaseHelper.CAMPO_DIGSEG,
                AppDataBaseHelper.CAMPO_MEDIDOR,
                AppDataBaseHelper.CAMPO_DIRECCION,
                AppDataBaseHelper.CAMPO_FACMUL,
                AppDataBaseHelper.CAMPO_TARIFA,
                AppDataBaseHelper.CAMPO_ESTANT,
                AppDataBaseHelper.CAMPO_PROMEDIO,
                AppDataBaseHelper.CAMPO_CODUBICACION,
                AppDataBaseHelper.CAMPO_CODMENSAJE,
                AppDataBaseHelper.CAMPO_VALIDACION,
                AppDataBaseHelper.CAMPO_INTENTOS
        };

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_MEDIDOR, campos, null, null, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();
        }

        return resultado;
    }

    public Medidor buscar(int id)
    {
        Medidor medidor = null;

        String[] campos = {AppDataBaseHelper.CAMPO_ID_MEDIDOR,
                AppDataBaseHelper.CAMPO_RUTA_MEDIDOR,
                AppDataBaseHelper.CAMPO_FOLIO,
                AppDataBaseHelper.CAMPO_DIGSEG,
                AppDataBaseHelper.CAMPO_MEDIDOR,
                AppDataBaseHelper.CAMPO_DIRECCION,
                AppDataBaseHelper.CAMPO_FACMUL,
                AppDataBaseHelper.CAMPO_TARIFA,
                AppDataBaseHelper.CAMPO_ESTANT,
                AppDataBaseHelper.CAMPO_PROMEDIO,
                AppDataBaseHelper.CAMPO_CODUBICACION,
                AppDataBaseHelper.CAMPO_CODMENSAJE,
                AppDataBaseHelper.CAMPO_VALIDACION,
                AppDataBaseHelper.CAMPO_INTENTOS
        };

        String[] argumentos = {String.valueOf(id)};

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_MEDIDOR, campos, AppDataBaseHelper.CAMPO_ID_MEDIDOR + " = ?", argumentos, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();

            medidor = new Medidor();
            medidor.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_MEDIDOR)));
            medidor.setRuta(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_RUTA_MEDIDOR)));
            medidor.setFolio(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_FOLIO)));
            medidor.setDigseg(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_DIGSEG)));
            medidor.setMedidor(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_MEDIDOR)));
            medidor.setDireccion(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_DIRECCION)));
            medidor.setFacmul(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_FACMUL)));
            medidor.setTarifa(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_TARIFA)));
            medidor.setEstant(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ESTANT)));
            medidor.setPromedio(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_PROMEDIO)));
            medidor.setCodubicacion(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CODUBICACION)));
            medidor.setCodmensaje(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CODMENSAJE)));
            medidor.setValidacion(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_VALIDACION)));
            medidor.setIntentos(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_INTENTOS)));
        }

        return medidor;
    }

    public void limpiar()
    {
        db.delete(AppDataBaseHelper.TABLE_MEDIDOR, null, null);
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

    public Cursor obtenerPorRuta(String ruta)
    {
        String[] campos = {AppDataBaseHelper.CAMPO_ID_MEDIDOR,
                AppDataBaseHelper.CAMPO_RUTA_MEDIDOR,
                AppDataBaseHelper.CAMPO_FOLIO,
                AppDataBaseHelper.CAMPO_DIGSEG,
                AppDataBaseHelper.CAMPO_MEDIDOR,
                AppDataBaseHelper.CAMPO_DIRECCION,
                AppDataBaseHelper.CAMPO_FACMUL,
                AppDataBaseHelper.CAMPO_TARIFA,
                AppDataBaseHelper.CAMPO_ESTANT,
                AppDataBaseHelper.CAMPO_PROMEDIO,
                AppDataBaseHelper.CAMPO_CODUBICACION,
                AppDataBaseHelper.CAMPO_CODMENSAJE,
                AppDataBaseHelper.CAMPO_VALIDACION,
                AppDataBaseHelper.CAMPO_INTENTOS
        };

        String[] argumentos = {ruta};
        Cursor resultado = db.query(AppDataBaseHelper.TABLE_MEDIDOR, campos, AppDataBaseHelper.CAMPO_RUTA_MEDIDOR + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }

        return resultado;
    }
}