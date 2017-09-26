package model;

import clases.Empleado;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


public class EmpleadoDataBaseAdapter
{
    private Context context;
    private AppDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public static EmpleadoDataBaseAdapter instance;

    public EmpleadoDataBaseAdapter(Context context)
    {
        this.context = context;
    }

    public static EmpleadoDataBaseAdapter getInstance(Context context)
    {
        if (instance==null)
        {
            instance=new EmpleadoDataBaseAdapter(context);
        }
        return instance;
    }

    public EmpleadoDataBaseAdapter abrir() throws SQLiteException
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
    public void agregar(Empleado emp)
    {
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_EMPLEADO_ADMIN,emp.isAdmin());
        valores.put(AppDataBaseHelper.CAMPO_EMPLEADO_LEGAJO, emp.getLegajo());
        valores.put(AppDataBaseHelper.CAMPO_EMPLEADO_NOMBRE, emp.getNombre());
        valores.put(AppDataBaseHelper.CAMPO_EMPLEADO_PASS, emp.getPass());

        db.insert(AppDataBaseHelper.TABLE_EMPLEADO, null, valores);


    }

    public void eliminarEmp(Empleado emp)
    {

        String[] argumentos = new String[]{String.valueOf(emp.get_id())};
        db.delete(AppDataBaseHelper.TABLE_EMPLEADO, AppDataBaseHelper.CAMPO_ID_EMPLEADO + " = ?", argumentos);


    }



    public void eliminarTodos()
    {
        db.delete(AppDataBaseHelper.TABLE_EMPLEADO, null, null);


    }

    public void limpiar()
    {
        db.delete(AppDataBaseHelper.TABLE_EMPLEADO, null, null);
    }



    public Empleado buscar(int id)
    {
        Empleado emp = null;


        String[] campos = {AppDataBaseHelper.CAMPO_ID_EMPLEADO,
                AppDataBaseHelper.CAMPO_EMPLEADO_NOMBRE,
                AppDataBaseHelper.CAMPO_EMPLEADO_LEGAJO,
                AppDataBaseHelper.CAMPO_EMPLEADO_PASS,
                AppDataBaseHelper.CAMPO_EMPLEADO_ADMIN
        };



        String[] argumentos = {String.valueOf(id)};

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_EMPLEADO, campos, AppDataBaseHelper.CAMPO_ID_EMPLEADO + " = ?", argumentos, null, null, null);

        if (resultado.getCount()>0)
        {
            resultado.moveToFirst();

            emp = new Empleado();
            emp.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_EMPLEADO)));
            emp.setNombre(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_EMPLEADO_NOMBRE)));
            emp.setLegajo(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_EMPLEADO_LEGAJO)));
            emp.setPass(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_EMPLEADO_PASS)));
            emp.setAdmin(Boolean.parseBoolean(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_EMPLEADO_ADMIN))));
        }
        return emp;
    }

    public Cursor obtenerTodos()
    {

        String[] campos = {
                AppDataBaseHelper.CAMPO_ID_EMPLEADO,
                AppDataBaseHelper.CAMPO_EMPLEADO_NOMBRE,
                AppDataBaseHelper.CAMPO_EMPLEADO_LEGAJO,
                AppDataBaseHelper.CAMPO_EMPLEADO_PASS,
                AppDataBaseHelper.CAMPO_EMPLEADO_ADMIN
        };

        Cursor resultado = db.query(AppDataBaseHelper.TABLE_EMPLEADO, campos, null, null, null, null, null);

        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public void modificar(Empleado empleado)
    {
        String[] argumentos = new String[]{String.valueOf(empleado.get_id())};
        ContentValues valores = new ContentValues();

        valores.put(AppDataBaseHelper.CAMPO_EMPLEADO_NOMBRE,empleado.getNombre());
        valores.put(AppDataBaseHelper.CAMPO_EMPLEADO_LEGAJO, empleado.getLegajo());
        valores.put(AppDataBaseHelper.CAMPO_EMPLEADO_PASS, empleado.getPass());
        valores.put(AppDataBaseHelper.CAMPO_EMPLEADO_ADMIN, empleado.isAdmin());


        db.update(AppDataBaseHelper.TABLE_EMPLEADO, valores, AppDataBaseHelper.CAMPO_ID_EMPLEADO + " = ?", argumentos);
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