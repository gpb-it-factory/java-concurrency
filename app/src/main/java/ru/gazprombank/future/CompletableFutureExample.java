package ru.gazprombank.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {

    public static void main(String[] args) {
        // Создаем CompletableFuture для получения данных
        CompletableFuture<String> fetchData = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000); // Симуляция задержки
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Данные из удаленного сервиса";
        });

        // Создаем CompletableFuture для обработки данных
        CompletableFuture<String> processFetchedData = fetchData.thenApply(data -> {
            System.out.println("Обработка данных: " + data);
            return data.toUpperCase();
        });

        // Создаем CompletableFuture для обработки результата
        CompletableFuture<Void> result = processFetchedData.thenAccept(resultData -> {
            System.out.println("Обработанный результат: " + resultData);
        });

        // Если нужно дождаться завершения всех задач
        try {
            result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
