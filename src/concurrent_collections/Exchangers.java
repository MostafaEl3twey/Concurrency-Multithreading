package concurrent_collections;

import java.util.concurrent.Exchanger;

/**
 * With the help of Exchanger -> two threads can exchange objects
 * 
 * exchange() -> exchanging objects is done via one of the two exchange()
 * methods
 * 
 * 	For example: genetic algorithms, training neural networks
 *
 */

class Worker1 implements Runnable {

	private int counter;
	private Exchanger<Integer> exchanger;

	public Worker1(Exchanger<Integer> exchanger) {
		this.exchanger = exchanger;
	}

	@Override
	public void run() {

		while (true) {

			counter = counter + 1;
			System.out.println("FirstWorker incremented the counter: " + counter);
			
			try {
				counter = exchanger.exchange(counter);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Worker2 implements Runnable {

	private int counter;
	private Exchanger<Integer> exchanger;

	public Worker2(Exchanger<Integer> exchanger) {
		this.exchanger = exchanger;
	}

	@Override
	public void run() {

		while (true) {

			counter = counter - 1;
			System.out.println("SecondWorker decremented the counter: " + counter);
			
			try {
				counter = exchanger.exchange(counter);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Exchangers {

	public static void main(String[] args) {

		Exchanger<Integer> exchanger = new Exchanger<>();

		new Thread(new Worker1(exchanger)).start();
		new Thread(new Worker2(exchanger)).start();

	}
}