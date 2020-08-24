package Model;

public class JumpMan {
	// The platforms a player jumps on
	private Platform[] platforms;
	
	// The character the player controls
	private Sprite sprite;
	
	private boolean gameRunning = false;
	
	/**
	 * Set the game to running or not running
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
}
