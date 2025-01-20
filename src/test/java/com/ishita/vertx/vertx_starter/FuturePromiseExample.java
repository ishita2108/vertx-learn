package com.ishita.vertx.vertx_starter;


import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class FuturePromiseExample {

  @Test
  void promise_success(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    System.out.println("Start");
    vertx.setTimer(500, id ->{
      promise.complete("success");
      System.out.println("Success");
      context.completeNow();
    });
    System.out.println("End");
  }


  @Test
  void promise_fail(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    System.out.println("Start");
    vertx.setTimer(500, id ->{
      promise.fail(new RuntimeException("Failed"));
      System.out.println("Failed");
      context.completeNow();
    });
    System.out.println("End");
  }

  @Test
  void future_success(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    System.out.println("Start");
    vertx.setTimer(500, id ->{
      promise.complete("success");
      System.out.println("Success");
      //context.completeNow();
    });
    final Future<String> future =  promise.future();
    future.onSuccess(result -> {
      System.out.println("Future Success Message");
      context.completeNow();
    }).onFailure(context::failNow);
    System.out.println("End");
  }


  @Test
  void future_failure(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    System.out.println("Start");
    vertx.setTimer(500, id ->{
      promise.fail(new RuntimeException("Failed"));
      System.out.println("Failed");
      //context.completeNow();
    });
    final Future<String> future =  promise.future();
    future.onSuccess(result -> {
      System.out.println("Future Success Message");
      context.completeNow();
    }).onFailure(error ->{
      System.out.println("Result " + error.toString());
      context.completeNow();
    });
    System.out.println("End");
  }

  @Test
  void future_map(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    System.out.println("Start");
    vertx.setTimer(500, id ->{
      promise.complete("Success");
      System.out.println("Success");
      //context.completeNow();
    });
    final Future<String> future =  promise.future();
    future.map(asString ->{
        System.out.println("map String to json object");
        return new JsonObject().put("key", asString);
      })
      .map(jsonObject -> new JsonArray().add(jsonObject))
      .onSuccess(result -> {
      System.out.println("Future Success Message " + result);
      context.completeNow();
    }).onFailure(error ->{
      System.out.println("Result " + error.toString());
      context.completeNow();
    });
    System.out.println("End");
  }

  @Test
  void future_coordinate(Vertx vertx, VertxTestContext context){
    vertx.createHttpServer().requestHandler(req ->{
      System.out.println("RESult "+ req);
    }).listen(10_000)
      .compose(server ->{
      System.out.println("First task");
      return Future.succeededFuture(server);
    }).compose(server ->{
        System.out.println("Second task");
        return Future.succeededFuture(server);
      })
      .onFailure(context::failNow)
      .onSuccess(server ->{
        System.out.println("Server started on port " + server.actualPort());
        context.completeNow();
      });
  }
  @Test
  void future_composition(Vertx vertx, VertxTestContext context) {
    var one = Promise.<Void>promise();
    var two = Promise.<Void>promise();
    var three = Promise.<Void>promise();

    var futureOne = one.future();
    var futureTwo = two.future();
    var futureThree = three.future();

    CompositeFuture.all(futureOne, futureTwo, futureThree)
      .onFailure(context::failNow)
      .onSuccess(result -> {
        System.out.println("Success");
        context.completeNow();
      });

    // Complete futures
    vertx.setTimer(500, id -> {
      one.complete();
      two.complete();
      three.complete();
    });
  }
}
