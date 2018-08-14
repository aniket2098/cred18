package com.asdsoft.reg_app_18;

import java.util.ArrayList;

public class Event {


    private String eventName;
    private int eventPrice;
    private boolean eventCheck;
    private boolean eventEnable;

    public Event(String event, Integer price, boolean check,boolean enable) {
        eventName = event;
        eventPrice = price;
        eventCheck = check;
        eventEnable=enable;
    }

    public String getName() {
        return eventName;
    }

    public Integer getPrice() {
        return eventPrice;
    }

    public boolean getCheck(){
        return eventCheck;
    }

    public boolean getEnable(){
        return eventEnable;
    }


    public void modify(int position,boolean value,ArrayList<Event> events)
    {
        events.get(position).eventCheck=value;
    }

}
