package com.google.sps.data;

public class Comment {
  private String comment;
  private String posterName;
  private String imageURL;

  public Comment(String posterName, String comment, String imageURL) {
    this.comment = comment;
    this.posterName = posterName;
    this.imageURL = imageURL;
    if(comment.trim().equals("") && imageURL==null) 
      this.comment = "\"Hey look at me I commented whitespace I'm so cool\"";
    if(posterName.trim().equals("")) this.posterName = "Anonymous";
  }

  public Comment(String comment) {
    this.comment = comment;
    this.posterName = "Anonymous";
    this.imageURL = "";
  }

  public String getComment() {
    return comment;
  }

  public String getPosterName() {
    return posterName;
  }
}