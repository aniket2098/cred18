package com.asdsoft.reg_app_18;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences;

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
    public static final String MY_PREFS_NAME = "MyPrefsFile";

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
                + ID + " integer" + " primary key autoincrement not null" +
                ");" );

        sqLiteDatabase.execSQL("create table if not exists " + "EVENTS" + "("
                + UID + " integer" + ","
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
//        contentValues.put(ID,prevData.getUniId());
        contentValues.put(COLLEGE, prevData.getRegCollege());
        contentValues.put(UNIKEY, "CRED18");
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        contentValues.clear();

        Cursor cursor=sqLiteDatabase.rawQuery("select unique_id from prev_reg",null);
        cursor.moveToLast();
        for(int i=0;i<prevData.getReceipt().size();i++)
        {
//            Log.i("bool", Boolean.toString(prevData.getReceipt().get(i).getCheck()));
            if(prevData.getReceipt().get(i).getCheck()) {
                contentValues.put(UID, cursor.getString(cursor.getColumnIndex("unique_id")));
                contentValues.put(EVENT, prevData.getReceipt().get(i).getName());
                sqLiteDatabase.insert("EVENTS",null,contentValues);
            }

        }
    cursor.close();

        sqLiteDatabase.close();
        serverdata=new ServerData();
        serverdata.name=prevData.getRegName();
        serverdata.name2=prevData.getRegName2();
        serverdata.name3=prevData.getRegName3();
        serverdata.name4=prevData.getRegName4();
        serverdata.email=prevData.getRegEmail();
        serverdata.phone=prevData.getRegPhone();
        serverdata.date=prevData.getRegDate();
        serverdata.total=prevData.gettotal();
        serverdata.ieee=prevData.getIeee();
        serverdata.noOfEvents=prevData.getNoOfEvents();
        serverdata.uniId=prevData.getUniId();
        serverdata.college=prevData.getRegCollege();
        serverdata.BPlan=prevData.getReceipt().get(0).getCheck() ? 1:0;
        serverdata.Contraption=prevData.getReceipt().get(1).getCheck() ? 1:0;
        serverdata.Clash=prevData.getReceipt().get(2).getCheck() ? 1:0;
        serverdata.Cretronix=prevData.getReceipt().get(3).getCheck() ? 1:0;
        serverdata.Datawiz=prevData.getReceipt().get(4).getCheck() ? 1:0;
        serverdata.Enigma=prevData.getReceipt().get(5).getCheck() ? 1:0;
        serverdata.NTH=prevData.getReceipt().get(6).getCheck() ? 1:0;
        serverdata.paperPresentation=prevData.getReceipt().get(7).getCheck() ? 1:0;
        serverdata.Pixelate=prevData.getReceipt().get(8).getCheck() ? 1:0;
        serverdata.Roboliga=prevData.getReceipt().get(9).getCheck() ? 1:0;
        serverdata.Reverse_Coding=prevData.getReceipt().get(10).getCheck() ? 1:0;
        serverdata.QuizB=prevData.getReceipt().get(11).getCheck() ? 1:0;
        serverdata.QuizG=prevData.getReceipt().get(12).getCheck() ? 1:0;
        serverdata.QuizM=prevData.getReceipt().get(13).getCheck() ? 1:0;
        serverdata.Software_Development=prevData.getReceipt().get(14).getCheck() ? 1:0;
        serverdata.WebWeaver=prevData.getReceipt().get(15).getCheck() ? 1:0;
        serverdata.WallStreet=prevData.getReceipt().get(16).getCheck() ? 1:0;
        serverdata.Xodia=prevData.getReceipt().get(17).getCheck() ? 1:0;


    }
    public ServerData getServeData(){
        return serverdata;
    }
    void makeRequst(ServerData serverData){

    }
}
