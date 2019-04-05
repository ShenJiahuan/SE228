package com.shenjiahuan.eBook.filter;

import javax.servlet.*;
import java.io.IOException;

public class GetBookListFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        req.setAttribute("keyword", req.getParameter("keyword"));
        chain.doFilter(req, res);
    }
    @Override
    public void destroy() {
    }
}
