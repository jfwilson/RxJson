JSON Push Parser
================

jsonpp is a non-blocking, push-based JSON parser written in Java.  It is designed to work nicely with rx.Observables, supporting either reading the entire JSON structure into an in-memory representation or emitting results as they get read e.g. items of an array.

Example
-------

Build up an in-memory representation of the entire JSON:

```
Observable<Byte> jsonInput = Observable.from("[1, 2, 3]".getBytes);
Observable<JsonValue> parsedJson = jsonInput.lift(JsonParser.readFullTree());
```

Print out elements of an array as they are received:

```
Observable<Byte> jsonInput = Observable.from("[1, 2, 3]".getBytes);
jsonInput.subcribe(JsonParser.subscriber(new JsonArrayParser() {

  @Override
  public JsonParser onChildItem() {
    return super.onChildItem().doOnComplete(System.out::println);
  }
});
```
