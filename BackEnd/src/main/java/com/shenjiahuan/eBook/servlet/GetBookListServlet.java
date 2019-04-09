package com.shenjiahuan.eBook.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.manage.ManageBook;
import com.shenjiahuan.eBook.util.LoadRawBookList;
import org.apache.log4j.*;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/booklist")
public class GetBookListServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(GetBookListServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String keyword = (String)request.getAttribute("keyword");
        JsonObject result = null;
        ManageBook manageBook = new ManageBook();
        Gson gson = new Gson();
        List<Book> books = manageBook.showRelatedBookList(keyword);
        if (books != null) {
            result = new JsonObject();
            result.add("data", gson.toJsonTree(books));
            result.addProperty("status", 0);
        }
        out.write(result.toString());
        out.close();
    }
}
