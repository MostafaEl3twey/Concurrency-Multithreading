package inter_thread_communication;

import java.util.ArrayList;
import java.util.List;

class Processor {

    private List<Integer> list = new ArrayList<>();
    private static final int UPPER_LIMIT = 5;
    private static final int LOWER_LIMIT = 0;
    private static final Object lock = new Object();
    private int value = 0;

    public void producer() throws InterruptedException{
        synchronized (lock) {
            while (true) {
                if (list.size() == UPPER_LIMIT) {
                    System.out.println("Waiting for removing items...");
                    lock.wait(); // wait on the lock and notify on the lock
                } else {
                    System.out.println("Adding: " + value);
                    list.add(value);
                    value++;
                    // we can call the notify() method - because the other thread will be notified
                    // only when it is in a waiting state
                    lock.notify();
                }
                Thread.sleep(1000);
            }
        }
    }

    public void consumer() throws InterruptedException{
        /** Note: Remember after notify producer will not continue until finish
         *  executing the rest of the synchronized block
         *  but because it reaches the lower limit it will wait and release the lock so producer will continue
         *  and vise versa
         */
        synchronized (lock) {
            while (true) {
                if (list.size() == LOWER_LIMIT) {
                    System.out.println("Waiting for adding items...");
                    lock.wait(); // wait on the lock and notify on the lock
                } else {
                    System.out.println("Removing: " + list.remove(list.size() - 1));
                    lock.notify();
                }
                Thread.sleep(1000);
            }
        }
    }
}

public class ProducerAndConsumer {


    public static void main(String[] args) throws InterruptedException {
        Processor processor = new Processor();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.producer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consumer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
    }
}
