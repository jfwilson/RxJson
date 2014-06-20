package com.github.jfwilson.rxjson.core;

import org.junit.Test;

import static com.github.jfwilson.rxjson.core.Json.parseAsJavaObject;
import static com.github.jfwilson.rxjson.core.Maps.map;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class JsonTest {

    @Test
    public void canParseJsonString() {
        String input = "\"Hello World!\"";
        assertThat(parseAsJavaObject(input), is("Hello World!"));
    }

    @Test
    public void canParseJsonList() {
        String input = "[\"a\",\"b\",\"c\"]";
        assertThat(parseAsJavaObject(input), is(asList("a", "b", "c")));
        assertThat(parseAsJavaObject("[]"), is(emptyList()));
    }

    @Test
    public void canParseJsonObject() {
        String input = "{\"a\":\"foo\",\"b\":[\"x\",\"y\"]}";
        assertThat(parseAsJavaObject(input), is(map(
                "a", "foo",
                "b", asList("x", "y")
        )));
        assertThat(parseAsJavaObject("{}"), is(emptyMap()));
    }

    @Test
    public void canParseJsonPrimitives() {
        assertThat(parseAsJavaObject("true"), is(true));
        assertThat(parseAsJavaObject("false"), is(false));
        assertThat(parseAsJavaObject("null"), is(nullValue()));
    }

    @Test
    public void whitespaceIsIgnored() {
        String input = " { \"a\" : \"foo\" , \"b\" : [ \"x\" , \"y\" ] } ".replace(" ", "\t \r\n");
        assertThat(parseAsJavaObject(input), is(map(
                "a", "foo",
                "b", asList("x", "y")
        )));
    }
}
