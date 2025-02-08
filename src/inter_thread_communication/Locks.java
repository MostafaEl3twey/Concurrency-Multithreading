package inter_thread_communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Worker {
    private Lock lock = new ReentrantLock(); // the default value for fairness is true
    private Condition condition = lock.newCondition(); // because we can't use wait and notify here we use condition

    public void produce() throws InterruptedException {
        lock.lock();
        try {
            System.out.println("Running the produce method...");
            condition.await(); // similar to wait() - it will release the intrinsic lock of the object
            // and put the thread in a waiting state until signal() is called on the same object
            System.out.println("Resuming the produce method...");
        } finally {
            lock.unlock();
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(2000);
        lock.lock();
        try {
            System.out.println("Running the consume method...");
            Thread.sleep(3000);
            condition.signal(); // similar to notify() - it will notify the thread in a waiting state
        } finally {
            lock.unlock();
        }
    }
}


public class Locks {

//    private static int counter = 0;
//    private static final Lock lock = new ReentrantLock(); // default value for fairness is true
//
//    private static void increment() {
//        lock.lock();
//        try {
//            for(int i = 0; i < 10000; i++) {
//                counter++;
//            }
//        } finally {
//            lock.unlock();
//        }
//    }

    public static void main(String[] args) {
        Worker worker = new Worker();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
//                increment();
                try {
                    worker.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
//                increment();
                try {
                    worker.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("Counter: " + counter);
    }

}
