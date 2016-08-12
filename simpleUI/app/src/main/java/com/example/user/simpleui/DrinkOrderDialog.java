package com.example.user.simpleui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment; 支援API22以下的再以前還沒有fragment得時候
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DrinkOrderDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DrinkOrderDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrinkOrderDialog extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DrinkOrderDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrinkOrderDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static DrinkOrderDialog newInstance(String param1, String param2) {//目的new出frgment的 unstense<
        DrinkOrderDialog fragment = new DrinkOrderDialog();
        Bundle args = new Bundle();//會攜帶所有變數
        args.putString(ARG_PARAM1, param1);//希望別人拿的時候不要看到裡面東西
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);//從bumdel拿key值所對應到的變數
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,//ViewGrou就是幫他的是誰
                             Bundle savedInstanceState) {//用來設定layout子葉面
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drink_order_dialog, container, false);//幫他把fragment_drink_order_dialog頁面用出來
    }//在這拿UIcomponet 的話' 用View view =  inflater.inflate(R.layout.fragment_drink_order_dialog, container, false);Textview textview = view ;return  textview來拿

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {//看mListener存不存在 也就是有沒有會這個介面的人
            mListener.onFragmentInteraction(uri);
        }
    }

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);//定義一個介面 讓他實做功能，才可以做溝 通
    }
}
