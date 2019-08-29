package com.linxi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface Controller {
    default void doGet(Request request, Response response) throws IOException{
        response.status = "405 Method Not Allowed";
        response.println("Method Error");
    }
    default void doPost(Request request, Response response) throws IOException {
        response.status = "405 Method Not Allowed";
        response.println("Method Error");
    }
}
