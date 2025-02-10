package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class StockMarketUpdater implements Runnable {

    @Override
    public void run() {
        System.out.println("Updating the stock market...");
    }
}


public class ScheduledThreadPoolExample {

    public static void main(String[] args) {
        // it is a single thread that will execute the tasks sequentially
        // so one after another
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        // schedule the task to be executed after 1 second
        // and repeat it every 2 seconds
        executor.scheduleAtFixedRate(new StockMarketUpdater(), 1000, 2000, TimeUnit.MILLISECONDS);

    }

}
