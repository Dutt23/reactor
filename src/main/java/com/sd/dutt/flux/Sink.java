package com.sd.dutt.flux;

import com.github.javafaker.Faker;
import com.sd.dutt.producer.NameProducer;
import com.sd.dutt.subscribers.PrintingSubscriber;
import reactor.core.publisher.Flux;

public class Sink {

  public static void main(String[] args) {
//    Flux.create(fluxSink -> {
//          for (int i = 0; i < 10; i++) {
//            fluxSink.next(Faker.instance()
//                .country()
//                .name());
//          }
//          System.out.println(fluxSink.contextView().size());
//
//          fluxSink.complete();
//        })
//        .subscribe(new PrintingSubscriber(10));

//    NameProducer producer = new NameProducer();
//    Flux.create(producer)
//        .take(3)
//        .subscribe(new PrintingSubscriber(10));
//
//
//    Runnable runnable = producer::produce;
//
//    for (int i = 0; i < 20; i++) {
//      new Thread(runnable).start();
//    }


//    https://www.baeldung.com/java-flux-create-generate

//    Comment out the second next.
//    Flux.generate(synchronousSink -> {
//          synchronousSink.next(Faker.instance()
//              .name()
//              .fullName());
////          synchronousSink.complete();
//        })
//        .take(5)
//        .subscribe(new PrintingSubscriber(10));

    Flux.generate(() -> 1, (counter, syncSink) -> {
          String country = Faker.instance()
              .country()
              .name();

          if (country.equalsIgnoreCase("canada") || counter >= 10) {
            syncSink.complete();
          }
          syncSink.next(country);
          return counter + 1;
        }, counter -> System.out.println("Clean up method " + counter))
        .subscribe(new PrintingSubscriber(10));

  }
}
