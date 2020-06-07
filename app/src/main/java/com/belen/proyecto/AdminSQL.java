package com.belen.proyecto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;


public class AdminSQL extends SQLiteOpenHelper {
    public final static String NOMBRE_BD = "desescalada.bd";
    public final static int VERSION_BD = 1;
    private SQLiteDatabase sqlLiteDB;
    private static AdminSQL sInstance;
    private final String user_consult = "SELECT user,password,nombre,apellidos,email FROM Usuarios";

    public static synchronized AdminSQL getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AdminSQL(context.getApplicationContext());
        }
        return sInstance;
    }

    public AdminSQL(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    public void abrirBD() {
        if (sqlLiteDB == null || !sqlLiteDB.isOpen()) {
            sqlLiteDB = sInstance.getWritableDatabase();
        }
    }

    public void pecharBD() {
        if (sqlLiteDB != null && sqlLiteDB.isOpen()) {
            sqlLiteDB.close();
        }
    }

    public ArrayList<Provincia> recuperaProvincias() {
        Cursor datosConsulta = sqlLiteDB.rawQuery("Select provincia,fase FROM Provincias", null);
        ArrayList<Provincia> listitems = new ArrayList<>();
        if (datosConsulta.moveToFirst()) {
            while (!datosConsulta.isAfterLast()) {
                listitems.add(new Provincia(datosConsulta.getString(0),
                        datosConsulta.getString(1)));
                datosConsulta.moveToNext();
            }

        }
        return listitems;
    }
    public ArrayList<String> recuperanombreProvincias() {
        Cursor datosConsulta = sqlLiteDB.rawQuery("Select provincia FROM Provincias", null);
        ArrayList<String> listitems = new ArrayList<>();
        if (datosConsulta.moveToFirst()) {
            while (!datosConsulta.isAfterLast()) {
                listitems.add(datosConsulta.getString(0));
                datosConsulta.moveToNext();
            }

        }
        return listitems;
    }
    public long addfoto (String provincia, String ruta){
        ContentValues nuevo = new ContentValues();
        nuevo.put("provincia", provincia);
        nuevo.put("ruta", ruta);
        long id = sqlLiteDB.replaceOrThrow("Fotos",null,nuevo);

        return id;
    }
    public ArrayList<String> recuperaruta(String provincia) {
        Cursor datosConsulta = sqlLiteDB.rawQuery("Select ruta FROM Fotos where provincia = ?", new String[] { provincia });
        ArrayList<String> listitems = new ArrayList<>();
        Log.d("aviso 10","aquí llego");
        if (datosConsulta.moveToFirst()) {
            while (!datosConsulta.isAfterLast()) {
                Log.d("aviso 12","bucle"+datosConsulta.getString(0));
                listitems.add(datosConsulta.getString(0));

                datosConsulta.moveToNext();
            }

        }
        return listitems;
    }

    public ArrayList<Fase> recuperaFase() {
        Cursor datosConsulta = sqlLiteDB.rawQuery("Select fase,normas FROM Fases", null);
        ArrayList<Fase> listitems = new ArrayList<>();
        if (datosConsulta.moveToFirst()) {
            while (!datosConsulta.isAfterLast()) {
                listitems.add(new Fase(datosConsulta.getString(0),
                        datosConsulta.getString(1)));
                datosConsulta.moveToNext();
            }

        }
        return listitems;
    }
    public ArrayList<String> recuperanombreFase() {
        Cursor datosConsulta = sqlLiteDB.rawQuery("Select fase FROM Fases", null);
        ArrayList<String> listitems = new ArrayList<>();
        if (datosConsulta.moveToFirst()) {
            while (!datosConsulta.isAfterLast()) {
                listitems.add(datosConsulta.getString(0));
                datosConsulta.moveToNext();
            }

        }
        return listitems;
    }
    public ArrayList<String> checkfase(String provincia) {
        Cursor datosConsulta = sqlLiteDB.rawQuery("Select fase FROM Provincias where provincia = ?", new String[] { provincia });
        ArrayList<String> listitems = new ArrayList<>();
        if (datosConsulta.moveToFirst()) {
            while (!datosConsulta.isAfterLast()) {
                listitems.add(datosConsulta.getString(0));

                datosConsulta.moveToNext();
            }

        }
        return listitems;
    }
    public ArrayList<String> checknormas(String fase) {
        Cursor datosConsulta = sqlLiteDB.rawQuery("Select normas FROM Fases where fase = ?", new String[] { fase });
        ArrayList<String> listitems = new ArrayList<>();
        if (datosConsulta.moveToFirst()) {
            while (!datosConsulta.isAfterLast()) {
                listitems.add(datosConsulta.getString(0));

                datosConsulta.moveToNext();
            }

        }
        return listitems;
    }

    public int updatefase(String provincia,String fase) {
        ContentValues nuevo = new ContentValues();
        nuevo.put("fase", fase);
        String condicionwhere = "provincia=?";
        String[] parametros = new String[]{provincia};
        int rexistrosafectados = sqlLiteDB.update("Provincias",nuevo,condicionwhere,parametros);
        return rexistrosafectados;
    }


    public long adduser(Usuario usuario){
        ContentValues nuevo = new ContentValues();
        nuevo.put("user", usuario.getUser());
        nuevo.put("password", usuario.getPass());
        nuevo.put("nombre", usuario.getNombre());
        nuevo.put("apellidos", usuario.getApellidos());
        nuevo.put("email", usuario.getMail());
        long id = sqlLiteDB.insert("Usuarios",null,nuevo);

        return id;
    }
    public long addfase(Fase fase){
        ContentValues nuevo = new ContentValues();
        nuevo.put("fase", fase.getFase());
        nuevo.put("normas", fase.getNormas());
        long id = sqlLiteDB.replaceOrThrow("Fases",null,nuevo);
        return id;
    }
    public boolean verificar(String username,String pw, int opc) {
        boolean existe = false;
        boolean login=false;
        Cursor datosConsulta = sqlLiteDB.rawQuery(user_consult, null);
        if (datosConsulta.moveToFirst()) {
            while (!datosConsulta.isAfterLast()) {
                String bduser = datosConsulta.getString(0);
                String bdpw = datosConsulta.getString(1);
                if (bduser.equals(username)) {
                    existe = true;
                    if (bdpw.equals(pw)){
                        login=true;
                    }
                }
                datosConsulta.moveToNext();
            }
        }
       if (opc==0){
           return existe;}
       else{
           return login;
       }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS Usuarios (user varchar primary key,password varchar,nombre varchar," +
                "apellidos varchar,email varchar)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Fases(fase varchar primary key,normas varchar)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Provincias (provincia varchar primary key,fase varchar," +
                "FOREIGN KEY(fase) REFERENCES Fases(fase))");
        db.execSQL("INSERT OR REPLACE INTO Fases (fase,normas) values ('Fase 0','Apertura de restaurantes y cafeterías con entrega para llevar." +
                " Necesario pedido previo telefónico o por internet. Paseos de niños, mayores y convivientes con franjas horarias.Realizar ejercicio al aire libre')");
        db.execSQL("INSERT OR REPLACE INTO Provincias (provincia,fase) values('A Coruña','Fase 0')");
        db.execSQL("INSERT OR REPLACE INTO Provincias  (provincia,fase) values('Lugo','Fase 0')");
        db.execSQL("INSERT OR REPLACE INTO Provincias  (provincia,fase) values('Pontevedra','Fase 0')");
        db.execSQL("INSERT OR REPLACE INTO Provincias  (provincia,fase) values('Ourense','Fase 0')");
        db.execSQL("INSERT OR REPLACE INTO Usuarios  (user,password,nombre,apellidos,email)" +
                " values ('admin','abc123.','Belen','Camino','admin@correo.com')");
        db.execSQL("CREATE TABLE IF NOT EXISTS Fotos (provincia varchar primary key,ruta varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        db.execSQL("DROP TABLE IF EXISTS Provincias");
        db.execSQL("DROP TABLE IF EXISTS Fases");
        db.execSQL("DROP TABLE IF EXISTS Fotos");
        onCreate(db);
    }
}
