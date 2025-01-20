package com.ishita.vertx.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestResponseExample  extends AbstractVerticle {

  public static void main(String[] args) {
     var vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
     vertx.deployVerticle(new ResponseVerticle());
  }

  static class RequestVerticle extends  AbstractVerticle{
    private static final Logger LOG = LogManager.getLogger();
    static final String ADDRESS = "my.request.address";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      String message ="Hello world";
      var eventBus = vertx.eventBus();
      LOG.debug("Request message is {}", message);
      eventBus.<String>request(RequestVerticle.ADDRESS, message, response ->{
        LOG.debug("Response recieved is {} ", response.result());
      });
      startPromise.complete();
    }
  }

  static class ResponseVerticle extends  AbstractVerticle {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      vertx.eventBus().<String>consumer(RequestVerticle.ADDRESS, message ->{
        LOG.debug("Received Message : {} ", message.body());
        message.reply("Received your Message");
      });
      startPromise.complete();
    }
  }
}
