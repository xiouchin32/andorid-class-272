package com.example.user.simpleui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment; 支援API22以下的再以前還沒有fragment得時候
import android.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DrinkOrderDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DrinkOrderDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrinkOrderDialog extends DialogFragment//DialogFragment 會是POPUP的視窗
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    NumberPicker mNumberPicker;
    NumberPicker lNumberPicker;
    RadioGroup  iceRadioGroup;
    RadioGroup sugarRadioGroup;
    EditText noteEditText;

    private DrinkOrder drinkOrder;

    private OnFragmentInteractionListener mListener;//介面的監聽器

    public DrinkOrderDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DrinkOrderDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static DrinkOrderDialog newInstance(DrinkOrder drinkOrder) {//目的new出frgment的 unstense
        DrinkOrderDialog fragment = new DrinkOrderDialog();
        Bundle args = new Bundle();//會攜帶所有變數
//        args.putString(ARG_PARAM1, param1);//希望別人拿的時候不要看到裡面東西
//        args.putString(ARG_PARAM2, param2);

        args.putParcelable(ARG_PARAM1, drinkOrder);
        fragment.setArguments(args);
        return fragment;
    }
//要在架構上新增自己的layout n所以將他原本設計的oncreat註解

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(getArguments()!=null)
        {
            this.drinkOrder = getArguments().getParcelable(ARG_PARAM1);
        }
        View contentView = getActivity().getLayoutInflater().inflate(R.layout.fragment_drink_order_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//依照他的架構創兆出POPUP的視窗

        builder.setView(contentView)
                .setTitle(drinkOrder.getDrink().getName())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        DrinkOrder drinkOrder = new DrinkOrder(drink); 在外面已經做過了
                        drinkOrder.setmNumber(mNumberPicker.getValue());
                        drinkOrder.setlNumber(lNumberPicker.getValue());
                        //寫一個小的function 回傳RADIOBUTTON 上面的字
                        drinkOrder.setIce(getSelectedTextFromRadioGroup(iceRadioGroup));
                        drinkOrder.setSugar(getSelectedTextFromRadioGroup(sugarRadioGroup));
                        drinkOrder.setNote(noteEditText.getText().toString());


                        if (mListener != null) {
                            mListener.onDrinkOrderResult(drinkOrder);//回傳
                            //當我們按下BUTTON 先把DRINK的資料做出來 再回傳
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        mNumberPicker = (NumberPicker)contentView.findViewById(R.id.mnumberPicker);
        lNumberPicker = (NumberPicker)contentView.findViewById(R.id.lnumberPicker);
        iceRadioGroup = (RadioGroup)contentView.findViewById(R.id.iceRadioGroup);
        sugarRadioGroup = (RadioGroup)contentView.findViewById(R.id.sugarRadioGroup);
        noteEditText = (EditText)contentView.findViewById(R.id.noteEditText);


        mNumberPicker.setMaxValue(100);
        mNumberPicker.setMinValue(0);//設定最大最小值
        mNumberPicker.setValue(drinkOrder.getmNumber());

        lNumberPicker.setMaxValue(100);
        lNumberPicker.setMinValue(0);
        lNumberPicker.setValue(drinkOrder.getlNumber());//一打開為之前的數量

        noteEditText.setText(drinkOrder.getNote());

        setSelectedTextInRadioGroup(drinkOrder.getIce(),iceRadioGroup);//把資料做復原
        setSelectedTextInRadioGroup(drinkOrder.getSugar(),sugarRadioGroup);


        return builder.create();

    }

    private String getSelectedTextFromRadioGroup(RadioGroup radioGroup)
    {
        //拿到Radio 上面的字
        int id = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(id);
        return  radioButton.getText().toString();

    }

    private  void setSelectedTextInRadioGroup(String selectedText,RadioGroup radioGroup)
    {
        //selectedText 選擇那個字 從radioGroup裡面找到並且打勾
        int count =  radioGroup.getChildCount();
        for(int i=0;i<count ;i++)
        {
            View view = radioGroup.getChildAt(i);
            if(view instanceof RadioButton)//判斷是否為RadioButton
            {
                RadioButton radioButton = (RadioButton)view;
                if(radioButton.getText().toString().equals(selectedText))
                {
                    radioButton.setChecked(true);
                }
                else
                    radioButton.setChecked(false);
            }

        }
    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);//從bumdel拿key值所對應到的變數
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,//ViewGrou就是幫他的是誰
//                             Bundle savedInstanceState) {//用來設定layout子葉面
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_drink_order_dialog, container, false);//幫他把fragment_drink_order_dialog頁面用出來
//    }//在這拿UIcomponet 的話' 用View view =  inflater.inflate(R.layout.fragment_drink_order_dialog, container, false);Textview textview = view ;return  textview來拿

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {    //看mListener存不存在 也就是有沒有會這個介面的人
//            mListener.onDrinkOrderResult(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {//context是actitvity ，攜帶activity 進來
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {//代表這個activity會不會這個介面，兩個人做溝通
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {//取消兩人溝通的介面
        super.onDetach();
        mListener = null;//把翻譯人變NULL 取消關係
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onDrinkOrderResult(DrinkOrder drinkOrder);//定義一個介面 讓他實做功能，才可以做溝 通
    }
}
