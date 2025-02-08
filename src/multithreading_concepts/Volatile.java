package multithreading_concepts;

class Worker implements Runnable {
    // it will be stored in main memory because of volatile
    // 1.) variables can be stored on the main memory without the volatile keyword
    // 2.) it may happen that both the main thread and the worker thread are within the same CPU core which means that
    // they are writing the variables and reading the variables from the same cache.
    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            System.out.println("Worker class is running...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}

public class Volatile {

    public static void main(String[] args) {
        Worker worker = new Worker();

        Thread t1 = new Thread(worker);

        t1.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        worker.setRunning(false);
        System.out.println("Worker class is stopped...");
    }



}
