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

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;


/** Servlet responsible for creating new locations. */
@WebServlet("/new-location")
public class NewLocationServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
  public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
    // Sanitize user input to remove HTML tags and JavaScript.
    final String name = Jsoup.clean(request.getParameter("name"), Whitelist.none());
    final String city = Jsoup.clean(request.getParameter("city"), Whitelist.none());
    final String state = Jsoup.clean(request.getParameter("state"), Whitelist.none());
    final String description = Jsoup.clean(request.getParameter("description"), Whitelist.none());
    final String category = Jsoup.clean(request.getParameter("category"), Whitelist.none());
    final long num_likes = 0;
      //Replace with cloud storage image upload
    //String img = Jsoup.clean(request.getParameter("img"), Whitelist.none());
    final long timestamp = System.currentTimeMillis();



    // Get the file chosen by the user.
    final Part filePart = request.getPart("img");
    final String fileName = filePart.getSubmittedFileName();
    final InputStream fileInputStream = filePart.getInputStream();

    // Upload the file and get its URL
    final String uploadedFileUrl = uploadToCloudStorage(fileName, fileInputStream);

    final String img = Jsoup.clean(uploadedFileUrl, Whitelist.none());;
    
    //Construct each location entity
    final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    final KeyFactory keyFactory = datastore.newKeyFactory().setKind("Location");
    final FullEntity locationEntity =
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


  /** Uploads a file to Cloud Storage and returns the uploaded file's URL. */
  private static String uploadToCloudStorage(final String fileName, final InputStream fileInputStream) {
    final String projectId = "spring21-sps-23";
    final String bucketName = "spring21-sps-23.appspot.com";
    final Storage storage =
        StorageOptions.newBuilder().setProjectId(projectId).build().getService();
    final BlobId blobId = BlobId.of(bucketName, fileName);
    final BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

    // Upload the file to Cloud Storage.
    final Blob blob = storage.create(blobInfo, fileInputStream);

    // Return the uploaded file's URL.
    return blob.getMediaLink();
  }
}