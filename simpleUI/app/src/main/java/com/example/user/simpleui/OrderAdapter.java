package com.example.user.simpleui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 2016/8/10.
 */
public class OrderAdapter extends BaseAdapter{
    //建構子
    List<Order> orders;
    LayoutInflater layoutInflater;


    public  OrderAdapter(Context context,List<Order> orderList)
    {
        this.orders = orderList;
        this.layoutInflater = layoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
//        View convertView做有效回收 把上次itemviews拿出。parent  飲料可能有很多 可以用viewdroup的奶茶類都收起來，幫助把itemview group起來
        //重新設定UI上面的convertView
        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.listview_order_item,null);//第二個元件應該是要塞viewgroup
            TextView noteTextView = (TextView)convertView.findViewById(R.id.noteTextView);
            TextView storeInfoTextView = (TextView)convertView.findViewById(R.id.storeInfoTextView);
            TextView drinktextView = (TextView)convertView.findViewById(R.id.drinktextView);

            holder = new Holder();
            holder.noteTextView = noteTextView;
            holder.drinktextView = drinktextView;
            holder.storeInfoTextView = storeInfoTextView;
            //代表holder已經握有元件

            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder)convertView.getTag();//直接重convertview 拿出
        }

        Order order = orders.get(position);
        holder.noteTextView.setText(order.note);
        holder.storeInfoTextView.setText(order.storeInfo);
        holder.drinktextView.setText(order.drink);

        return convertView;
    }
    class Holder//握有哪些UI元件
    {
        TextView noteTextView;
        TextView storeInfoTextView;
        TextView drinktextView;

    }
}
