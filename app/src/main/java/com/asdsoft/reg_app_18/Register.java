package com.asdsoft.reg_app_18;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity{
    ArrayList<Event> events;
    TextView textView;
    int total;
    View view;
    PrevData prevData;
    public ArrayList<Event> receipt=new ArrayList<>();
    Bundle bundle;
    String UNI;
    ProgressBar progressBar;
    private FirebaseUser firebaseUser;
    private ArrayList<Event> event;
    public String gname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressBar = findViewById(R.id.progressbar);
        Intent i = getIntent();
        gname = i.getStringExtra("gname");
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
//                            Log.i("remove", events.get(position).getName())

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

        final Button register = findViewById(R.id.registerButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
register.setEnabled(false);

                progressBar.setVisibility(View.VISIBLE);
                database();
//                        Intent intent = new Intent(Register.this, QRCode.class);
//                        intent.putExtra("unikey", value);
//                        //Toast.makeText(getApplicationContext(),val,Toast.LENGTH_SHORT).show();
//                        startActivity(intent);


            }
        });
    }

    public ArrayList<Event> createContactsList() {
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<Integer> price=new ArrayList<>();
        bundle=getIntent().getExtras();
        if(!bundle.getBoolean("ieee"))
        {
            price.add(150);
            price.add(200);
            price.add(100);
            price.add(150);
            price.add(100);
            price.add(50);
            price.add(0);
            price.add(200);
            price.add(150);
            price.add(200);
            price.add(100);
            price.add(50);
            price.add(200);
            price.add(0);
            price.add(150);
            price.add(50);
            price.add(0);
            price.add(0);
        }
        else
        {
            price.add(120);
            price.add(160);
            price.add(80);
            price.add(120);
            price.add(80);
            price.add(40);
            price.add(0);
            price.add(180);
            price.add(120);
            price.add(160);
            price.add(80);
            price.add(40);
            price.add(160);
            price.add(0);
            price.add(120);
            price.add(40);
            price.add(0);
            price.add(0);
        }

        events.clear();
        Log.i("bool11", Boolean.toString(bundle.getString("name2","NULL").isEmpty()));
       if(bundle.getString("name2","NULL").isEmpty()) {
           events.add(new Event("B-Plan", price.get(0), false,true));
           events.add(new Event("Contraption", price.get(1), false,true));
           events.add(new Event("Clash", price.get(2), false,true));
           events.add(new Event("Cretronix", price.get(3), false,true));
           events.add(new Event("DataWiz", price.get(4), false,true));
           events.add(new Event("Enigma",price.get(5),false,true));
           events.add(new Event("NTH", price.get(6), false,true));
           events.add(new Event("Paper\nPresentation", price.get(7), false,true));
           events.add(new Event("Pixelate", price.get(8), false,true));
           events.add(new Event("Roboliga", price.get(9), false,true));
           events.add(new Event("Reverse\nCoding", price.get(10), false,true));
           events.add(new Event("Quiz(BizTech)", price.get(11), false,true));
           events.add(new Event("Quiz(General)", price.get(11), false,true));
           events.add(new Event("Quiz(MELA)", price.get(11), false,true));
           events.add(new Event("Software\nDevelopment", price.get(12), false,true));
           events.add(new Event("Web Weaver", price.get(14), false,true));
           events.add(new Event("Wall Street", price.get(15), false,true));
           events.add(new Event("Xodia", price.get(16), false,true));
       }
        else if(bundle.getString("name3","NULL").isEmpty())
        {
            events.add(new Event("B-Plan", price.get(0), false,true));
            events.add(new Event("Contraption", price.get(1), false,true));
            events.add(new Event("Clash", price.get(2), false,true));
            events.add(new Event("Cretronix", price.get(3), false,true));
            events.add(new Event("DataWiz", price.get(4), false,true));
            events.add(new Event("Enigma",price.get(5),false,true));
            events.add(new Event("NTH", price.get(6), false,false));
            events.add(new Event("Paper\nPresentation", price.get(7), false,true));
            events.add(new Event("Pixelate", price.get(8), false,true));
            events.add(new Event("Roboliga", price.get(9), false,true));
            events.add(new Event("Reverse\nCoding", price.get(10), false,true));
            events.add(new Event("Quiz(BizTech)", price.get(11), false,true));
            events.add(new Event("Quiz(General)", price.get(11), false,true));
            events.add(new Event("Quiz(MELA)", price.get(11), false,true));            events.add(new Event("Software\nDevelopment", price.get(12), false,true));
            events.add(new Event("Web Weaver", price.get(14), false,true));
            events.add(new Event("Wall Street", price.get(15), false,false));
            events.add(new Event("Xodia", price.get(16), false,false));
        }
        else if(bundle.getString("name4","NULL").isEmpty())
        {
            events.add(new Event("B-Plan", price.get(0), false,true));
            events.add(new Event("Contraption", price.get(1), false,true));
            events.add(new Event("Clash", price.get(2), false,false));
            events.add(new Event("Cretronix", price.get(3), false,true));
            events.add(new Event("DataWiz", price.get(4), false,false));
            events.add(new Event("Enigma",price.get(5),false,false));
            events.add(new Event("NTH", price.get(6), false,false));
            events.add(new Event("Paper\nPresentation", price.get(7), false,true));
            events.add(new Event("Pixelate", price.get(8), false,false));
            events.add(new Event("Roboliga", price.get(9), false,true));
            events.add(new Event("Reverse\nCoding", price.get(10), false,false));
            events.add(new Event("Quiz(BizTech)", price.get(11), false,true));
            events.add(new Event("Quiz(General)", price.get(11), false,true));
            events.add(new Event("Quiz(MELA)", price.get(11), false,true));            events.add(new Event("Software\nDevelopment", price.get(12), false,true));
            events.add(new Event("Web Weaver", price.get(14), false,true));
            events.add(new Event("Wall Street", price.get(15), false,false));
            events.add(new Event("Xodia", price.get(16), false,false));
        }
        else
        {
            events.add(new Event("B-Plan", price.get(0), false,false));
            events.add(new Event("Contraption", price.get(1), false,true));
            events.add(new Event("Clash", price.get(2), false,false));
            events.add(new Event("Cretronix", price.get(3), false,true));
            events.add(new Event("DataWiz", price.get(4), false,false));
            events.add(new Event("Enigma",price.get(5),false,false));
            events.add(new Event("NTH", price.get(6), false,false));
            events.add(new Event("Paper\nPresentation", price.get(7), false,false));
            events.add(new Event("Pixelate", price.get(8), false,false));
            events.add(new Event("Roboliga", price.get(9), false,false));
            events.add(new Event("Reverse\nCoding", price.get(10), false,false));
            events.add(new Event("Quiz(BizTech)", price.get(11), false,true));
            events.add(new Event("Quiz(General)", price.get(11), false,true));
            events.add(new Event("Quiz(MELA)", price.get(11), false,true));            events.add(new Event("Software\nDevelopment", price.get(12), false,false));
            events.add(new Event("Web Weaver", price.get(14), false,false));
            events.add(new Event("Wall Street", price.get(15), false,false));
            events.add(new Event("Xodia", price.get(16), false,false));
        }
        return events;
    }

    public void database()
    {
        bundle=getIntent().getExtras();
        SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("previousData",MODE_PRIVATE,null);
        Date c=Calendar.getInstance().getTime();
        SimpleDateFormat d=new SimpleDateFormat("dd-MM-yyyy");
        prevData=new PrevData(bundle.getString("name"),bundle.getString("name2"),bundle.getString("name3"),bundle.getString("name4"),bundle.getString("phone"),bundle.getString("email"),bundle.getString("phone"),total,receipt.size(),d.format(c),bundle.getString("college"),bundle.getBoolean("ieee"),event);
        Database database=new Database(prevData, sqLiteDatabase);
        makeRequest(database.getServeData());
    }
    @Override
    public void onBackPressed()
    {
        if(!(findViewById(R.id.register)).isEnabled())
        {
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
        }
    }
    public void makeRequest(ServerData serverData){
        final String[] output = new String[1];
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ApiClient api = retrofit.create(ApiClient.class);

        Call<List<DataRecv>> call = api.sendData(firebaseUser.getPhoneNumber(),
                                                gname,
                                                serverData.name,
                                                serverData.name2,
                                                serverData.name3,
                                                serverData.name3,
                                                serverData.email,
                                                serverData.phone,
                                                serverData.date,
                                                serverData.total,
                                                serverData.college,
                                                serverData.ieee?1:0,
                                                serverData.BPlan,
                                                serverData.Contraption,
                                                serverData.Clash,
                                                serverData.Cretronix,
                                                serverData.Datawiz,
                                                serverData.Enigma,
                                                serverData.NTH,
                                                serverData.paperPresentation,
                                                serverData.Pixelate,
                                                serverData.Roboliga,
                                                serverData.Reverse_Coding,
                                                serverData.QuizB,
                                                serverData.QuizG,
                                                serverData.QuizM,
                                                serverData.Software_Development,
                                                serverData.WebWeaver,
                                                serverData.WallStreet,
                                                serverData.Xodia);


        call.enqueue(new Callback<List<DataRecv>>() {
            @Override
            public void onResponse(Call<List<DataRecv>> call, Response<List<DataRecv>> response) {
                List<DataRecv> out = response.body();
                DataRecv d = out.get(0);
                output[0] = d.uniKey;
                Log.e("TAG",d.uniKey);
                gotonect(d.uniKey);
            }

            @Override
            public void onFailure(Call<List<DataRecv>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                output[0] = "NEG";
                Log.e("TAG",t.getMessage());
            }
        });


    }
    public void gotonect(String Key){
        Key.replace(" ","");
        Intent it = new Intent(Register.this,QRCode.class);
        Log.e("TAG1",Key);
        it.putExtra("unikey",Key);
        progressBar.setVisibility(View.GONE);
        startActivity(it);

    }
}
