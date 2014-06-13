package com.github.jfwilson.jsonpp;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class JsonParserTest {

    @Test
    public void canParseJsonString() {
        String input = "\"Hello World!\"";
        assertThat(parseJson(input), is("Hello World!"));
    }

    @Test
    public void canParseJsonList() {
        String input = "[\"a\",\"b\",\"c\"]";
        assertThat(parseJson(input), is(asList("a", "b", "c")));
    }

    @Test
    public void canParseJsonObject() {
        String input = "{\"a\":\"foo\",\"b\":[\"x\",\"y\"]}";
        assertThat(parseJson(input), is(map(
                "a", "foo",
                "b", asList("x", "y")
        )));
    }

    @Test
    public void canParseJsonPrimitives() {
        assertThat(parseJson("true"), is(true));
        assertThat(parseJson("false"), is(false));
        assertThat(parseJson("null"), is(nullValue()));
    }

    @Test
    public void whitespaceIsIgnored() {
        String input = " { \"a\" : \"foo\" , \"b\" : [ \"x\" , \"y\" ] } ".replace(" ", "\t \r\n");
        assertThat(parseJson(input), is(map(
                "a", "foo",
                "b", asList("x", "y")
        )));
    }

    public static Object parseJson(String input) {
        final AtomicReference<Object> result = new AtomicReference<>();

        JsonParser parser = new JsonAnyParser(s -> {
            result.set(s);
            return new JsonEofParser();
        });

        for (char c : input.toCharArray()) {
            parser = parser.onNext(c);
        }
        return result.get();
    }

    private LinkedHashMap<String, Object> map(String k1, Object v1, String k2, Object v2) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>(2);
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }
}
