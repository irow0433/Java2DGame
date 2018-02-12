package ca.rowlette.game;
import java.awt.BorderLayout;
//short cut for imports is Command shift and then O!!! very useful and quick
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

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
	public int tickCount = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	//private Screen screen;
	
	public Game() {
		//a dimension is just a width and a height.
		//below is what will allow the frame size to be from Minimum to Preferred will keep frame at one size
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT* SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT* SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT* SCALE));
		
		frame = new JFrame(NAME);
		
		//TAKES AN INT (PUBLIC STATIC FINAL INT) WHICH IS SAYING WHEN THE GAME IS DONE RUNNING IT WILL CLOSE
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//will make the frame stay positioned or a consistent size.
		frame.setLayout(new BorderLayout());
		
		//borderlayout.center will keep it inside the JFrame.
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	//init = initialize
	//public void init() {
	//	screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/spritesheet1.png"));
	//}
	
	//Synchronized means that the method cannot be executed by two threads at the same time. For future references you'll be using more of this in C++.
	public synchronized void start() {
		//thread is another instance of runnable when it starts it is going to run the  run thread. Used also for not taking from the main thread.
		running = true;
		new Thread(this).start();
		
		
	}
	
	public synchronized void stop() {
		running = false;
		
	}
	
	public void run() {
		//D = delta and delta is how many nano seconds have gone by so far. once it hits one it will minus 1.
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		//init();
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while(delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (shouldRender) {
			frames++;
			render();
			}
			
			//1000 mill seconds = 1 second.every second that passes by it will update.
			if(System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				System.out.println(ticks + "ticks" + frames + "frames");
				frames = 0;
				ticks = 0;
				
			}
		}
	}
	
	public void tick() {
		tickCount++;
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = i * tickCount;
		}
		
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3); // higher the number the clearer it is but to big will cause tearing in the image
			return;
			}
		
		//screen.render(pixels, 0, WIDTH);
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.drawRect(0, 0, getWidth(), getHeight());

		g.dispose();
		bs.show(); //shows contents of the buffer
		
		
		
	}
	
	public static void main(String[] args) {
		new Game().start();
		
	}



}