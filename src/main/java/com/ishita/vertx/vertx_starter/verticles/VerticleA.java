package com.ishita.vertx.vertx_starter.verticles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleA extends AbstractVerticle {
	private static final Logger LOG = LogManager.getLogger();

  @Override
  public void start(final Promise<Void> startpromise) throws Exception{
    LOG.debug("Start {} ", getClass().getName());
    vertx.deployVerticle(new VerticleAA(), whenDeployed -> {
      LOG.debug("Depolyed {}" , VerticleAA.class.getName());
      vertx.undeploy(whenDeployed.result());
    });
    vertx.deployVerticle(new VerticleAB(),whenDeployed -> {
      LOG.debug("Depolyed {}" ,VerticleAB.class.getName());;
    });
    startpromise.complete();
  }
}
