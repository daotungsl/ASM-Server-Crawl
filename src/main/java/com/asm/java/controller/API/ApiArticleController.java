package com.asm.java.controller.API;

import com.asm.java.entity.Article;
import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ApiArticleController extends HttpServlet {
    static {
        ObjectifyService.register(Article.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Article> list = ofy().load().type(Article.class).filter("status",1).list();
        resp.getWriter().print(new Gson().toJson(list));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String bodyContent = convertInputStreamToString(req.getInputStream());
        Article article = new Gson().fromJson(bodyContent, Article.class);
        ofy().save().entity(article).now();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(new Gson().toJson(article));
    }

    private static String convertInputStreamToString (InputStream inputStream) throws IOException{
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer))!= -1){
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }
}
