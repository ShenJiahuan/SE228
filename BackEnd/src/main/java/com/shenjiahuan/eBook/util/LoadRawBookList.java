package com.shenjiahuan.eBook.util;

import com.shenjiahuan.eBook.servlet.GetBookInfoServlet;
import org.json.JSONArray;

import java.io.InputStream;
import java.util.Scanner;

public class LoadRawBookList {
    public static JSONArray get() throws java.io.IOException {
        InputStream is = GetBookInfoServlet.class.getResourceAsStream("/bookList.json");
        Scanner s = new Scanner(is).useDelimiter("\\A");
        String jsonStr = s.hasNext() ? s.next() : "";
        return new JSONArray(jsonStr);
    }
}
