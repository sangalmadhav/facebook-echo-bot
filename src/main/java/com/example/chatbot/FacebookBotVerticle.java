package com.example.chatbot;

import java.util.concurrent.Future;

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
    
     vertx
     .createHttpServer()
     .requestHandler(router::accept)
     .listen(
         // Retrieve the port from the configuration,
         // default to 8080.
         config().getInteger("http.port", 8080),
         result -> {}
     );
  }
  
  private void getAll(RoutingContext routingContext) {
  routingContext.response()
      .putHeader("content-type", "application/json; charset=utf-8")
      .end("Some sample data");
}
}

