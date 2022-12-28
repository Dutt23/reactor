package com.sd.dutt.producer;

import com.github.javafaker.Faker;
import com.sd.dutt.flux.Sink;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class NameProducer implements Consumer<FluxSink<String>> {

  FluxSink<String> sink;

  @Override
  public void accept(FluxSink<String> stringFluxSink) {
    this.sink = stringFluxSink;
  }

  public void produce() {
    if (!this.sink.isCancelled()) {
      this.sink.next(Faker.instance()
          .name()
          .fullName());
    }
  }

  @Override
  public Consumer<FluxSink<String>> andThen(Consumer<? super FluxSink<String>> after) {
    System.out.println("After then being called here.");
    return Consumer.super.andThen(after);
  }
}
