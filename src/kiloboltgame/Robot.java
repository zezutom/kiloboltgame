package kiloboltgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Robot {
	
	public static final int JUMPSPEED = -15;
	
	public static final int MOVESPEED = 5;
	
	public static final int GROUND = 382;

	private static Background bg1 = Starter.getBg1();
	private static Background bg2 = Starter.getBg2();
	
	private int centerX = 100;
	private int centerY = GROUND;
	private boolean jumped = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean ducked = false;

	private int speedX = 0;
	private int speedY = 1;

	private List<Projectile> projectiles = new ArrayList<>();

	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public boolean isJumped() {
		return jumped;
	}

	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public void update() {

		// Moves Character or Scrolls Background accordingly.
		
		if (speedX <= 0) {
			centerX += speedX;
			// Do not scroll the background
			bg1.setSpeed(0);
            bg2.setSpeed(0);

		} 
		else {
			if (centerX <= 200) {
	            centerX += speedX;
	        }
			else {
				bg1.setSpeed(-MOVESPEED);
				bg2.setSpeed(-MOVESPEED);				
			}
		}
		 				
		// Updates Y Position
		centerY += speedY;
		
		if (centerY + speedY >= GROUND) {
			centerY = GROUND;
		}

		// Handles Jumping
		if (jumped) {
			speedY += 1;

			if (centerY + speedY >= GROUND) {
				centerY = GROUND;
				speedY = 0;
				jumped = false;
			}

		}

		// Prevents going beyond X coordinate of 0
		if (centerX + speedX <= 60) {
			centerX = 61;
		}
	}

	public void moveRight() {
		if (!ducked) {
			speedX = MOVESPEED;
		}
		
	}

	public void moveLeft() {
		if (!ducked) {
			speedX = -MOVESPEED;
		}
	}

	public void stopRight() {
        setMovingRight(false);
        stop();
    }

    public void stopLeft() {
        setMovingLeft(false);
        stop();
    }	
	
	public void stop() {
		if (!isMovingRight() && !isMovingLeft()) {
			speedX = 0;
		}

		else if (!isMovingRight() && isMovingLeft()) {
			moveLeft();
		}

		else if (isMovingRight() && !isMovingLeft()) {
			moveRight();
		}

	}

	public void jump() {
		if (!jumped) {
			speedY = JUMPSPEED;
			jumped = true;
		}
	}

	public void shoot() {
		Projectile p = new Projectile(centerX + 50, centerY - 25);
		projectiles.add(p);
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

	public boolean isDucked() {
		return ducked;
	}

	public void setDucked(boolean ducked) {
		this.ducked = ducked;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}
		
	
	
	
}
