package com.asm.java.controller;

import com.asm.java.entity.CrawlerSource;
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
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class CrawlerSourceController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CrawlerSourceController.class.getSimpleName());

    static {
        ObjectifyService.register((CrawlerSource.class));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("Hello Admin");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String bodyContent = convertInputStreamToString(req.getInputStream());

        CrawlerSource crawlerSource1 = new  Gson().fromJson(bodyContent,CrawlerSource.class);
        String url = crawlerSource1.getUrl();
        String linkSelector = crawlerSource1.getLinkSelector();
        String imgSelector = crawlerSource1.getImgSelector();
        String titleSelector = crawlerSource1.getTitleSelector();
        String desSelector = crawlerSource1.getDesSelector();
        String contentSelector = crawlerSource1.getContentSelector();
        String authorSelector = crawlerSource1.getAuthorSelector();
        // validate
        CrawlerSource crawlerSource = new CrawlerSource(url, CrawlerSource.Status.ACTIVE.getValue());
        crawlerSource.setTitleSelector(titleSelector);
        crawlerSource.setDesSelector(desSelector);
        crawlerSource.setImgSelector(imgSelector);
        crawlerSource.setContentSelector(contentSelector);
        crawlerSource.setAuthorSelector(authorSelector);
        crawlerSource.setLinkSelector(linkSelector);
        LOGGER.info(new Gson().toJson(crawlerSource));
        ofy().save().entity(crawlerSource).now();
        resp.getWriter().print("Okie");

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
