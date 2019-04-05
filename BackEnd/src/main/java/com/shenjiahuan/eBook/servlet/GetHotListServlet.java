package com.shenjiahuan.eBook.servlet;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.Comparator;

@WebServlet("/hot")
public class GetHotListServlet extends GetTopListServlet {
    @Override
    public void init() throws ServletException {
        comparator = new Comparator<JSONObject>() {
            private static final String KEY_NAME = "hot";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                int valA, valB;
                valA = (int) a.get(KEY_NAME);
                valB = (int) b.get(KEY_NAME);
                return Integer.compare(valA, valB);
            }
        };
    }
}
