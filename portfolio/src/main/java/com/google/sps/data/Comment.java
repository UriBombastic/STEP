package com.google.sps.data;

public class Comment {
  private String comment;
  private String posterName;

  public Comment(String posterName, String comment) {
    this.comment = comment;
    this.posterName = posterName;
    if(comment.trim().equals("")) this.comment = "\"Hey look at me I commented whitespace I'm so cool\"";
    if(posterName.trim().equals("")) this.posterName = "Anonymous";
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