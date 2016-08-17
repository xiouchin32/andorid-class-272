package com.example.user.simpleui;

import java.util.List;

/**
 * Created by user on 2016/8/10.
 */
public class Order {
    String note;
    String storeInfo;
    List<DrinkOrder> drinkOrderList;

    public int getTotal()
    {
        int total = 0;
        //把DRINKORDER 飲料訂單拿出並且加總
        for(DrinkOrder drinkOrder: drinkOrderList)
        {
            total += drinkOrder.getlNumber()*drinkOrder.getDrink().getlPrices() + drinkOrder.getmNumber()*drinkOrder.getDrink().getmPrices();
        }
       return total;

    }
}
