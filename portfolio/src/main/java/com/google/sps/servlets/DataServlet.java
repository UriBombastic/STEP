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
import com.google.sps.data.Constants;
import com.google.gson.Gson;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/data") 
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
<<<<<<< HEAD
    // create comment and convert to Json
    Query query = new Query(Constants.COMMENT_ENTITY_NAME).addSort(Constants.TIMESTAMP_FIELD_NAME, SortDirection.DESCENDING);
=======
    // Create comment and convert to Json
    Query query = new Query(COMMENT_ENTITY_NAME).addSort(TIMESTAMP_FIELD_NAME, SortDirection.DESCENDING);
>>>>>>> 7164cacb3485f906419b4207dc14d070e27007cd
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    String commentsParameter = request.getParameter(Constants.NUM_COMMENT_PARAMETER);
    int maxComments = Integer.parseInt(commentsParameter);
    List<Comment> comments = new ArrayList<>();
    List<Entity> entities = results.asList(FetchOptions.Builder.withLimit(maxComments));
    
    for (int i = 0; i < entities.size(); i++) {
      String author = (String) entities.get(i).getProperty(Constants.AUTHOR_FIELD_NAME);
      String comment = (String) entities.get(i).getProperty(Constants.COMMENT_FIELD_NAME);
      String uploadUrl = (String) entities.get(i).getProperty(Constants.IMAGE_URL_FIELD_NAME);
      comments.add(new Comment(author, comment, uploadUrl));
    }

    // Send the JSON as the response
    String json = new Gson().toJson(comments);
    response.setContentType("application/json");
    response.getWriter().println(json);
  }
// experiment: moving this to FormHandlerServlet
/*
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String enteredName = request.getParameter("name-entry");
    String enteredComment = request.getParameter("comment-entry");
<<<<<<< HEAD
   // BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    String uploadUrl = "";//blobstoreService.createUploadUrl("/my-form-handler");
    // block completely empty comments
    if (!(enteredName.trim().equals("") && enteredComment.trim().equals(""))) {
      long timestamp = System.currentTimeMillis();
      // Upload via datastore
      Entity commentEntity = new Entity(COMMENT_ENTITY_NAME);
      commentEntity.setProperty(AUTHOR_FIELD_NAME, enteredName);
      commentEntity.setProperty(COMMENT_FIELD_NAME, enteredComment);
      commentEntity.setProperty(IMAGE_URL_FIELD_NAME, uploadUrl);
      commentEntity.setProperty(TIMESTAMP_FIELD_NAME, timestamp);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(commentEntity);
    }
    response.sendRedirect("/comments.html");
  }
*/
}
