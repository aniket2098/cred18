package com.asdsoft.reg_app_18;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("previousData", MODE_PRIVATE, null);
        Bundle bundle = getIntent().getExtras();
        String uid = bundle.getString("id");
//        Log.i("id", cursor.ge);
        Cursor cursor;
        ArrayList<String> events = new ArrayList<>();
        final RecyclerView rv = findViewById(R.id.eventRv);

        try {


            cursor=sqLiteDatabase.rawQuery("select * from prev_reg where unique_id=" + uid + ";",null);
            cursor.moveToFirst();
            Log.i("name", cursor.getString(1));
            TextView textView=findViewById(R.id.partName);
            textView.setText(cursor.getString(cursor.getColumnIndex("name")));
            textView=findViewById(R.id.regDate);
            textView.setText(cursor.getString(cursor.getColumnIndex("date")));
            textView=findViewById(R.id.totalEvents);
            textView.setText("Total Events: "+cursor.getString(cursor.getColumnIndex("total_events")));
            int total=Integer.parseInt(cursor.getString(cursor.getColumnIndex("total_events")));
            textView=findViewById(R.id.totalPrice);
            textView.setText("Total: " + cursor.getString(cursor.getColumnIndex("total")));
            textView=findViewById(R.id.uniqueId);
            textView.setText(uid);
            cursor.close();

            cursor = sqLiteDatabase.rawQuery("select * from EVENTS where uid=" + uid + ";", null);
            if (cursor.moveToFirst()) {
//                if(cursor.getString(cursor.getColumnIndex("event")).equals("B-Plan"))
//                    cursor.moveToNext();
                do {
                    Log.i("last", cursor.getString(cursor.getColumnIndex("event")));
                    events.add(cursor.getString(cursor.getColumnIndex("event")));

//                    total--;
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();
            ReceiptItems adapter = new ReceiptItems(events);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(Details.this));
        }catch(Exception e)
        {

        }
    }
}
