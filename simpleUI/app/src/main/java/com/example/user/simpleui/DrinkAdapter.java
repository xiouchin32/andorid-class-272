package com.example.user.simpleui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 2016/8/11.
 */
public class DrinkAdapter extends BaseAdapter {

    List<Drink> drinkList;
    LayoutInflater inflater;

    public DrinkAdapter(Context context,List<Drink> drinks)
    {
        this.drinkList = drinks;
       inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return drinkList.size();
    }

    @Override
    public Object getItem(int position) {
        return drinkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        // View convertView做有效回收 把上次itemviews拿出。parent  飲料可能有很多 可以用viewdroup的奶茶類都收起來，幫助把itemview group起來
        //重新設定UI上面的convertView
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.listview_drink_item,null);//第二個元件應該是要塞viewgroup
            TextView nameTextView = (TextView)convertView.findViewById(R.id.drinkNameTextView);
            TextView lpriceTextView = (TextView)convertView.findViewById(R.id.lPriceTextView);
            TextView mpriceTextView = (TextView)convertView.findViewById(R.id.mPriceTextView);
            ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);

            holder = new Holder();
            holder.drinknameTextView = nameTextView;
            holder.lpriceTextView = lpriceTextView;
            holder.mpriceTextView = mpriceTextView;
            holder.imageView = imageView;
            //代表holder已經握有元件

            convertView.setTag(holder); //把holdertag的東西放入TAG
        }
        else
        {
            holder = (Holder)convertView.getTag();//直接重convertview TAG拿出
        }

        Drink drink = drinkList.get(position);
        holder.drinknameTextView.setText(drink.getName());
        holder.lpriceTextView.setText(String.valueOf(drink.getlPrices()));
        holder.mpriceTextView.setText(String.valueOf(drink.getmPrices()));//把數字轉呈字串
//        holder.imageView.setImageResource(drink.imageId);
        String imageURL = drink.getImage().getUrl();//接下來只要讓圖片載入URL就可以顯示圖片
        //用picasso 載入圖片
        Picasso.with(inflater.getContext()).load(imageURL).into(holder.imageView);

        return convertView;
    }
    class Holder
    {
        TextView drinknameTextView;
        TextView lpriceTextView;
        TextView mpriceTextView;
        ImageView imageView;
    }
}
