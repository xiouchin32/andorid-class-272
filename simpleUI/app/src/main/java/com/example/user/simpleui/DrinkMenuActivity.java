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
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);

        setdata();//把飲料放到資料結構
        drinkMenuLisView = (ListView)findViewById(R.id.drinkMenuListView);
        totalTextView = (TextView)findViewById(R.id.totalTextView);

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

        setupDrinkMenu();

        Log.d("DEBUG", "DrinkMenuActivity Oncreate");
    }
    private  void  setdata()
    {
        for(int i = 0;i<names.length;i++) {
            Drink drink = new Drink();
            drink.name = names[i];
            drink.lPrices = lprices[i];
            drink.mPrices = mprices[i];
            drink.imageId = imageIDs[i];
            drinkList.add(drink);//把drink 丟到list裡面
        }
    }

    public  void done(View view)
    {
        //資料放回去也需要Intent
        Intent intent = new Intent();
        intent.putExtra("result",String.valueOf(total));

        setResult(RESULT_OK,intent);
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
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction ft =  fragmentManager.beginTransaction();//開起交易
        // "FragmentTransaction" 1.希望當大量刷新頁面的時候，刪掉跟加入一起做，要不要就都不要做   2.在有空狀況下才去做換頁面，避免發生問題

        //交易內容以下
        DrinkOrderDialog dialog = DrinkOrderDialog.newInstance("","");
        ft.replace(R.id.root,dialog);//替換當前頁面的fragment
        ft.commit();
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
    public void onFragmentInteraction(Uri uri) {

    }
}
