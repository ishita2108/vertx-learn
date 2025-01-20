package com.ishita.vertx.vertx_starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorkerExample extends AbstractVerticle {
  private static final Logger LOG = LogManager.getLogger();

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new WorkerExample());
  }

  @Override
  public void start(final Promise<Void> startPromise) throws Exception{

    vertx.deployVerticle(new WorkerVerticle(), new DeploymentOptions()
      .setWorker(true)
      .setWorkerPoolSize(1)
      .setWorkerPoolName("my-worker-verticle"));

    startPromise.complete();
    vertx.executeBlocking(event -> {
      LOG.debug("Execute Blocking thread");
      try {
        Thread.sleep(5000);
        event.complete();
      } catch (InterruptedException e) {
        LOG.error("Failed ", e);
        event.fail(e);
      }
    }, result -> {
      if(result.succeeded()){
        LOG.debug("Blocking call done.");
      }else{
        LOG.debug("Blocking call failed due to:", result.cause());
      }
    });
  }
}
