package com.shenjiahuan.eBook.entrypoint;

import com.google.gson.Gson;
import com.shenjiahuan.eBook.response.HandlerResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component("unauthorizedEntryPoint")
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(401);

        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new HandlerResponse("Unauthorized", 401)));
        out.close();
    }
}
