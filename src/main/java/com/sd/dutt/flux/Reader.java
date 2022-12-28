package com.sd.dutt.flux;

import com.sd.dutt.subscribers.PrintingSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Reader {

  private static Callable<BufferedReader> openReader(Path path) {
    return () -> Files.newBufferedReader(path);
  }

  private static BiFunction<BufferedReader, SynchronousSink<String>, BufferedReader> read() {
    return (br, sink) -> {
      try {
        String line = br.readLine();
        if (Objects.isNull(line)) {
          sink.complete();
        } else {
          sink.next(line);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return br;
    };
  }

  private static Consumer<BufferedReader> close() {
    return (br) -> {
      try {
        System.out.println("Closing file");
        br.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    };
  }

  public static void main(String[] args) {
    Path path = Paths.get("src/main/resources/flux/large_file.txt");
//    Using generate incase we add a take 20 or 10 here.
    Flux.generate(openReader(path), read(), close())
        .subscribe(new PrintingSubscriber());
  }
}
