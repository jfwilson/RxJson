package com.github.jfwilson.rxjson.rx;

import org.junit.Test;
import rx.Notification;
import rx.Observable;
import rx.subjects.PublishSubject;

import java.util.ArrayList;

import static com.github.jfwilson.rxjson.rx.RxJson.fromCharSequencesToJavaObject;
import static java.util.Collections.singletonMap;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class RxJsonTest {

    @Test
    public void fromCharSequencesToJavaObject_emitsObjectOnceReceived() {
        PublishSubject<String> chunks = PublishSubject.create();

        Observable<Object> json = chunks.lift(fromCharSequencesToJavaObject());

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
