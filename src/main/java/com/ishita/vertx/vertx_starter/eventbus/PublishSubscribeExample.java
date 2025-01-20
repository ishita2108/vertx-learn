package com.ishita.vertx.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PublishSubscribeExample extends AbstractVerticle {

  public static void main(String[] args) {

    var vertx = Vertx.vertx();
    vertx.deployVerticle(new Publisher());
    vertx.deployVerticle(new Subscriber1());
    vertx.deployVerticle(Subscriber2.class.getName(), new DeploymentOptions().setInstances(2));
  }

  public static class Publisher extends AbstractVerticle{

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      vertx.setPeriodic(10000, id ->{
        vertx.eventBus().<String>publish(Publisher.class.getName(),"A message for everyone!");
      });
      startPromise.complete();
    }
  }

  public static class Subscriber1 extends AbstractVerticle{
    private static final Logger LOG = LogManager.getLogger();
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      vertx.eventBus().<String>consumer(Publisher.class.getName(), message ->{
        LOG.debug("Message Received from Publisher to Subscriber1 {}", message.body());
      });
      startPromise.complete();
    }
  }

  public static class Subscriber2 extends AbstractVerticle{
    private static final Logger LOG = LogManager.getLogger();
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      vertx.eventBus().<String>consumer(Publisher.class.getName(), message ->{
        LOG.debug("Message Received from Publisher to Subscriber2 {}", message.body());
      });
      startPromise.complete();
    }
  }
}
