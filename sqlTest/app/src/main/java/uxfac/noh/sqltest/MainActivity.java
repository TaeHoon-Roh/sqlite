package uxfac.noh.sqltest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import uxfac.noh.sqltest.db.DataBase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DataBase db;
    String dbName = "PERSONLIST";

    ImageView faceiv;
    Button insertbt, showbt;
    TextView nametv, infotext;
    Person p = null;
    static {
        System.loadLibrary("native-lib");
    }


    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        faceiv = (ImageView)findViewById(R.id.faceiv);
        insertbt = (Button)findViewById(R.id.insertbt);
        showbt = (Button)findViewById(R.id.showbt);
        nametv = (TextView)findViewById(R.id.nametv);
        infotext = (TextView)findViewById(R.id.infotext);

        insertbt.setOnClickListener(this);
        showbt.setOnClickListener(this);

        String name = null;
        String info = null;
        String vector = null;

        String imageString= null;
        Bitmap testImage = null;
        testImage = BitmapFactory.decodeResource(getResources(), R.drawable.test_static_image);

        name = "Noh";
        info = "UxFac";
        vector = "vector Test";
        imageString = getBase64String(testImage);
        //imageString = "face Test";
        p = new Person(imageString,info,name,vector);

        db = new DataBase(getApplicationContext(),dbName,null,1);
        db.deleteAllUser();
    }


    public String getBase64String(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }

    public Bitmap getBitmapforString(String pictureToString){
        Bitmap result = null;

        byte[] decodedByteArray = Base64.decode(pictureToString, Base64.NO_WRAP);
        result = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);

        return result;
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.insertbt){
            db.insertDB(p);

        } else if(view.getId() == R.id.showbt){
            p = null;
            ArrayList<Person> people = new ArrayList<Person>();
            people = db.getAllUser();
            if(people.isEmpty() == false) {
                p = people.get(0);
                faceiv.setImageBitmap(getBitmapforString(p.face));
                nametv.setText(p.name);
                infotext.setText(p.info);
            }

        }
    }
}
