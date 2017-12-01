package com.example.chatbot;

/**
 * Created by anuj on 3/9/17.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    public String text;
}
