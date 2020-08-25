package View;

import Model.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

import Controller.Controller;
import Model.JumpMan;

public class GUI {
	private static JFrame frame;
	private JComponent drawing;

	public static JumpMan game;

	// Frame and drawing area constants
	private final boolean RESIZEABLE = false;
	public final static int DEFAULT_WIDTH = 500;
	public final static int DEFAULT_HEIGHT = 500;

	// Home screen constants
	private final int START_BOX_WIDTH = 200;
	private final int START_BOX_HEIGHT = 100;

	// Game Screen constants
	public static final int PLATFORM_WIDTH = 30;
	public static final int SPRITE_SIZE = 30;
	private final Color PLATFORM_COLOR = new Color(0, 0, 0);

	// Sprite
	private final File SPRITE_FILE = new File("src/sprite.png");
	private BufferedImage SPRITE_IMAGE;

	// Tick length (milli seconds)
	private final static int TICK_LENGTH = 40;

	public GUI() {
		drawing = new JComponent() {
			protected void paintComponent(Graphics g) {
				redraw(g);
			}
		};
		drawing.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		drawing.setVisible(true);

		// Mouse listener
		drawing.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!game.isRunning() && clickedStart(e)) {
					game.setRunning(true);
					redraw();
				}
			}
		});
		
		// Key listener
		KeyListener keyListener = new KeyListener() {

			public void keyTyped(KeyEvent e) {}

			public void keyPressed(KeyEvent e) {
				new Controller("Key Press", e);
			}

			public void keyReleased(KeyEvent e) {
				new Controller("Key Release", e);
				
			}
			
		};

		frame = new JFrame("Jump Man");
		frame.setResizable(RESIZEABLE);
		frame.addKeyListener(keyListener);
		frame.add(drawing);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game = new JumpMan();
		gameLoop();
	}

	/**
	 * Checks if a player has clicked on the start button
	 * 
	 * @param e
	 * @return boolean
	 */
	private boolean clickedStart(MouseEvent e) {
		return e.getX() > (DEFAULT_WIDTH - START_BOX_WIDTH) / 2
				&& e.getX() < ((DEFAULT_WIDTH - START_BOX_WIDTH) / 2) + START_BOX_WIDTH
				&& e.getY() > (DEFAULT_HEIGHT - START_BOX_HEIGHT) / 2
				&& e.getY() < ((DEFAULT_HEIGHT - START_BOX_HEIGHT) / 2) + START_BOX_HEIGHT;
	}

	/**
	 * Redraws the game screen depending on the state of the game
	 * 
	 * @param g
	 */
	public void redraw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(game == null) {
			
		} else if (!game.isRunning())
			drawHomeScreen(g2);
		else
			drawGameScreen(g2);
	}

	/**
	 * Draws screen with buttons to start the game
	 * 
	 * @param g
	 */
	private void drawHomeScreen(Graphics2D g2) {
		int x = (DEFAULT_WIDTH - START_BOX_WIDTH) / 2;
		int y = (DEFAULT_HEIGHT - START_BOX_HEIGHT) / 2;

		g2.fillRect(x, y, START_BOX_WIDTH, START_BOX_HEIGHT);
	}

	/**
	 * Draws the platforms and player positions
	 * 
	 * @param g
	 */
	private void drawGameScreen(Graphics2D g2) {
		// Draw platforms
		g2.setColor(PLATFORM_COLOR);
		for (Platform p : game.getPlatforms()) {
			g2.drawLine(p.getX(), p.getY(), p.getX() + PLATFORM_WIDTH, p.getY());
		}

		// Draw sprite
		try {
			SPRITE_IMAGE = ImageIO.read(SPRITE_FILE);
		} catch (IOException e) {
			System.out.println("FAILED TO LOAD IMAGE");
		}
		g2.drawImage(SPRITE_IMAGE, game.getSprite().getX(), game.getSprite().getY(), SPRITE_SIZE, SPRITE_SIZE, drawing);
	}

	public static void redraw() {
		frame.repaint();
	}

	public static void main(String[] args) {
		new GUI();
	}

	static void gameLoop() {
		long lastTimer = System.currentTimeMillis();

		while (true) {
			if (System.currentTimeMillis() - lastTimer >= TICK_LENGTH) {
				game.tick();
				lastTimer = System.currentTimeMillis();
				redraw();
			} else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					System.out.println("INTERRUPTED");
				}
			}
		}
	}
}
