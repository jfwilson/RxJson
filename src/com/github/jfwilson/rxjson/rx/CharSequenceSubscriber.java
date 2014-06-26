package com.github.jfwilson.rxjson.rx;

import com.github.jfwilson.rxjson.core.JsonEofParser;
import com.github.jfwilson.rxjson.core.JsonParser;
import com.github.jfwilson.rxjson.TypeHandler;
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
