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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.manage.ManageBook;
import com.shenjiahuan.eBook.util.LoadRawBookList;
import org.apache.log4j.*;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class GetTopListServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(GetBookListServlet.class);
    protected abstract List<Book> showTopBookList(int limit);

    private JsonObject get(int limit) throws IOException {
        JsonObject result = null;
        ManageBook manageBook = new ManageBook();
        Gson gson = new Gson();
        logger.debug("\n\n\n\n");
        List<Book> books = showTopBookList(limit);
        if (books != null) {
            result = new JsonObject();
            result.add("data", gson.toJsonTree(books));
            result.addProperty("status", 0);
            logger.debug(result);
        }
        return result;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        JsonObject filteredBookList = get(10);
        out.write(filteredBookList.toString());
        out.close();
    }
}
