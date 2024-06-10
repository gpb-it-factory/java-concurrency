package ru.gazprombank.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample2 {

    public static CompletableFuture<String> fetchData() {
        return CompletableFuture.supplyAsync(() -> {
            // получение данных
            System.out.println("Fetching data...");
            sleep(1000); // Заглушка для времени выполнения операции
            return "data";
        });
    }

    public static CompletableFuture<String> transformData(String data) {
        return CompletableFuture.supplyAsync(() -> {
            // преобразование данных
            System.out.println("Transforming data...");
            sleep(1000);
            return "transformed " + data;
        });
    }

    public static CompletableFuture<Void> saveData(String transformedData) {
        return CompletableFuture.runAsync(() -> {
            // сохранение данных
            System.out.println("Saving data...");
            sleep(1000);
            System.out.println("Data saved: " + transformedData);
        });
    }

    public static CompletableFuture<Void> sendNotification(String message) {
        return CompletableFuture.runAsync(() -> {
            // отправка уведомления
            System.out.println("Sending notification...");
            sleep(1000);
            System.out.println("Notification sent: " + message);
        });
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        try {
            CompletableFuture<Void> pipeline = fetchData()
                    .thenCompose(data -> transformData(data))
                    .thenCompose(transformedData -> {
                        return saveData(transformedData)
                                .thenCompose(v -> sendNotification("Data processing completed"));
                    });

            // Ожидание завершения всех этапов pipeline
            pipeline.get();

            System.out.println("Pipeline completed successfully");
        } catch (InterruptedException | ExecutionException e) {
        //} catch (Exception e) {
            e.printStackTrace();
        }
    }
}
