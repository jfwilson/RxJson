package com.github.jfwilson.rxjson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

public class JavaObjectTypeHandler implements TypeHandler {

    private final Consumer<Object> consumer;

    public JavaObjectTypeHandler(Consumer<Object> consumer) {
        this.consumer = consumer;
    }

    @Override
    public ArrayHandler onArray() {
        return new JavaObjectArrayHandler();
    }

    @Override
    public ObjectHandler onObject() {
        return new JavaObjectObjectHandler();
    }

    @Override
    public void onString(String value) {
        consumer.accept(value);
    }

    @Override
    public void onNumber(String value) {
        consumer.accept(new BigDecimal(value));
    }

    @Override
    public void onBoolean(boolean value) {
        consumer.accept(value);
    }

    @Override
    public void onNull() {
        consumer.accept(null);
    }

    public class JavaObjectArrayHandler implements ArrayHandler {

        private final ArrayList<Object> items = new ArrayList<>();

        @Override
        public TypeHandler onItem() {
            return new JavaObjectTypeHandler(items::add);
        }

        @Override
        public void onEndArray() {
            consumer.accept(items);
        }
    }

    public class JavaObjectObjectHandler implements ObjectHandler {

        private final LinkedHashMap<String, Object> fields = new LinkedHashMap<>();

        @Override
        public TypeHandler onField(String name) {
            return new JavaObjectTypeHandler(value -> fields.put(name, value));
        }

        @Override
        public void onEndObject() {
            consumer.accept(fields);
        }
    }
}
