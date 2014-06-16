package com.github.jfwilson.jsonpp;

import rx.Observable;
import rx.Subscriber;

public class RxJson {
    public static Observable.Operator<Object, CharSequence> jsonAnyFromCharSequences() {
        return StringSubscriber::new;
    }

    public static class StringSubscriber extends Subscriber<CharSequence> {

        private final Subscriber<? super Object> downstream;
        private JsonParser parser;

        public StringSubscriber(Subscriber<? super Object> downstream) {
            this.downstream = downstream;
            parser = new JsonAnyParser(result -> {
                downstream.onNext(result);
                return new JsonEofParser() {
                    @Override
                    public void onComplete() {
                        downstream.onCompleted();
                    }
                };
            });
        }

        @Override
        public void onNext(CharSequence s) {
            for (int i = 0; i < s.length(); ++i) {
                parser = parser.onNext(s.charAt(i));
            }
        }

        @Override
        public void onCompleted() {
            parser.onComplete();
        }

        @Override
        public void onError(Throwable e) {
            downstream.onError(e);
        }
    }
}
