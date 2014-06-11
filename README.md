JSON Push Parser
================

jsonpp is a non-blocking, push-based JSON parser written in Java.  It is designed to work nicely with rx.Observables, supporting either reading the entire JSON structure into an in-memory representation or emitting results as they get read e.g. items of an array.

Example
-------

`rx.Observable<Byte> bytes = Observable.from("[1, 2, 3]".getBytes);
rx.Observable<JsonValue> fullJson = bytes.lift(JsonParser.readFullTree());
`
