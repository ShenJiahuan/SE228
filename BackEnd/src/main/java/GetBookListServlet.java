import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
        JSONArray bookList = LoadRawBookList.get();
        for (Object obj : bookList) {
            JSONObject book = (JSONObject) obj;
            if (book.getString("title").contains(keyword)) {
                out.write(book.toString());
            }
        }
        out.close();
    }
}
