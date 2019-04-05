package com.shenjiahuan.eBook.filter;

import org.json.JSONObject;

import javax.servlet.*;
import java.io.IOException;
import java.util.stream.Collectors;

public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        res.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        chain.doFilter(req, res);
    }
    @Override
    public void destroy() {
    }
}
