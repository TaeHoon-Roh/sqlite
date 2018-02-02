package uxfac.noh.sqltest.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import uxfac.noh.sqltest.Person;

/**
 * Created by Noh on 2018-01-31.
 */

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE PERSONLIST (name TEXT, info TEXT, vector TEXT, picture TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertDB(Person person) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO PERSONLIST VALUES('" + person.name + "', '" + person.info + "', '" + person.CNN_data + "', '" + person.face + "');");
    }

    public void update(String name, Bitmap picture) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE PERSONLIST SET picture=" + picture + " WHERE name='" + name + "';");
        db.close();
    }

    public void delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM PERSONLIST WHERE name='" + name + "';");
        db.close();
    }

    public void deleteAllUser(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("PERSONLIST",null, null );
    }

    public ArrayList<Person> getAllUser() {
        // 읽기가 가능하게 DB 열기
        ArrayList<Person> people = new ArrayList<Person>();
        Person p = new Person(null, null, null, null);
        SQLiteDatabase db = getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM PERSONLIST", null);
        String result = "";
        while (cursor.moveToNext()) {
            p.name = cursor.getString(0);
            p.info = cursor.getString(1);
            p.CNN_data = cursor.getString(2);
            p.face = cursor.getString(3);

            result += p.name + " " + p.info + " " + p.CNN_data + "\n";
            people.add(p);
        }

        Log.i("DB :: " , result);

        return people;
    }

}
