package threads;

class Runner1 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Runner1 " + i);
        }
    }
}

class Runner2 implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Runner2 " + i);
        }
    }
}

class Thread1 extends Thread{

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread1 " + i);
        }
    }
}

class Thread2 extends Thread{

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread2 " + i);
        }
    }
}

class DaemonWorker implements Runnable {


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Daemon thread is running...");
        }
    }
}

class NormalWorker implements Runnable {


    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Normal thread finishes execution...");
    }
}


public class Main {
    public static void main(String[] args) {
        // multi-threading
        Thread t1 = new Thread(new Runner1());//1
        Thread t2 = new Thread(new Runner2());//2

        // multithreaded execution with help of time-slicing (Not Parallel Execution)
        t1.start();// runs for some time then switch to t2
        t2.start();// runs for some time then switch to t1

        Thread t3 = new Thread1();
        Thread t4 = new Thread2();

        t3.start();
        t4.start();

        // we can wait for the thread to finish using: join()

        try {
            t1.join(); // waiting until t1 finish before executing other parts of the application.
            // so System.out.println("Finished with threads..."); will execute only if t1 finish
            t2.join(); // now System.out.println("Finished with threads..."); will wait until t2 finish as well
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Finished with threads...");

        /////////////////////////////////////////////////////////////////
        Thread daemonWorker = new Thread(new DaemonWorker());
        Thread normalThread = new Thread(new NormalWorker());

        daemonWorker.setDaemon(true); // make thread daemon thread if u want it to run in background

        daemonWorker.start();
        normalThread.start(); //daemonWorker will be terminated by JVM when all other normalThreads are terminated
        /////////////////////////////////////////////////////////////////
        System.out.println(Thread.currentThread().getName());
        Thread.currentThread().setPriority(8);
        System.out.println(Thread.currentThread().getPriority());
        // high priority thread 10
        t1 = new Thread(new Runner1());
        t1.setPriority(Thread.MAX_PRIORITY); // usually executed before lower priority threads but depends on OS
        t1.start();
        // main thread priority 5
        System.out.println("This is in the main thread...");

    }
}