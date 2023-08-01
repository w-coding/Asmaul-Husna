package com.dya.asmaulhusna;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseConnect extends SQLiteOpenHelper {

    public static String dbName = "allah_names.db";
    public static int dbVersion = 1;
    public static String dbPath ="";
    Context myContext;
    public DatabaseConnect(@Nullable Context context) {
        super(context,dbName,null,dbVersion);
        this.myContext=context;
        StartWork();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    private boolean ExistDatabase(){
        File myFile = new File(dbPath+dbName);

        return myFile.exists();
    }
    private static void CopyDataBase(Context context){

        try {
            InputStream myInput = context.getAssets().open(dbName);
            OutputStream myOutput =new FileOutputStream(dbPath+dbName);
            byte [] myBuffer = new byte[1024];
            int length;
            while ((length = myInput.read(myBuffer))>0){
                myOutput.write(myBuffer,0,length);
            }
            myOutput.flush(); myOutput.close(); myInput.close();
        }catch (Exception ignored){

        }
    }

    public void StartWork(){

        dbPath = myContext.getFilesDir().getParent()+"/databases/";
        // dbPath = myContext.getDatabasePath(dbName).getPath();
        if (!ExistDatabase()){
            this.getReadableDatabase();
            CopyDataBase(myContext);
        }

    }



    Cursor readAllNamesData(){
        String query ="select * from allahname";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;

    }
}
