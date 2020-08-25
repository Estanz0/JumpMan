package Model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import View.GUI;

public class JumpMan {
	// The platforms a player jumps on
	private List<Platform> platforms;

	// The character the player controls
	private Sprite sprite;

	private boolean gameRunning = false;

	// Constants for platforms and sprite
	private final int JUMP_HEIGHT = 60;
	private final int MOVE_SPEED = 3;
	
	// Constants for generating platforms
	private final int PLATFORM_Y_DIFFERENCE = (int) (JUMP_HEIGHT / 1.5);
	private final int PLATFORM_Y_RANDOMNESS = (int) (PLATFORM_Y_DIFFERENCE / 2);
	
	// Jump button has been released
	private boolean jumpButtonReleased = true;
	

	/**
	 * Constructor to set up game. Set up platforms starting locations and sprite
	 * location
	 */
	public JumpMan() {
		initialisePlatforms();
		sprite = new Sprite(getSpriteStartPosition(), JUMP_HEIGHT, MOVE_SPEED);
	}
	
	/**
	 * Sprite start position id the platform is the middle of the screen
	 * @return
	 */
	private Point getSpriteStartPosition() {
		Platform startPlatform = platforms.get(platforms.size() / 2);
		Point startPoint =  new Point(startPlatform.getX() + ((GUI.PLATFORM_WIDTH - GUI.SPRITE_SIZE) / 2), startPlatform.getY() - 1 - GUI.SPRITE_SIZE);
		return startPoint;
	}

	/**
	 * Main game loop for updating model each tick
	 * 
	 * @throws InterruptedException
	 */
	public void tick() {
		// Update platform locations
		for (Platform p : platforms) {
			// If the platform is at the bottom of the screen move it to the top
			if (p.getY() == GUI.DEFAULT_HEIGHT)
				p.setY(0);
			// Otherwise move it down one pixel
			else
				p.setY(p.getY() + 1);
		}

		// Check if sprite is on a platform and update
		if (!sprite.isJumping()) 
			sprite.setOnPlatform(sprite.isOnPlatform(platforms));
		
		sprite.update();

	}
	
	/**
	 * Inititialise platforms
	 */
	private void initialisePlatforms() {
		platforms = new ArrayList<Platform>();
		
		int curX = GUI.DEFAULT_WIDTH / 2;
		int curY = GUI.DEFAULT_HEIGHT - 10;
		
		// add starting platform
		platforms.add(new Platform(new Point(curX, curY)));
		
		while(curY > 0) {
			curY = getNextPlatformY(curY);
			curX = getNextPlatformX(curX);
			platforms.add(new Platform(new Point(curX, curY)));
		}
	}
	
	private int getNextPlatformY(int previousY) {
		return (previousY - PLATFORM_Y_DIFFERENCE) + (int) (Math.random() * (PLATFORM_Y_RANDOMNESS - (-PLATFORM_Y_RANDOMNESS) + 1) - PLATFORM_Y_RANDOMNESS);
	}
	
	private int getNextPlatformX(int previousX) {
		int mid = previousX + (GUI.PLATFORM_WIDTH / 2);
		int minX = Math.max(0, mid - (GUI.PLATFORM_WIDTH * MOVE_SPEED));
		int maxX = Math.min(GUI.DEFAULT_WIDTH, mid + (GUI.PLATFORM_WIDTH * (MOVE_SPEED)));
		int newMid = (int) (Math.random() * (maxX - minX + 1) + minX);
		return newMid - (GUI.PLATFORM_WIDTH / 2);
	}
	
	

	public List<Platform> getPlatforms() {
		return this.platforms;
	}

	/**
	 * Set the game to running or not running
	 * 
	 * @param boolean running
	 */
	public void setRunning(boolean running) {
		this.gameRunning = running;
	}

	/**
	 * Get whether the game is running or not
	 */
	public boolean isRunning() {
		return this.gameRunning;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public boolean canJump() {
		return jumpButtonReleased && sprite.isOnPlatform();
	}
	
	public void setJumpButtonReleased(boolean released) {
		jumpButtonReleased = released;
	}
}
