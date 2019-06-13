package com.asm.java.controller.View;


import com.asm.java.entity.Article;
import com.asm.java.entity.PreviewDTO;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ViewSelectorController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("view-selector.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String bodyContent = convertInputStreamToString(req.getInputStream());
        PreviewDTO previewDTO = new Gson().fromJson(bodyContent, PreviewDTO.class);
        Document document = Jsoup.connect(previewDTO.getPreviewLink()).get();
        String title = document.select(previewDTO.getTitleSelector()).text();
        String description = document.select(previewDTO.getDesSelector()).text();
        String content = document.select(previewDTO.getContentSelector()).html();
        Article article = new Article();
        article.setLink(previewDTO.getPreviewLink());
        article.setTitle(title);
        article.setDescription(description);
        article.setContent(content);
        resp.getWriter().print(new Gson().toJson((article)));
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
