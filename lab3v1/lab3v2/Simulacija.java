package lab3v2;

import java.awt.*;
import java.awt.event.*;



public class Simulacija extends Frame {

	private Scena scena;
	
    private void terminate() {
        dispose();
        scena.terminate();
    }
	
	public Simulacija() {
		addComponents();
		scena.setFocusable(false);
		addListeners();
		setTitle("Simulacija");
        setLocation(50,50);
        pack();
		//setSize(500, 500);
       
		//popuniProzor();
		setVisible(true);
	}
	


	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				terminate();
				//System.exit(0);
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				//getParent().requestFocus();
				int keyCode = e.getKeyCode();
				switch(keyCode) {
				case KeyEvent.VK_SPACE:
					if(scena.running()) scena.pause();
					else scena.go();
					break;
				case KeyEvent.VK_ESCAPE:
					//terminate();
					System.exit(0);
					break;
				}   
              }
		});

	}

	 public void addComponents() {
	        setLayout(new BorderLayout());
	        add(scena = new Scena(), BorderLayout.CENTER);
	    }

	public static void main(String[] args) {
		new Simulacija();
	}
}
