import org.json.JSONObject;

import javax.servlet.*;
import java.io.IOException;
import java.util.stream.Collectors;

public class GetBookInfoFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String reqStr = req.getReader().lines().collect(Collectors.joining());
        JSONObject reqObject = new JSONObject(reqStr);
        req.setAttribute("bookid", reqObject.getInt("book_id"));
        chain.doFilter(req, res);
    }
    @Override
    public void destroy() {
    }
}
