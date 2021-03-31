package com.google.sps.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.gson.Gson;
import com.google.sps.data.Location;

/** Servlet responsible for listing locations. */
@WebServlet("/list-locations")
public class ListLocationsServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("Location").setOrderBy(OrderBy.desc("timestamp"))
                .build();
        QueryResults<Entity> results = datastore.run(query);

        //Retrieve each location
        while (results.hasNext()) {
            Entity entity = results.next();

            long id = entity.getKey().getId();
            String name = entity.getString("name");
            String description = entity.getString("description");
            String category = entity.getString("category");
            String img = entity.getString("img");
            long timestamp = entity.getLong("timestamp");
            Location location = new Location(id, name, description, category, img, timestamp);

            //Create JSON object for each location
            Gson gson = new Gson();
            response.setContentType("application/json;");
            response.getWriter().println(gson.toJson(location));
        }
  }
}