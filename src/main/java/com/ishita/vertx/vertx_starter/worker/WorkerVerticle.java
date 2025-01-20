package com.ishita.vertx.vertx_starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorkerVerticle extends AbstractVerticle {

  private static final Logger LOG = LogManager.getLogger();

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    LOG.debug("Deploy as worker verticle");
    startPromise.complete();
    Thread.sleep(5000);
    LOG.debug("Blocking operation done");
  }
}
