package com.github.jfwilson.rxjson.core;

import java.util.function.Function;

public class JsonStringParser extends JsonParser {

    private final Function<? super String, JsonParser> onFinished;
    private final StringBuilder content = new StringBuilder();
    private boolean isEscapeSequence = false;

    private JsonStringParser(Function<? super String, JsonParser> onFinished) {
        this.onFinished = onFinished;
    }

    @Override
    public JsonParser onNext(char c) {
        if (isEscapeSequence) {
            content.append(c);
            isEscapeSequence = false;
            return this;
        }
        switch (c) {
            case DOUBLE_QUOTE:
                return onFinished.apply(content.toString());
            case BACKSLASH:
                isEscapeSequence = true;
                return this;
            default:
                content.append(c);
                return this;
        }
    }

    public static JsonParser readStringContent(Function<? super String, JsonParser> onFinished) {
        return new JsonStringParser(onFinished);
    }
}
