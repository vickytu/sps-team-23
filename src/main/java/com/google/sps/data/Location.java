package com.google.sps.data;

/** A Location object. */
public final class Location {

  private final long id;
  private final String name;
  private final String description;
  private final String img;
  private final String category;
  private final long timestamp;
  /* Initializing its id, name, descriptions, background colors, and time stamps. */
  public Location(long id, String name, String description, String category, String img, long timestamp) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.category = category;
    this.img = img;
    this.timestamp = timestamp;
  }

}