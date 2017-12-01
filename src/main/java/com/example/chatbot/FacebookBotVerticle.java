package com.example.chatbot;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class FacebookBotVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
      
      
    Router router = Router.router(vertx);
    
    router.route().handler(BodyHandler.create());
    
    router.get("/webhook").handler(this::verify);
    
    router.post("/webhook").handler(this::message);
    
    vertx.createHttpServer().requestHandler(router::accept)
    .listen(
        Integer.getInteger("http.port"), System.getProperty("http.address", "0.0.0.0"));
  }
  
    private void verify(RoutingContext routingContext) {
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
    
    private void message(RoutingContext routingContext) {

        System.out.println(routingContext.getBodyAsString());
        final Hook hook = Json.decodeValue(routingContext.getBodyAsString(), Hook.class);

        for(Hook.Item item : hook.entry) {
            for (Content i : item.messaging) {


                Response response = new Response();
                response.recipient = i.sender;
                response.message = i.message;

                if (response.message.text == null) {
                    response.message.text = "Thanks for your attachment";
                } else {
                    String data = response.message.text;

                    if(data.contains("joke")){
                        Jokes jokes = new Jokes();
                        int random = (int) (Math.random() * jokes.jokes.size());
                        response.message.text = (new Jokes()).jokes.get(random);
                    } else {
                        response.message.text = "Please please ask me to tell a joke";
                    }
                }

            }
        }
        
        routingContext.response()
          .putHeader("content-type", "application/json; charset=utf-8")
          .end("done");
    }
    
    
}

