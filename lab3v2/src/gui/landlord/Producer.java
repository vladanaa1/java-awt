package gui.landlord;

import java.awt.Color;
import java.util.Optional;

public abstract class Producer extends Parcel implements Runnable {

	/**
	 * This class is an abstraction for an energy Producer
	 */
	private static final long serialVersionUID = 2901617260657859773L;
	private int baseTime;
	private Battery battery;
	private Thread thread;

	public Producer(char mark, Color bgColor, int period, Battery battery) {
		super(mark, bgColor);
		this.battery = battery;
		baseTime = period;
		thread = new Thread(this);
		thread.start();
	}

	protected abstract int productionAmount();

	protected abstract boolean productionSuccessful();

	private Optional<Integer> produceEnergy() {
		if (productionSuccessful()) {
			return Optional.of(productionAmount());
		} else {
			return Optional.empty();
		}
	}

	private int nextProductionTime() {
		return baseTime + (int) (Math.random() * 300);
	}

	@Override
	public void run() {
		Color color = getForeground();
		try {
			while (!Thread.interrupted()) {
				// Sleep random time
				Thread.sleep(nextProductionTime());
				// Do work
				produceEnergy().ifPresent(amount -> {
					battery.charge(amount);
					setForeground(Color.RED);
				});
				// Sleep more
				Thread.sleep(300);
				// Reset color
				setForeground(color);
			}
		} catch (InterruptedException e) {
		}
	}

	public synchronized void finish() {
		if (thread == null) {
			return;
		}
		thread.interrupt();
		try {
			thread.join();
			thread = null;
		} catch (InterruptedException e) {
		}
	}
}
