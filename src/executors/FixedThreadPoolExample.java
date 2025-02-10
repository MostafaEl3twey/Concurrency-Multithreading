package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Work implements Runnable {

    private int id;

    public Work(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        System.out.println("Task with id: " + id + " is running - thread id: " + Thread.currentThread().getId());
        long duration = (long) (Math.random() * 5);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            System.out.println("interrupted");
            Thread.currentThread().interrupt();
        }
    }
}


public class FixedThreadPoolExample {

    public static void main(String[] args) {
        // it is a single thread that will execute the tasks sequentially
        // so one after another
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executor.execute(new Work(i));
        }

        // shutdown the executor we prevent the executor to execute any more tasks
        executor.shutdown();

        // terminate actual (running) tasks
        try {
            if(!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                // if didn't finish in 1 second
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
