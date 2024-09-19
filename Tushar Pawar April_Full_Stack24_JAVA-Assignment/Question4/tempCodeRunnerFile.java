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
                    for (int i = 0; i < NUM_THREADS; i++) {
                        new Thread(new RunnableTask("Thread-" + i)).start();
                    }
                    break;

                case 2:
                    System.out.println("Synchronizing threads...");
                    new Thread(new SyncTask()).start();
                    new Thread(new SyncTask()).start();
                    break;

                case 3:
                    System.out.println("Demonstrating wait() and notify()...");
                    ThreadWaitNotify twn = new ThreadWaitNotify();
                    new Thread(() -> twn.consumer()).start();
                    new Thread(() -> twn.producer()).start();
                    break;

                case 4:
                    System.out.println("Demonstrating sleep()...");
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                            System.out.println("Thread woke up after 2 seconds");
                        } catch (InterruptedException e) {
                            System.out.println("Thread interrupted: " + e.getMessage());
                        }
                    }).start();
                    break;

                case 5:
                    System.out.println("Demonstrating thread interruption...");
                    ThreadInterrupt demo = new ThreadInterrupt();
                    Thread t = new Thread(demo);
                    t.start();
                    try {
                        Thread.sleep(1000); 
                    } catch (InterruptedException e) {
                        System.out.println("Main thread interrupted: " + e.getMessage());
                    }
                    t.interrupt(); 
                    break;

                case 6:
                    System.out.println("Using thread pools...");
                    ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
                    for (int i = 0; i < NUM_THREADS; i++) {
                        final int taskId = i;
                        executor.submit(() -> {
                            System.out.println("Task " + taskId + " running");
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                System.out.println("Task " + taskId + " interrupted: " + e.getMessage());
                            }
                        });
                    }
                    executor.shutdown();
                    break;

                case 7:
                    System.out.println("Demonstrating locks and conditions...");
                    new Thread(new LockConditionTask()).start();
                    new Thread(new LockConditionTask()).start();
                    break;

                case 8:
                    // deadlock and ways to avoid it
                    System.out.println("Demonstrating deadlock...");
                    new Thread(new DeadlockTask()).start();
                    new Thread(new DeadlockTask()).start();
                    break;

                case 9:
                    //thread-local variables
                    System.out.println("Using thread-local variables...");
                    new Thread(() -> {
                        threadLocalValue.set((int) (Math.random() * 100));
                        System.out.println("Thread-local value: " + threadLocalValue.get());
                    }).start();
                    break;

                case 10:
                    // Implement producer-consumer problem
                    System.out.println("Implementing producer-consumer problem...");
                    for (int i = 0; i < PRODUCER_COUNT; i++) {
                        new Thread(new Producer()).start();
                    }
                    for (int i = 0; i < CONSUMER_COUNT; i++) {
                        new Thread(new Consumer()).start();
                    }
                    break;

                case 11:
                    //Executors and Callable
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
        private final Object lock = new Object();
        private boolean dataAvailable = false;

        public void producer() {
            synchronized (lock) {
                try {
                    System.out.println("Producer is producing data...");
                    Thread.sleep(2000);
                    dataAvailable = true;
                    lock.notify(); 
                    System.out.println("Producer has produced data.");
                } catch (InterruptedException e) {
                    System.out.println("Producer interrupted: " + e.getMessage());
                }
            }
        }

        public void consumer() {
            synchronized (lock) {
                try {
                    while (!dataAvailable) {
                        lock.wait(); 
                    }
                    System.out.println("Consumer is consuming data...");
                } catch (InterruptedException e) {
                    System.out.println("Consumer interrupted: " + e.getMessage());
                }
            }
        }
    }

    // Thread Interrupt Task
    static class ThreadInterrupt implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("Running thread...");
                    Thread.sleep(500); 
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt(); 
                }
            }
            System.out.println("Thread terminated.");
        }
    }

    // Lock and Condition Task
    static class LockConditionTask implements Runnable {
        private static final Lock lock = new ReentrantLock();
        private static final Condition condition = lock.newCondition();

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("Thread acquired lock, waiting...");
                condition.await(); 
                System.out.println("Thread resumed after condition signal.");
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            } finally {
                lock.unlock();
            }
        }
    }

    static class DeadlockTask implements Runnable {
        private static final Object lock1 = new Object();
        private static final Object lock2 = new Object();

        @Override
        public void run() {
            lock1();
            lock2();
        }

        private void lock1() {
            synchronized (lock1) {
                System.out.println("Locked on lock1");
                try {
                    Thread.sleep(100); 
                } catch (InterruptedException e) {
                    System.out.println("Interrupted while locking lock1: " + e.getMessage());
                }
                lock2(); 
            }
        }

        private void lock2() {
            synchronized (lock2) {
                System.out.println("Locked on lock2");
            }
        }
    }

    static class Producer implements Runnable {
        @Override
        public void run() {
            try {
                queue.put((int) (Math.random() * 100));
                System.out.println("Produced an item");
            } catch (InterruptedException e) {
                System.out.println("Producer interrupted: " + e.getMessage());
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                Integer item = queue.take();
                System.out.println("Consumed an item: " + item);
            } catch (InterruptedException e) {
                System.out.println("Consumer interrupted: " + e.getMessage());
            }
        }
    }
}
