package com.ishita.vertx.vertx_starter.verticles;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {


	private static final Logger LOG = LogManager.getLogger();

  public static void main(String[] args) {
   final Vertx vertx =  Vertx.vertx();
   vertx.deployVerticle(new MainVerticle());

  }

  @Override
  public void start(final Promise<Void> startpromise) throws Exception{
    LOG.debug("Start {} ", getClass().getName());
    System.out.println("Start "+ getClass().getName());
    vertx.deployVerticle(new VerticleA());
    vertx.deployVerticle(new VerticleB());
    vertx.deployVerticle(VerticleN.class.getName(), new DeploymentOptions().setInstances(4)
      .setConfig(new JsonObject().put("id", UUID.randomUUID().toString()).put("name",
        VerticleN.class.getSimpleName())));
    startpromise.complete();
  }
}
