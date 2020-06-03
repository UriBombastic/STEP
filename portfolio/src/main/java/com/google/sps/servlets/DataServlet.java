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
import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data") //"data" kept loading a blank screen no matter what I did so this was my workaround
public class DataServlet extends HttpServlet {

  private ArrayList<String> comments = new ArrayList<String>(
    Arrays.asList("This site is awesome!!!!!!1!",
    "Hi, I'm Andrew Yang, and I approve this message.\n",
    "Hi, I'm Andrew Ying, and I dissaprove of the above.\n")
  );
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //create comment adn convert to Json

    String json = convertToJson(comments);

    // Send the JSON as the response
    response.setContentType("application/json");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String enteredName = request.getParameter("name-entry");
    String enteredComment = request.getParameter("comment-entry");
    Comment comment = new Comment(enteredName, enteredComment);
    //String json = convertToJson(comment);
    comments.add(enteredComment); //testing to se if it's working
    response.sendRedirect("/comments.html");
  }

  private String convertToJson(ArrayList<String> comment)
  {
    Gson gson = new Gson();
    String json = gson.toJson(comment);
    return json;
  }
}
