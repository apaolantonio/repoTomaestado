package model;


import clases.Geolocalizacion;
import clases.MedidorNoCoincidente;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;


public class NoCoincidenteDataBaseAdapter
{
    private Context context;
    private AppDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public static NoCoincidenteDataBaseAdapter instance;

    public NoCoincidenteDataBaseAdapter(Context context)
    {
        this.context = context;
    }

    public static NoCoincidenteDataBaseAdapter getInstance(Context context)
    {
        if (instance==null)
        {
            instance=new NoCoincidenteDataBaseAdapter(context);
        }
        return instance;
    }

    public NoCoincidenteDataBaseAdapter abrir() throws SQLiteException
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
    public void agregar(MedidorNoCoincidente nocoincidente)
    {
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_RUTA, nocoincidente.getRuta());
        valores.put(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORANT, nocoincidente.getMedidorAnterior());
        valores.put(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORACTUAL, nocoincidente.getMedidorActual());
        valores.put(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_ESTADO, nocoincidente.getEstado());
        valores.put(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_DIRECCION, nocoincidente.getDireccion());
        valores.put(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_FOLIO, nocoincidente.getFolio());
        valores.put(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_DS, nocoincidente.getDs());

        db.insert(AppDataBaseHelper.TABLE_NOCOINCIDENTE, null, valores);
        Toast.makeText(context, "Se agregó un registro.", Toast.LENGTH_LONG).show();


    }

    public void modificar(MedidorNoCoincidente nocoincidente)
    {
        String[] argumentos = new String[]{String.valueOf(nocoincidente.getMedidorAnterior())};
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_RUTA, nocoincidente.getRuta());
        valores.put(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORANT, nocoincidente.getMedidorAnterior());
        valores.put(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_ESTADO, nocoincidente.getEstado());
        valores.put(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_DIRECCION, nocoincidente.getDireccion());
        valores.put(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORACTUAL, nocoincidente.getMedidorActual());
        valores.put(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_FOLIO, nocoincidente.getFolio());
        valores.put(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_DS, nocoincidente.getDs());

        db.update(AppDataBaseHelper.TABLE_NOCOINCIDENTE, valores, AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORANT + " = ?", argumentos);
    }


    public void eliminarTodos()
    {
        db.delete(AppDataBaseHelper.TABLE_NOCOINCIDENTE, null, null);

    }

    public Cursor obtenerTodos()
    {
        String[] campos = {AppDataBaseHelper.CAMPO_ID_NOCOINCIDENTE,
                AppDataBaseHelper.CAMPO_NOCOINCIDENTE_RUTA,
                AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORANT,
                AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORACTUAL,
                AppDataBaseHelper.CAMPO_NOCOINCIDENTE_ESTADO,
                AppDataBaseHelper.CAMPO_NOCOINCIDENTE_DIRECCION,
                AppDataBaseHelper.CAMPO_NOCOINCIDENTE_FOLIO,
                AppDataBaseHelper.CAMPO_NOCOINCIDENTE_DS
        };

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_NOCOINCIDENTE, campos, null, null, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();
        }

        return resultado;
    }

    public MedidorNoCoincidente buscar(String id)
    {
        MedidorNoCoincidente nocoincidente = null;

        String[] campos = {AppDataBaseHelper.CAMPO_ID_NOCOINCIDENTE,
                AppDataBaseHelper.CAMPO_NOCOINCIDENTE_RUTA,
                AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORANT,
                AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORACTUAL,
                AppDataBaseHelper.CAMPO_NOCOINCIDENTE_ESTADO,
                AppDataBaseHelper.CAMPO_NOCOINCIDENTE_DIRECCION,
                AppDataBaseHelper.CAMPO_NOCOINCIDENTE_FOLIO,
                AppDataBaseHelper.CAMPO_NOCOINCIDENTE_DS
        };


        String[] argumentos = {id};

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_NOCOINCIDENTE, campos, AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORANT + " = ?", argumentos, null, null, null);

        if (resultado.getCount()>0)
        {
            resultado.moveToFirst();

            nocoincidente = new MedidorNoCoincidente();
            nocoincidente.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_NOCOINCIDENTE)));
            nocoincidente.setRuta(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_RUTA)));
            nocoincidente.setMedidorAnterior(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORANT)));
            nocoincidente.setMedidorActual(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORACTUAL)));
            nocoincidente.setEstado(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_ESTADO)));
            nocoincidente.setDireccion(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_DIRECCION)));
            nocoincidente.setFolio(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_FOLIO)));
            nocoincidente.setDs(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_DS)));

        }
        return nocoincidente;
    }
    public void eliminar(MedidorNoCoincidente med)
    {
        String[] argumentos = new String[]{String.valueOf(med.get_id())};
        db.delete(AppDataBaseHelper.TABLE_NOCOINCIDENTE, AppDataBaseHelper.CAMPO_ID_NOCOINCIDENTE + " = ?", argumentos);
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