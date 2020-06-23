// Based off sample code at: 
// https://developers.google.com/youtube/v3/docs/commentThreads/list?apix=true&apix_params=%7B%22part%22%3A%22snippet%2Creplies%22%2C%22videoId%22%3A%22wtLJPvx7-ys%22%7D
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/YouTubeData")
public class YouTubeDataServlet extends HttpServlet {
   
  private static final String DEVELOPER_KEY = "AIzaSyDhTPFz-z0XN0a_VUyZoYNNg8Qcz0X_9Sc";

  private static final String APPLICATION_NAME = "API code samples";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  /**
   * Call function to create API service object. Define and
   * execute API request. Print API response.
   *
   * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
   */
   @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    try {
      System.err.println("Hello world!");
      YouTube youtubeService = getService();
      String url = request.getParameter("url");
        // Define and execute the API request
      YouTube.CommentThreads.List commentRequest = youtubeService.commentThreads()
          .list("snippet,replies");
      CommentThreadListResponse commentResponse = commentRequest.setKey(DEVELOPER_KEY)
          .setVideoId(url)
          .setOrder("relevance")
          .setMaxResults(100L)
          .execute();
    //  System.out.println(commentResponse);
      response.getWriter().println(commentResponse);
    } catch(GeneralSecurityException | GoogleJsonResponseException e) { 
        //oh well
    }

  }

  /**
   * Build and return an authorized API client service.
   *
   * @return an authorized API client service
   * @throws GeneralSecurityException, IOException
   */
  public static YouTube getService() throws GeneralSecurityException, IOException {
    final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
        .setApplicationName(APPLICATION_NAME)
        .build();
  }

}