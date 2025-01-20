package com.ishita.vertx.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PointToPointExample  extends AbstractVerticle {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new SenderVerticle());
    vertx.deployVerticle(new ReceiverVerticle());
  }

  static class SenderVerticle extends AbstractVerticle{
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      vertx.setPeriodic(1000, id -> {
        vertx.eventBus().<String>send(SenderVerticle.class.getName(), "Sending Message");
      });
      startPromise.complete();
    }
  }

  static class ReceiverVerticle extends AbstractVerticle{
    private static final Logger LOG = LogManager.getLogger();
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      vertx.eventBus().<String>consumer(SenderVerticle.class.getName(), message ->{
        LOG.debug("Message Received {}", message.body());
      });
      startPromise.complete();
    }
  }

}
