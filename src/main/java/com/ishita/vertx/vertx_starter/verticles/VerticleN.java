package com.ishita.vertx.vertx_starter.verticles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleN extends AbstractVerticle {
	private static final Logger LOG = LogManager.getLogger();
  @Override
  public void start(final Promise<Void> startpromise) throws Exception{
    LOG.debug("Start {} on thread {} with config {} ",  getClass().getName(), Thread.currentThread().getName() ,config().toString());
    startpromise.complete();
  }
}
