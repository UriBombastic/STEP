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

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data") 
public class DataServlet extends HttpServlet {

  private ArrayList<Comment> comments = new ArrayList<Comment>(
    Arrays.asList(new Comment("This site is awesome!!!!!!1!","Some fanboy"),
    new Comment("Hi, I'm Andrew Yang, and I approve this message.","Andrew Yang"),
    new Comment("Hi, I'm Andrew Ying, and I dissaprove of the above.","Andrew Ying")));

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //create comment and convert to Json
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Comment> comments = new ArrayList<>();
    for(Entity entity : results.asIterable()){
      String author = (String) entity.getProperty("author");
      String comment = (String) entity.getProperty("comment");
      comments.add(new Comment(author, comment));
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
    long timestamp = System.currentTimeMillis();
    //upload via datastore
    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("author", enteredName);
    commentEntity.setProperty("comment", enteredComment);
    commentEntity.setProperty("timestamp", timestamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);
  
    response.sendRedirect("/comments.html");
  }

}
