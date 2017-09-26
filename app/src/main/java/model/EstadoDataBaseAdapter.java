package model;

import clases.Estado;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;


public class EstadoDataBaseAdapter
{
    private Context context;
    private AppDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public static EstadoDataBaseAdapter instance;

    public EstadoDataBaseAdapter(Context context)
    {
        this.context = context;
    }

    public static EstadoDataBaseAdapter getInstance(Context context)
    {
        if (instance==null)
        {
            instance=new EstadoDataBaseAdapter(context);
        }
        return instance;
    }

    public EstadoDataBaseAdapter abrir() throws SQLiteException
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
    public void agregar(Estado estado)
    {
        ContentValues valores = new ContentValues();



        valores.put(AppDataBaseHelper.CAMPO_ESTADO,estado.getEstado());
        valores.put(AppDataBaseHelper.CAMPO_MEDIDOR_ESTADO, estado.getId_Medidor());
        valores.put(AppDataBaseHelper.CAMPO_HORA, estado.getHora());
        valores.put(AppDataBaseHelper.CAMPO_SECUENCIA, estado.getSecuencia());
        valores.put(AppDataBaseHelper.CAMPO_ORDENATIVO1, estado.getOrdenativo1());
        valores.put(AppDataBaseHelper.CAMPO_ORDENATIVO2, estado.getOrdenativo2());
        valores.put(AppDataBaseHelper.CAMPO_ORDENATIVO3, estado.getOrdenativo3());
        valores.put(AppDataBaseHelper.CAMPO_ORDENATIVO4, estado.getOrdenativo4());
        valores.put(AppDataBaseHelper.CAMPO_ORDENATIVO5, estado.getOrdenativo5());
        valores.put(AppDataBaseHelper.CAMPO_ORDENATIVO6, estado.getOrdenativo6());
        valores.put(AppDataBaseHelper.CAMPO_CONEXIONDIRECTA, estado.isConexiondirecta());
        db.insert(AppDataBaseHelper.TABLE_ESTADO, null, valores);
        Toast.makeText(context, "Se agregó un registro.", Toast.LENGTH_LONG).show();

    }

    public void modificar(Estado estado)
    {
        String[] argumentos = new String[]{String.valueOf(estado.getId_Medidor())};
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_ESTADO,estado.getEstado());
        valores.put(AppDataBaseHelper.CAMPO_MEDIDOR_ESTADO, estado.getId_Medidor());
        valores.put(AppDataBaseHelper.CAMPO_HORA, estado.getHora());
        valores.put(AppDataBaseHelper.CAMPO_SECUENCIA, estado.getSecuencia());
        valores.put(AppDataBaseHelper.CAMPO_ORDENATIVO1, estado.getOrdenativo1());
        valores.put(AppDataBaseHelper.CAMPO_ORDENATIVO2, estado.getOrdenativo2());
        valores.put(AppDataBaseHelper.CAMPO_ORDENATIVO3, estado.getOrdenativo3());
        valores.put(AppDataBaseHelper.CAMPO_ORDENATIVO4, estado.getOrdenativo4());
        valores.put(AppDataBaseHelper.CAMPO_ORDENATIVO5, estado.getOrdenativo5());
        valores.put(AppDataBaseHelper.CAMPO_ORDENATIVO6, estado.getOrdenativo6());
        valores.put(AppDataBaseHelper.CAMPO_CONEXIONDIRECTA, estado.isConexiondirecta());

        db.update(AppDataBaseHelper.TABLE_ESTADO, valores, AppDataBaseHelper.CAMPO_MEDIDOR_ESTADO + " = ?", argumentos);
        Toast.makeText(context, "Se modificó un registro.", Toast.LENGTH_LONG).show();
    }

    public void eliminar(Estado estado)
    {
        String[] argumentos = new String[]{String.valueOf(estado.get_id())};


        db.delete(AppDataBaseHelper.TABLE_ESTADO, AppDataBaseHelper.CAMPO_ID_ESTADO + " = ?", argumentos);
    }

    public void eliminarTodos()
    {
        db.delete(AppDataBaseHelper.TABLE_ESTADO, null, null);
        Toast.makeText(context, "Se eliminaron todos los estados", Toast.LENGTH_LONG).show();

    }


    public Cursor obtenerTodos()
    {

        String[] campos = {AppDataBaseHelper.CAMPO_ID_ESTADO,
                AppDataBaseHelper.CAMPO_ESTADO,
                AppDataBaseHelper.CAMPO_MEDIDOR_ESTADO,
                AppDataBaseHelper.CAMPO_HORA,
                AppDataBaseHelper.CAMPO_SECUENCIA,
                AppDataBaseHelper.CAMPO_ORDENATIVO1,
                AppDataBaseHelper.CAMPO_ORDENATIVO2,
                AppDataBaseHelper.CAMPO_ORDENATIVO3,
                AppDataBaseHelper.CAMPO_ORDENATIVO4,
                AppDataBaseHelper.CAMPO_ORDENATIVO5,
                AppDataBaseHelper.CAMPO_ORDENATIVO6,
                AppDataBaseHelper.CAMPO_CONEXIONDIRECTA,
        };

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_ESTADO, campos, null, null, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public Estado buscar(int id)
    {
        Estado estado = null;


        String[] campos = {AppDataBaseHelper.CAMPO_ID_ESTADO,
                AppDataBaseHelper.CAMPO_ESTADO,
                AppDataBaseHelper.CAMPO_MEDIDOR_ESTADO,
                AppDataBaseHelper.CAMPO_HORA,
                AppDataBaseHelper.CAMPO_SECUENCIA,
                AppDataBaseHelper.CAMPO_ORDENATIVO1,
                AppDataBaseHelper.CAMPO_ORDENATIVO2,
                AppDataBaseHelper.CAMPO_ORDENATIVO3,
                AppDataBaseHelper.CAMPO_ORDENATIVO4,
                AppDataBaseHelper.CAMPO_ORDENATIVO5,
                AppDataBaseHelper.CAMPO_ORDENATIVO6,
                AppDataBaseHelper.CAMPO_CONEXIONDIRECTA
        };


        String[] argumentos = {String.valueOf(id)};

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_ESTADO, campos, AppDataBaseHelper.CAMPO_MEDIDOR_ESTADO + " = ?", argumentos, null, null, null);

        if (resultado.getCount()>0)
        {
            resultado.moveToFirst();

            estado = new Estado();
            estado.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_ESTADO)));
            estado.setEstado(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ESTADO)));
            estado.setId_Medidor(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_MEDIDOR_ESTADO)));
            estado.setHora(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_HORA)));
            estado.setSecuencia(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_SECUENCIA)));
            estado.setOrdenativo1(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO1)));
            estado.setOrdenativo2(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO2)));
            estado.setOrdenativo3(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO3)));
            estado.setOrdenativo4(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO4)));
            estado.setOrdenativo5(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO5)));
            estado.setOrdenativo6(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO6)));
            estado.setConexiondirecta(Boolean.parseBoolean(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CONEXIONDIRECTA))));

        }
        return estado;
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
