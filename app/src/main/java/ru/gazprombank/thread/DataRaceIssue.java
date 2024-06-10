package ru.gazprombank.thread;

/**
 * В этом примере используется общий изменяемый счетчик counter, который инкрементируется в двух различных потоках.
 * Метод incrementCounter не синхронизирован, что приводит к тому, что оба потока могут одновременно считывать значение
 * счетчика, инкрементировать его и записывать обратно, вызывая тем самым некорректные результаты.
 * <p>
 * При многократном выполнении программы вы заметите, что окончательное значение счетчика (Final counter value)
 * не всегда равно ожидаемому значению 20000 (10000 инкрементов из каждого потока)
 * <p>
 * Disclaimer: это очень плохой код! В реальной жизни так писать не надо.
 */

public class DataRaceIssue {

    private int counter = 0;

    public static void main(final String[] args) throws InterruptedException {
        final DataRaceIssue example = new DataRaceIssue();
        example.runTest();
    }

    public void runTest() throws InterruptedException {

        final Thread thread1 = new Thread(new IncrementTask());
        final Thread thread2 = new Thread(new IncrementTask());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final counter value: " + counter);
    }

    private void incrementCounter() {
        int temp = counter;
        temp = temp + 1;
        counter = temp;
    }

    private class IncrementTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                incrementCounter();
            }
        }
    }
}
