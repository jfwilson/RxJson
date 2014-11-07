package com.github.jfwilson.rxjson.core;

import java.util.function.Function;
import java.util.regex.Pattern;

public class JsonNumberParser extends JsonParser {

    private static final String NUMERIC = "0123456789.-+eE";
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("-?(0|[1-9][0-9]*)(\\.[0-9]+)?([eE][-+]?[0-9]+)?");

    private final Function<? super String, JsonParser> onFinished;
    private final StringBuilder content = new StringBuilder();

    private JsonNumberParser(Function<? super String, JsonParser> onFinished) {
        this.onFinished = onFinished;
    }

    @Override
    public JsonParser onNext(char c) {
        if (NUMERIC.indexOf(c) >= 0) {
            content.append(c);
            return this;
        } else {
            return endNumber().onNext(c);
        }
    }

    @Override
    public void onCompleted() {
        endNumber().onCompleted();
    }

    private JsonParser endNumber() {
        if (!NUMERIC_PATTERN.matcher(content).matches())
            throw new JsonFormatException("Illegal number '" + content + "'");
        return onFinished.apply(content.toString());
    }

    public static JsonParser readNumericContent(Function<? super String, JsonParser> onFinished) {
        return new JsonNumberParser(onFinished);
    }
}
