package gui.landlord;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Parcel extends Label {

	/**
	 * Abstraction for all types of Parcel
	 */
	private static final long serialVersionUID = -3511537489353087561L;
	private static final Color FG_COLOR = Color.WHITE;
	private static final Font SERIF_14 = new Font("Serif", Font.BOLD, 14);

	public Parcel(char mark, Color bgColor) {
		setFont(SERIF_14);
		setForeground(FG_COLOR);
		setBackground(bgColor);
		setText(String.valueOf(mark));
		setAlignment(Label.CENTER);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				var parent = getParent();
				if (parent instanceof LandLot) {
					LandLot landlot = (LandLot) parent;
					landlot.changeSelection(Parcel.this);
				}
			}
		});
	}
}
