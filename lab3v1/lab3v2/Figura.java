package lab3v2;

import java.awt.*;



public abstract class Figura {
	protected Vektor polozaj, pomeraj;
	protected double poluprecnik;
	protected Color color;
	public Figura(Vektor polozaj, Vektor pomeraj, int poluprecnik){
		this.polozaj = polozaj;
		this.pomeraj = pomeraj;
		this.poluprecnik = poluprecnik;
	}
	
	public Figura(Vektor polozaj, Vektor pomeraj){
		this.polozaj = polozaj;
		this.pomeraj = pomeraj;
		poluprecnik = 20;
	}
	/*
	public boolean uOkviru (Vektor v) {
		if(Math.pow(v.getX() - polozaj.getX(), 2) + Math.pow(v.getY() - polozaj.getY(), 2)<=poluprecnik)
			return true;
		return false;
	}
	*/
	
	private int distance(Figura fig) {
        return (int)Math.sqrt(Math.pow(polozaj.getX() - fig.polozaj.getX(), 2) 
        	+ Math.pow(polozaj.getY() - fig.polozaj.getY(), 2));
    }

    public boolean uOkviru(Figura fig) {
        return distance(fig) <= (poluprecnik + fig.poluprecnik);
    }
	
	
	public boolean poklapa(Figura f) {
		if(polozaj.equals(f)&&poluprecnik==f.poluprecnik) return true;
		return false;
	}
	
	public abstract Color getColor();
	
	public abstract void paint(Graphics g,int centerX, int centerY);

	
}
