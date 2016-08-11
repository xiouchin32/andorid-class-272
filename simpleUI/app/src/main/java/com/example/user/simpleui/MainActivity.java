package com.example.user.simpleui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CODE_DRINK_MENU_ACTIVITY = 0;
    TextView textView;
    EditText editText;
    RadioGroup radioGroup;
    ListView listView;
    Spinner spinner;
//    List<String> data = new ArrayList<>();
    List<Order> data = new ArrayList<>();

    String drink = "Black Tea";//初設為紅茶

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        editText = (EditText)findViewById(R.id.editText);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        listView = (ListView)findViewById(R.id.listView);
        spinner = (Spinner)findViewById(R.id.spinner);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //知道有改變，在事件裡寫東西 checkedId是知道選哪個
                if (checkedId == R.id.blackTearadioButton) {
                    drink = "Black Tea";
                } else if (checkedId == R.id.greenTearadioButton2) {
                    drink = "Green Tea";
                }
            }
        });//要知道有沒有改變
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = (Order) parent.getAdapter().getItem(position);
                Toast.makeText(MainActivity.this, order.note, Toast.LENGTH_LONG).show();
                //MainActivity.this 被包在listner裡面 this 代表的是 listener
            }
        });
        setupListView();
        setupSpinner();

        Log.d("DEBUG","MainActivity OnCreate");
    }
    private void setupListView()
    {
//        String []data = new String[]{"1","2","3","4","5","6","7","8"};
//        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data);
//        List<Map<String,String>> mapList = new ArrayList<>();
//        for(Order order: data)
//        {
//            Map<String,String> item = new HashMap<>();
//
//            item.put("note",order.note);
//            item.put("storeInfo",order.storeInfo);
//            item.put("drink", order.drink);
//            mapList.add(item);
////            每一個key，對應到一個value
//        }

        String[] from = {"note","storeInfo","drink"};
        int[] to = {R.id.noteTextView,R.id.storeInfoTextView,R.id.drinktextView};
//        因為resourse裡面拿出的都是ID，所以用int
//        SimpleAdapter adapter = new SimpleAdapter(this,mapList,R.layout.listview_order_item,from,to);
        OrderAdapter adapter = new OrderAdapter(this,data);

        listView.setAdapter(adapter);
    }
    private  void setupSpinner()
    {
//        dase來自resourse
        String []storeInfo = getResources().getStringArray(R.array.storeInfo);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,storeInfo);
        spinner.setAdapter(adapter);
    }
    public void click (View view)
    {
        String text = editText.getText().toString();//轉字串放上去，因為怕抓到的東西會有其他屬性
//        text = text + " Order: " + drink;
        String result = text + " Order: " + drink;
        textView.setText(result);
        editText.setText("");

        Order order = new Order();//        new一個訂單
        order.note = text;
        order.drink = drink;
        order.storeInfo = (String)spinner.getSelectedItem();

        data.add(order);
        setupListView();
//        重新刷新LISTVIEW
    }
    public void goToMenu(View view)//進來呼叫為button
    {
        Intent intent = new Intent();
        intent.setClass(this,DrinkMenuActivity.class);//呼叫DrinkMenuActivity.class 到this
        startActivityForResult(intent, REQUEST_CODE_DRINK_MENU_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//requestCode 辨別重哪個actitvity回來 resultCode 狀態
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_DRINK_MENU_ACTIVITY)
        {
            if(resultCode == RESULT_OK)
            {
                String result = data.getStringExtra("result");
                Toast.makeText(this ,result,Toast.LENGTH_LONG).show();
            }
        }
        //requestcode代表頁面的序碼
    }

    //以下為生命周期，在android monitor 可以觀察
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("DEBUG", "MainActivity OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DEBUG", "MainActivity OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DEBUG", "MainActivity OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("DEBUG", "MainActivity OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG", "MainActivity OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("DEBUG", "MainActivity OnRestart");
    }
}
