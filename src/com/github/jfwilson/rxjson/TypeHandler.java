package com.github.jfwilson.rxjson;

public interface TypeHandler {

    public ArrayHandler onArray();

    public ObjectHandler onObject();

    public void onString(String value);

    public void onNumber(String value);

    public void onBoolean(boolean value);

    public void onNull();

    public static interface ArrayHandler {

        public TypeHandler onItem();

        public void onEndArray();
    }

    public static interface ObjectHandler {

        public TypeHandler onField(String name);

        public void onEndObject();
    }
}
