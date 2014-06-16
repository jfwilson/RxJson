JSON Push Parser
================

jsonpp is a non-blocking, push-based JSON parser written in Java.
It is designed to work nicely with rx.Observables and includes rx Operators for some common uses, e.g. reading JSON into an in-memory representation.
RxJava is not required to use the library, custom adapters can be written to support individual circumstances.

Example
-------

Read an entire JSON value from String chunks:

```
Observable<String> jsonInput = Observable.from("[true, false, \"foo\"]");

Observable<Object> jsonValue = jsonInput.lift(RxJson.jsonAnyFromCharSequences());

assertThat(jsonValue.toBlocking().single(), is(asList(true, false, "foo")));
```
