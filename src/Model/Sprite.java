package Model;

import java.awt.Point;
import java.util.List;

import View.GUI;

public class Sprite {
	private int x;
	private int y;

	private boolean jumping = false;
	private boolean onPlatform = false;

	private int jumpHeightCurrent = 0;

	private final int JUMP_HEIGHT;
	public final int MOVE_SPEED;

	private boolean movingLeft = false;
	private boolean movingRight = false;

	public Sprite(Point start, int jh, int ms) {
		this.x = start.x;
		this.y = start.y;
		this.JUMP_HEIGHT = jh;
		this.MOVE_SPEED = ms;
	}

	public void update() {
		// Jumping
		if (jumping) {
			y -= MOVE_SPEED;
			jumpHeightCurrent += MOVE_SPEED;
			if(jumpHeightCurrent >= JUMP_HEIGHT) {
				jumping = false;
				jumpHeightCurrent = 0;
			}
		} else if (onPlatform) {
			y += 1;
		} else {
			y += MOVE_SPEED;
		}

		// Moving left
		if (movingLeft)
			this.x -= 2;

		// Moving right
		if (movingRight)
			this.x += 2;
	}

	public boolean isOnPlatform(List<Platform> platforms) {
		if (jumping) {
			return false;
		}
		for (Platform p : platforms) {
			if (withinBounds(this.x, p.getX(), p.getX() + GUI.PLATFORM_WIDTH)
					|| withinBounds(this.x + GUI.SPRITE_SIZE, p.getX(), p.getX() + GUI.PLATFORM_WIDTH)) {
				if (this.y + GUI.SPRITE_SIZE == p.getY() || this.y + GUI.SPRITE_SIZE == p.getY() - 1)
					return true;
			}
		}
		return false;
	}

	private boolean withinBounds(int x, int l, int u) {
		return x >= l && x <= u;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isOnPlatform() {
		return onPlatform;
	}

	public void setOnPlatform(boolean onPlatform) {
		this.onPlatform = onPlatform;
	}
}
