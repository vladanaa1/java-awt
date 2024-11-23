package gui.landlord;

public class Battery {
	private int energy;
	private int capacity;

	public Battery(int capacity) {
		this.energy = capacity;
		this.capacity = capacity;
	}

	public synchronized void charge(int amount) {
		energy = Math.min(energy + amount, capacity);
	}

	public synchronized void discharge() {
		energy = 0;
	}

	public synchronized boolean fullyCharged() {
		return energy == capacity;
	}
}
