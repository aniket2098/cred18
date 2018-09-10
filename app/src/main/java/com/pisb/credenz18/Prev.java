package com.pisb.credenz18;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class Prev extends AppCompatActivity {

    private List<RecepitData> recepitDataList;
    NetWork netWork;
    RecepitData recepitData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev);

        netWork = new NetWork(getApplicationContext());

        recepitDataList = netWork.retriveData();


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar_prev);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final RecyclerView rv = findViewById(R.id.registrations);

        RegAdapter adapter = new RegAdapter(recepitDataList,Prev.this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(Prev.this));

    }

    public void goToNext(String uid)
    {

        Log.i("touch", "onInterceptTouchEvent: ");
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
            String url = "https://sites.google.com/view/credenz-18-privacy-policies";

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);

            //pass the url to intent data
            intent.setData(Uri.parse(url));

            startActivity(intent);
        }
        else {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
