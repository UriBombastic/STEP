// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.sps.data.Comment;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/data") 
public class DataServlet extends HttpServlet {

  private final String COMMENT_ENTITY_NAME = "Comment";
  private final String AUTHOR_FIELD_NAME = "author";
  private final String COMMENT_FIELD_NAME = "comment";
  private final String TIMESTAMP_FIELD_NAME = "timestamp";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // create comment and convert to Json
    Query query = new Query(COMMENT_ENTITY_NAME).addSort(TIMESTAMP_FIELD_NAME, SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    String commentsParameter = request.getParameter("num-comments");
    System.out.println("Comments parameter is: " + commentsParameter);
    int numComments = Integer.parseInt(commentsParameter);
    int commentsLength = 0;
    List<Comment> comments = new ArrayList<>();
    for(Entity entity : results.asIterable()){
      String author = (String) entity.getProperty(AUTHOR_FIELD_NAME);
      String comment = (String) entity.getProperty(COMMENT_FIELD_NAME);
      comments.add(new Comment(author, comment));
      commentsLength++; // keep track of how many comments were added.
      // cap number of comments to designated value.
      if(commentsLength >= numComments)
        break;
    }

    // Send the JSON as the response
    String json = new Gson().toJson(comments);
    response.setContentType("application/json");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String enteredName = request.getParameter("name-entry");
    String enteredComment = request.getParameter("comment-entry");
    // block completely empty comments
    if(!(enteredName.trim().equals("") && enteredComment.trim().equals(""))) {
      long timestamp = System.currentTimeMillis();
      // upload via datastore
      Entity commentEntity = new Entity(COMMENT_ENTITY_NAME);
      commentEntity.setProperty(AUTHOR_FIELD_NAME, enteredName);
      commentEntity.setProperty(COMMENT_FIELD_NAME, enteredComment);
      commentEntity.setProperty(TIMESTAMP_FIELD_NAME, timestamp);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(commentEntity);
    }
    response.sendRedirect("/comments.html");
  }

}
