package com.example.user.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 2016/8/10.
 */
@ParseClassName("Order")
public class Order extends ParseObject implements Parcelable{
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if(getObjectId()==null)
        {
            dest.writeInt(0);
            dest.writeString(getNote());
            dest.writeString(getStoreInfo());
            dest.writeParcelableArray((Parcelable[])getDrinkOrderList().toArray(),flags);//LIST轉ARRAY 先 TOARRAY 再說是PARCEABLE的
        }
        else
        {
            dest.writeInt(1);
            dest.writeString(getObjectId());
        }
    }
    protected Order(Parcel in)
    {
        super();
        this.setNote(in.readString());
        this.setStoreInfo(in.readString());
        this.setDrinkOrderList(Arrays.asList(((DrinkOrder[])in.readArray(DrinkOrder.class.getClassLoader()))));//把array換成LIST
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            int isFromRemote = source.readInt();
            if (isFromRemote == 0)
            {
                return new Order(source);//包裹改回原本我們要的物件
            }
            else {
                String objectId = source.readString();
                return getOrderFromCache(objectId);
            }
        }

    @Override
    public Order[] newArray(int size) {
        return new Order[size];
     }
    };
    public static Order getOrderFromCache(String ObjectId)//從CACHE裡面拿DRINKORDER
    {
        try {
            return getQuery().fromLocalDatastore().get(ObjectId);
            //setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK) 先從CACHE 再從NETWORK
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Order.createWithoutData(Order.class,ObjectId);
        //設定他的QUERY是先從CACHE去拿   把ObjectID 改成DRINK
    }
}
