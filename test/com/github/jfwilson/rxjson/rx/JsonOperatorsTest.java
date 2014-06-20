package com.github.jfwilson.rxjson.rx;

import com.github.jfwilson.rxjson.JavaObjectTypeHandler;
import com.github.jfwilson.rxjson.NoOpTypeHandler;
import com.github.jfwilson.rxjson.TypeHandler;
import org.junit.Test;
import rx.Notification;
import rx.Observable;
import rx.subjects.PublishSubject;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class JsonOperatorsTest {

    @Test
    public void jsonAny_example() {
        Observable<String> jsonInput = Observable.from("[true, false, \"foo\"]");

        Observable<Object> jsonValue = jsonInput.lift(JsonOperators.fromCharSequencesToJavaObject());

        assertThat(jsonValue.toBlocking().single(), is(asList(true, false, "foo")));
    }

    @Test
    public void jsonPrintOut_example() {
        PublishSubject<String> jsonInput = PublishSubject.create();

        jsonInput.subscribe(new CharSequenceSubscriber(new NoOpTypeHandler() {
            @Override
            public ArrayHandler onArray() {
                return new NoOpArrayHandler() {
                    @Override
                    public TypeHandler onItem() {
                        return new JavaObjectTypeHandler(System.out::println);
                    }
                };
            }
        }));

        jsonInput.onNext("[true,");                 // prints out 'true'
        jsonInput.onNext(       "false, \"fo");     // prints out 'false'
        jsonInput.onNext(                  "o\"]"); // prints out 'foo'
        jsonInput.onCompleted();
    }

    @Test
    public void jsonAny_emitsAsChunksAreReceived() {
        PublishSubject<String> chunks = PublishSubject.create();

        Observable<Object> json = chunks.lift(JsonOperators.fromCharSequencesToJavaObject());

        ArrayList<Notification<Object>> notifications = new ArrayList<>();
        json.materialize().subscribe(notifications::add);

        assertThat(notifications, hasSize(0));

        chunks.onNext("{\"foo\"");
        assertThat(notifications, hasSize(0));

        chunks.onNext(":\"baa\"}");
        assertThat(notifications, hasSize(1));
        assertThat(notifications.get(0).getValue(), is(singletonMap("foo", "baa")));

        chunks.onNext("\r\n");
        assertThat(notifications, hasSize(1));

        chunks.onCompleted();
        assertThat(notifications, hasSize(2));
        assertTrue(notifications.get(1).isOnCompleted());
    }
}
