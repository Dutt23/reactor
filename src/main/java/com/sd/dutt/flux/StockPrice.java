package com.sd.dutt.flux;

import com.github.javafaker.Faker;
import com.sd.dutt.subscribers.ISubscribeFunc;
import com.sd.dutt.subscribers.PrintingSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class StockPrice {

  public static void main(String[] args) throws InterruptedException {
    ISubscribeFunc<Integer> priceChecker = price -> price > 110 || price < 90;
    CountDownLatch latch = new CountDownLatch(1);
    getPrice().subscribe(new PrintingSubscriber(1, priceChecker, latch));
    latch.await();
  }

  public static Flux<Integer> getPrice() {
    AtomicInteger initialVal = new AtomicInteger(100);
    return Flux.interval(Duration.ofSeconds(1))
        .map(i -> initialVal.getAndAccumulate(Faker.instance()
            .number()
            .numberBetween(-5, 5), Integer::sum));
  }
}
