# RxJson

RxJson is a non-blocking, event-driven JSON parser designed primarily for use with [RxJava](https://github.com/Netflix/RxJava) observables.
The goals of the library are:
- Lightweight, no other libraries required
- Can map JSON to built-in Java types (Map, List, String, Number, boolean, null)
- Custom handlers can easily be created for handling parts of a JSON document as they are read/received
- Easy integration with [RxJava](https://github.com/Netflix/RxJava) observables

RxJava is not required to use the parser, but obviously the RxJava support will not work without it.

## Classic Examples (not using RxJava)

Read JSON from a String as a Java object:

```
Object jsonValue = Json.parseAsJavaObject("[true, false, \"foo\"]");

assertThat(jsonValue, is(asList(true, false, "foo")));
```

Respond to elements of an array as they are read (e.g. print them to `System.out`):

```
Json.parse("[true, false, \"foo\"]", onArray(() -> onAny(System.out::println)));
```

## Rx Examples (using RxJava)

Read JSON from a String as a Java object:

```
Observable<String> jsonInput = Observable.from("[true, false, \"foo\"]");

Observable<Object> jsonValue = jsonInput.lift(fromCharSequencesToJavaObject());

assertThat(jsonValue.toBlocking().single(), is(asList(true, false, "foo")));
```

Respond to elements of an array as they are read (e.g. print them to `System.out`):

```
PublishSubject<String> jsonInput = PublishSubject.create();

jsonInput.subscribe(fromCharSequences(onArray(() -> onAny(System.out::println))));

jsonInput.onNext("[true,");                 // prints out 'true'
jsonInput.onNext(       "false, \"fo");     // prints out 'false'
jsonInput.onNext(                  "o\"]"); // prints out 'foo'
jsonInput.onCompleted();
```