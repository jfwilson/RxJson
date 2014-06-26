package com.github.jfwilson.rxjson.core;

public abstract class JsonParser {

    public static final char DOUBLE_QUOTE = '"';
    public static final char BACKSLASH = '\\';
    public static final char START_ARRAY = '[';
    public static final char END_ARRAY = ']';
    public static final char START_OBJECT = '{';
    public static final char END_OBJECT = '}';
    public static final char COMMA = ',';
    public static final char COLON = ':';
    public static final String WHITESPACE = " \t\r\n";

    public JsonParser onNext(char c) {
        if (WHITESPACE.indexOf(c) < 0)
            throw JsonFormatException.unexpectedToken(c);
        return this;
    }

    public void onCompleted() {
        throw JsonFormatException.unexpectedCompletion();
    }
}
