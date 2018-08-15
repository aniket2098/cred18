package com.asdsoft.reg_app_18;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Prev extends AppCompatActivity {

    private ArrayList<PrevData> registrations=new ArrayList<>();
    PrevData prevData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev);

        final RecyclerView rv= findViewById(R.id.registrations);

        try
        {
            SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("previousData",MODE_PRIVATE,null);
            Cursor cursor=sqLiteDatabase.rawQuery("select * from " + "prev_reg" + ";",null);

            if (cursor.moveToFirst()){
                do{
                    prevData=new PrevData(cursor.getString(cursor.getColumnIndex("name")),
                            null,
                            null,
                            null,
                            null,
                            null,
                            cursor.getString(cursor.getColumnIndex("unique_id")),
                            cursor.getInt(cursor.getColumnIndex("total")),
                            cursor.getInt(cursor.getColumnIndex("total_events")),
                            cursor.getString(cursor.getColumnIndex("date")),
                            cursor.getString(cursor.getColumnIndex("college")),
                            null,null);
                    registrations.add(prevData);
                }while(cursor.moveToNext());
            }
            cursor.close();


            RegAdapter adapter = new RegAdapter(registrations,Prev.this);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(Prev.this));
        }
        catch(Exception e)
        {
            TextView textView=findViewById(R.id.message);
            textView.setVisibility(View.VISIBLE);
        }




    }
    public void goToNext(String uid)
    {

        Log.i("touch", "onInterceptTouchEvent: ");
    }

}
