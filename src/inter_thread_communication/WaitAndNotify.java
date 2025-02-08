package inter_thread_communication;

class Process {
    public void produce() throws InterruptedException{
        synchronized (this) {
            System.out.println("Running the produce method...");
            wait(); // wait and notify must be on the same object
            // wait() will release the intrinsic lock of the object and put the thread in a waiting state
            // until notify() is called on the same object
            System.out.println("Resuming the produce method...");
        }
    }

    public void consume() throws InterruptedException{
        Thread.sleep(1000);
        synchronized (this) {
            System.out.println("Running the consume method...");
            notify();
            // it is not going to handle the lock: we can make further operations
            // it means that java will finish the execution of this synchronized block first then
            // it will notify the thread in a waiting state
            Thread.sleep(5000); // produce won't continue until the 5 seconds passed
        }
    }
}

public class WaitAndNotify {

    public static void main(String[] args) {

        Process process = new Process();


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();

    }
}
