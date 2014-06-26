package com.github.jfwilson.rxjson.core;

import com.github.jfwilson.rxjson.JavaObjectTypeHandler;
import com.github.jfwilson.rxjson.TypeHandler;

import java.util.concurrent.atomic.AtomicReference;

public class Json {

    public static Object parseAsJavaObject(CharSequence jsonText) {
        AtomicReference<Object> result = new AtomicReference<>();
        parse(jsonText, new JavaObjectTypeHandler(result::set));
        return result.get();
    }
    
    public static void parse(CharSequence jsonText, TypeHandler typeHandler) {
        JsonParser parser = new TypeReader(typeHandler, new JsonEofParser());
        for (int i = 0; i < jsonText.length(); ++i) {
            parser = parser.onNext(jsonText.charAt(i));
        }
        parser.onCompleted();
    }
}
