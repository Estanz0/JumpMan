package View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

import Model.JumpMan;

public class GUI {
	private static JFrame frame;
	private JComponent drawing;
	
	private JumpMan game;
	
	// Frame and drawing area constants
	private final boolean RESIZEABLE = false;
	private final int DEFAULT_WIDTH = 500;
	private final int DEFAULT_HEIGHT = 500;
	
	// Home screen constants
	private final int START_BOX_WIDTH = 200;
	private final int START_BOX_HEIGHT = 100;
	
	
	
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
				if(!game.isRunning() && clickedStart(e)) {
					game.setRunning(true);
					redraw();
				}
			}
		});
		
		frame = new JFrame("Jump Man");
		frame.setResizable(RESIZEABLE);
		frame.add(drawing);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game = new JumpMan();
	}
	
	/**
	 * Checks if a player has clicked on the start button
	 * @param e
	 * @return boolean
	 */
	private boolean clickedStart(MouseEvent e) {
		return e.getX() > (DEFAULT_WIDTH - START_BOX_WIDTH) / 2 &&
				e.getX() < ((DEFAULT_WIDTH - START_BOX_WIDTH) / 2) + START_BOX_WIDTH &&
				e.getY() > (DEFAULT_HEIGHT - START_BOX_HEIGHT) / 2 &&
				e.getY() < ((DEFAULT_HEIGHT - START_BOX_HEIGHT) / 2) + START_BOX_HEIGHT;
	}
	
	/**
	 * Redraws the game screen depending on the state of the game
	 * @param Graphics g
	 */
	public void redraw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(!game.isRunning()) 
			drawHomeScreen(g2);
		else 
			drawGameScreen(g2);
	}
	
	/**
	 * Draws screen with buttons to start the game
	 * @param g
	 */
	private void drawHomeScreen(Graphics2D g2) {
		int x = (DEFAULT_WIDTH - START_BOX_WIDTH) / 2;
		int y = (DEFAULT_HEIGHT - START_BOX_HEIGHT) / 2;
		
		g2.fillRect(x, y, START_BOX_WIDTH, START_BOX_HEIGHT);
	}
	
	/**
	 * Draws the platforms and player positions
	 * @param g
	 */
	private void drawGameScreen(Graphics2D g2) {
		
	}
	
	public static void redraw() {
		frame.repaint();
	}
	
	public static void main(String[] args) {
		new GUI();
	}
}
