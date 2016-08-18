package com.example.user.simpleui;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by user on 2016/8/10.
 */
@ParseClassName("Order")
public class Order extends ParseObject{
//    String note;
//    String storeInfo;
//    List<DrinkOrder> drinkOrderList;


    static final String NOTE_COL = "note";
    static final String STOREINFO_COL = "storeInfo";
    static final String DRINKORDERLIST_COL = "drinkOrderList";

    public int getTotal()
    {
        int total = 0;
        //把DRINKORDER 飲料訂單拿出並且加總
        for(DrinkOrder drinkOrder: getDrinkOrderList())
        {
            total += drinkOrder.getlNumber()*drinkOrder.getDrink().getlPrices() + drinkOrder.getmNumber()*drinkOrder.getDrink().getmPrices();
        }
       return total;

    }

    public String getNote() {
        return getString(NOTE_COL);
    }

    public void setNote(String note) {
        this.put(NOTE_COL,note);
    }

    public List<DrinkOrder> getDrinkOrderList() {
        return getList(DRINKORDERLIST_COL);
    }

    public void setDrinkOrderList(List<DrinkOrder> drinkOrderList) {
        this.put(DRINKORDERLIST_COL,drinkOrderList);
    }

    public String getStoreInfo() {
        return getString(STOREINFO_COL);
    }

    public void setStoreInfo(String storeInfo) {
        this.put(STOREINFO_COL,storeInfo);
    }

    public static ParseQuery<Order> getQuery(){

        return ParseQuery.getQuery(Order.class)
                .include(DRINKORDERLIST_COL)
                .include(DRINKORDERLIST_COL + '.' + DrinkOrder.DRINK_COL);
        //告訴他在載資料時要包含哪一些資料才可以
        //DRINKORDERLIST_COL + '.' +DrinkOrder.DRINK_COL 要標名是從 DRINKORDERLIST_COL裡面的DrinkOrder.DRINK_COL拿資料
    }
    public static void getOrdersFromLocalThenRemote(final FindCallback<Order> callback)
    {
        getQuery().fromLocalDatastore().findInBackground(callback);
        getQuery().findInBackground(new FindCallback<Order>() {
            @Override
            public void done(List<Order> list, ParseException e) {
                if (e == null) {
                    pinAllInBackground("Order", list);
                }
                callback.done(list, e);
            }
        });
    }
}
