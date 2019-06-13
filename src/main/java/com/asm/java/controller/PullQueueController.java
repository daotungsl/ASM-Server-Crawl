package com.asm.java.controller;

import com.asm.java.entity.Article;
import com.asm.java.entity.Categories;
import com.asm.java.entity.CrawlerSource;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskHandle;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class PullQueueController extends HttpServlet {

    private static Queue q = QueueFactory.getQueue("my-queue");
    private static final Logger LOGGER = Logger.getLogger(PullQueueController.class.getSimpleName());
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        List<TaskHandle> tasks = q.leaseTasks(60, TimeUnit.SECONDS, 1);
        if (tasks.size() > 0) {
            String articleId = new String(tasks.get(0).getPayload());
            LOGGER.info("Got task with article id: " + articleId);

            // gọi trong database article với id như trên.
            Article article = ofy().load().type(Article.class).id(articleId).now();
            CrawlerSource crawlerSource = ofy().load().type(CrawlerSource.class).id(article.getSource()).now();

            // gọi lên trang chủ lấy từ source để crawl dữ liêu.
            Document document = Jsoup.connect(article.getLink()).get();
            String link = article.getLink();
            String source = article.getSource();
            String img = article.getImg();
            String title = document.select(crawlerSource.getTitleSelector()).text();
            String url_detail = toSlug(title)+"-"+new Date().getTime();
            String description = document.select(crawlerSource.getDesSelector()).text();
            String content = document.select(crawlerSource.getContentSelector()).html();
            String[] arr_category = articleId.split("/");
            String url_category = arr_category[3];
            String category = "";
            switch (url_category) {
                case "suc-khoe":
                    category = "Sức khoẻ";
                    break;
                case "giao-duc":
                    category = "Giáo dục";
                    break;
                case "phap-luat":
                    category = "Pháp luật";
                    break;
                case "oto-xe-may":
                    category = "Xe";
                    break;
                case "so-hoa":
                    category = "Số hoá";
                    break;
                case "khoa-hoc":
                    category = "Khoa học";
                    break;
                case "du-lich":
                    category = "Du lịch";
                    break;
                case "doi-song":
                    category = "Đời sống";
                    break;
                case "the-thao":
                    category = "Thể thao";
                    break;
                case "giai-tri":
                    category = "Giải trí";
                    break;
                case "kinh-doanh":
                    category = "Kinh doanh";
                    break;
                case "the-gioi":
                    category = "Thế giới";
                    break;
                case "thoi-su":
                    category = "Thời sự";
                    break;
                case "y-kien":
                    category = "Ý kiến";
                    break;
                case "bong-da":
                    category = "Thể thao";
                    break;
            }
            Categories categories = new Categories(url_category, category);
            // lấy xong thì save lại vào database.
            Article newArticle = new Article(link, title, img, description, content, source,category, url_category, url_detail);

            if (title.length() < 3 || img.length() < 3 || description.length() < 3 || content.length() < 3 || source.length() < 3 || url_category.length() < 3 || url_detail.length() < 3) {
                newArticle.setStatus(-1);
            } else {
                newArticle.setStatus(1);
            }


            ofy().save().entity(categories).now();
            ofy().save().entity(newArticle).now();


            q.deleteTask(tasks.get(0));
            LOGGER.info("Finish task with article id: " + articleId);
        }

    }

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = EDGESDHASHES.matcher(slug).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
