package com.shenjiahuan.eBook.servlet;

import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.manage.ManageBook;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.Comparator;
import java.util.List;

@WebServlet("/hot")
public class GetHotListServlet extends GetTopListServlet {
    protected List<Book> showTopBookList(int limit) {
        ManageBook manageBook = new ManageBook();
        return manageBook.showTopBookList("hot", limit);
    }
}
