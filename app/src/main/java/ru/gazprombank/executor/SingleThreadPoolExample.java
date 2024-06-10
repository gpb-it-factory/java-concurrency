package ru.gazprombank.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * SingleThreadPool создает единственный поток для выполнения задач. Все задачи выполняются последовательно в порядке
 * их поступления.
 * <p>
 * Сценарии использования:
 * Используется, когда необходимо обеспечить последовательное выполнение задач, например, для записи в файл или
 * обновления состояния
 * */

public class SingleThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        IntStream.range(0, 10).forEach(i -> {
                Runnable worker = new WorkerThread("Task " + i);
                executorService.execute(worker);
            }
        );

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks are finished");
    }
}

class WorkerThread implements Runnable {
    private final String message;

    public WorkerThread(String s) {
        this.message = s;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start: message = " + message);
        processMessage();
        System.out.println(Thread.currentThread().getName() + " End");
    }

    private void processMessage() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
