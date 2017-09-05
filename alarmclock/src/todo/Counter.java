package todo;

public class Counter extends Thread {


	private Time time;

	public Counter(Time time) {
		this.time = time;

	}

	public void run() {
		long tempTime, diff;
		tempTime = System.currentTimeMillis();
		while (true) {
			tempTime += 1000; 
			time.count(); // Räknar så länge programmet är igång
			diff = tempTime - System.currentTimeMillis();

			System.out.println(" Nbr of Threads: " + java.lang.Thread.activeCount());	// Antalet trådar igång
			try {
				Thread.sleep(diff);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.err.println("Som error in sleep in counter");
				e.printStackTrace();
			}
		}
	}
}
