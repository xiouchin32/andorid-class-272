package com.example.user.simpleui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DrinkMenuActivity extends AppCompatActivity {

    ListView drinkMenuLisView;
    TextView totalTextView;


    String[] names = {"冬瓜紅茶","玫瑰鹽奶蓋紅茶","珍珠紅茶拿鐵","紅茶拿鐵"};
    int[] lprices = {35,45,55,45};//大杯價格
    int[] mprices = {25,35,45,35};//中杯價格
    int[] imageIDs = {R.drawable.drink1,R.drawable.drink2,R.drawable.drink3,R.drawable.drink4};//飲料圖片

    List<Drink>  drinkList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);

        setdata();//把飲料放到資料結構
        drinkMenuLisView = (ListView)findViewById(R.id.drinkMenuListView);
        totalTextView = (TextView)findViewById(R.id.totalTextView);

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
    private  void setupDrinkMenu()
    {
        DrinkAdapter adapter = new DrinkAdapter(this,drinkList);
        drinkMenuLisView.setAdapter(adapter);
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
}
