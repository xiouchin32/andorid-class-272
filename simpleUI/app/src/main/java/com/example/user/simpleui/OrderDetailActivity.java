package com.example.user.simpleui;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import android.os.Handler;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class OrderDetailActivity extends AppCompatActivity implements GeoCodingTask.GeocodingCallback{

    TextView latlngTextView;
    GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        TextView noteTextView = (TextView)findViewById(R.id.noteTextView);
        TextView storeInfoTextView = (TextView)findViewById(R.id.storeinfotextView);
        TextView drinkOrderResultTextView = (TextView)findViewById(R.id.drinkOrderResultTextView);
//        final TextView latlngTextView = (TextView)findViewById(R.id.latLngTextView);//經緯度需要直接指到外面CLASS的變數 所以先不用找他的ID

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

      //  String address = order.getStoreInfo().split(",")[1];

        MapFragment fragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);
        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                (new GeoCodingTask(OrderDetailActivity.this)).execute("台北市大安區羅斯福路四段一號");
            }
        });//拿到地圖後呼叫一個CALLBACK

//        final Handler handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                latlngTextView.setText("123,456");
//                return false;
//            }
//        });
//
//        Thread thread = new Thread(new Runnable() {//new Runnable 代表一件事情 也就是第一個要做的事情
//            @Override
//            public void run() {
//                //要模擬等待的時候，如果你要更改UI上面事件你必須要用HANDLER 不能直接用THREAD改
//                try {
//                    Thread.sleep(10000);
//                    handler.sendMessage(new Message());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
////        thread.run();//假設ＭＡＩＮＴＨＲＥＡＤ前面有１０件事情run 會是他第十一件事情，但他在做這件事情時他先睡一秒
//        thread.start();

        //但後來沒有人用thread了

    }

    @Override
    public void done(double[] latlng) {
        if(latlng != null)
        {
            String latlngString = String.valueOf(latlng[0]+","+latlng[1]);
            latlngTextView.setText(latlngString);

            LatLng latLng = new LatLng(latlng[0],latlng[1]);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,17);//newLatLngZoom 限定相機放到我們要的位置跟放大倍數;
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("NTU");

            map.moveCamera(cameraUpdate);
            map.addMarker(markerOptions);


        }
    }
}
