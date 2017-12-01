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
    
    router.get("/webhook").handler(this::getAll);
    
    vertx.createHttpServer().requestHandler(router::accept)
    .listen(
        Integer.getInteger("http.port"), System.getProperty("http.address", "0.0.0.0"));
  }
  
  private void getAll(RoutingContext routingContext) {
    String challenge = routingContext.request().getParam("hub.challenge");
    String token = routingContext.request().getParam("hub.verify_token");
	String t = "76783489768943768946967847638";
	if(!t.equals(token)){
		challenge = "fake";
	}
    routingContext.response()
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(challenge);
    }
}

