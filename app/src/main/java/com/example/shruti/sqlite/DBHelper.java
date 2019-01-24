package com.example.shruti.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBName = "Student.db";   //database name
    public static final String TableName = "Assignment table";  //table name
    public static final String ID = "ID";
    public static final String RegisNo = "Registration No";         //column names
    public static final String StuName = "Student Name";
    public static final String  Marks = "Marks";
    private static final String CREATE_TABLE =  "create table "+TableName +"("+ ID+" INTEGER PRIMARY KEY AUTOINCREMENT ," +RegisNo +"INTEGER ,"+StuName +"TEXT ,"+Marks+"INTEGER);";

    public DBHelper( Context context) {
        super(context, DBName, null, 1);
    }
    //constructor

    //basic functions = onCreate and onUpgrade
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL (CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL ( String.format ( "Drop Table if exists%s", TableName ) );
        onCreate ( db );

    }

    public boolean insertVal(String regis, String name, String marks)
    {
        SQLiteDatabase db = this.getWritableDatabase ();    //instance of database
        ContentValues contentValues = new ContentValues (  );
        contentValues.put ( RegisNo,regis );
        contentValues.put ( StuName,name );
        contentValues.put ( Marks,marks );
        long res = db.insert(TableName,null,contentValues);
        if(res==-1)
        {
            return false;
        }
        else
            return true;


    }

    public Cursor getData()
    {
        SQLiteDatabase db =this.getWritableDatabase ();
        Cursor res = db.rawQuery ( String.format ( "select * from %s", TableName ),null );
        return res;

    }

    public boolean update(String id, String name,String marks,String regis )
    {
        SQLiteDatabase db = this.getWritableDatabase ();    //instance of database
        ContentValues contentValues = new ContentValues (  );
        contentValues.put ( RegisNo,regis );
        contentValues.put ( StuName,name );
        contentValues.put ( Marks,marks );
        contentValues.put(ID, id);
        db.update ( TableName,contentValues,"ID =?",new String[] {id} );
        return true;

    }

    public int delete(String id)
    {
        SQLiteDatabase db =this.getWritableDatabase ();
        return db.delete ( TableName, "ID = ?",new String[] {id} );
    }

}
