package com.example.user.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2016/8/15.
 */
public class DrinkOrder implements Parcelable {
    Drink drink;
    int lNumber=0;
    int mNumber=0;
    String ice = "REGULAR";
    String sugar = "REGULAR";
    String note = "";

    public DrinkOrder(Drink drink)
    {
        this.drink = drink;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.drink, flags);
        dest.writeInt(this.lNumber);
        dest.writeInt(this.mNumber);
        dest.writeString(this.ice);
        dest.writeString(this.sugar);
        dest.writeString(this.note);
    }

    protected DrinkOrder(Parcel in) {
        this.drink = in.readParcelable(Drink.class.getClassLoader());
        this.lNumber = in.readInt();
        this.mNumber = in.readInt();
        this.ice = in.readString();
        this.sugar = in.readString();
        this.note = in.readString();
    }

    public static final Parcelable.Creator<DrinkOrder> CREATOR = new Parcelable.Creator<DrinkOrder>() {
        @Override
        public DrinkOrder createFromParcel(Parcel source) {
            return new DrinkOrder(source);
        }

        @Override
        public DrinkOrder[] newArray(int size) {
            return new DrinkOrder[size];
        }
    };
}
