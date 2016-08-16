package com.example.user.simpleui;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
}
