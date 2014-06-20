package com.github.jfwilson.rxjson;

import com.github.jfwilson.jsonpp.JsonEofParser;
import com.github.jfwilson.jsonpp.JsonParser;
import com.github.jfwilson.rxjson.core.TypeReader;
import rx.Subscriber;
import rx.exceptions.OnErrorNotImplementedException;

public class CharSequenceSubscriber extends Subscriber<CharSequence> {

    private JsonParser parser;

    public CharSequenceSubscriber(TypeHandler handler) {
        this.parser = new TypeReader(handler, new JsonEofParser());
    }

    @Override
    public void onNext(CharSequence s) {
        for (int i = 0; i < s.length(); ++i) {
            parser = parser.onNext(s.charAt(i));
        }
    }

    @Override
    public void onCompleted() {
        parser.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        throw new OnErrorNotImplementedException(e);
    }
}
