package inter_thread_communication;

public class Synchronization {

    public static int counter1 = 0;
    public static int counter2 = 0;

    public static final Object lock1 = new Object();
    public static final Object lock2 = new Object();

    // we have to make sure this method is executed only by a single thread at a given time
    // this is done by using the synchronized keyword -not a good practice-
    public static void increment1() {
        synchronized(lock1) {
            counter1++;
        }
    }

    //now if I have another synchronized method
    /**  public static synchronized void increment2() {
     *       counter2++;
     *   }
     * instead of using synchronized keyword we can use the following code:
     * --> class level locking
     * synchronized(Synchronization.class) {
     *     counter1++;
     * }
     */


    public static void increment2() {
        synchronized(lock2) {
            counter2++;
        }
    }

    public static void process() {

        /** First thread will lock on this lock1 object
         *  Second thread will lock on the lock2 object
         *  so, they are not using the same intrinsic lock of the object or class
         *  which means they can access increment1() and increment2() independently
         *  still not parallel execution but thread1 can access increment1()
         *  without waiting for thread2 to finish executing increment2()
         *  depends on time-slicing
         */

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 1000; i++) {
                    //counter++;
                    //increment();
                    increment1();
                    /** if increment2() is executing then increment1() will not execute until increment2() finish
                     *  because when thread2 execute increment2() it acquire the object's intrinsic lock
                     */
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 1000; i++) {
                    //counter++;
                    //increment();
                    increment2();
                    /** if increment1() is executing then increment2() will not execute until increment1() finish
                     *  because when thread1 execute increment1() it acquire the object's intrinsic lock
                     */
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

        System.out.println("The counter1 is: " + counter1);
        System.out.println("The counter2 is: " + counter2);

    }

    public static void main(String[] args) {

        process();

    }

}
