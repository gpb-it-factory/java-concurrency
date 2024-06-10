package ru.gazprombank.thread;

public class Deadlock {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(new LockTask(LOCK1, LOCK2), "Thread-1");
        Thread thread2 = new Thread(new LockTask(LOCK2, LOCK1), "Thread-2");

        thread1.start();
        thread2.start();
    }

    private record LockTask(Object firstLock, Object secondLock) implements Runnable {

        @Override
            public void run() {
                synchronized (firstLock) {
                    System.out.println(Thread.currentThread().getName() + ": удерживает " + firstLock);
                    System.out.println(Thread.currentThread().getName() + ": пытается захватить " + secondLock);

                    synchronized (secondLock) {
                        System.out.println(Thread.currentThread().getName() + ": удерживает " + firstLock + " и " + secondLock);
                    }
                }

                System.out.println(Thread.currentThread().getName() + ": отпускает " + firstLock);
            }
        }
}

/*
Ожидаемый результат:
Thread-1: удерживает java.lang.Object@...
Thread-2: удерживает java.lang.Object@...
Thread-1: пытается захватить java.lang.Object@...
Thread-2: пытается захватить java.lang.Object@...

Программа висит, так как оба потока будут ждать освобождения ресурсов друг от друга.
*/