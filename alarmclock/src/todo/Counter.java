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
			time.count(); // R�knar s� l�nge programmet �r ig�ng
			diff = tempTime - System.currentTimeMillis();

			System.out.println(" Nbr of Threads: " + java.lang.Thread.activeCount());	// Antalet tr�dar ig�ng
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
