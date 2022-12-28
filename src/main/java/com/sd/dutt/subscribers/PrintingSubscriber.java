package com.sd.dutt.subscribers;

import com.sd.dutt.subscribers.ISubscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.CountDownLatch;

public class PrintingSubscriber implements ISubscriber<Object> {
  private int limit = 1;
  private CountDownLatch latch;

  private ISubscribeFunc func;

  public PrintingSubscriber() {
  }

  public PrintingSubscriber(int limit) {
    this.limit = limit;
    this.latch = new CountDownLatch(1);
    this.func = null;
  }

  public PrintingSubscriber(int limit, ISubscribeFunc<Integer> func, CountDownLatch latch) {
    this.limit = limit;
    this.latch = latch;
    this.func = func;
  }

  private Subscription subscription;

  @Override
  public void onSubscribe(Subscription subscription) {
    this.subscription = subscription;
//    Tells the publisher how many items to publish here.
    subscription.request(this.limit);
  }

  @Override
  public void onNext(Object o) {
    String threadName = Thread.currentThread()
        .getName();
    System.out.println("Received data on " + threadName + " : " + o);
    if (func != null) {
      boolean cancelled = func.shouldCancel(o);
      if (cancelled) {
        this.subscription.cancel();
//        this.latch.countDown();
      }
    }
    subscription.request(this.limit);
  }

  @Override
  public void onError(Throwable throwable) {
    System.out.println("Received error here" + throwable.getMessage());
//    latch.countDown();
  }

  @Override
  public void onComplete() {
    System.out.println("On complete signal received\n ====");
//    latch.countDown();
  }
}
