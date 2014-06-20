package com.github.jfwilson.rxjson.core;

import org.junit.Test;

import static com.github.jfwilson.rxjson.core.Json.parseAsJavaObject;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ArrayReaderTest {

    @Test
    public void emptyArray() {
        assertThat(parseAsJavaObject("[]"), is(emptyList()));
        assertThat(parseAsJavaObject(" [ ] "), is(emptyList()));
    }

    @Test
    public void singletonArray() {
        assertThat(parseAsJavaObject("[true]"), is(singletonList(true)));
        assertThat(parseAsJavaObject(" [ \"Hello\" ] "), is(singletonList("Hello")));
    }

    @Test
    public void multipleArray() {
        assertThat(parseAsJavaObject("[true,false]"), is(asList(true, false)));
        assertThat(parseAsJavaObject(" [ \"A\" , \"B\" , \"C\" ] "), is(asList("A", "B", "C")));
    }

    @Test(expected = JsonFormatException.class)
    public void errorDueToMissingComma() {
        parseAsJavaObject("[true false]");
    }

    @Test(expected = JsonFormatException.class)
    public void errorDueToStartingComma() {
        parseAsJavaObject("[,true]");
    }

    @Test(expected = JsonFormatException.class)
    public void errorDueToEndingComma() {
        parseAsJavaObject("[true,]");
    }
}
