package gui.landlord;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.stream.Stream;

public class LandLot extends Panel {

	/**
	 * Abstraction for a several parcels of land, a Land Lot
	 */
	private static final long serialVersionUID = 2533699200541612083L;

	private int rows, columns;
	private Parcel selectedParcel = null;
	private ArrayList<Parcel> parcels = new ArrayList<>();
	private ArrayList<Producer> producers = new ArrayList<>();

	public LandLot(int rows, int columns) {
		super(new GridLayout(rows, columns, 5, 5));
		this.rows = rows;
		this.columns = columns;
		initializeLandLot();
	}

	public void changeSelection(Parcel next) {
		if (!parcels.contains(next)) {
			return;
		}
		if (selectedParcel != null) {
			changeFontSize(selectedParcel, 14);
		}
		selectedParcel = next;
		changeFontSize(selectedParcel, 20);
	}

	private static Parcel randomParcel() {
		return Math.random() <= 0.7 ? new GrassArea() : new WaterArea();
	}

	private void initializeLandLot() {
		Stream.generate(LandLot::randomParcel).limit(rows * columns)
				.forEach(parcel->{
					parcels.add(parcel);
					add(parcel);
				});
	}

	public void emplaceProducer(Producer producer) {
		if (selectedParcel == null) {
			producer.finish();
			return;
		}
		if (selectedParcel instanceof Producer) {
			
			((Producer) selectedParcel).finish();
			producers.remove(selectedParcel);
		}
		int index = parcels.indexOf(selectedParcel);

		parcels.set(index, producer);
		producers.add(producer);

		remove(index);
		add(producer, index);
		revalidate();

		changeSelection(producer);

		updateNumberOfSoroundingWaterAreas();
	}

	private int getSurroundingWaterAreaCount(HydroelectricPlant plant) {
		int count = 0;
		int index = parcels.indexOf(plant);
		int row = index / columns;
		int column = index % columns;
		for (int i = row - 1; i < row + 2; i++) {
			for (int j = column - 1; j < column + 2; j++) {
				if (i < 0 || i >= rows || j < 0 || j >= columns)
					continue;
				if (parcels.get(i * columns + j) instanceof WaterArea) {
					count++;
				}
			}
		}
		return count;
	}

	private void updateNumberOfSoroundingWaterAreas() {
		producers.stream()
				.filter(producer -> producer instanceof HydroelectricPlant)
				.map(producer -> (HydroelectricPlant) producer)
				.forEach(plant -> {
					int count = getSurroundingWaterAreaCount(plant);
					plant.setSurroudingWaterAreaCount(count);
				});
	}

	public void stopAllProducers() {
		producers.stream().forEach(Producer::finish);
	}

	private void changeFontSize(Parcel parcel,float size) {
		Font oldFont = parcel.getFont();
		Font newFont = oldFont.deriveFont(size);
		parcel.setFont(newFont);
	}
}
