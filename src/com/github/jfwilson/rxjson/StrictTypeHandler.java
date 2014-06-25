package com.github.jfwilson.rxjson;

public class StrictTypeHandler implements TypeHandler {

    @Override
    public ArrayHandler onArray() {
        throw new JsonStructureException("JSON array was not expected here");
    }

    @Override
    public ObjectHandler onObject() {
        throw new JsonStructureException("JSON object was not expected here");
    }

    @Override
    public void onString(String value) {
        throw new JsonStructureException("JSON string was not expected here");
    }

    @Override
    public void onNumber(String value) {
        throw new JsonStructureException("JSON number was not expected here");
    }

    @Override
    public void onBoolean(boolean value) {
        throw new JsonStructureException("JSON boolean was not expected here");
    }

    @Override
    public void onNull() {
        throw new JsonStructureException("JSON null was not expected here");
    }

    public static class JsonStructureException extends RuntimeException {

        public JsonStructureException(String message) {
            super(message);
        }
    }
}
