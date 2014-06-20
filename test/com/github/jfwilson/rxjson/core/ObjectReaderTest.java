package com.github.jfwilson.rxjson.core;

import org.junit.Test;

import static com.github.jfwilson.rxjson.core.Json.parseAsJavaObject;
import static com.github.jfwilson.rxjson.core.Maps.map;
import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ObjectReaderTest {

    @Test
    public void emptyObject() {
        assertThat(parseAsJavaObject("{}"), is(emptyMap()));
        assertThat(parseAsJavaObject(" { } "), is(emptyMap()));
    }

    @Test
    public void singletonObject() {
        assertThat(parseAsJavaObject("{\"x\":true}"), is(singletonMap("x", true)));
        assertThat(parseAsJavaObject(" { \"Hello\" : \"World!\" } "), is(singletonMap("Hello", "World!")));
    }

    @Test
    public void multipleObject() {
        assertThat(parseAsJavaObject("{\"a\":true,\"b\":false}"), is(map("a", true, "b", false)));
        assertThat(parseAsJavaObject(" { \"x\":\"A\" , \"y\":\"B\" , \"z\":\"C\" } "), is(map("x", "A", "y", "B", "z", "C")));
    }

    @Test(expected = JsonFormatException.class)
    public void errorDueToMissingComma() {
        parseAsJavaObject("{\"a\":true \"b\":false}");
    }

    @Test(expected = JsonFormatException.class)
    public void errorDueToStartingComma() {
        parseAsJavaObject("{,\"a\":true}");
    }

    @Test(expected = JsonFormatException.class)
    public void errorDueToEndingComma() {
        parseAsJavaObject("{\"a\":true,}");
    }
}
