package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.gson.Gson;
import com.google.sps.data.Location;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        Query<Entity> query = 
            Query.newEntityQueryBuilder().setKind("Location").setOrderBy(OrderBy.desc("timestamp")).build();
        QueryResults<Entity> results = datastore.run(query);

        //Hard-coded location data
        ArrayList<Location> locations = new ArrayList<Location>();
        //Location location1 = new Location(365, "The Met", "NYC", "art", "dixie_mine_trail.jpeg", 1);
        //locations.add(location1);

        //Code for retrieving each of the locations
        while (results.hasNext()) {
            Entity entity = results.next();

            long id = entity.getKey().getId();
            String name = entity.getString("name");
            String city = entity.getString("city");
            String state = entity.getString("state");
            String description = entity.getString("description");
            String category = entity.getString("category");
            String img = entity.getString("img");
            long timestamp = entity.getLong("timestamp");
            long num_likes = entity.getLong("num_likes");
            Location location = new Location(id, name, city, state, description, category, num_likes, img, timestamp);
            locations.add(location);
        }

    //Create JSON object for locations
    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(locations));
    }
}