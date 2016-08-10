package com.example.user.simpleui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    RadioGroup radioGroup;
    ListView listView;
    Spinner spinner;
    List<String> data = new ArrayList<>();

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
                if(checkedId == R.id.blackTearadioButton)
                {
                    drink = "Black Tea";
                }
                else if(checkedId == R.id.greenTearadioButton2)
                {
                    drink = "Green Tea";
                }
            }
        });//要知道有沒有改變

        setupListView();
        setupSpinner();
    }
    private void setupListView()
    {
//        String []data = new String[]{"1","2","3","4","5","6","7","8"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data);
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
        text = text + " Order: " + drink;
        textView.setText(text);
        editText.setText("");
        data.add(text);

        setupListView();
//        重新刷新LISTVIEW
    }
}
