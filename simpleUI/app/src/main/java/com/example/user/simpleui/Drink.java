package com.example.user.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2016/8/11.
 */
public class Drink implements Parcelable {
    String name;
    int lPrices;
    int mPrices;
    int imageId;

    @Override
    public int describeContents() {//包裹序碼是多少，通常不會用到
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {//物件怎麼打包，寫的順序就是拿的順序
        dest.writeString(this.name);
        dest.writeInt(this.lPrices);
        dest.writeInt(this.mPrices);
        dest.writeInt(this.imageId);
    }

    public Drink() {
    }

    protected Drink(Parcel in) {//Parcel in從記憶體拿回來的物件
        this.name = in.readString();
        this.lPrices = in.readInt();
        this.mPrices = in.readInt();
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
}
