package com.sd.dutt.flux;

import com.github.javafaker.Faker;
import com.sd.dutt.subscribers.PrintingSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FluxExamples {

  public static void main(String[] args) {
//    Flux<Integer> just = Flux.just(1, 2, 3, 4, 5);
//    just.subscribe(new PrintingSubscriber());

//    Flux<Integer> empty = Flux.empty();
//    empty.subscribe(new PrintingSubscriber());

//    just.filter(i -> i % 2 == 0)
//        .subscribe(new PrintingSubscriber());
//    just.subscribe(new PrintingSubscriber());


//   List<String> arrays = Arrays.asList("a", "b", "c");
//   Flux.fromIterable(arrays).subscribe(new PrintingSubscriber());

//    For loop
//    Flux.range(1, 10).subscribe(new PrintingSubscriber());

//    Flux.range(1, 10)
//        .log()
//        .map(name -> Faker.instance()
//            .name()
//            .firstName())
//        .log()
//        .subscribe(new PrintingSubscriber(3));


//    Flux vs List
//    System.out.println(getNamesList(5));
//    getNamesFlux(5).subscribe(new PrintingSubscriber(10));


//    Flux to Mono
//    Move the next operation below and above the filter operator to see the results
    Flux.range(1, 10)
        .next()
        .filter(i -> i > 3)
        .subscribe(new PrintingSubscriber(10));

  }

  private static Flux<String> getNamesFlux(int count) {
    return Flux.range(0, count)
        .map(i -> getName(i));
  }


  private static List<String> getNamesList(int count) {
    List<String> list = new ArrayList<String>();
    for (int i = 0; i < count; i++) {
      list.add(getName(i));
    }
    return list;
  }

  private static String getName(int i) {
    try {
      Thread.sleep(i % 2 == 0 ? 1000L : 5000L);
      return Faker.instance()
          .name()
          .firstName() + "_" + i;
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
