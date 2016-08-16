package com.example.user.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by user on 2016/8/11.
 */
//標明class名稱 就是deshBoard上所顯示的名稱
@ParseClassName("Drink")
public class Drink extends ParseObject implements Parcelable {
//    String name;
//    int lPrices;
//    int mPrices;　改成符合ＰＡＲＳＥ的
    static final String NAME_COL = "name";
    static final String MPRICE_COL = "mPrice";
    static final String LPRICE_COL = "lPrice";
    int imageId;

    @Override
    public int describeContents() {//包裹序碼是多少，通常不會用到
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {//物件怎麼打包，寫的順序就是拿的順序
        dest.writeString(this.getName());
        dest.writeInt(this.getlPrices());
        dest.writeInt(this.getmPrices());
        dest.writeInt(this.imageId);
    }

    public Drink() {
    }

    protected Drink(Parcel in) {//Parcel in從記憶體拿回來的物件
        this.setName(in.readString());
        this.setlPrices(in.readInt());
        this.setmPrices(in.readInt());
        this.imageId = in.readInt();//全部都會從包果拿出來
    }

    public static final Parcelable.Creator<Drink> CREATOR = new Parcelable.Creator<Drink>() {//'幫助我們把包裹改回我們要的資料結構
        @Override
        public Drink createFromParcel(Parcel source) {
            return new Drink(source);//包裹改回原本我們要的物件
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };

    public String getName() {
        return getString(NAME_COL);
    }

    public void setName(String name) {
        this.put(NAME_COL,name);
    }

    public int getmPrices() {
        return getInt(MPRICE_COL);
    }

    public void setmPrices(int mPrices) {
        this.put(MPRICE_COL,mPrices);
    }

    public int getlPrices() {
        return getInt(LPRICE_COL);
    }

    public void setlPrices(int lPrices) {
        this.put(LPRICE_COL,lPrices);
    }
}
