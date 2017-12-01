package com.example.chatbot;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class FacebookBotVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
      
    Router router = Router.router(vertx);
    
    router.get("/api/whiskies").handler(this::getAll);
    
    vertx.createHttpServer().requestHandler(router::accept)
    .listen(
        Integer.getInteger("http.port"), System.getProperty("http.address", "0.0.0.0"));
  }
  
  private void getAll(RoutingContext routingContext) {
  routingContext.response()
      .putHeader("content-type", "application/json; charset=utf-8")
      .end("Some sample ");
}
}

