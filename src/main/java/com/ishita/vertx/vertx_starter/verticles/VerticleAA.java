package com.ishita.vertx.vertx_starter.verticles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleAA extends AbstractVerticle {
	private static final Logger LOG = LogManager.getLogger();

  @Override
  public void start(final Promise<Void> startpromise) throws Exception{
    LOG.debug("Start {}", getClass().getName());
    startpromise.complete();
  }

  @Override
  public void stop(final Promise<Void> stopPromise) throws Exception{
    LOG.debug("Stop {}", getClass().getName());
    stopPromise.complete();
  }
}
