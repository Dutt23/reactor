package com.sd.dutt.mono;

import com.sd.dutt.subscribers.PrintingSubscriber;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileService {

  private static final Path BASE_PATH = Paths.get("src/main/resources/mono");

  public static void main(String[] args) {
    read("file1.txt").subscribe(new PrintingSubscriber());
    for (int i = 0; i < 1000; i++) {
      write("large_file.txt", "This is line! " + " " + i + "\n").subscribe(new PrintingSubscriber());
    }
    delete("file3.txt").subscribe(new PrintingSubscriber());

    read("file9.txt").subscribe(new PrintingSubscriber());
  }

  private static Mono<String> read(String fileName) {
    return Mono.fromSupplier(() -> readFile(fileName));
  }

  private static Mono<String> write(String fileName, String content) {
    return Mono.fromRunnable(() -> writeFile(fileName, content));
  }

  private static Mono<String> delete(String fileName) {
    return Mono.fromRunnable(() -> deleteFile(fileName));
  }

  private static String readFile(String fileName) {
    try {
      return Files.readString(BASE_PATH.resolve(fileName));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static void writeFile(String fileName, String content) {
    try {
      if (Files.exists(BASE_PATH.resolve(fileName))) {
        Files.write(
            BASE_PATH.resolve(fileName),
            content.getBytes(StandardCharsets.UTF_8),
            StandardOpenOption.APPEND);
      } else {
        Files.write(BASE_PATH.resolve(fileName), content.getBytes(StandardCharsets.UTF_8));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static void deleteFile(String fileName) {
    try {
      Files.deleteIfExists(BASE_PATH.resolve(fileName));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
