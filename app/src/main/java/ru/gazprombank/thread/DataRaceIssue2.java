package ru.gazprombank.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class DataRaceIssue2 {
    static int count = 0;
    static AtomicInteger countAtomic = new AtomicInteger(0);

    static void increment() { count = count + 1; }

    static void letsRace(){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0,1000).forEach(i -> executor.submit(DataRaceIssue2::increment));
        executor.shutdown();
        System.out.println(count);
    }

    static void noRace() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, 10000).forEach(i -> executor.submit(countAtomic::incrementAndGet));
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(countAtomic.get());
    }

    public static void main(String[] args)  {
        noRace();
        letsRace();
    }
}
