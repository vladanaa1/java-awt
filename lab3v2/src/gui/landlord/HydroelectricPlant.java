package gui.landlord;

import java.awt.Color;

public class HydroelectricPlant extends Producer {

	/**
	 * This class is an abstraction of a Hydroelectric Power Plant
	 */
	private static final long serialVersionUID = 5107502630907962776L;

	private int surroundingWaterAreaCount;

	public HydroelectricPlant(Battery battery) {
		super('H', Color.BLUE, 1500, battery);
	}

	@Override
	protected int productionAmount() {
		return surroundingWaterAreaCount;
	}

	@Override
	protected boolean productionSuccessful() {
		return surroundingWaterAreaCount >= 1;
	}

	public void setSurroudingWaterAreaCount(int count) {
		surroundingWaterAreaCount = count;
	}

}
