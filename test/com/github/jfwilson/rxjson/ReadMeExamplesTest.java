package com.github.jfwilson.rxjson;

import com.github.jfwilson.rxjson.core.Json;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.subjects.PublishSubject;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static com.github.jfwilson.rxjson.TypeHandlers.onAny;
import static com.github.jfwilson.rxjson.TypeHandlers.onArray;
import static com.github.jfwilson.rxjson.rx.RxJson.fromCharSequences;
import static com.github.jfwilson.rxjson.rx.RxJson.fromCharSequencesToJavaObject;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ReadMeExamplesTest {

    @Test
    public void example1a_parseAsJavaObject() {
        Object jsonValue = Json.parseAsJavaObject("[true, false, \"foo\"]");

        assertThat(jsonValue, is(asList(true, false, "foo")));
    }

    @Test
    public void example1b_printOutArrayElements() {
        Json.parse("[true, false, \"foo\"]", onArray(() -> onAny(System.out::println)));

        assertThat(outputtedLines(), is(asList("true", "false", "foo")));
    }

    @Test
    public void example2a_parseAsJavaObject() {
        Observable<String> jsonInput = Observable.from("[true, false, \"foo\"]");

        Observable<Object> jsonValue = jsonInput.lift(fromCharSequencesToJavaObject());

        assertThat(jsonValue.toBlocking().single(), is(asList(true, false, "foo")));
    }

    @Test
    public void example2b_printOutArrayElements() {
        PublishSubject<String> jsonInput = PublishSubject.create();

        jsonInput.subscribe(fromCharSequences(onArray(() -> onAny(System.out::println))));

        jsonInput.onNext("[true,");                 // prints out 'true'
        assertThat(outputtedLines(), is(asList("true")));
        jsonInput.onNext("false, \"fo");     // prints out 'false'
        assertThat(outputtedLines(), is(asList("true", "false")));
        jsonInput.onNext(                  "o\"]"); // prints out 'foo'
        assertThat(outputtedLines(), is(asList("true", "false", "foo")));
        jsonInput.onCompleted();
    }

    private PrintStream realSysOut;
    private ByteArrayOutputStream overriddenSysOut;

    @Before
    public void before() {
        realSysOut = System.out;
        System.setOut(new PrintStream(overriddenSysOut = new ByteArrayOutputStream()));
    }

    @After
    public void after() {
        System.setOut(realSysOut);
    }

    private List<String> outputtedLines() {
        return asList(overriddenSysOut.toString().split(System.lineSeparator()));
    }
}
