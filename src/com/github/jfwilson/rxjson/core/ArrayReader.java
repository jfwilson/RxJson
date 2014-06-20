package com.github.jfwilson.rxjson.core;

import com.github.jfwilson.jsonpp.JsonParser;
import com.github.jfwilson.rxjson.TypeHandler;

public class ArrayReader extends JsonParser {

    private final TypeHandler.ArrayHandler arrayHandler;
    private final JsonParser outerScope;

    public ArrayReader(TypeHandler.ArrayHandler arrayHandler, JsonParser outerScope) {
        this.arrayHandler = arrayHandler;
        this.outerScope = outerScope;
    }

    @Override
    public JsonParser onNext(char c) {
        switch (c) {
            case COMMA:
                return onStartItem();
            case END_ARRAY:
                arrayHandler.onEndArray();
                return outerScope;
            default:
                return super.onNext(c);
        }
    }

    public JsonParser onStartItem() {
        return new TypeReader(arrayHandler.onItem(), this);
    }
}
