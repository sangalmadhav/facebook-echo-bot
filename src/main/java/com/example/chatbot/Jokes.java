package com.example.chatbot;

import java.util.ArrayList;

/**
 * Created by anuj on 3/9/17.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Jokes {

    public ArrayList<String> jokes;

    Jokes() {
        jokes = new ArrayList<>();

        jokes.add("I originally wrote this getQueryParameters function to return the address bar query string as a key/value object. Originally it was like a twenty-line function with conditionals and error checking and shit like that. I thought I could do better.");
        jokes.add("A computer once beat me at chess, but it was no match for me at kick boxing.");
        jokes.add("As long as there are tests, there will be prayer in schools. ");
        jokes.add("What did one ocean say to the other ocean? Nothing, they just waved.");
    }
}
