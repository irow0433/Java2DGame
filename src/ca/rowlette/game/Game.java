package ca.rowlette.game;
import java.awt.BorderLayout;
//short cut for imports is Command shift and then O!!! very useful and quick
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Creates width of the game
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH/12 * 9;
	public static final int SCALE = 3;
	public static final String NAME = "Macally";
	
	//writable area to put stuff on = JFrame.
	private JFrame frame;
	
	public boolean running = false;
	
	public Game() {
		//a dimention is just a width and a height.
		//below is what will allow the frame size to be from Minimum to Preferred will keep frame at one size
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT* SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT* SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT* SCALE));
		
		frame = new JFrame(NAME);
		
		//TAKES AN INT (PUBLIC STATIC FINAL INT) WHICH IS SAYING WHEN THE GAME IS DONE RUNNING IT WILL CLOSE
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//will make the frame stay positioned or a consistint size.
		frame.setLayout(new BorderLayout());
		
		//borderlayout.center will keep it inside the JFrame.
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	//Synchhronized means that the method cannot be executed by two threads at the same time. For future references you'll be using more of this in C++.
	public synchronized void start() {
		//thread is another instince of runnable when it starts it is going to run the thee run thread. Used also for not taking from the main thread.
		running = true;
		new Thread(this).start();
		
		
	}
	
	public synchronized void stop() {
		running = false;
		
	}
	
	public void run() {
		while(running) {
			System.out.println("Hi");
		}
	}
	
	public static void main(String[] args) {
		new Game().start();
		
	}



}
