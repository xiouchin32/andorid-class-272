package com.example.user.simpleui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class DrinkMenuActivity extends AppCompatActivity implements DrinkOrderDialog.OnFragmentInteractionListener {

    ListView drinkMenuLisView;
    TextView totalTextView;


    String[] names = {"冬瓜紅茶","玫瑰鹽奶蓋紅茶","珍珠紅茶拿鐵","紅茶拿鐵"};
    int[] lprices = {35,45,55,45};//大杯價格
    int[] mprices = {25,35,45,35};//中杯價格
    int[] imageIDs = {R.drawable.drink1,R.drawable.drink2,R.drawable.drink3,R.drawable.drink4};//飲料圖片

    List<Drink>  drinkList= new ArrayList<>();
    ArrayList<DrinkOrder> drinkOrdersList = new ArrayList<>();//把收到的訂單存下來
    //如果要給東西BUNDLE 必須告訴他們是神麼明確的型態 才可以給人帶回
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);

        setdata();//把飲料放到資料結構

        Intent intent = getIntent();
        drinkOrdersList = intent.getParcelableArrayListExtra("result");

        drinkMenuLisView = (ListView)findViewById(R.id.drinkMenuListView);
        totalTextView = (TextView)findViewById(R.id.totalTextView);

        updateTotalTextView();

        drinkMenuLisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Drink drink = (Drink)parent.getAdapter().getItem(position);
                //選取中杯大杯
                showDrinkOrferDialog(drink);
//                total += drink.mPrices;
//                totalTextView.setText(String.valueOf(total));
            }
        });

       // setupDrinkMenu(); 因為還沒拿到background

        Log.d("DEBUG", "DrinkMenuActivity Oncreate");
    }
    private  void  setdata()
    {
//        for(int i = 0;i<names.length;i++) {
//            Drink drink = new Drink();
//            drink.name = names[i];
//            drink.lPrices = lprices[i];
//            drink.mPrices = mprices[i];
//            drink.imageId = imageIDs[i];
//            drinkList.add(drink);//把drink 丟到list裡面
//        }//我們會把資料放到網上 會從網上抓
        Drink.getQuery().findInBackground(new FindCallback<Drink>() {
            @Override
            public void done(List<Drink> objects, ParseException e) {
                if(e == null)
                {
                    drinkList = objects;
                    setupDrinkMenu();
                }
            }
        });
    }

    public  void done(View view)
    {
        //資料放回去也需要Intent
        Intent intent = new Intent();
        intent.putExtra("result", drinkOrdersList);

        setResult(RESULT_OK, intent);
        //RESULT_OK (回去的狀態是好的) 就是完成了事情回上一頁
        //intent 回去的DATA;
        finish();//把頁面關閉
        //回activity 接收值
    }

    public void cancel(View view)
    {
        Intent intent = new Intent();
        intent.putExtra("resultcancel", "取消訂單");
        setResult(RESULT_CANCELED,intent);
        finish();
    }
    private  void setupDrinkMenu()
    {
        DrinkAdapter adapter = new DrinkAdapter(this,drinkList);
        drinkMenuLisView.setAdapter(adapter);
    }

    private void showDrinkOrferDialog(Drink drink)
    {
        DrinkOrder order = null;
        for(DrinkOrder drinkOrder :drinkOrdersList)
        {
            if(drinkOrder.drink.getObjectId().equals(drink.getObjectId()))
            {
                order = drinkOrder;
                break;
            }

        }

        if(order == null)
        {
            order = new DrinkOrder(drink);
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft =  fragmentManager.beginTransaction();//開起交易
        // "FragmentTransaction" 1.希望當大量刷新頁面的時候，刪掉跟加入一起做，要不要就都不要做   2.在有空狀況下才去做換頁面，避免發生問題

        //交易內容以下
        DrinkOrderDialog dialog = DrinkOrderDialog.newInstance(order);
//        ft.replace(R.id.root,dialog);//替換當前頁面的fragment
//        ft.commit();
        dialog.show(ft, "DinkOrderDialog");//在show的時候自動在translation做commit
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("DEBUG", "DrinkMenuActivity OnStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DEBUG", "DrinkMenuActivity OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DEBUG", "DrinkMenuActivity OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("DEBUG", "DrinkMenuActivity OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG", "DrinkMenuActivity OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("DEBUG", "DrinkMenuActivity OnRestart");
    }

    @Override
    public void onDrinkOrderResult(DrinkOrder drinkOrder) {
    //回收資料做整數的相加
        boolean flag= false;//沒有找到一樣的

        for(int i=0;i<drinkOrdersList.size();i++)
        {
            if(drinkOrdersList.get(i).drink.getObjectId().equals(drinkOrder.drink.getObjectId()))//判斷飲料ID是不是一樣
            {
//                order = drinkOrder; 不能這樣打因為訂單會一一連結這樣會出問題\
                drinkOrdersList.set(i,drinkOrder);
                flag = true;
                break;
            }
        }
        if(!flag)
            drinkOrdersList.add(drinkOrder);
        updateTotalTextView();

    }

    private  void updateTotalTextView()
    {
        int total = 0;
        //把DRINKORDER 飲料訂單拿出並且加總
        for(DrinkOrder drinkOrder: drinkOrdersList)
        {
            total += drinkOrder.lNumber*drinkOrder.drink.getlPrices() + drinkOrder.mNumber*drinkOrder.drink.getmPrices();

        }
        totalTextView.setText(String.valueOf(total));
    }
}
