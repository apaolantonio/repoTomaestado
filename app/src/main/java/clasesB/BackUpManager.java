package clasesB;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


import clases.*;
import model.*;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.widget.Toast;



public class BackUpManager
{
    public static final String APP_PATH = "/BackUp_Ultimo";
    public static final String FILE_CAPTOR = "BackupCaptor.bkp";
    public static final String FILE_EMPLEADO = "BackupEmpleado.bkp";
    public static final String FILE_ESTADO = "backupEstado.bkp";
    public static final String FILE_MEDIDOR = "backupMedidor.bkp";
    public static final String FILE_MEDIDORCD = "backupMedidorCD.bkp";
    public static final String FILE_MEDIDORNC = "backupMedidorNC.bkp";
    public static final String FILE_MEDIDORNI = "backupMedidorNI.bkp";
    public static final String FILE_MENSAJE = "backupMensaje.bkp";
    public static final String FILE_ORDENATIVO = "backupOrdenativo.bkp";
    public static final String FILE_RUTA = "backupRuta.bkp";
    public static final String FILE_UBICACION = "backupUbicacion.bkp";

    private Context context;

    public BackUpManager(Context context)
    {
        this.context = context;
    }


    public boolean crearBackUp()
    {
        String sdStatus = Environment.getExternalStorageState();

        if ( sdStatus.equals(Environment.MEDIA_MOUNTED) == true )
        {
            try
            {
                File sdDir = Environment.getExternalStorageDirectory();

                File a = new File(sdDir.getAbsolutePath() + APP_PATH);
                File ultimo = new File(sdDir.getAbsolutePath() + "/BackUp_Penultimo");


                if ( a.exists() == false )
                {
                    a.mkdir();
                }

                copiarDirectorio(a,ultimo);



                backUpCaptor(a,sdDir);
                backUpEmpleado(a,sdDir);
                backUpEstado(a,sdDir);
                backUpMedidor(a,sdDir);
                backUpMedidorCD(a,sdDir);
                backUpMedidorNC(a,sdDir);
                backUpMedidorNI(a,sdDir);
                backUpMensaje(a,sdDir);
                backUpOrdenativo(a,sdDir);
                backUpRuta(a,sdDir);
                backUpUbicacion(a,sdDir);



                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    public boolean restaurarBackUp()
    {

        String sdStatus = Environment.getExternalStorageState();
        if ( sdStatus.equals(Environment.MEDIA_MOUNTED) == true )
        {

            restaurarCaptor();
            restaurarEmpleado();
            restaurarEstado();
            restaurarMedidor();
            restaurarMedidorNC();
            restaurarMedidorCD();
            restaurarMedidorNI();
            restaurarMsj();
            restaurarOrdenativo();
            restaurarRuta();
            restaurarUbicacion();
            return true;

        }

        else return false;
    }

    public void backUpMensaje(File a, File sdDir) throws FileNotFoundException, IOException
    {

        a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_MENSAJE);

        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(a));

        MensajeDataBaseAdapter db = MensajeDataBaseAdapter.getInstance(context);

        db.abrir();

        Cursor resultado = db.obtenerTodos();

        if ( resultado.getCount()>0 )
        {
            resultado.moveToFirst();

            do
            {

                Mensaje msj = new Mensaje();

                msj.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_MENSAJE)));
                msj.setmsj(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_MENSAJECOD)));
                msj.setMensaje(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_MENSAJE)));

                writer.writeObject(msj);

            }
            while ( resultado.moveToNext() != false );
        }

        db.cerrar();

        writer.close();
        Toast.makeText(context, "Mensaje", Toast.LENGTH_SHORT).show();
    }

    public void backUpCaptor(File a, File sdDir) throws FileNotFoundException, IOException
    {

        a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_CAPTOR);

        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(a));

        CaptorDataBaseAdapter db = CaptorDataBaseAdapter.getInstance(context);

        db.abrir();

        Captor captor = db.buscar();
        writer.writeObject(captor);

        db.cerrar();

        writer.close();
        Toast.makeText(context, "Captor", Toast.LENGTH_SHORT).show();
    }

    public void backUpEmpleado(File a, File sdDir) throws FileNotFoundException, IOException
    {

        a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_EMPLEADO);



        EmpleadoDataBaseAdapter db = EmpleadoDataBaseAdapter.getInstance(context);

        db.abrir();

        Cursor resultado = db.obtenerTodos();
        //Toast.makeText(context, ""+resultado.getCount(), Toast.LENGTH_SHORT).show();

        if ( resultado.getCount()>0 )
        {
            resultado.moveToFirst();
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(a));
            do
            {

                Empleado emp = new Empleado();

                emp.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_EMPLEADO)));
                emp.setNombre(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_EMPLEADO_NOMBRE)));
                emp.setLegajo(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_EMPLEADO_LEGAJO)));
                emp.setPass(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_EMPLEADO_PASS)));
                emp.setAdmin(Boolean.parseBoolean(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_EMPLEADO_ADMIN))));

                writer.writeObject(emp);
                //Toast.makeText(context,""+msj.get_id()+" "+msj.getmsj()+" "+msj.getMensaje() , Toast.LENGTH_SHORT).show();
            }
            while ( resultado.moveToNext() != false );
            writer.close();
        }

        db.cerrar();


        Toast.makeText(context, "Empleado", Toast.LENGTH_SHORT).show();
    }

    public void backUpEstado(File a, File sdDir) throws FileNotFoundException, IOException
    {

        a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_ESTADO);

        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(a));

        EstadoDataBaseAdapter db = EstadoDataBaseAdapter.getInstance(context);

        db.abrir();

        Cursor resultado = db.obtenerTodos();
        //Toast.makeText(context, ""+resultado.getCount(), Toast.LENGTH_SHORT).show();

        if (resultado.getCount()>0 )
        {
            resultado.moveToFirst();

            do
            {

                Estado est = new Estado();

                est.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_ESTADO)));
                est.setEstado(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ESTADO)));
                est.setId_Medidor(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_MEDIDOR_ESTADO)));
                est.setHora(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_HORA)));
                est.setSecuencia(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_SECUENCIA)));
                est.setOrdenativo1(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO1)));
                est.setOrdenativo2(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO2)));
                est.setOrdenativo3(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO3)));
                est.setOrdenativo4(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO4)));
                est.setOrdenativo5(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO5)));
                est.setOrdenativo6(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO6)));
                est.setConexiondirecta(Boolean.parseBoolean(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CONEXIONDIRECTA))));


                writer.writeObject(est);
                //Toast.makeText(context,""+msj.get_id()+" "+msj.getmsj()+" "+msj.getMensaje() , Toast.LENGTH_SHORT).show();
            }
            while ( resultado.moveToNext() != false );
        }

        db.cerrar();

        writer.close();
        Toast.makeText(context, "Estado", Toast.LENGTH_SHORT).show();
    }

    public void backUpMedidor(File a, File sdDir) throws FileNotFoundException, IOException
    {

        a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_MEDIDOR);

        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(a));

        MedidorDataBaseAdapter db = MedidorDataBaseAdapter.getInstance(context);

        db.abrir();

        Cursor resultado = db.obtenerTodos();
        //Toast.makeText(context, ""+resultado.getCount(), Toast.LENGTH_SHORT).show();

        if ( resultado.getCount()>0 )
        {
            resultado.moveToFirst();

            do
            {

                Medidor med = new Medidor();

                med.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_MEDIDOR)));
                med.setRuta(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_RUTA_MEDIDOR)));
                med.setFolio(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_FOLIO)));
                med.setDigseg(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_DIGSEG)));
                med.setMedidor(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_MEDIDOR)));
                med.setDireccion(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_DIRECCION)));
                med.setFacmul(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_FACMUL)));
                med.setTarifa(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_TARIFA)));
                med.setEstant(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ESTANT)));
                med.setPromedio(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_PROMEDIO)));
                med.setCodubicacion(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CODUBICACION)));
                med.setCodmensaje(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CODMENSAJE)));
                med.setValidacion(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_VALIDACION)));
                med.setIntentos(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_INTENTOS)));




                writer.writeObject(med);
                //Toast.makeText(context,""+msj.get_id()+" "+msj.getmsj()+" "+msj.getMensaje() , Toast.LENGTH_SHORT).show();
            }
            while ( resultado.moveToNext() != false );
        }

        db.cerrar();

        writer.close();
        Toast.makeText(context, "Medidor", Toast.LENGTH_SHORT).show();
    }

    public void backUpMedidorCD(File a, File sdDir) throws FileNotFoundException, IOException
    {

        a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_MEDIDORCD);

        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(a));

        ConexionDirectaDataBaseAdapter db = ConexionDirectaDataBaseAdapter.getInstance(context);

        db.abrir();

        Cursor resultado = db.obtenerTodos();
        //Toast.makeText(context, ""+resultado.getCount(), Toast.LENGTH_SHORT).show();

        if ( resultado.getCount()>0)
        {
            resultado.moveToFirst();

            do
            {

                MedidorConexionDirecta med = new MedidorConexionDirecta();

                med.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_CONEXIONDIRECTA)));
                med.setRuta(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CONEXION_RUTA)));
                med.setDireccion(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CONEXION_DIRECCION)));
                med.setResponsable(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CONEXION_RESPONSABLE)));
                med.setTipo(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_CONEXION_TIPO)));

                writer.writeObject(med);
                //Toast.makeText(context,""+msj.get_id()+" "+msj.getmsj()+" "+msj.getMensaje() , Toast.LENGTH_SHORT).show();
            }
            while ( resultado.moveToNext() != false );
        }

        db.cerrar();

        writer.close();
        Toast.makeText(context, "MedidorCD", Toast.LENGTH_SHORT).show();
    }

    public void backUpMedidorNC(File a, File sdDir) throws FileNotFoundException, IOException
    {

        a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_MEDIDORNC);

        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(a));

        NoCoincidenteDataBaseAdapter db = NoCoincidenteDataBaseAdapter.getInstance(context);

        db.abrir();

        Cursor resultado = db.obtenerTodos();
        //Toast.makeText(context, ""+resultado.getCount(), Toast.LENGTH_SHORT).show();

        if ( resultado.getCount()>0 )
        {
            resultado.moveToFirst();

            do
            {

                MedidorNoCoincidente med = new MedidorNoCoincidente();

                med.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_NOCOINCIDENTE)));
                med.setRuta(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_RUTA)));
                med.setMedidorAnterior(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORANT)));
                med.setMedidorActual(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORACTUAL)));
                med.setEstado(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_ESTADO)));
                med.setDireccion(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_DIRECCION)));
                med.setFolio(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_FOLIO)));
                med.setDs(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_DS)));

                writer.writeObject(med);
                //Toast.makeText(context,""+msj.get_id()+" "+msj.getmsj()+" "+msj.getMensaje() , Toast.LENGTH_SHORT).show();
            }
            while ( resultado.moveToNext() != false );
        }

        db.cerrar();

        writer.close();
        Toast.makeText(context, "MedidorNC", Toast.LENGTH_SHORT).show();
    }

    public void backUpMedidorNI(File a, File sdDir) throws FileNotFoundException, IOException
    {

        a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_MEDIDORNI);

        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(a));

        NoIncorporadoDataBaseAdapter db = NoIncorporadoDataBaseAdapter.getInstance(context);

        db.abrir();

        Cursor resultado = db.obtenerTodos();
        //Toast.makeText(context, ""+resultado.getCount(), Toast.LENGTH_SHORT).show();

        if ( resultado.getCount()>0 )
        {
            resultado.moveToFirst();

            do
            {

                MedidorNoIncorporado med = new MedidorNoIncorporado();

                med.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_NOINCORPORADO)));
                med.setRuta(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_RUTA)));
                med.setMedidor(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_MEDIDOR)));
                med.setEstado(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_ESTADO)));
                med.setDireccion(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_DIRECCION)));
                med.setFolio(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_FOLIO)));
                med.setDs(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_DS)));

                writer.writeObject(med);
                //Toast.makeText(context,""+msj.get_id()+" "+msj.getmsj()+" "+msj.getMensaje() , Toast.LENGTH_SHORT).show();
            }
            while ( resultado.moveToNext() != false );
        }

        db.cerrar();

        writer.close();
        Toast.makeText(context, "MedidorNI", Toast.LENGTH_SHORT).show();
    }

    public void backUpOrdenativo(File a, File sdDir) throws FileNotFoundException, IOException
    {

        a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_ORDENATIVO);

        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(a));

        OrdenativoDataBaseAdapter db = OrdenativoDataBaseAdapter.getInstance(context);

        db.abrir();

        Cursor resultado = db.obtenerTodos();
        //Toast.makeText(context, ""+resultado.getCount(), Toast.LENGTH_SHORT).show();

        if ( resultado.getCount()>0 )
        {
            resultado.moveToFirst();

            do
            {

                Ordenativo ord = new Ordenativo();

                ord.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_ORDENATIVO)));
                ord.setord(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORD)));
                ord.setOrdenativo(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO)));

                writer.writeObject(ord);
                //Toast.makeText(context,""+msj.get_id()+" "+msj.getmsj()+" "+msj.getMensaje() , Toast.LENGTH_SHORT).show();
            }
            while ( resultado.moveToNext() != false );
        }

        db.cerrar();

        writer.close();
        Toast.makeText(context, "Ordenativo", Toast.LENGTH_SHORT).show();
    }

    public void backUpUbicacion(File a, File sdDir) throws FileNotFoundException, IOException
    {

        a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_UBICACION);

        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(a));

        UbicacionDataBaseAdapter db = UbicacionDataBaseAdapter.getInstance(context);

        db.abrir();

        Cursor resultado = db.obtenerTodos();
        //Toast.makeText(context, ""+resultado.getCount(), Toast.LENGTH_SHORT).show();

        if (resultado.getCount()>0 )
        {
            resultado.moveToFirst();

            do
            {

                Ubicacion ubi = new Ubicacion();

                ubi.set_id(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_ID_UBICACION)));
                ubi.setcodubi(resultado.getInt(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_COD_UBICACION)));
                ubi.setUbicacion(resultado.getString(resultado.getColumnIndex(AppDataBaseHelper.CAMPO_UBICACION_MEDIDOR)));

                writer.writeObject(ubi);
                //Toast.makeText(context,""+msj.get_id()+" "+msj.getmsj()+" "+msj.getMensaje() , Toast.LENGTH_SHORT).show();
            }
            while ( resultado.moveToNext() != false );
        }

        db.cerrar();

        writer.close();
        Toast.makeText(context, "Ubicacion", Toast.LENGTH_SHORT).show();
    }

    public void backUpRuta(File a, File sdDir) throws FileNotFoundException, IOException
    {

        a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_RUTA);

        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(a));

        RutaDataBaseAdapter db = RutaDataBaseAdapter.getInstance(context);

        db.abrir();

        Cursor resultado = db.obtenerTodos();
        //Toast.makeText(context, ""+resultado.getCount(), Toast.LENGTH_SHORT).show();

        if ( resultado.getCount()>0 )
        {
            resultado.moveToFirst();

            do
            {

                Ruta ruta = new Ruta();

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




                writer.writeObject(ruta);
                //Toast.makeText(context,""+msj.get_id()+" "+msj.getmsj()+" "+msj.getMensaje() , Toast.LENGTH_SHORT).show();
            }
            while ( resultado.moveToNext() != false );
        }

        db.cerrar();

        writer.close();
        Toast.makeText(context, "Ruta", Toast.LENGTH_SHORT).show();
    }

    public void restaurarMsj()
    {
        try
        {
            File sdDir = Environment.getExternalStorageDirectory();
            File a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_MENSAJE);
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(a));
            MensajeDataBaseAdapter db = MensajeDataBaseAdapter.getInstance(context);
            db.abrir();
            db.beginTransaction();

            try
            {

                db.limpiar();
                Mensaje msj;

                try
                {
                    //Toast.makeText(context, "entro al try try try", Toast.LENGTH_SHORT).show();

                    while ((msj = (Mensaje)reader.readObject()) != null )
                    {
                        //Toast.makeText(context, "entro al while "+i, Toast.LENGTH_SHORT).show();
                        db.agregar(msj);

                    }
                }
                catch(EOFException e)
                {
                }
                reader.close();
                db.flush();
            }
            finally
            {
                db.commit();
                db.cerrar();
            }

        }
        catch (Exception e)
        {

        }
    }

    public void restaurarCaptor()
    {

        try
        {
            File sdDir = Environment.getExternalStorageDirectory();
            File a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_CAPTOR);
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(a));
            CaptorDataBaseAdapter db = CaptorDataBaseAdapter.getInstance(context);
            db.abrir();
            db.beginTransaction();

            try
            {

                db.limpiar();
                Captor captor;

                try
                {
                    //Toast.makeText(context, "entro al try try try", Toast.LENGTH_SHORT).show();

                    while ((captor = (Captor)reader.readObject()) != null )
                    {
                        //Toast.makeText(context, "entro al while "+i, Toast.LENGTH_SHORT).show();
                        db.agregar(captor);

                    }
                }
                catch(EOFException e)
                {
                }
                reader.close();
                db.flush();
            }
            finally
            {
                db.commit();
                db.cerrar();
            }

        }
        catch (Exception e)
        {

        }
    }

    public void restaurarEmpleado()
    {

        try
        {
            File sdDir = Environment.getExternalStorageDirectory();
            File a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_EMPLEADO);
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(a));
            EmpleadoDataBaseAdapter db = EmpleadoDataBaseAdapter.getInstance(context);
            db.abrir();
            db.beginTransaction();

            try
            {

                db.limpiar();
                Empleado emp;

                try
                {
                    //Toast.makeText(context, "entro al try try try", Toast.LENGTH_SHORT).show();

                    while ((emp = (Empleado)reader.readObject()) != null )
                    {
                        //Toast.makeText(context, "entro al while "+i, Toast.LENGTH_SHORT).show();
                        db.agregar(emp);

                    }
                }
                catch(EOFException e)
                {
                }
                reader.close();
                db.flush();
            }
            finally
            {
                db.commit();
                db.cerrar();
            }

        }
        catch (Exception e)
        {

        }
    }

    public void restaurarEstado()
    {
        try
        {
            File sdDir = Environment.getExternalStorageDirectory();
            File a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_ESTADO);
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(a));
            EstadoDataBaseAdapter db = EstadoDataBaseAdapter.getInstance(context);
            db.abrir();
            db.beginTransaction();

            try
            {

                db.limpiar();
                Estado est;

                try
                {
                    //Toast.makeText(context, "entro al try try try", Toast.LENGTH_SHORT).show();

                    while ((est = (Estado)reader.readObject()) != null )
                    {
                        //Toast.makeText(context, "entro al while "+i, Toast.LENGTH_SHORT).show();
                        db.agregar(est);

                    }
                }
                catch(EOFException e)
                {
                }
                reader.close();
                db.flush();
            }
            finally
            {
                db.commit();
                db.cerrar();
            }

        }
        catch (Exception e)
        {

        }

    }

    public void restaurarMedidor()
    {
        try
        {
            File sdDir = Environment.getExternalStorageDirectory();
            File a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_MEDIDOR);
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(a));
            MedidorDataBaseAdapter db = MedidorDataBaseAdapter.getInstance(context);
            db.abrir();
            db.beginTransaction();

            try
            {

                db.limpiar();
                Medidor med;

                try
                {
                    //Toast.makeText(context, "entro al try try try", Toast.LENGTH_SHORT).show();

                    while ((med = (Medidor)reader.readObject()) != null )
                    {
                        //Toast.makeText(context, "entro al while "+i, Toast.LENGTH_SHORT).show();
                        db.agregar(med);

                    }
                }
                catch(EOFException e)
                {
                }
                reader.close();
                db.flush();
            }
            finally
            {
                db.commit();
                db.cerrar();
            }

        }
        catch (Exception e)
        {

        }

    }

    public void restaurarMedidorCD()
    {
        try
        {
            File sdDir = Environment.getExternalStorageDirectory();
            File a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_MEDIDORCD);
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(a));
            ConexionDirectaDataBaseAdapter db = ConexionDirectaDataBaseAdapter.getInstance(context);
            db.abrir();
            db.beginTransaction();

            try
            {

                db.limpiar();
                MedidorConexionDirecta med;

                try
                {
                    //Toast.makeText(context, "entro al try try try", Toast.LENGTH_SHORT).show();

                    while ((med = (MedidorConexionDirecta)reader.readObject()) != null )
                    {
                        //Toast.makeText(context, "entro al while "+i, Toast.LENGTH_SHORT).show();
                        db.agregar(med);

                    }
                }
                catch(EOFException e)
                {
                }
                reader.close();
                db.flush();
            }
            finally
            {
                db.commit();
                db.cerrar();
            }

        }
        catch (Exception e)
        {

        }

    }

    public void restaurarMedidorNC()
    {
        try
        {
            File sdDir = Environment.getExternalStorageDirectory();
            File a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_MEDIDORNC);
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(a));
            NoCoincidenteDataBaseAdapter db = NoCoincidenteDataBaseAdapter.getInstance(context);
            db.abrir();
            db.beginTransaction();

            try
            {

                db.eliminarTodos();
                MedidorNoCoincidente med;

                try
                {
                    //Toast.makeText(context, "entro al try try try", Toast.LENGTH_SHORT).show();

                    while ((med = (MedidorNoCoincidente)reader.readObject()) != null )
                    {
                        //Toast.makeText(context, "entro al while "+i, Toast.LENGTH_SHORT).show();
                        db.agregar(med);

                    }
                }
                catch(EOFException e)
                {
                }
                reader.close();
                db.flush();
            }
            finally
            {
                db.commit();
                db.cerrar();
            }

        }
        catch (Exception e)
        {

        }

    }

    public void restaurarMedidorNI()
    {
        try
        {
            File sdDir = Environment.getExternalStorageDirectory();
            File a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_MEDIDORNI);
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(a));
            NoIncorporadoDataBaseAdapter db = NoIncorporadoDataBaseAdapter.getInstance(context);
            db.abrir();
            db.beginTransaction();

            try
            {

                db.eliminarTodos();
                MedidorNoIncorporado med;

                try
                {
                    //Toast.makeText(context, "entro al try try try", Toast.LENGTH_SHORT).show();

                    while ((med = (MedidorNoIncorporado)reader.readObject()) != null )
                    {
                        //Toast.makeText(context, "entro al while "+i, Toast.LENGTH_SHORT).show();
                        db.agregar(med);

                    }
                }
                catch(EOFException e)
                {
                }
                reader.close();
                db.flush();
            }
            finally
            {
                db.commit();
                db.cerrar();
            }

        }
        catch (Exception e)
        {

        }

    }

    public void restaurarRuta()
    {
        try
        {
            File sdDir = Environment.getExternalStorageDirectory();
            File a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_RUTA);
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(a));
            RutaDataBaseAdapter db = RutaDataBaseAdapter.getInstance(context);
            db.abrir();
            db.beginTransaction();

            try
            {

                db.eliminarTodos();
                Ruta ruta;

                try
                {
                    //Toast.makeText(context, "entro al try try try", Toast.LENGTH_SHORT).show();

                    while ((ruta = (Ruta)reader.readObject()) != null )
                    {
                        //Toast.makeText(context, "entro al while "+i, Toast.LENGTH_SHORT).show();
                        db.agregar(ruta);

                    }
                }
                catch(EOFException e)
                {
                }
                reader.close();
                db.flush();
            }
            finally
            {
                db.commit();
                db.cerrar();
            }

        }
        catch (Exception e)
        {

        }

    }

    public void restaurarOrdenativo()
    {
        try
        {
            File sdDir = Environment.getExternalStorageDirectory();
            File a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_ORDENATIVO);
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(a));
            OrdenativoDataBaseAdapter db = OrdenativoDataBaseAdapter.getInstance(context);
            db.abrir();
            db.beginTransaction();

            try
            {

                db.eliminarTodos();
                Ordenativo ord;

                try
                {
                    //Toast.makeText(context, "entro al try try try", Toast.LENGTH_SHORT).show();

                    while ((ord = (Ordenativo)reader.readObject()) != null )
                    {
                        //Toast.makeText(context, "entro al while "+i, Toast.LENGTH_SHORT).show();
                        db.agregar(ord);

                    }
                }
                catch(EOFException e)
                {
                }
                reader.close();
                db.flush();
            }
            finally
            {
                db.commit();
                db.cerrar();
            }

        }
        catch (Exception e)
        {

        }

    }

    public void restaurarUbicacion()
    {
        try
        {
            File sdDir = Environment.getExternalStorageDirectory();
            File a = new File(sdDir.getAbsolutePath() + APP_PATH, FILE_UBICACION);
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(a));
            UbicacionDataBaseAdapter db = UbicacionDataBaseAdapter.getInstance(context);
            db.abrir();
            db.beginTransaction();

            try
            {

                db.eliminarTodos();
                Ubicacion ubi;

                try
                {
                    //Toast.makeText(context, "entro al try try try", Toast.LENGTH_SHORT).show();

                    while ((ubi = (Ubicacion)reader.readObject()) != null )
                    {
                        //Toast.makeText(context, "entro al while "+i, Toast.LENGTH_SHORT).show();
                        db.agregar(ubi);

                    }
                }
                catch(EOFException e)
                {
                }
                reader.close();
                db.flush();
            }
            finally
            {
                db.commit();
                db.cerrar();
            }

        }
        catch (Exception e)
        {

        }

    }

    public static void copiarDirectorio(File sourceLocation, File targetLocation)
            throws IOException {

        //SIEMPRE USO EL IF PORQUE COPIO DE DIRECTORIOS A DIRECTORIOS,
        //PERO DEJO EL ELSE POR LAS DUDAS QUE EN ALGUN MOMENTO NECESITE
        //COPIAR UN SOLO ARCHIVO.
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i = 0; i < sourceLocation.listFiles().length; i++) {

                copiarDirectorio(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);

            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[512*1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }

    }


}