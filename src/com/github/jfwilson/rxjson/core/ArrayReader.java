package com.github.jfwilson.rxjson.core;

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
        if (WHITESPACE.indexOf(c) >= 0)
            return this;
        if (END_ARRAY == c) {
            return onEndArray();
        }
        return new DelimiterReader().onItem().onNext(c);
    }

    public JsonParser onEndArray() {
        arrayHandler.onEndArray();
        return outerScope;
    }

    public class DelimiterReader extends JsonParser {
        @Override
        public JsonParser onNext(char c) {
            switch (c) {
                case COMMA:
                    return onItem();
                case END_ARRAY:
                    return onEndArray();
                default:
                    return super.onNext(c);
            }
        }

        public JsonParser onItem() {
            return new TypeReader(arrayHandler.onItem(), this);
        }
    }
}
