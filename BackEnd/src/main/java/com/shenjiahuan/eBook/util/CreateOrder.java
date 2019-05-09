package com.shenjiahuan.eBook.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.OrderItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateOrder {
    public static Order fromJsonStr(String str, int uid) {
        BigDecimal payTime = new BigDecimal(new Date().getTime() / 1000.0);
        JsonParser parser = new JsonParser();
        JsonArray orderItemJson = parser.parse(str).getAsJsonObject().getAsJsonArray("orders");

        Order order = new Order(uid, payTime);
        List<OrderItem> orderItemList = new ArrayList<>();
        for (JsonElement elem : orderItemJson) {
            JsonObject orderJsonObject = elem.getAsJsonObject();
            int bookId = orderJsonObject.get("id").getAsInt();
            int count = orderJsonObject.get("count").getAsInt();
            OrderItem orderItem = new OrderItem(bookId, count);
            orderItem.setOrder(order);
            orderItemList.add(orderItem);
        }
        order.setItems(orderItemList);
        return order;
    }
}
