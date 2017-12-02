package com.example.chatbot;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.client.WebClientOptions;

public class FacebookBotVerticle extends AbstractVerticle {


    private String VERIFY_TOKEN;
    private String ACCESS_TOKEN;

    @Override
    public void start() throws Exception {

        updateProperties();

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
        if (!token.equals(VERIFY_TOKEN)) {
            challenge = "fake";
        }

        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(challenge);
    }

    private void message(RoutingContext routingContext) {

        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end("done");

        final Hook hook = Json.decodeValue(routingContext.getBodyAsString(), Hook.class);

        for (Hook.Item item : hook.entry) {
            for (Content i : item.messaging) {

                Response response = new Response();
                response.recipient = i.sender;
                response.message = i.message;

                if (response.message.text == null) {
                    response.message.text = "Thanks for your attachment";
                } else {
                    String data = response.message.text;

                    if (data.contains("joke")) {
                        Jokes jokes = new Jokes();
                        int random = (int) (Math.random() * jokes.jokes.size());
                        response.message.text = (new Jokes()).jokes.get(random);
                    } else {
                        response.message.text = "Please please ask me to tell a joke";
                    }
                }

                WebClientOptions options = new WebClientOptions();
                options.setSsl(true).setLogActivity(true);
                WebClient client = WebClient.create(vertx, options);

                System.out.println(Json.encode(response));

                client
                        .post(443, "graph.facebook.com", "/v2.6/me/messages/")
                        .addQueryParam("access_token", ACCESS_TOKEN)
                        .sendJsonObject(JsonObject.mapFrom(response), ar -> {
                            if (ar.succeeded()) {
                                // Obtain response
                                HttpResponse<Buffer> res = ar.result();

                                System.out.println("Received response with status code" + res.statusCode());
                            } else {
                                System.out.println("Something went wrong " + ar.cause().getMessage());
                            }
                        });

            }
        }
    }

    private void updateProperties() {
        
            VERIFY_TOKEN = System.getenv("facebook.verify.token");
            ACCESS_TOKEN = System.getenv("facebook.access.token");
        
    }


}

