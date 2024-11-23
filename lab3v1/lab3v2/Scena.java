package lab3v2;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Scena extends Canvas implements Runnable{
	private Thread nit = new Thread(this);
	private ArrayList<Figura> figure = new ArrayList<>();
	boolean running;
	private int offset = 3;
	
	public Scena() {
		super();
		running = true;
        addListeners();
        setPreferredSize(new Dimension(400, 300));
        setBackground(Color.LIGHT_GRAY);
        nit.start();
	}
	
	@Override
	public void paint(Graphics g) {
		
		for(Figura f: figure) {
			//int centerX, centerY; //moras ovako da bi pomerila koor pocetak iz centra u gornji levi ugao
			//centerX = (int) (f.polozaj.getX()*getWidth()/2 + getWidth() / 2);
			//centerY = (int) (f.polozaj.getY()*getHeight()/2*(-1) + getHeight() / 2);
			f.paint(g, (int)f.polozaj.getX(), (int)f.polozaj.getY());
		}
		if(!running) {
			Color color = g.getColor();
			String pauseText = "PAUSE";
			g.setColor(Color.BLACK);
			FontMetrics fontMetrics = g.getFontMetrics();
			int x = (getWidth() - fontMetrics.stringWidth(pauseText)) / 2;
            int y = getHeight() / 2;
            g.drawString(pauseText, x, y);
            g.setColor(color);
            //Font font = new Font("Arial", Font.BOLD, 24);
            
           
		}
	}
	
	private synchronized void pomeri() {
		/*//kod bez ikakve provere
		for(Figura f: figure)
		{
			//ovde treba da pomeris figuru za vektor pomeraja * br piksela
			Vektor ort = f.pomeraj.ort();
			Vektor noviPolozaj = new Vektor(f.polozaj.getX() + ort.getX()*offset,
					f.polozaj.getY() + ort.getY()*offset);
			
			f.polozaj = noviPolozaj;
		}
		*/
		ArrayList<Vektor> stariPolozaji = new ArrayList<Vektor>();
		ArrayList<Vektor> noviPolozaji = new ArrayList<Vektor>();
		for(Figura f:figure) {
			stariPolozaji.add(f.polozaj);
		}
		for(Figura f: figure)
		{
			//ovde treba da pomeris figuru za vektor pomeraja * br piksela
			Vektor ort = f.pomeraj.ort();
			Vektor noviPolozaj = new Vektor(f.polozaj.getX() + ort.getX()*offset,
					f.polozaj.getY() + ort.getY()*offset);
			noviPolozaji.add(noviPolozaj);
			f.polozaj = noviPolozaj;
		}
		//ArrayList<Vektor> polozaji = new ArrayList<Vektor>();
		for(Figura f1: figure) {
			if(udaraGornji(f1) || udaraDonji(f1)) {
				f1.pomeraj.setY(f1.pomeraj.getY() * (-1));
				Vektor ort = f1.pomeraj.ort();
				Vektor noviPolozaj = new Vektor(f1.polozaj.getX() + ort.getX()*offset,
						f1.polozaj.getY() + ort.getY()*offset);
				f1.polozaj = noviPolozaj;
			}
			if(udaraLevi(f1) || udaraDesni(f1)) {
				f1.pomeraj.setX(f1.pomeraj.getX() * (-1));
				Vektor ort = f1.pomeraj.ort();
				Vektor noviPolozaj = new Vektor(f1.polozaj.getX() + ort.getX()*offset,
						f1.polozaj.getY() + ort.getY()*offset);
				f1.polozaj = noviPolozaj;
			}
			for(Figura f2:figure) {
				if(f1.uOkviru(f2) && f1 != f2) {
					Vektor p1 = new Vektor(f1.pomeraj.getX(), f1.pomeraj.getY());
					Vektor p2 = new Vektor(f2.pomeraj.getX(), f2.pomeraj.getY());
					//f1.pomeraj.setX(f1.pomeraj.getX() * (-1));
					//f1.pomeraj.setY(f1.pomeraj.getY() * (-1));
					f1.pomeraj.setX(p2.getX());
					f1.pomeraj.setY(p2.getY());
					Vektor ort = f1.pomeraj.ort();
					Vektor noviPolozaj = new Vektor(f1.polozaj.getX() + ort.getX()*offset,
							f1.polozaj.getY() + ort.getY()*offset);
					f1.polozaj = noviPolozaj;
					
					
					f2.pomeraj.setX(p1.getX());
					f2.pomeraj.setY(p1.getY());
					//f2.pomeraj.setX(f2.pomeraj.getX() * (-1));
					//f2.pomeraj.setY(f2.pomeraj.getY() * (-1));
					ort = f2.pomeraj.ort();
					noviPolozaj = new Vektor(f2.polozaj.getX() + ort.getX()*offset,
							f2.polozaj.getY() + ort.getY()*offset);
					f2.polozaj = noviPolozaj;
				}
			}
			
		}
		
	}
	
	@Override
	public void run() {
		try {
			while(!nit.isInterrupted()) { //ovo pok na trenutnu running nit?
				synchronized (this) {
					while(!running){
						wait();
					}
				}
				repaint();
				Thread.sleep(100);
				pomeri();
				}
				
				
			
		} catch(InterruptedException e) {}
	}
	
	public synchronized void go() {
		
		running = true;
		repaint();
		notify();
	}
	
	public synchronized void pause() {
		running = false;
		//treba da ispises string "PAUZA" na sred ekrana, jesam!
        repaint();
	}
	
	public synchronized void terminate() { //ovo okej
		nit.interrupt();
	}
	
	public synchronized void dodaj(Figura f) {
		
		for(Figura ff:figure) {
			if(f.equals(ff) || ff.uOkviru(f) ) 
				return;
	}
		/*
	if(udaraGornji(f) || udaraDonji(f) || udaraLevi(f) || udaraDesni(f)) {
		return;
	}
		*/
	if((f.polozaj.getX() + f.poluprecnik) >= getWidth()) return;
	if((f.polozaj.getX() - f.poluprecnik) <= 0) return;
	if((f.polozaj.getY() + f.poluprecnik) >= getHeight()) return;
	if((f.polozaj.getX() - f.poluprecnik) <= 0) return;
	figure.add(f);
	
	}

	private void addListeners() {
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(running) {
					getParent().requestFocus();
					//ovde treba da x i y iz koor sistema prozora(koor pocetak u gornjem levom uglu) pretvoris u koor sistem nas(koor poc u centru)
					int x = e.getX();
					int y = e.getY();
					/*
					x -= getWidth() / 2;
					y -= getHeight() / 2;
					y *= -1;
					x /= (getWidth() / 2);
					y /= (getHeight() / 2);
					*/
					Vektor polozaj = new Vektor(x, y);
					Vektor pomeraj = polozaj.pseudoslucajan();
					Disk d = new Disk(polozaj, pomeraj);
					for(Figura f:figure) {
						if(f.uOkviru(d))
							return;
					}
					dodaj(d);
					repaint();//ovaj repaint ti se poziva
				}
				}
		});
	}
	
	private boolean udaraGornji(Figura f) {
		return((f.polozaj.getY() - f.poluprecnik) >= 0);
	}
	
	private boolean udaraDonji(Figura f) {
		return((f.polozaj.getY() + f.poluprecnik ) <= getHeight());
	}
	
	private boolean udaraLevi(Figura f) {
		return((f.polozaj.getX() - f.poluprecnik) <= 0);
	}
	
	private boolean udaraDesni(Figura f) {
		return ((f.polozaj.getX() + f.poluprecnik)>=getWidth());
	}
	
	public synchronized boolean running() {
		return running;
	}
}
