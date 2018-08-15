package com.asdsoft.reg_app_18;

import java.util.ArrayList;

public class PrevData {

    private String name;
    private String name2;
    private String name3;
    private String name4;
    private String email;
    private String phone;
    private String date;
    private int total;
    private int noOfEvents;
    private String uniId;
    private String college;
    private Boolean ieee;
    private ArrayList<Event> receipt;


    public String getRegName()
    {
        return name;
    }

    public String getRegName2()
    {
        return name2;
    }

    public String getRegName3()
    {
        return name3;
    }

    public String getRegName4()
    {
        return name4;
    }

    public String getRegEmail()
    {
        return email;
    }

    public String getRegPhone()
    {
        return phone;
    }

    public String getRegDate()
    {
        return date;
    }

    public String getRegCollege()
    {
        return college;
    }

    public String getUniId()
    {
        return uniId;
    }

    public int gettotal()
    {
        return total;
    }

    public Boolean getIeee()
    {
        return ieee;
    }


    public ArrayList<Event> getReceipt()
    {
        return receipt;
    }
    public int getNoOfEvents()
    {
        return noOfEvents;
    }

    PrevData(String nameOfPerson, String name02, String name03, String name04,
             String ph,
             String mail,
             String id,
             int tot, int no,
             String prevDate,
             String cllg,
             Boolean Ieee,
             ArrayList<Event> receipt1)
    {
        name=nameOfPerson;
        name2=name02;
        name3=name03;
        name4=name04;
        phone=ph;
        email=mail;
        uniId=id;
        total=tot;
        noOfEvents=no;
        date=prevDate;
        college=cllg;
        receipt=receipt1;
        ieee=Ieee;
    }

}
