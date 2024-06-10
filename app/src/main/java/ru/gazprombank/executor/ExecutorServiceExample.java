package ru.gazprombank.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static ru.gazprombank.util.CommonUtil.noOfCores;

public class ExecutorServiceExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(noOfCores());
        IntStream.range(0, 10).forEach(i ->
            executor.execute(
                () -> {
                    System.out.println("Task executed by " + Thread.currentThread().getName());
                }
            )
        );
        executor.shutdown();
    }
}
