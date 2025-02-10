package multithreading_concepts;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {

	private Lock lock1 = new ReentrantLock(true);
	private Lock lock2 = new ReentrantLock(true);
	
	public static void main(String[] args) {
		
		Deadlock deadlock = new Deadlock();
		
		new Thread(deadlock::worker1, "worker1").start();
		new Thread(deadlock::worker2, "worker1").start();
	}
	
	public void worker1() {
		lock1.lock();
		System.out.println("Worker1 acquires the lock1...");
		
		try {
			Thread.sleep(300); // make sure worker2 acquired the lock2
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		lock2.lock();
		System.out.println("Worker1 acquires the lock2...");
		
		lock1.unlock();
		lock2.unlock();
	}
	
	public void worker2() {
		lock2.lock();
		System.out.println("Worker2 acquires the lock1...");
		
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		lock1.lock();
		System.out.println("Worker2 acquires the lock2...");
		
		lock1.unlock();
		lock2.unlock();
	}
}
