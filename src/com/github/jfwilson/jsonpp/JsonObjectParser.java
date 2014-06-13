package com.github.jfwilson.jsonpp;

import java.util.LinkedHashMap;
import java.util.function.Function;

import static com.github.jfwilson.jsonpp.JsonStringParser.readStringContent;

public class JsonObjectParser extends JsonParser {

    private final Function<? super LinkedHashMap<String, Object>, JsonParser> onFinished;
    private final LinkedHashMap<String, Object> fields = new LinkedHashMap<>();

    private JsonObjectParser(Function<? super LinkedHashMap<String, Object>, JsonParser> onFinished) {
        this.onFinished = onFinished;
    }

    @Override
    public JsonParser onNext(char c) {
        switch (c) {
            case COMMA:
                return readFieldName();
            case END_OBJECT:
                return onFinished.apply(fields);
            default:
                return super.onNext(c);
        }
    }

    private JsonParser readFieldName() {
        return new JsonParser() {
            @Override
            public JsonParser onNext(char c) {
                return DOUBLE_QUOTE == c ? readStringContent(JsonObjectParser.this::readFieldValue) : super.onNext(c);
            }
        };
    }

    private JsonParser readFieldValue(String fieldName) {
        return new JsonParser() {
            @Override
            public JsonParser onNext(char c) {
                return COLON == c ? new JsonAnyParser(value -> {fields.put(fieldName, value); return JsonObjectParser.this;}) : super.onNext(c);
            }
        };
    }

    public static JsonParser readObjectContent(Function<? super LinkedHashMap<String, Object>, JsonParser> onFinished) {
        return new JsonObjectParser(onFinished).readFieldName();
    }
}
