package com.ishita.vertx.vertx_starter.codec;

public class Pong {

  private Integer id;

  public Pong() {
    // Default Constructor
  }

  public Pong(final Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Pong{" +
      "id=" + id +
      '}';
  }
}
