package main.java.com.google.sps.data;

/** An item on a todo list. */
public final class Location {

  private final long id;
  private final String name;
  private final long timestamp;

  public Location(long id, String name, long timestamp) {
    this.id = id;
    this.name = name;
    this.timestamp = timestamp;
  }
}