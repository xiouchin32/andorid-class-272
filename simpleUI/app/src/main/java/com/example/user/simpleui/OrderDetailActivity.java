package com.example.user.simpleui;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import android.os.Handler;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        TextView noteTextView = (TextView)findViewById(R.id.noteTextView);
        TextView storeInfoTextView = (TextView)findViewById(R.id.storeinfotextView);
        TextView drinkOrderResultTextView = (TextView)findViewById(R.id.drinkOrderResultTextView);
        final TextView latlngTextView = (TextView)findViewById(R.id.latLngTextView);

        Intent intent = getIntent();
        Order order = intent.getParcelableExtra("order");
        noteTextView.setText(order.getNote());
        storeInfoTextView.setText(order.getStoreInfo());

        String resultText = "";
        for(DrinkOrder drinkOrder:order.getDrinkOrderList())
        {
            String lNumber = String.valueOf(drinkOrder.getlNumber());
            String mNumber = String.valueOf(drinkOrder.getmNumber());
            String drinkName = drinkOrder.getDrink().getName();
            resultText +=drinkName+"   M:  " + mNumber + " L:  "+lNumber+"\n";
        }
        drinkOrderResultTextView.setText(resultText);
        //先讓UI做改變要先創造HANDLER  .... MAINTHREAD創造出HANDLEW

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                latlngTextView.setText("123,456");
                return false;
            }
        });

        Thread thread = new Thread(new Runnable() {//new Runnable 代表一件事情 也就是第一個要做的事情
            @Override
            public void run() {
                //要模擬等待的時候，如果你要更改UI上面事件你必須要用HANDLER 不能直接用THREAD改
                try {
                    Thread.sleep(10000);
                    handler.sendMessage(new Message());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
//        thread.run();//假設ＭＡＩＮＴＨＲＥＡＤ前面有１０件事情run 會是他第十一件事情，但他在做這件事情時他先睡一秒
        thread.start();
    }
}
