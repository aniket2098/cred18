package com.pisb.credenz18;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class Details extends AppCompatActivity {

    private RecepitData recepitData;

    NetWork netWork;

    ArrayList<String> events = new ArrayList<>();
    ArrayList<String> totsevents = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle bundle = getIntent().getExtras();
        String uid = bundle.getString("id");
//        Log.i("id", cursor.ge);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        netWork = new NetWork(getApplicationContext());

        recepitData = netWork.getDataKey(uid);

        totsevents = getEvents(recepitData);

        final RecyclerView rv = findViewById(R.id.eventRv);

        TextView tvname =findViewById(R.id.partName);
        tvname.setText(recepitData.getName1());

        TextView tvdate = findViewById(R.id.regDate);
        tvdate.setText(recepitData.getDate());

        TextView tvtotal = findViewById(R.id.totalPrice);
        tvtotal.setText("Total Price: " + Integer.toString(recepitData.getTotal()));

        TextView tvevents = findViewById(R.id.totalEvents);
        tvevents.setText("Total Events: " + Integer.toString(getCount(recepitData)));

        TextView tvuid = findViewById(R.id.uniqueId);
        tvuid.setText(recepitData.getUniKey());

        ReceiptAdapter receiptAdapter = new ReceiptAdapter(totsevents);
        rv.setAdapter(receiptAdapter);
        rv.setLayoutManager(new LinearLayoutManager(Details.this));

    }

    public int getCount(RecepitData recepitData){
        return recepitData.getBPlan() + recepitData.getClash() + recepitData.getContraption() +
                         recepitData.getCretronix() + recepitData.getDatawiz() + recepitData.getEnigma() +
                         recepitData.getNTH() + recepitData.getPaperPresentation() + recepitData.getPixelate() +
                         recepitData.getQuizB() + recepitData.getQuizG() + recepitData.getQuizM() + recepitData.getReverse_Coding() +
                         recepitData.getRoboliga() + recepitData.getSoftware_Development() + recepitData.getWallStreet() +
                         recepitData.getWebWeaver() + recepitData.getXodia();
    }

    public ArrayList<String> getEvents(RecepitData recepitData){
        if(recepitData.getXodia() == 1){
            events.add("Xodia");
        }
        if(recepitData.getWebWeaver() == 1){
            events.add("WebWeaver");
        }
        if(recepitData.getWallStreet() == 1){
            events.add("WallStreet");
        }
        if(recepitData.getSoftware_Development() == 1){
            events.add("Software Developement");
        }
        if(recepitData.getRoboliga() == 1){
            events.add("Roboliga");
        }
        if(recepitData.getReverse_Coding() == 1){
            events.add("Reverse Coding");
        }
        if(recepitData.getQuizM() == 1){
            events.add("QuizM");
        }
        if(recepitData.getQuizG() == 1){
            events.add("QuizG");
        }
        if(recepitData.getQuizB() == 1){
            events.add("QuizB");
        }
        if(recepitData.getPixelate() == 1){
            events.add("Pixelate");
        }
        if(recepitData.getPaperPresentation() == 1){
            events.add("Paper Presentation");
        }
        if(recepitData.getNTH() == 1){
            events.add("NTH");
        }
        if(recepitData.getEnigma() == 1){
            events.add("Enigma");
        }
        if(recepitData.getDatawiz() == 1){
            events.add("DataWiz");
        }
        if(recepitData.getCretronix() == 1){
            events.add("Cretronix");
        }
        if(recepitData.getContraption() == 1){
            events.add("Contraption");
        }
        if(recepitData.getClash() == 1){
            events.add("Clash");
        }
        if(recepitData.getBPlan() == 1){
            events.add("BPlan");
        }
        return  events;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        else {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
