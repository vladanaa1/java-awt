package lab3v2;

import java.util.*;

public class Vektor {
	private double x, y;

	public Vektor(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vektor ort() {
		//double mgn = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double xx = x / Math.abs(x);
		double yy = y / Math.abs(y);
		return new Vektor(xx,yy);
	}
	
	public Vektor pseudoslucajan() {
		/*
		double x = Math.random();
		double y = Math.random(); // ovo izmedju nula i jedan
		double znak1 = Math.random();
		if(znak1 <= 0.5) znak1 = -1;
		else znak1 = 1;
		double znak2 = Math.random();
		if(znak2 <= 0.5) znak2 = -1;
		else znak2 = 1;
		
		return new Vektor(znak1*x , znak2*y); //nisam osigurala da ne sme (0,0), posle
		*/
		Random random = new Random();
        double x, y;

        do {
            x = random.nextDouble() * 2 - 1; // Random value between -1 and 1
            y = random.nextDouble() * 2 - 1; // Random value between -1 and 1
        } while (x == 0 && y == 0);
        return new Vektor(x,y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vektor other = (Vektor) obj;
		return Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x)
				&& Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y);
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	
}
