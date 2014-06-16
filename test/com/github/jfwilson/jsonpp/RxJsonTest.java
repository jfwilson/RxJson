package com.github.jfwilson.jsonpp;

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

public class RxJsonTest {

    @Test
    public void jsonAny_example() {
        Observable<String> jsonInput = Observable.from("[true, false, \"foo\"]");

        Observable<Object> jsonValue = jsonInput.lift(RxJson.jsonAnyFromCharSequences());

        assertThat(jsonValue.toBlocking().single(), is(asList(true, false, "foo")));
    }

    @Test
    public void jsonAny_emitsAsChunksAreReceived() {
        PublishSubject<String> chunks = PublishSubject.create();

        Observable<Object> json = chunks.lift(RxJson.jsonAnyFromCharSequences());

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
