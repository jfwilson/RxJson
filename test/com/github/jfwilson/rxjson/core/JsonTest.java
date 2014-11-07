package com.github.jfwilson.rxjson.core;

import org.junit.Test;

import java.math.BigDecimal;

import static com.github.jfwilson.rxjson.core.Json.parseAsJavaObject;
import static com.github.jfwilson.rxjson.core.Maps.map;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class JsonTest {

    @Test
    public void canParseJsonString() {
        assertThat(parseAsJavaObject("\"\""), is(""));
        assertThat(parseAsJavaObject("\"Hello World!\""), is("Hello World!"));
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
    public void canParseJsonNumbers() {
        assertThat(parseAsJavaObject("0"), is(BigDecimal.ZERO));
        assertThat(parseAsJavaObject("10"), is(BigDecimal.TEN));
        assertThat(parseAsJavaObject("[3.142,-1e2]"), is(asList(new BigDecimal("3.142"), new BigDecimal("-1e2"))));
        assertThat(parseAsJavaObject("{\"e\":2.71828182846}"), is(singletonMap("e", new BigDecimal("2.71828182846"))));
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
