package gui.landlord;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EnergySystem extends Frame {

	/**
	 * This class is an abstraction of an energy system
	 */
	private static final long serialVersionUID = 1266108357344845696L;
	private Battery battery;
	private LandLot landlot;

	private void populateWindow() {
		Panel pnlButton = new Panel();
		Button btnAddProducer = new Button("Dodaj");
		btnAddProducer.addActionListener((ae) -> {
			var plant = new HydroelectricPlant(battery);
			landlot.emplaceProducer(plant);
		});
		pnlButton.add(btnAddProducer);
		add(pnlButton, BorderLayout.NORTH);

		add(landlot);
	}

	public EnergySystem(int rows, int columns, int capacity) {
		battery = new Battery(capacity);
		landlot = new LandLot(rows, columns);

		setLocationByPlatform(true);
		setSize(500, 500);
		setResizable(false);

		populateWindow();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				landlot.stopAllProducers();
				dispose();
			}
		});
		setVisible(true);
	}

	public static void main(String[] args) {
		new EnergySystem(5, 5, 100);
	}

}
