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
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data1") //"data" kept loading a blank screen no matter what I did so this was my workaround
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //create comment adn convert to Json
   // Comment firstComment = new Comment("Andrew Yang", "Hi I'm Andrew Yang");
  ArrayList<String> comments = new ArrayList<String>();
  comments.add("This site is awesome!!!!!!1!");
  comments.add("Hi, I'm Andrew Yang, and I approve this message.\n");
  comments.add("Hi, I'm Andrew Ying, and I dissaprove of the above.\n");

    String json = convertToJson(comments);

    // Send the JSON as the response
    response.setContentType("text/html");
    response.getWriter().println(json);
  }

  private String convertToJson(Object comment)
  {
    Gson gson = new Gson();
    String json = gson.toJson(comment);
    return json;
  }
}
