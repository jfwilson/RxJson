package com.github.jfwilson.rxjson;

public class EmptyTypeHandler implements TypeHandler {

    @Override
    public ArrayHandler onArray() {
        return () -> this;
    }

    @Override
    public ObjectHandler onObject() {
        return field -> this;
    }

    @Override
    public void onString(String value) {
    }

    @Override
    public void onNumber(String value) {
    }

    @Override
    public void onBoolean(boolean value) {
    }

    @Override
    public void onNull() {
    }
}
