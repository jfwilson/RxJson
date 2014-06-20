package com.github.jfwilson.rxjson;

public class NoOpTypeHandler implements TypeHandler {

    @Override
    public ArrayHandler onArray() {
        return new NoOpArrayHandler();
    }

    @Override
    public ObjectHandler onObject() {
        return new NoOpObjectHandler();
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

    public static class NoOpArrayHandler implements ArrayHandler {
        @Override
        public TypeHandler onItem() {
            return new NoOpTypeHandler();
        }

        @Override
        public void onEndArray() {
        }
    }

    public static class NoOpObjectHandler implements ObjectHandler {
        @Override
        public TypeHandler onField(String name) {
            return new NoOpTypeHandler();
        }

        @Override
        public void onEndObject() {
        }
    }
}
