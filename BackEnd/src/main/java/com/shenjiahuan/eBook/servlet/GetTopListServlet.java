package com.shenjiahuan.eBook.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.shenjiahuan.eBook.util.LoadRawBookList;
import org.apache.log4j.*;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class GetTopListServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(GetBookListServlet.class);

    protected Comparator<JSONObject> comparator;

    private JSONArray get(int limit) throws IOException {
        JSONArray rawBookList = LoadRawBookList.get();
        JSONArray result = new JSONArray();

        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < rawBookList.length(); i++) {
            jsonValues.add(rawBookList.getJSONObject(i));
        }
        Collections.sort(jsonValues, comparator);

        for (int i = 0; i < limit; i++) {
            result.put(jsonValues.get(i));
        }
        return result;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        JSONArray filteredBookList = get(10);
        out.write(filteredBookList.toString());
        out.close();
    }
}
