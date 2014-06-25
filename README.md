RxJson
======

RxJson is a non-blocking, event-driven JSON parser designed primarily for use with RxJava observables.
The library includes rx Subscribers and Operators for common uses, e.g. reading JSON into an in-memory representation.
RxJava is not required to use the library, custom event handlers can easily be written to support other use cases.

Examples
--------

Read JSON from a String as a Java object:

```
Observable<String> jsonInput = Observable.from("[true, false, \"foo\"]");

Observable<Object> jsonValue = jsonInput.lift(fromCharSequencesToJavaObject());

assertThat(jsonValue.toBlocking().single(), is(asList(true, false, "foo")));
```

Respond to elements of an array as they are received:

```
PublishSubject<String> jsonInput = PublishSubject.create();

jsonInput.subscribe(fromCharSequences(onArray(() -> onAny(System.out::println))));

jsonInput.onNext("[true,");                 // prints out 'true'
jsonInput.onNext(       "false, \"fo");     // prints out 'false'
jsonInput.onNext(                  "o\"]"); // prints out 'foo'
jsonInput.onCompleted();
```
