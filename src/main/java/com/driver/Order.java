package com.driver;

import java.util.Arrays;
import java.util.List;

public class Order {

    private String id;
    private int deliveryTime;

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Order(String id, String deliveryTime) {
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id=id;
        this.deliveryTime=convertDeliveryTime(deliveryTime);
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
    public static int convertDeliveryTime(String deliveryTim){
        List<String> list= Arrays.asList(deliveryTim.split(":"));
        int HH=Integer.parseInt((list.get(0)));
        int MM=Integer.parseInt(list.get(1));
        return HH*60+MM;
    }
}
