package com.github.jfwilson.jsonpp;

import java.util.function.Function;

import static com.github.jfwilson.jsonpp.JsonArrayParser.readArrayContent;
import static com.github.jfwilson.jsonpp.JsonConstantParser.readConstantContent;
import static com.github.jfwilson.jsonpp.JsonObjectParser.readObjectContent;
import static com.github.jfwilson.jsonpp.JsonStringParser.readStringContent;

public class JsonAnyParser extends JsonParser {

    private final Function<Object, JsonParser> onFinished;

    public JsonAnyParser(Function<Object, JsonParser> onFinished) {
        this.onFinished = onFinished;
    }

    @Override
    public JsonParser onNext(char c) {
        switch (c) {
            case DOUBLE_QUOTE:
                return readStringContent(onFinished);
            case START_ARRAY:
                return readArrayContent(onFinished);
            case START_OBJECT:
                return readObjectContent(onFinished);
            case 't':
                return readConstantContent("true", () -> onFinished.apply(true));
            case 'f':
                return readConstantContent("false", () -> onFinished.apply(false));
            case 'n':
                return readConstantContent("null", () -> onFinished.apply(null));
            default:
                return super.onNext(c);
        }
    }
}
