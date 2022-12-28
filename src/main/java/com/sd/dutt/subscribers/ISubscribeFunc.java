package com.sd.dutt.subscribers;

import org.reactivestreams.Subscription;

@FunctionalInterface
public interface ISubscribeFunc<I> {
  public boolean shouldCancel(I input);
}
