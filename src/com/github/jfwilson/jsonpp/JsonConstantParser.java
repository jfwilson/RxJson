package com.github.jfwilson.jsonpp;

import java.util.function.Supplier;

public class JsonConstantParser extends JsonParser {
    private final Supplier<JsonParser> onFinished;
    private final String constant;
    private int index;

    public JsonConstantParser(String constant, int index, Supplier<JsonParser> onFinished) {
        this.onFinished = onFinished;
        this.constant = constant;
        this.index = index;
    }

    @Override
    public JsonParser onNext(char c) {
        if (constant.charAt(index) == c)
            return ++index < constant.length() ? this : onFinished.get();
        throw new IllegalArgumentException("Expected '" + constant.charAt(index) + "' of " + constant + " but got '" + c + "'");
    }

    public static JsonParser readConstantContent(String constant, Supplier<JsonParser> onFinished) {
        return new JsonConstantParser(constant, 1, onFinished);
    }
}
