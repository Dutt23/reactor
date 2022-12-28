package com.sd.dutt.mono;

import com.github.javafaker.Faker;
import com.sd.dutt.subscribers.PrintingSubscriber;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class MonoExamples {

  public static void main(String[] args) {
//        publisher
//    Use just only when you aready have the data with you.
//    Mono<Integer> justMono = Mono.just("ball")
//        .map(String::length)
//        .map(l -> l / 1);

//    justMono.subscribe(i ->
//            System.out.println("Received data " + 1),
//        err -> System.out.println("Received error here" + err.getMessage()),
//        () -> System.out.println("On complete signal received")
//    );


//    justMono.subscribe(new PrintingSubscriber(10));

//    userRepo(1).subscribe(new PrintingSubscriber());
//    userRepo(2).subscribe(new PrintingSubscriber());
//    userRepo(10).subscribe(new PrintingSubscriber());


//    fromSuppplier waits for someone to subscribe on the the object only then does the evaluation.
//    Mono.just(nameGenerator());
//    Mono.fromSupplier(() -> nameGenerator());

//    namePublisher();
//    namePublisher().subscribeOn(Schedulers.boundedElastic())
//        .subscribe(new PrintingSubscriber());
//    namePublisher();
//
////    Blocks main thread from exiting.
//    String name = namePublisher().block();
//    System.out.println(name);

//    From Future
//    Mono.fromFuture(futureNamePublisher())
//        .subscribe(new PrintingSubscriber());

//    From Runnable
//    Doesn't return any value but , incase there is a time consuming process where you don't need any value back from
//    You can use this for notification purpose

    Runnable runnable = () -> {
      System.out.println("Started operation");
      try {
        Thread.sleep(3000L);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println("");
    };

    Mono.fromRunnable(runnable)
        .subscribe(new PrintingSubscriber());
  }

  private static Mono<String> userRepo(Integer id) {
    if (id.equals(1)) {
      return Mono.just(Faker.instance()
          .name()
          .firstName());
    } else if (id.equals(2)) {
      return Mono.empty();
    }
    return Mono.error(new RuntimeException("User not found here"));
  }

  private static String nameGenerator() {
    System.out.println("Started call to generate name");
    return Faker.instance()
        .name()
        .firstName();
  }

  private static Mono<String> namePublisher() {
    System.out.println("Entered method to publish");
    return Mono.fromSupplier(() -> {
          try {
            Thread.sleep(3000L);
            return Faker.instance()
                .name()
                .firstName();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        })
        .map(String::toUpperCase);
  }

  private static CompletableFuture<String> futureNamePublisher() {
    return CompletableFuture.supplyAsync(() -> Faker.instance()
        .name()
        .firstName());
  }
}

