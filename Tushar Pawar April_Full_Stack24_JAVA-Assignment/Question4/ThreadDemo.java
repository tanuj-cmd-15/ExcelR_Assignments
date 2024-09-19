import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;

public class ThreadDemo {

    private static final int NUM_THREADS = 3;
    private static final int PRODUCER_COUNT = 1;
    private static final int CONSUMER_COUNT = 2;
    private static final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static final ThreadLocal<Integer> threadLocalValue = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Thread Operations Menu:");
            System.out.println("1. Create and start multiple threads");
            System.out.println("2. Synchronize threads");
            System.out.println("3. Use wait() and notify()");
            System.out.println("4. Use sleep() to pause threads");
            System.out.println("5. Demonstrate thread interruption and termination");
            System.out.println("6. Use thread pools");
            System.out.println("7. Implement thread synchronization using locks and conditions");
            System.out.println("8. Demonstrate deadlock and ways to avoid it");
            System.out.println("9. Use thread-local variables");
            System.out.println("10. Implement producer-consumer problem");
            System.out.println("11. Use Executors and Callable");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
                System.out.print("Enter your choice: ");
            }

            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.println("Creating and starting threads...");
                    List<Thread> threads = new ArrayList<>();
                    for (int i = 0; i < NUM_THREADS; i++) {
                        Thread thread = new Thread(new RunnableTask("Thread-" + i));
                        threads.add(thread);
                        thread.start();
                    }
                    for (Thread thread : threads) {
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            System.out.println("Thread interrupted: " + e.getMessage());
                        }
                    }
                    break;

                case 2:
                    System.out.println("Synchronizing threads...");
                    Thread syncThread1 = new Thread(new SyncTask());
                    Thread syncThread2 = new Thread(new SyncTask());
                    syncThread1.start();
                    syncThread2.start();
                    try {
                        syncThread1.join();
                        syncThread2.join();
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Demonstrating wait() and notify()...");
                    ThreadWaitNotify twn = new ThreadWaitNotify();
                    Thread consumerThread = new Thread(() -> twn.consumer());
                    Thread producerThread = new Thread(() -> twn.producer());
                    consumerThread.start();
                    producerThread.start();
                    try {
                        consumerThread.join();
                        producerThread.join();
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Demonstrating sleep()...");
                    Thread sleepThread = new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                            System.out.println("Thread woke up after 2 seconds");
                        } catch (InterruptedException e) {
                            System.out.println("Thread interrupted: " + e.getMessage());
                        }
                    });
                    sleepThread.start();
                    try {
                        sleepThread.join();
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Demonstrating thread interruption...");
                    ThreadInterrupt demo = new ThreadInterrupt();
                    Thread interruptThread = new Thread(demo);
                    interruptThread.start();
                    try {
                        Thread.sleep(1000); 
                        interruptThread.interrupt(); 
                        interruptThread.join();
                    } catch (InterruptedException e) {
                        System.out.println("Main thread interrupted: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("Using thread pools...");
                    ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
                    List<Future<?>> futures = new ArrayList<>();
                    for (int i = 0; i < NUM_THREADS; i++) {
                        final int taskId = i;
                        futures.add(executor.submit(() -> {
                            System.out.println("Task " + taskId + " running");
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                System.out.println("Task " + taskId + " interrupted: " + e.getMessage());
                            }
                        }));
                    }
                    for (Future<?> future : futures) {
                        try {
                            future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println("Error in task: " + e.getMessage());
                        }
                    }
                    executor.shutdown();
                    break;

                case 7:
                    System.out.println("Demonstrating locks and conditions...");
                    Thread lockThread1 = new Thread(new LockConditionTask());
                    Thread lockThread2 = new Thread(new LockConditionTask());
                    lockThread1.start();
                    lockThread2.start();
                    try {
                        lockThread1.join();
                        lockThread2.join();
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted: " + e.getMessage());
                    }
                    break;

                case 8:
                    System.out.println("Demonstrating deadlock...");
                    Thread deadlockThread1 = new Thread(new DeadlockTask(true));
                    Thread deadlockThread2 = new Thread(new DeadlockTask(false));
                    deadlockThread1.start();
                    deadlockThread2.start();
                    try {
                        deadlockThread1.join();
                        deadlockThread2.join();
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted: " + e.getMessage());
                    }
                    break;

                case 9:
                    System.out.println("Using thread-local variables...");
                    Thread threadLocalThread1 = new Thread(() -> {
                        threadLocalValue.set((int) (Math.random() * 100));
                        System.out.println("Thread-local value: " + threadLocalValue.get());
                    });
                    threadLocalThread1.start();
                    try {
                        threadLocalThread1.join();
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted: " + e.getMessage());
                    }
                    break;

                case 10:
                    System.out.println("Implementing producer-consumer problem...");
                    List<Thread> producerThreads = new ArrayList<>();
                    List<Thread> consumerThreads = new ArrayList<>();
                    for (int i = 0; i < PRODUCER_COUNT; i++) {
                        Thread producer = new Thread(new Producer());
                        producerThreads.add(producer);
                        producer.start();
                    }
                    for (int i = 0; i < CONSUMER_COUNT; i++) {
                        Thread consumer = new Thread(new Consumer());
                        consumerThreads.add(consumer);
                        consumer.start();
                    }
                    for (Thread producer : producerThreads) {
                        try {
                            producer.join();
                        } catch (InterruptedException e) {
                            System.out.println("Producer thread interrupted: " + e.getMessage());
                        }
                    }
                    for (Thread consumer : consumerThreads) {
                        try {
                            consumer.join();
                        } catch (InterruptedException e) {
                            System.out.println("Consumer thread interrupted: " + e.getMessage());
                        }
                    }
                    break;

                case 11:
                    System.out.println("Using Executors and Callable...");
                    ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
                    List<Future<Integer>> results = new ArrayList<>();
                    for (int i = 0; i < NUM_THREADS; i++) {
                        final int taskId = i;
                        results.add(executorService.submit(() -> {
                            System.out.println("Callable task " + taskId + " running");
                            Thread.sleep(2000);
                            return taskId * 2;
                        }));
                    }
                    for (Future<Integer> future : results) {
                        try {
                            System.out.println("Result: " + future.get());
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println("Error getting result: " + e.getMessage());
                        }
                    }
                    executorService.shutdown();
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        } while (choice != 0);

        scanner.close();
    }

    static class RunnableTask implements Runnable {
        private final String name;

        public RunnableTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + " is running.");
        }
    }

    // Synchronization Task
    static class SyncTask implements Runnable {
        private static int count = 0;
        private static final Object lock = new Object();

        @Override
        public void run() {
            synchronized (lock) {
                count++;
                System.out.println("Count: " + count);
            }
        }
    }

    // Wait and Notify Task
    static class ThreadWaitNotify {
        private boolean flag = false;

        public synchronized void producer() {
            System.out.println("Producer started");
            try {
                Thread.sleep(1000);
                flag = true;
                notify();
                System.out.println("Producer finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void consumer() {
            System.out.println("Consumer started");
            while (!flag) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Consumer finished");
        }
    }

    // Interrupt Task
    static class ThreadInterrupt implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Running...");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted!");
            }
        }
    }

    // Locks and Conditions Task
    static class LockConditionTask implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("Thread is waiting...");
                condition.await();
                System.out.println("Thread is resuming...");
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            } finally {
                lock.unlock();
            }
        }
    }

    // Deadlock Task
    static class DeadlockTask implements Runnable {
        private final boolean flag;
        private static final Object lock1 = new Object();
        private static final Object lock2 = new Object();

        public DeadlockTask(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            if (flag) {
                synchronized (lock1) {
                    System.out.println("Lock 1 acquired by " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lock2) {
                        System.out.println("Lock 2 acquired by " + Thread.currentThread().getName());
                    }
                }
            } else {
                synchronized (lock2) {
                    System.out.println("Lock 2 acquired by " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lock1) {
                        System.out.println("Lock 1 acquired by " + Thread.currentThread().getName());
                    }
                }
            }
        }
    }

    // Producer-Consumer Problem
    static class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    queue.put(i);
                    System.out.println("Produced: " + i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Producer interrupted: " + e.getMessage());
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Integer item = queue.take();
                    System.out.println("Consumed: " + item);
                } catch (InterruptedException e) {
                    System.out.println("Consumer interrupted: " + e.getMessage());
                }
            }
        }
    }
}
