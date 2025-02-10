package multithreading_concepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

enum Downloader {
    INSTANCE;

    private Semaphore semaphore = new Semaphore(3, true);

    public void download() {
        try {
            semaphore.acquire();
            downloadData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }

    }

    public void downloadData() {
        try {
            System.out.println("Downloading data from the web...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class SemaphoresExample {

    /** Semaphores:
     *      It is used to control access to a shared resource.
     *          that uses a counter variable.
     *
     *          semaphore maintains a set of permits.
     *
     *  - acquire(): if a permit is available then takes it
     *  - release(): adds a permit
     *
     *  Semaphore just keeps a count of the number of permits available.
     *      new Semaphore(int permits, boolean fair)!!!
     */

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        for(int i = 0; i < 12; i++) {
            service.execute(Downloader.INSTANCE::download);
        }
    }
}
