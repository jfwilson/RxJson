package com.github.jfwilson.rxjson.core;

public class JsonFormatException extends RuntimeException {
    public JsonFormatException(String message) {
        super(message);
    }

    public static JsonFormatException unexpectedToken(char token) {
        return new JsonFormatException("Unexpected token '" + token + "'");
    }

    public static JsonFormatException unexpectedCompletion() {
        return new JsonFormatException("Unexpected end-of-file");
    }
}
