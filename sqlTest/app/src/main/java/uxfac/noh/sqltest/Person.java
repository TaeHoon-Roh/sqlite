package uxfac.noh.sqltest;

import android.graphics.Bitmap;

/**
 * Created by kaist on 2017-06-13.
 */

public class Person {
    public String face = null;
    public String name = null;
    public String info = null;
    public String CNN_data = null;
    public Person(String face, String info, String name, String CNN_data){
        this.face = face;
        this.name = name;
        this.info = info;
        this.CNN_data = CNN_data;
    }
}
