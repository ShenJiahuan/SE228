package com.shenjiahuan.eBook.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shenjiahuan.eBook.entity.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateOrderList {
    public static List<Order> fromJsonStr(String str, int uid, boolean paid) {
        double addTime = new Date().getTime() / 1000.0;
        System.out.println(paid);
        JsonParser parser = new JsonParser();
        JsonArray orders = parser.parse(str).getAsJsonObject().getAsJsonArray("orders");
        List<Order> orderList = new ArrayList<>();
        for (JsonElement order : orders) {
            JsonObject orderJsonObject = order.getAsJsonObject();
            int bookId = orderJsonObject.get("id").getAsInt();
            int count = orderJsonObject.get("count").getAsInt();
            if (paid) {
                orderList.add(new Order(uid, bookId, count, addTime, addTime, (byte) 1));
            } else {
                orderList.add(new Order(uid, bookId, count, addTime, null, (byte )0));
            }
        }
        return orderList;
    }
}
