package com.asm.java.controller.API;

import com.asm.java.entity.Article;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ApiListArticleByCategoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo(); // /{value}/test
        String[] pathParts = pathInfo.split("/");
        String category = pathParts[1]; // test
        List<Article> list = ofy().load().type(Article.class).filter("url_category", category).filter("status",1).list();
        resp.getWriter().print(new Gson().toJson(list));
    }
}
