package com.ishita.vertx.vertx_starter.codec;

import io.vertx.core.*;

public class PingPongExample  extends AbstractVerticle {

  public static void main(String[] args) {
     var vertx = Vertx.vertx();
     vertx.deployVerticle(new PingVerticle() , logError());

     vertx.deployVerticle(new PongVerticle(), logError());
  }

  private static Handler<AsyncResult<String>> logError() {
    return result -> {
      if (result.failed()) {
        System.out.println("Error " + result.cause());
      }
    };
  }

  public static class PingVerticle extends AbstractVerticle{
    static final String ADDRESS = PingVerticle.class.getName();
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      var eventBus = vertx.eventBus();

      Ping ping = new Ping("Hello Ping" ,true);
      eventBus.registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));
      eventBus.<Pong>request(ADDRESS, ping, req ->{
        if(req.failed()) {
          System.out.println("Ping Request Failed " + req.cause());
          return;
        }
        else{
          System.out.println("Response " + req.result().body());
        }
      });
      startPromise.complete();
    }
  }

  public static class PongVerticle extends AbstractVerticle{
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      var eventBus = vertx.eventBus();
      eventBus.registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
      eventBus.consumer(PingVerticle.ADDRESS, message ->{
        System.out.println("Message received "+ message.body());
      message.reply(new Pong(0));
      }).exceptionHandler(error -> {
        System.out.println("Error is " + error.getCause().toString());
      });
      startPromise.complete();
    }
  }
}
