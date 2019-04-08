package com.shenjiahuan.eBook.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.manage.ManageBook;
import com.shenjiahuan.eBook.util.LoadRawBookList;
import org.apache.log4j.*;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/bookinfo")
public class GetBookInfoServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(GetBookInfoServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        int bookid = (int)request.getAttribute("bookid");
        ManageBook manageBook = new ManageBook();
        Gson gson = new Gson();
        Book book = manageBook.showBook(bookid);
        if (book != null) {
            JsonElement jsonElement = gson.toJsonTree(book);
            jsonElement.getAsJsonObject().addProperty("status", 0);
            out.write(gson.toJson(jsonElement));
        }
        out.close();
    }
}
