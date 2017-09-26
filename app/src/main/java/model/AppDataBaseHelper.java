package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AppDataBaseHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "DB_medidores";
    public static final int DB_VERSION = 27;
    public static final String TABLE_RUTA = "ruta";
    public static final String TABLE_MEDIDOR = "medidor";
    public static final String TABLE_EMPLEADO = "empleado";
    public static final String TABLE_ESTADO = "estado";
    public static final String TABLE_MENSAJE = "mensaje";
    public static final String TABLE_ORDENATIVO = "ordenativo";
    public static final String TABLE_UBICACION = "ubicacion";
    public static final String TABLE_CONEXIONDIRECTA = "conexiondirecta";
    public static final String TABLE_NOCOINCIDENTE = "nocoincidente";
    public static final String TABLE_NOINCORPORADO = "nocoincorporado";
    public static final String TABLE_CAPTOR = "captor";
    public static final String TABLE_GEOLOCALIZACION="geolocalizacion";


    //////RUTA/////////////
    public static final String CAMPO_ID = "_id";
    public static final String CAMPO_RUTA = "ruta";
    public static final String CAMPO_PLAN = "folio";
    public static final String CAMPO_PARAM = "digseg";
    public static final String CAMPO_CANTCLIENTES = "cantclientes";
    public static final String CAMPO_TOTALMEDIDORES = "totalmedidores";
    public static final String CAMPO_MEDIDORESTOMADOS = "medidorestomados";
    public static final String CAMPO_REGINICMEDIDORES = "reginicmedidores";
    public static final String CAMPO_ULTSEC_FOL_DS_SENTIDOTE = "ultsec_fol_ds_sentidote";
    public static final String CAMPO_RUTAINT = "rutaint";

    private static final String DROP_RUTA = "DROP TABLE IF EXISTS " + TABLE_RUTA;
    private static final String CREATE_RUTA = "CREATE TABLE " + TABLE_RUTA + " (" +
            CAMPO_ID + " integer not null primary key autoincrement," +
            CAMPO_RUTA + " text not null," +
            CAMPO_PLAN + " text not null," +
            CAMPO_PARAM + " integer not null," +
            CAMPO_CANTCLIENTES + " integer not null," +
            CAMPO_TOTALMEDIDORES + " integer not null," +
            CAMPO_MEDIDORESTOMADOS + " integer not null," +
            CAMPO_REGINICMEDIDORES + " integer not null," +
            CAMPO_RUTAINT + " text not null," +
            CAMPO_ULTSEC_FOL_DS_SENTIDOTE + " integer not null)";


    //////ATRIBUTOS DEL MEDIDOR/////////////
    public static final String CAMPO_ID_MEDIDOR = "_id";
    public static final String CAMPO_RUTA_MEDIDOR = "ruta";
    public static final String CAMPO_FOLIO = "folio";
    public static final String CAMPO_DIGSEG = "digseg";
    public static final String CAMPO_MEDIDOR = "medidor";
    public static final String CAMPO_DIRECCION = "direccion";
    public static final String CAMPO_FACMUL = "facmul";
    public static final String CAMPO_TARIFA = "tarifa";
    public static final String CAMPO_ESTANT = "estant"; //estado anterior
    public static final String CAMPO_PROMEDIO = "promedio";
    public static final String CAMPO_CODUBICACION = "codubicacion";
    public static final String CAMPO_CODMENSAJE = "codmensaje";
    public static final String CAMPO_VALIDACION="validacion";
    public static final String CAMPO_INTENTOS="intentos";

    private static final String DROP_MEDIDOR = "DROP TABLE IF EXISTS " + TABLE_MEDIDOR;
    private static final String CREATE_MEDIDOR = "CREATE TABLE " + TABLE_MEDIDOR + " (" +
            CAMPO_ID_MEDIDOR + " integer not null primary key autoincrement," +
            CAMPO_RUTA_MEDIDOR + " text not null," +
            CAMPO_FOLIO + " text not null," +
            CAMPO_DIGSEG + " text not null," +
            CAMPO_MEDIDOR + " text not null," +
            CAMPO_DIRECCION + " text not null," +
            CAMPO_FACMUL + " integer not null," +
            CAMPO_TARIFA + " text not null," +
            CAMPO_ESTANT + " text not null," +
            CAMPO_PROMEDIO + " text not null," +
            CAMPO_CODUBICACION + " text not null," +
            CAMPO_CODMENSAJE + " text not null," +
            CAMPO_INTENTOS + " integer not null," +
            CAMPO_VALIDACION + " integer not null)";


    ////////////////////ESTADO/////////////////
    public static final String CAMPO_ID_ESTADO = "_id";
    public static final String CAMPO_ESTADO = "estado";
    public static final String CAMPO_MEDIDOR_ESTADO="id_Medidor";
    public static final String CAMPO_HORA = "hora";
    public static final String CAMPO_MIN = "min";
    public static final String CAMPO_SECUENCIA = "secuencia";
    public static final String CAMPO_ORDENATIVO1 = "ordenativo1";
    public static final String CAMPO_ORDENATIVO2 = "ordenativo2";
    public static final String CAMPO_ORDENATIVO3 = "ordenativo3";
    public static final String CAMPO_ORDENATIVO4 = "ordenativo4";
    public static final String CAMPO_ORDENATIVO5 = "ordenativo5";
    public static final String CAMPO_ORDENATIVO6 = "ordenativo6";
    public static final String CAMPO_ID_GEOLOCALIZACION="geolocalizacion";
    public static final String CAMPO_CONEXIONDIRECTA = "conexiondirecta";

    private static final String DROP_ESTADO = "DROP TABLE IF EXISTS " + TABLE_ESTADO;
    private static final String CREATE_ESTADO = "CREATE TABLE " + TABLE_ESTADO + " (" +
            CAMPO_ID + " integer not null primary key autoincrement," +
            CAMPO_ESTADO + " text," +
            CAMPO_MEDIDOR_ESTADO + " text not null," + //Representa el id del medidor
            CAMPO_HORA + " text," +
            CAMPO_MIN + " text," +
            CAMPO_SECUENCIA + " integer," +
            CAMPO_ORDENATIVO1 + " integer," +
            CAMPO_ORDENATIVO2 + " integer," +
            CAMPO_ORDENATIVO3 + " integer," +
            CAMPO_ORDENATIVO4 + " integer," +
            CAMPO_ORDENATIVO5 + " integer," +
            CAMPO_ORDENATIVO6 + " integer," +
            CAMPO_ID_GEOLOCALIZACION + " integer," +
            CAMPO_CONEXIONDIRECTA + " boolean)";


    //////////////MENSAJES////////////////////
    public static final String CAMPO_ID_MENSAJE = "_id";
    public static final String CAMPO_MENSAJECOD = "msj";
    public static final String CAMPO_MENSAJE = "mensaje";

    private static final String DROP_MENSAJE = "DROP TABLE IF EXISTS " + TABLE_MENSAJE;
    private static final String CREATE_MENSAJE = "CREATE TABLE " + TABLE_MENSAJE + " (" +
            CAMPO_ID + " integer not null primary key autoincrement," +
            CAMPO_MENSAJECOD + " integer not null," +
            CAMPO_MENSAJE + " text not null)";

    ////////////ORDENATIVOS//////////////////
    public static final String CAMPO_ID_ORDENATIVO = "_id";
    public static final String CAMPO_ORD="ord";
    public static final String CAMPO_ORDENATIVO = "ordenativo";

    private static final String DROP_ORDENATIVO = "DROP TABLE IF EXISTS " + TABLE_ORDENATIVO;
    private static final String CREATE_ORDENATIVO = "CREATE TABLE " + TABLE_ORDENATIVO + " (" +
            CAMPO_ID + " integer not null primary key autoincrement," +
            CAMPO_ORD + " integer not null," +
            CAMPO_ORDENATIVO + " text not null)";

    /////////////////UBICACION//////////////////
    public static final String CAMPO_ID_UBICACION = "_id";
    public static final String CAMPO_COD_UBICACION = "codubi";
    public static final String CAMPO_UBICACION_MEDIDOR = "ubicacion";

    private static final String DROP_UBICACION = "DROP TABLE IF EXISTS " + TABLE_UBICACION;
    private static final String CREATE_UBICACION = "CREATE TABLE " + TABLE_UBICACION + " (" +
            CAMPO_ID + " integer not null primary key autoincrement," +
            CAMPO_COD_UBICACION + " integer not null," +
            CAMPO_UBICACION_MEDIDOR + " text not null)";

    /////////////////CONEXION DIRECTA//////////////////
    public static final String CAMPO_ID_CONEXIONDIRECTA = "_id";
    public static final String CAMPO_CONEXION_RUTA = "ruta";
    public static final String CAMPO_CONEXION_DIRECCION = "direccion";
    public static final String CAMPO_CONEXION_RESPONSABLE = "responsable";
    public static final String CAMPO_CONEXION_TIPO = "tipo";

    private static final String DROP_CONEXIONDIRECTA = "DROP TABLE IF EXISTS " + TABLE_CONEXIONDIRECTA;
    private static final String CREATE_CONEXIONDIRECTA = "CREATE TABLE " + TABLE_CONEXIONDIRECTA + " (" +
            CAMPO_ID_CONEXIONDIRECTA + " integer not null primary key autoincrement," +
            CAMPO_CONEXION_RUTA + " text not null," +
            CAMPO_CONEXION_DIRECCION + " text not null," +
            CAMPO_CONEXION_RESPONSABLE + " text not null," +
            CAMPO_CONEXION_TIPO + " integer not null)";

    /////////////////NO COINCIDENTE//////////////////
    public static final String CAMPO_ID_NOCOINCIDENTE = "_id";
    public static final String CAMPO_NOCOINCIDENTE_RUTA = "ruta";
    public static final String CAMPO_NOCOINCIDENTE_MEDIDORANT = "medidor";
    public static final String CAMPO_NOCOINCIDENTE_MEDIDORACTUAL = "medidoractual";
    public static final String CAMPO_NOCOINCIDENTE_ESTADO = "estado";
    public static final String CAMPO_NOCOINCIDENTE_DIRECCION = "direccion";
    public static final String CAMPO_NOCOINCIDENTE_FOLIO = "folio";
    public static final String CAMPO_NOCOINCIDENTE_DS = "ds";

    private static final String DROP_NOCOINCIDENTE = "DROP TABLE IF EXISTS " + TABLE_NOCOINCIDENTE;
    private static final String CREATE_NOCOINCIDENTE = "CREATE TABLE " + TABLE_NOCOINCIDENTE + " (" +
            CAMPO_ID_NOCOINCIDENTE + " integer not null primary key autoincrement," +
            CAMPO_NOCOINCIDENTE_RUTA + " text not null," +
            CAMPO_NOCOINCIDENTE_MEDIDORANT + " text not null," +
            CAMPO_NOCOINCIDENTE_MEDIDORACTUAL + " text not null," +
            CAMPO_NOCOINCIDENTE_ESTADO + " text not null," +
            CAMPO_NOCOINCIDENTE_DIRECCION + " text not null," +
            CAMPO_NOCOINCIDENTE_FOLIO + " text not null," +
            CAMPO_NOCOINCIDENTE_DS + " text not null)";

    /////////////////NO INCORPORADO//////////////////
    public static final String CAMPO_ID_NOINCORPORADO = "_id";
    public static final String CAMPO_NOINCORPORADO_RUTA = "ruta";
    public static final String CAMPO_NOINCORPORADO_MEDIDOR = "medidor";
    public static final String CAMPO_NOINCORPORADO_ESTADO = "estado";
    public static final String CAMPO_NOINCORPORADO_DIRECCION = "direccion";
    public static final String CAMPO_NOINCORPORADO_FOLIO = "folio";
    public static final String CAMPO_NOINCORPORADO_DS = "ds";

    private static final String DROP_NOINCORPORADO = "DROP TABLE IF EXISTS " + TABLE_NOINCORPORADO;
    private static final String CREATE_NOINCORPORADO = "CREATE TABLE " + TABLE_NOINCORPORADO + " (" +
            CAMPO_ID_NOINCORPORADO + " integer not null primary key autoincrement," +
            CAMPO_NOINCORPORADO_RUTA + " text not null," +
            CAMPO_NOINCORPORADO_MEDIDOR + " text not null," +
            CAMPO_NOINCORPORADO_ESTADO + " text not null," +
            CAMPO_NOINCORPORADO_DIRECCION + " text not null," +
            CAMPO_NOINCORPORADO_FOLIO + " text not null," +
            CAMPO_NOINCORPORADO_DS + " text not null)";

    /////////////////CAPTOR//////////////////
    public static final String CAMPO_ID_CAPTOR = "_id";
    public static final String CAMPO_IDCARGA = "idcarga";
    public static final String CAMPO_NROCAPTOR = "captor";
    public static final String CAMPO_NROLEGAJO = "legajo";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_PASS = "pass";

    private static final String DROP_CAPTOR = "DROP TABLE IF EXISTS " + TABLE_CAPTOR;
    private static final String CREATE_CAPTOR = "CREATE TABLE " + TABLE_CAPTOR + " (" +
            CAMPO_ID_CAPTOR + " integer not null primary key autoincrement," +
            CAMPO_IDCARGA + " integer not null," +
            CAMPO_NROCAPTOR + " integer not null," +
            CAMPO_NROLEGAJO + " integer not null," +
            CAMPO_NOMBRE + " text not null," +
            CAMPO_PASS + " text not null)";


    /////////////////EMPLEADO//////////////////
    public static final String CAMPO_ID_EMPLEADO = "_id";
    public static final String CAMPO_EMPLEADO_NOMBRE = "nombre";
    public static final String CAMPO_EMPLEADO_LEGAJO = "legajo";
    public static final String CAMPO_EMPLEADO_PASS = "pass";
    public static final String CAMPO_EMPLEADO_ADMIN = "admin";


    private static final String DROP_EMPLEADO = "DROP TABLE IF EXISTS " + TABLE_EMPLEADO;
    private static final String CREATE_EMPLEADO = "CREATE TABLE " + TABLE_EMPLEADO + " (" +
            CAMPO_ID_EMPLEADO + " integer not null primary key autoincrement," +
            CAMPO_EMPLEADO_NOMBRE + " text not null," +
            CAMPO_EMPLEADO_LEGAJO + " integer not null," +
            CAMPO_EMPLEADO_PASS + " text not null," +
            CAMPO_EMPLEADO_ADMIN + " boolean not null)";

    //////////////GEOLOCALIACION////////////////////
    public static final String CAMPO_GEOLOCALIZACION_ID = "_id";
    public static final String CAMPO_GEO_LATITUD = "latitud";
    public static final String CAMPO_GEO_LONGITUD = "longitud";

    private static final String DROP_GEOLOCALIZACION = "DROP TABLE IF EXISTS " + TABLE_GEOLOCALIZACION;
    private static final String CREATE_GEOLOCALIZACION = "CREATE TABLE " + TABLE_GEOLOCALIZACION + " (" +
            CAMPO_GEOLOCALIZACION_ID + " integer not null primary key," +
            CAMPO_GEO_LATITUD + " float not null," +
            CAMPO_GEO_LONGITUD + " float not null)";


    public static AppDataBaseHelper instance;
    static Context context;



    public AppDataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    public static AppDataBaseHelper getInstance(Context context)
    {
        if ( instance == null )
        {
            instance = new AppDataBaseHelper(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_RUTA);
        db.execSQL(CREATE_MEDIDOR);
        db.execSQL(CREATE_ESTADO);
        db.execSQL(CREATE_CONEXIONDIRECTA);
        db.execSQL(CREATE_NOCOINCIDENTE);
        db.execSQL(CREATE_ORDENATIVO);
        db.execSQL(CREATE_NOINCORPORADO);
        db.execSQL(CREATE_MENSAJE);
        db.execSQL(CREATE_CAPTOR);
        db.execSQL(CREATE_GEOLOCALIZACION);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DROP_RUTA);
        db.execSQL(DROP_MEDIDOR);
        db.execSQL(DROP_ESTADO);
        db.execSQL(DROP_CONEXIONDIRECTA);
        db.execSQL(DROP_NOCOINCIDENTE);
        db.execSQL(DROP_ORDENATIVO);
        db.execSQL(DROP_NOINCORPORADO);
        db.execSQL(DROP_UBICACION);
        db.execSQL(DROP_MENSAJE);
        db.execSQL(DROP_CAPTOR);
        db.execSQL(DROP_EMPLEADO);
        db.execSQL(DROP_GEOLOCALIZACION);

        db.execSQL(CREATE_RUTA);
        db.execSQL(CREATE_MEDIDOR);
        db.execSQL(CREATE_ESTADO);
        db.execSQL(CREATE_CONEXIONDIRECTA);
        db.execSQL(CREATE_NOCOINCIDENTE);
        db.execSQL(CREATE_ORDENATIVO);
        db.execSQL(CREATE_NOINCORPORADO);
        db.execSQL(CREATE_UBICACION);
        db.execSQL(CREATE_MENSAJE);
        db.execSQL(CREATE_CAPTOR);
        db.execSQL(CREATE_EMPLEADO);
        db.execSQL(CREATE_GEOLOCALIZACION);
    }

}