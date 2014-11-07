package com.github.jfwilson.rxjson.core;

import com.github.jfwilson.rxjson.StrictTypeHandler;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class JsonNumberParserTest {

    @Test
    public void zeroes() {
        verifyParseNumber("0");
        verifyParseNumber("-0");
        verifyParseNumber("0e12");
        verifyParseNumber("0.0E+3");
        verifyParseNumber("-0.000e-3");
    }

    @Test
    public void integers() {
        verifyParseNumber("1");
        verifyParseNumber("-2");
        verifyParseNumber("345");
        verifyParseNumber("-67890123456789012345667890123456789");
    }

    @Test
    public void decimals() {
        verifyParseNumber("0.1");
        verifyParseNumber("-3.456");
        verifyParseNumber("123456789.987654321");
    }

    @Test
    public void exponents() {
        verifyParseNumber("1e2");
        verifyParseNumber("-3.142E+0");
        verifyParseNumber("123456789.987654321e-123456789");
    }

    @Test
    public void incorrectFormats() {
        failsToParse("00");
        failsToParse("01");
        failsToParse("+7");
        failsToParse(".25");
        failsToParse("-3.");
        failsToParse("1E");
        failsToParse("1.2e-3.4");
    }

    private void verifyParseNumber(String json) {
        assertThat(parseNumberAsString(json), is(asList(json)));
        assertThat(parseNumberAsBigDecimal(json), is(new BigDecimal(json)));
    }

    private void failsToParse(String json) {
        try {
            fail("Expected an exception to be thrown but got " + parseNumberAsString(json));
        } catch (JsonFormatException ignored) {
        }
        try {
            fail("Expected an exception to be thrown but got " + parseNumberAsBigDecimal(json));
        } catch (JsonFormatException ignored) {
        }
    }

    private static List<String> parseNumberAsString(String json) {
        List<String> results = new ArrayList<>();
        Json.parse(json, new StrictTypeHandler() {
            @Override
            public void onNumber(String value) {
                results.add(value);
            }
        });
        return results;
    }

    private static Object parseNumberAsBigDecimal(String json) {
        return Json.parseAsJavaObject(json);
    }
}