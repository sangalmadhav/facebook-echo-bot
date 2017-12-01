package com.example.chatbot;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class FacebookBotVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    vertx.createHttpServer().requestHandler(req -> req.response().end("Hello World!"))
    .listen(
        Integer.getInteger("http.port"), System.getProperty("http.address", "0.0.0.0"));
  }
}

