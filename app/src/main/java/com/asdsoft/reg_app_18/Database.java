package com.asdsoft.reg_app_18;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Database {



    private static String TABLE_NAME="prev_reg";
    private static String NAME="name";
    private static String DATE="date";
    private static String TOTAL="total";
    private static String NO="total_events";
    private static String ID="unique_id";
    private static String NAME2="name2";
    private static String NAME3="name3";
    private static String NAME4="name4";
    private static String EMAIL="email";
    private static String PHONE="phone";
    private static String COLLEGE="college";
    private static String UNIKEY = "UNIKEY";
    private static String UID="uid";
    private static String EVENT="event";
    public String uni;
    SQLiteDatabase sqLiteDatabase;
    ServerData serverdata;


    Database(PrevData prevData, SQLiteDatabase sqLiteDatabase)
    {

        sqLiteDatabase.execSQL("create table if not exists " + TABLE_NAME + "("
                + NAME + " varchar(30)" + ","
                + NAME2 + " varchar(30)" + ","
                + NAME3 + " varchar(30)" + ","
                + NAME4 + " varchar(30)" + ","
                + EMAIL + " varchar(30)" + ","
                + PHONE + " varchar(30)" + ","
                + DATE + " varchar(30)" + ","
                + TOTAL + " integer"  + ","
                + NO + " integer"  + ","
                + COLLEGE + " varchar(30)" + ","
                + UNIKEY +  " varchar(40)" + ","
                + ID + " varchar(30)" + " primary key" +
                ");" );

        sqLiteDatabase.execSQL("create table if not exists " + "EVENTS" + "("
                + UID + " varchar(30)" + ","
                + EVENT + " varchar(20), foreign key (" + UID + ") references " + TABLE_NAME + "(" + ID + ")"
                + ");");


        ContentValues contentValues=new ContentValues();

        contentValues.put(NAME,prevData.getRegName());
        contentValues.put(NAME2,prevData.getRegName2());
        contentValues.put(NAME3,prevData.getRegName3());
        contentValues.put(NAME4,prevData.getRegName4());
        contentValues.put(EMAIL,prevData.getRegEmail());
        contentValues.put(PHONE,prevData.getRegPhone());
        contentValues.put(DATE,prevData.getRegDate());
        contentValues.put(TOTAL,prevData.gettotal());
        contentValues.put(NO,prevData.getNoOfEvents());
        contentValues.put(ID,prevData.getUniId());
        contentValues.put(COLLEGE, prevData.getRegCollege());


        for(int i=0;i<prevData.getReceipt().size();i++)
        {
//            Log.i("bool", Boolean.toString(prevData.getReceipt().get(i).getCheck()));
            if(prevData.getReceipt().get(i).getCheck()) {
                contentValues.put(UID, prevData.getUniId());
                contentValues.put(EVENT, prevData.getReceipt().get(i).getName());
                sqLiteDatabase.insert("EVENTS",null,contentValues);
            }

        }



        serverdata=new ServerData();
        serverdata.name=prevData.getRegName();
        serverdata.name2=prevData.getRegName2();
        serverdata.name3=prevData.getRegName3();
        serverdata.name4=prevData.getRegName4();
        serverdata.email=prevData.getRegEmail();
        serverdata.phone=prevData.getRegPhone();
        serverdata.date=prevData.getRegDate();
        serverdata.total=prevData.gettotal();
        serverdata.noOfEvents=prevData.getNoOfEvents();
        serverdata.uniId=prevData.getUniId();
        serverdata.college=prevData.getRegCollege();
        serverdata.BPlan=prevData.getReceipt().get(0).getCheck() ? 1:0;
        serverdata.Contraption=prevData.getReceipt().get(1).getCheck() ? 1:0;
        serverdata.Clash=prevData.getReceipt().get(2).getCheck() ? 1:0;
        serverdata.Cretronix=prevData.getReceipt().get(3).getCheck() ? 1:0;
        serverdata.Croodle=prevData.getReceipt().get(4).getCheck() ? 1:0;
        serverdata.MADTalks=prevData.getReceipt().get(5).getCheck() ? 1:0;
        serverdata.NTH=prevData.getReceipt().get(6).getCheck() ? 1:0;
        serverdata.paperPresentation=prevData.getReceipt().get(7).getCheck() ? 1:0;
        serverdata.Pixelate=prevData.getReceipt().get(8).getCheck() ? 1:0;
        serverdata.Roboliga=prevData.getReceipt().get(9).getCheck() ? 1:0;
        serverdata.Reverse_Coding=prevData.getReceipt().get(10).getCheck() ? 1:0;
        serverdata.Quiz=prevData.getReceipt().get(11).getCheck() ? 1:0;
        serverdata.Software_Development=prevData.getReceipt().get(12).getCheck() ? 1:0;
        serverdata.Seminars=prevData.getReceipt().get(13).getCheck() ? 1:0;
        serverdata.WebWeaver=prevData.getReceipt().get(14).getCheck() ? 1:0;
        serverdata.WallStreet=prevData.getReceipt().get(15).getCheck() ? 1:0;
        serverdata.Xodia=prevData.getReceipt().get(16).getCheck() ? 1:0;
        serverdata.Workshop=prevData.getReceipt().get(16).getCheck() ? 1:0;
        uni = makeRequst(serverdata);
        contentValues.put(UNIKEY, uni);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        contentValues.clear();
        sqLiteDatabase.close();

    }
    String makeRequst(ServerData serverData){
        final String[] output = new String[1];
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        ApiClient api = retrofit.create(ApiClient.class);
        Call<List<DataRecv>> call = api.sendData(serverData.email,serverData.phone);

        call.enqueue(new Callback<List<DataRecv>>() {
            @Override
            public void onResponse(Call<List<DataRecv>> call, Response<List<DataRecv>> response) {
                List<DataRecv> out = response.body();
                DataRecv d = out.get(0);
                output[0] = d.uniKey;
            }

            @Override
            public void onFailure(Call<List<DataRecv>> call, Throwable t) {

                output[0] = "NEG";
            }
        });


        return output[0];
    }
}
