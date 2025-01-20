package com.ishita.vertx.vertx_starter.eventloops;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class EventLoopExample  extends AbstractVerticle {

  private static final Logger LOG = LogManager.getLogger();

  public static void main(String[] args) {

    var vertx = Vertx.vertx(
      new VertxOptions().setMaxEventLoopExecuteTime(500)
      .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
        .setBlockedThreadCheckInterval(1)
      .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
        .setEventLoopPoolSize(4));
    //vertx.deployVerticle(new EventLoopExample());\
    vertx.deployVerticle(EventLoopExample.class.getName(),
      new DeploymentOptions().setInstances(4));
  }
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("Start " + getClass().getName());
    LOG.debug("Start {}", getClass().getName());
    startPromise.complete();
    Thread.sleep(5000);
  }
}
