package com.asm.java.cronjob;

import com.asm.java.entity.Categories;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import com.googlecode.objectify.ObjectifyService;
import com.asm.java.config.ASM_View_crawlConstant;
import com.asm.java.entity.Article;
import com.asm.java.entity.CrawlerSource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class CrawlerController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CrawlerController.class.getName());
    private static Queue q = QueueFactory.getQueue("my-queue");

    static {
        ObjectifyService.register(Article.class);
        ObjectifyService.register(CrawlerSource.class);
        ObjectifyService.register(Categories.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Crawler job. Started at: " + Calendar.getInstance().getTime());
        try {
            List<CrawlerSource> listSouce = ofy().load().type(CrawlerSource.class).list();
            for (CrawlerSource source :
                    listSouce) {
                Document document = Jsoup.connect(source.getUrl()).get();
                Elements els = document.select(source.getLinkSelector());
                Elements elsimg = document.select(source.getImgSelector());
                HashSet<String> uniqueLinks = new HashSet<>();
                HashSet<String> uniqueImgs = new HashSet<>();
                for (Element el :
                        els) {
                    uniqueLinks.add(el.attr("href"));
                }
                for (Element el :
                        elsimg) {
                    uniqueImgs.add(el.attr("src"));
                }
                String[] arrUniqueLinks = uniqueLinks.toArray(new String[uniqueLinks.size()]);
                String[] arrUniqueImgs = uniqueImgs.toArray(new String[uniqueImgs.size()]);
                for (int i = 0; i < arrUniqueLinks.length ; i++) {
                    Article article = new Article(arrUniqueLinks[i],arrUniqueImgs[i], Article.Status.PENDING);
                    Article existArticle = ofy().load().type(Article.class).id(arrUniqueLinks[i]).now();
                    if (existArticle == null) {
                        article.setSource(source.getUrl());
                        ofy().save().entity(article).now();
//                    publicLink(link);
                        addToQueue(article.getLink());
                    }
                }
//                Document document = Jsoup.connect(source.getUrl()).get();
//                ArrayList<Element> els = document.select(source.getLinkSelector());
//                ArrayList<Element> elsimg = document.select(source.getImgSelector());
//                Map<String,String> uniqueArticle = new HashMap<>();
//                Iterator elsIterator = els.iterator();
//                Iterator elsimgIterator = elsimg.iterator();
//
//                while (elsIterator.hasNext() && elsimgIterator.hasNext()){
//                    Element link = (Element) elsIterator.next();
//                    Element img = (Element) elsIterator.next();
//                    uniqueArticle.put(link.attr("href"),img.attr("src"));
//                }
//
//                for (Map.Entry<String,String> entry :
//                    uniqueArticle.entrySet() ) {
//                    Article article = new Article(entry.getKey(), entry.getValue(), Article.Status.PENDING);
//                    Article existArticle = ofy().load().type(Article.class).id(entry.getKey()).now();
//                    if (existArticle == null) {
//                        article.setSource(source.getUrl());
//                        ofy().save().entity(article).now();
////                    publicLink(link);
//                        addToQueue(article.getLink());
//                    }
//                }



            }

        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void addToQueue(String articleId) {
        q.add(TaskOptions.Builder.withMethod(TaskOptions.Method.PULL).payload(articleId));
    }

    public static void publicLink(String link) {
        try {
            ProjectTopicName topicName = ProjectTopicName.of(
                    ASM_View_crawlConstant.GAE_PROJECT_ID,
                    ASM_View_crawlConstant.GAE_PUBSUB_CRAWLER_TOPIC);
            Publisher publisher = null;
            List<ApiFuture<String>> futures = new ArrayList<>();
            try {
                CredentialsProvider credentialsProvider =
                        FixedCredentialsProvider.create(
                                ServiceAccountCredentials.fromStream(new FileInputStream(ASM_View_crawlConstant.GAE_PUBSUB_KEY_PATH)));
                // Create a publisher instance with default settings bound to the topic
                publisher = Publisher.newBuilder(topicName).setCredentialsProvider(credentialsProvider).build();
                ByteString data = ByteString.copyFromUtf8(link);
                PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                        .setData(data)
                        .build();
                // Schedule a message to be published. Messages are automatically batched.
                ApiFuture<String> future = publisher.publish(pubsubMessage);
                futures.add(future);
            } catch (Exception ex) {
                LOGGER.severe(ex.getMessage());
                ex.printStackTrace();
            } finally {
                List<String> messageIds = ApiFutures.allAsList(futures).get();

                for (String messageId : messageIds) {
                    System.out.println(messageId);
                }

                if (publisher != null) {
                    // When finished with the publisher, shutdown to free up resources.
                    publisher.shutdown();
                }
            }
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
        }
    }


}
