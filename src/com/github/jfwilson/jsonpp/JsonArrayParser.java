package com.github.jfwilson.jsonpp;

import java.util.ArrayList;
import java.util.function.Function;

public class JsonArrayParser extends JsonParser {

    private final Function<? super ArrayList<Object>, JsonParser> onFinished;
    private final ArrayList<Object> items = new ArrayList<>();

    private JsonArrayParser(Function<? super ArrayList<Object>, JsonParser> onFinished) {
        this.onFinished = onFinished;
    }

    @Override
    public JsonParser onNext(char c) {
        switch (c) {
            case COMMA:
                return readNextItem();
            case END_ARRAY:
                return onFinished.apply(items);
            default:
                return super.onNext(c);
        }
    }

    private JsonParser readNextItem() {
        return new JsonAnyParser(item -> {
            items.add(item);
            return this;
        });
    }

    public static JsonParser readArrayContent(Function<? super ArrayList<Object>, JsonParser> onFinished) {
        return new JsonArrayParser(onFinished).readNextItem();
    }
}
