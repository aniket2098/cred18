package com.asdsoft.reg_app_18;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Register extends AppCompatActivity{
    ArrayList<Event> events;
    TextView textView;
    int total;
    View view;
    PrevData prevData;
    public ArrayList<Event> receipt=new ArrayList<>();
    Bundle bundle;


    private ArrayList<Event> event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        class RegisterAdapter extends
                RecyclerView.Adapter<RegisterAdapter.ViewHolder> {

            class ViewHolder extends RecyclerView.ViewHolder {

                public TextView event;
                public CheckBox box;
                public TextView price;

                public ViewHolder(View itemView) {
                    super(itemView);
                    event = (TextView) itemView.findViewById(R.id.event);
                    price = itemView.findViewById(R.id.price);
                    box = (CheckBox) itemView.findViewById(R.id.checkbox);
                }
            }


            @Override
            public RegisterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                Context context = parent.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View contactView = inflater.inflate(R.layout.recycler_item, parent, false);
                ViewHolder viewHolder = new ViewHolder(contactView);
                return viewHolder;
            }



            public RegisterAdapter(ArrayList<Event> events) {
                event = events;
            }

            @Override
            public int getItemCount() {
                return event.size();
            }
            @Override
            public void onBindViewHolder(final RegisterAdapter.ViewHolder viewHolder, final int position) {
                final Event register = event.get(position);
                TextView textView = viewHolder.event;
                textView.setText(register.getName());
                textView = viewHolder.price;
                textView.setText(String.valueOf(register.getPrice()));
                final CheckBox checkBox=viewHolder.box;
                checkBox.setEnabled(event.get(position).getEnable());
                checkBox.setChecked(event.get(position).getCheck());
                viewHolder.box.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = viewHolder.getAdapterPosition();
                        if(checkBox.isChecked()){
//                            Log.i("remove", events.get(position).getName());

                            receipt.add(event.get(position));
                            event.get(position).modify(position,true,event);
                            total+=event.get(position).getPrice();
                        }
                        else{
                            event.get(position).modify(position,false,event);
                            String temp=event.get(position).getName();
                            for(int i=0;i<receipt.size();i++)
                            {
                                if(temp.equals(receipt.get(i).getName()))
                                {
                                    receipt.remove(i);
                                    total-=event.get(position).getPrice();
//                                    Log.i("remove", Integer.toString(receipt.size()));

                                }

                            }
                        }
                        ((TextView)findViewById(R.id.total)).setText(Integer.toString(total));
                    }
                });
            }
        }
        RegisterAdapter adapter;

        textView = findViewById(R.id.total);
        view = this.findViewById(android.R.id.content);
        final RecyclerView rvContacts = findViewById(R.id.rv);
        events =createContactsList();
        adapter = new RegisterAdapter(events);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        Button register = findViewById(R.id.registerButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                database();
                Intent intent = new Intent(Register.this, QRCode.class);
                startActivity(intent);
            }
        });
    }

    public ArrayList<Event> createContactsList() {
        ArrayList<Event> events = new ArrayList<>();
        bundle=getIntent().getExtras();
        events.clear();
        Log.i("bool11", Boolean.toString(bundle.getString("name2","NULL").isEmpty()));
       if(bundle.getString("name2","NULL").isEmpty()) {
           events.add(new Event("B-Plan", 10, false,true));
           events.add(new Event("Contraption", 20, false,true));
           events.add(new Event("Clash", 30, false,true));
           events.add(new Event("Cretronix", 40, false,true));
           events.add(new Event("Croodle", 50, false,true));
           events.add(new Event("MAD Talks", 60, false,true));
           events.add(new Event("NTH", 70, false,true));
           events.add(new Event("Paper\nPresentation", 80, false,true));
           events.add(new Event("Pixelate", 90, false,true));
           events.add(new Event("Roboliga", 100, false,true));
           events.add(new Event("Reverse\nCoding", 120, false,true));
           events.add(new Event("Quiz", 130, false,true));
           events.add(new Event("Software\nDevelopment", 140, false,true));
           events.add(new Event("Seminars", 150, false,true));
           events.add(new Event("Web Weaver", 160, false,true));
           events.add(new Event("Wall Street", 170, false,true));
           events.add(new Event("Xodia", 180, false,true));
           events.add(new Event("Workshop", 190, false,true));
       }
        else if(bundle.getString("name3","NULL").isEmpty())
        {
            events.add(new Event("B-Plan", 10, false,true));
            events.add(new Event("Contraption", 20, false,true));
            events.add(new Event("Clash", 30, false,true));
            events.add(new Event("Cretronix", 40, false,true));
            events.add(new Event("Croodle", 50, false,true));
            events.add(new Event("MAD Talks", 60, false,true));
            events.add(new Event("NTH", 70, false,false));
            events.add(new Event("Paper\nPresentation", 80, false,true));
            events.add(new Event("Pixelate", 90, false,true));
            events.add(new Event("Roboliga", 100, false,true));
            events.add(new Event("Reverse\nCoding", 120, false,true));
            events.add(new Event("Quiz", 130, false,true));
            events.add(new Event("Software\nDevelopment", 140, false,true));
            events.add(new Event("Seminars", 150, false,false));
            events.add(new Event("Web Weaver", 160, false,true));
            events.add(new Event("Wall Street", 170, false,false));
            events.add(new Event("Xodia", 180, false,false));
            events.add(new Event("Workshop", 190, false,false));

        }
        else if(bundle.getString("name4","NULL").isEmpty())
        {
            events.add(new Event("B-Plan", 10, false,true));
            events.add(new Event("Contraption", 20, false,true));
            events.add(new Event("Clash", 30, false,false));
            events.add(new Event("Cretronix", 40, false,true));
            events.add(new Event("Croodle", 50, false,false));
            events.add(new Event("MAD Talks", 60, false,true));
            events.add(new Event("NTH", 70, false,false));
            events.add(new Event("Paper\nPresentation", 80, false,true));
            events.add(new Event("Pixelate", 90, false,false));
            events.add(new Event("Roboliga", 100, false,true));
            events.add(new Event("Reverse\nCoding", 120, false,false));
            events.add(new Event("Quiz", 130, false,false));
            events.add(new Event("Software\nDevelopment", 140, false,true));
            events.add(new Event("Seminars", 150, false,false));
            events.add(new Event("Web Weaver", 160, false,true));
            events.add(new Event("Wall Street", 170, false,false));
            events.add(new Event("Xodia", 180, false,false));
            events.add(new Event("Workshop", 190, false,false));
        }
        else
        {
            events.add(new Event("B-Plan", 10, false,false));
            events.add(new Event("Contraption", 20, false,true));
            events.add(new Event("Clash", 30, false,false));
            events.add(new Event("Cretronix", 40, false,true));
            events.add(new Event("Croodle", 50, false,false));
            events.add(new Event("MAD Talks", 60, false,false));
            events.add(new Event("NTH", 70, false,false));
            events.add(new Event("Paper\nPresentation", 80, false,false));
            events.add(new Event("Pixelate", 90, false,false));
            events.add(new Event("Roboliga", 100, false,false));
            events.add(new Event("Reverse\nCoding", 120, false,false));
            events.add(new Event("Quiz", 130, false,false));
            events.add(new Event("Software\nDevelopment", 140, false,false));
            events.add(new Event("Seminars", 150, false,false));
            events.add(new Event("Web Weaver", 160, false,false));
            events.add(new Event("Wall Street", 170, false,false));
            events.add(new Event("Xodia", 180, false,false));
            events.add(new Event("Workshop", 190, false,false));
        }
        return events;
    }

    public void database()
    {
        bundle=getIntent().getExtras();
        SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("previousData",MODE_PRIVATE,null);
        Date c=Calendar.getInstance().getTime();
        SimpleDateFormat d=new SimpleDateFormat("dd-MM-yyyy");
        prevData=new PrevData(bundle.getString("name"),bundle.getString("name2"),bundle.getString("name3"),bundle.getString("name4"),bundle.getString("phone"),bundle.getString("email"),bundle.getString("phone"),total,receipt.size(),d.format(c),bundle.getString("college"),event);
        Database database=new Database(prevData, sqLiteDatabase);
    }
    @Override
    public void onBackPressed() {
    }

}
