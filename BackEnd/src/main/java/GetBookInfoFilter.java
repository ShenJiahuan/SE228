import javax.servlet.*;
import java.io.IOException;

public class GetBookInfoFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        req.setAttribute("bookid", Integer.parseInt(req.getParameter("book_id")));
        chain.doFilter(req, res);
    }
    @Override
    public void destroy() {
    }
}
