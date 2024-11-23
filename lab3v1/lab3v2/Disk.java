package lab3v2;

import java.awt.*;
import java.util.stream.IntStream;

public class Disk extends Figura{

	public Disk(Vektor polozaj, Vektor pomeraj) {
		super(polozaj, pomeraj);
	}
	
	public Disk(Vektor polozaj, Vektor pomeraj, int poluprecnik){
		super(polozaj,pomeraj,poluprecnik);
	}

	@Override
	public Color getColor() {
		return Color.BLUE;
	}



	@Override
	public void paint(Graphics g, int x, int y) { // ovo u pikselima
		// Calculate the coordinates of the octagon vertices
        int[] xPoints = new int[8];
        int[] yPoints = new int[8];

        int centerX = x; // X coordinate of the center
        int centerY = y; // Y coordinate of the center

        // Calculate the vertices using trigonometry
        for (int i = 0; i < 8; i++) {
            double angle = 2 * Math.PI * i / 8;
            xPoints[i] = (int) (centerX + 20 * Math.cos(angle));
            yPoints[i] = (int) (centerY + 20 * Math.sin(angle));
        }

        // Draw the octagon
        g.setColor(getColor());
        g.fillPolygon(xPoints, yPoints, 8);
	}

}
