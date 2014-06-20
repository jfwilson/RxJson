package com.github.jfwilson.rxjson.core;

import com.github.jfwilson.jsonpp.JsonParser;
import com.github.jfwilson.rxjson.TypeHandler;

import static com.github.jfwilson.jsonpp.JsonStringParser.readStringContent;

public class ObjectReader extends JsonParser {

    private final TypeHandler.ObjectHandler objectHandler;
    private final JsonParser outerScope;

    public ObjectReader(TypeHandler.ObjectHandler objectHandler, JsonParser outerScope) {
        this.objectHandler = objectHandler;
        this.outerScope = outerScope;
    }

    @Override
    public JsonParser onNext(char c) {
        switch (c) {
            case DOUBLE_QUOTE:
                return readStringContent(ObjectReader.this::readFieldValue);
            case END_OBJECT:
                return onEndObject();
            default:
                return super.onNext(c);
        }
    }

    private JsonParser onEndObject() {
        objectHandler.onEndObject();
        return outerScope;
    }

    private JsonParser readFieldName() {
        return new JsonParser() {
            @Override
            public JsonParser onNext(char c) {
                return DOUBLE_QUOTE == c ? readStringContent(ObjectReader.this::readFieldValue) : super.onNext(c);
            }
        };
    }

    private JsonParser readFieldValue(String fieldName) {
        return new JsonParser() {
            @Override
            public JsonParser onNext(char c) {
                return COLON == c ? new TypeReader(objectHandler.onField(fieldName), readDelimiter()) : super.onNext(c);
            }
        };
    }

    private JsonParser readDelimiter() {
        return new JsonParser() {
            @Override
            public JsonParser onNext(char c) {
                switch (c) {
                    case COMMA:
                        return readFieldName();
                    case END_OBJECT:
                        return onEndObject();
                    default:
                        return super.onNext(c);
                }
            }
        };
    }
}
