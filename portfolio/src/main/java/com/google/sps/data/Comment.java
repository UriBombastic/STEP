package com.google.sps.data;

import java.util.Date;//will use later
public class Comment {
  private String comment;
  private String posterName;

  public Comment(String comment, String posterName) {
    this.comment = comment;
    this.posterName = posterName;
  }

  public Comment(String comment) {
    this.comment = comment;
    this.posterName = "Anonymous";
  }

  public String getComment() {
    return comment;
  }

  public String getPosterName() {
    return posterName;
  }
}