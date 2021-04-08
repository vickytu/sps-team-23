package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import com.google.sps.data.Location;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/** Servlet responsible for creating new locations. */
@WebServlet("/new-location")
public class NewLocationServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Sanitize user input to remove HTML tags and JavaScript.
    String name = Jsoup.clean(request.getParameter("name"), Whitelist.none());
    String city = Jsoup.clean(request.getParameter("city"), Whitelist.none());
    String state = Jsoup.clean(request.getParameter("state"), Whitelist.none());
    String description = Jsoup.clean(request.getParameter("description"), Whitelist.none());
    String category = Jsoup.clean(request.getParameter("category"), Whitelist.none());
    long num_likes = 0;
    String img = "dixie_mine_trail.jpeg";  //Replace with cloud storage image upload
    //String img = Jsoup.clean(request.getParameter("img"), Whitelist.none());
    long timestamp = System.currentTimeMillis();

    //Construct each location entity
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Location");
    FullEntity locationEntity =
        Entity.newBuilder(keyFactory.newKey())
            .set("name", name)
            .set("city", city)
            .set("state", state)
            .set("description", description)
            .set("category", category)
            .set("img", img)
            .set("timestamp", timestamp)
            .set("num_likes", num_likes)
            .build();
    //Save in datastore
    datastore.put(locationEntity);

    response.sendRedirect("/index.html");
  }
}