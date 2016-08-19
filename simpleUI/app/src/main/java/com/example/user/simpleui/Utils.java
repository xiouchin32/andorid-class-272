package com.example.user.simpleui;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by user on 2016/8/16.
 */
public class Utils {
    //要用static 才可以不用呼叫就可以使用他的fintion
    public static void writeFile(Context context,String fileName,String content)
    {
        //context 手機的IO
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_APPEND);
            fos.write(content.getBytes());//輸入檔案轉成BYTE
            fos.close();//關檔
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(Context context,String fileName)
    {
        try {
            FileInputStream fis  = context.openFileInput(fileName);
            byte[] bytes = new byte[2048];
            fis.read(bytes, 0, bytes.length);
            fis.close();
            String content =  new String(bytes);
            return content;

        } catch (FileNotFoundException e) {//IO錯誤 選 surround with catch
            e.printStackTrace();
        } catch (IOException e) {//fis.read(bytes,0,bytes.length); 出問題　按add　claus
            e.printStackTrace();
        }
        return "";//發生任何錯誤的話回傳空字串
    }
    public static double[] getLatLngFromAddress(String address)//使用再拿經緯度
    {
        try
        {
            address = URLEncoder.encode(address,"utf-8");//預防使用者打亂碼進去
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String apiURL = "http://maps.google.com/maps/api/geocode/json?address="+address;
        //提供用網址供給API的介面 瑞絲佛 要的檔案的格式為JSON ?後代表要帶的變數

        byte[] data = Utils.urlToBytes(apiURL);
        if(data ==null)
            return null;
        String result = new String(data);

        try {
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getString("status").equals("OK"))
            {
                JSONObject location = jsonObject.getJSONArray("result")
                        .getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONObject("location");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");

                return new double[]{lat,lng};
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] urlToBytes(String urlString)
    {
        //把資料連到SERVES上 把資料載回來
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while(inputStream.read(buffer)!=-1)
            {
                byteArrayOutputStream.write(buffer,0,len);
            }
            return  byteArrayOutputStream.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
