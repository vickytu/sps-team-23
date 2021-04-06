package com.google.sps.data;

/** A Location object. */
public final class Location {

  public final long id;
  private final String name;
  private final String description;
  private final String category;
  private final String img;
  private final long timestamp;
  public long num_likes;
  /* Initializing its id, name, descriptions, categories, and time stamps. */
  public Location(long id, String name, String description, String category, long num_likes, String img, long timestamp) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.category = category;
    this.img = img;
    this.timestamp = timestamp;
    this.num_likes = num_likes;
  }

}