package com.example.user.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

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
    static final String IMAGE_COL= "image";

    @Override
    public int describeContents() {//包裹序碼是多少，通常不會用到
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {//物件怎麼打包，寫的順序就是拿的順序
        if(getObjectId()==null)
        {
            dest.writeInt(0);//先寫一個INT用來判斷是否有ObjectID的情況
            dest.writeString(this.getName());
            dest.writeInt(this.getlPrices());
            dest.writeInt(this.getmPrices());
//            dest.writeInt(this.imageId); 無意義了
        }
        else
        {
            dest.writeInt(1);//有objectID的情況
            dest.writeString(getObjectId());
        }
    }

    public Drink() {
    }


    protected Drink(Parcel in) {//Parcel in從記憶體拿回來的物件
        super();
        this.setName(in.readString());
        this.setlPrices(in.readInt());
        this.setmPrices(in.readInt());//全部都會從包果拿出來
//        this.imageId = in.readInt(); 無意義了
    }

    public static final Parcelable.Creator<Drink> CREATOR = new Parcelable.Creator<Drink>() {//'幫助我們把包裹改回我們要的資料結構
        @Override
        public Drink createFromParcel(Parcel source) {
            int isFromRemote = source.readInt();
            if (isFromRemote == 0) {
                return new Drink(source);//包裹改回原本我們要的物件
            }
            else {
                String objectId = source.readString();
                return getDrinkFromCache(objectId);
            }
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

    public ParseFile getImage(){return getParseFile(IMAGE_COL);}//在網路上設FILE 這裡就要用ParseFile

    public  static ParseQuery<Drink> getQuery()
    {
        return ParseQuery.getQuery(Drink.class);
    }
    public static Drink getDrinkFromCache(String ObjectId)
    {
        try {
//            Drink drink = getQuery().setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK).get(ObjectId); ////開啟LOCAL CACHE被關掉了
            Drink drink = getQuery().fromLocalDatastore().get(ObjectId);
            return drink;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Drink.createWithoutData(Drink.class, ObjectId);
        //設定他的QUERY是先從CACHE去拿   把ObjectID 改成DRINK
    }
    public static void getDrinksFromLocalThenRemote(final FindCallback<Drink> callback){//FindCallback 是一個INTERFACE 就是要會時做哪些FUNCTION的
        //callback 會一個叫DONE的功能
        getQuery().fromLocalDatastore().findInBackground(callback);//從LOCALDATABASE拿到資料後他會呼叫CALLBACK.DONE
        getQuery().findInBackground(new FindCallback<Drink>() {//new FindCallback<Drink>() 這個跟上面的CALLBACK是不一樣的事
            @Override
            public void done(final List<Drink> list, ParseException e) {
                if(e==null){
                    //代表有載到資料 要把LOCAL的東西刪掉
                    unpinAllInBackground("Drink", new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                           pinAllInBackground("Drink",list);
                        }
                    });
                }
                callback.done(list,e);//呼叫外面的DONE去更新資料　在ＤＲＩＮＫＭＥＮＵＡＣＴＩＶＩＴＹ的ＤＯＮＥ
            }
        });
    }
    //第一次先從LOCAL端拿 再拿REMOTE端  如果斷網的話就可以還有LOCAL的DATA 當有網路時就會更新
}
