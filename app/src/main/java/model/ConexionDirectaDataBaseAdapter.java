package model;

import clases.MedidorConexionDirecta;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;


public class ConexionDirectaDataBaseAdapter
{
    private Context context;
    private AppDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public static ConexionDirectaDataBaseAdapter instance;

    public ConexionDirectaDataBaseAdapter(Context context)
    {
        this.context = context;
    }

    public static ConexionDirectaDataBaseAdapter getInstance(Context context)
    {
        if (instance==null)
        {
            instance=new ConexionDirectaDataBaseAdapter(context);
        }
        return instance;
    }

    public ConexionDirectaDataBaseAdapter abrir() throws SQLiteException
    {
        dbHelper = AppDataBaseHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
        //dbHelper.crearTablaEstado(db);

        return this;
    }


    public void cerrar()
    {
        if ( db != null )
        {
            db.close();
        }
    }
    public void agregar(MedidorConexionDirecta cDirecta)
    {
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_CONEXION_RUTA, cDirecta.getRuta());
        valores.put(AppDataBaseHelper.CAMPO_CONEXION_DIRECCION, cDirecta.getDireccion());
        valores.put(AppDataBaseHelper.CAMPO_CONEXION_RESPONSABLE, cDirecta.getResponsable());
        valores.put(AppDataBaseHelper.CAMPO_CONEXION_TIPO, cDirecta.getTipo());

        db.insert(AppDataBaseHelper.TABLE_CONEXIONDIRECTA, null, valores);
        Toast.makeText(context, "Se agregó un registro.", Toast.LENGTH_LONG).show();


    }

    public void modificar(MedidorConexionDirecta cDirecta)
    {
        String[] argumentos = new String[]{String.valueOf(cDirecta.get_id())};
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_CONEXION_RUTA, cDirecta.getRuta());
        valores.put(AppDataBaseHelper.CAMPO_CONEXION_DIRECCION, cDirecta.getDireccion());
        valores.put(AppDataBaseHelper.CAMPO_CONEXION_RESPONSABLE, cDirecta.getResponsable());
        valores.put(AppDataBaseHelper.CAMPO_CONEXION_TIPO, cDirecta.getTipo());

        db.update(AppDataBaseHelper.TABLE_CONEXIONDIRECTA, valores, AppDataBaseHelper.CAMPO_ID_CONEXIONDIRECTA + " = ?", argumentos);
    }

    public void eliminar(MedidorConexionDirecta cDirecta)
    {
        String[] argumentos = new String[]{String.valueOf(cDirecta.get_id())};


        db.delete(AppDataBaseHelper.TABLE_CONEXIONDIRECTA, AppDataBaseHelper.CAMPO_ID_CONEXIONDIRECTA + " = ?", argumentos);
    }

    public void eliminarTodos(Context context)
    {
        db.delete(AppDataBaseHelper.TABLE_CONEXIONDIRECTA, null, null);


    }

    public Cursor obtenerTodos()
    {
        String[] campos = {AppDataBaseHelper.CAMPO_ID_CONEXIONDIRECTA,
                AppDataBaseHelper.CAMPO_CONEXION_RUTA,
                AppDataBaseHelper.CAMPO_CONEXION_DIRECCION,
                AppDataBaseHelper.CAMPO_CONEXION_RESPONSABLE,
                AppDataBaseHelper.CAMPO_CONEXION_TIPO
        };

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_CONEXIONDIRECTA, campos, null, null, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();
        }

        return resultado;
    }

    public MedidorConexionDirecta buscar(String id)
    {
        MedidorConexionDirecta cDirecta = null;

        String[] campos = {AppDataBaseHelper.CAMPO_ID_CONEXIONDIRECTA,
                AppDataBaseHelper.CAMPO_CONEXION_RUTA,
                AppDataBaseHelper.CAMPO_CONEXION_DIRECCION,
                AppDataBaseHelper.CAMPO_CONEXION_RESPONSABLE,
                AppDataBaseHelper.CAMPO_CONEXION_TIPO
        };


        String[] argumentos = {id};

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_CONEXIONDIRECTA, campos, AppDataBaseHelper.CAMPO_CONEXION_DIRECCION + " = ?", argumentos, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();

            cDirecta = new MedidorConexionDirecta();
            cDirecta.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_CONEXIONDIRECTA)));
            cDirecta.setRuta(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CONEXION_RUTA)));
            cDirecta.setDireccion(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CONEXION_DIRECCION)));
            cDirecta.setResponsable(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CONEXION_RESPONSABLE)));
            cDirecta.setTipo(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CONEXION_TIPO)));
        }
        return cDirecta;
    }


    public Cursor obtenerPorRuta(String ruta)
    {
        String[] campos =
                {
                        AppDataBaseHelper.CAMPO_ID_CONEXIONDIRECTA,
                        AppDataBaseHelper.CAMPO_CONEXION_RUTA,
                        AppDataBaseHelper.CAMPO_CONEXION_DIRECCION,
                        AppDataBaseHelper.CAMPO_CONEXION_RESPONSABLE,
                        AppDataBaseHelper.CAMPO_CONEXION_TIPO

                };

        String[] argumentos = {ruta};
        Cursor resultado = db.query(AppDataBaseHelper.TABLE_CONEXIONDIRECTA, campos, AppDataBaseHelper.CAMPO_CONEXION_RUTA + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }

        return resultado;
    }
    public void limpiar()
    {
        db.delete(AppDataBaseHelper.TABLE_CONEXIONDIRECTA, null, null);
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