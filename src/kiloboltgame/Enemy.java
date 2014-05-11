package kiloboltgame;

import java.awt.Rectangle;

public class Enemy {
	
	public static final int MAX_HEALTH = 5;
	
	// Collision detection (bullets, robot)
	private Rectangle collisionArea = new Rectangle();
	
	private int health = MAX_HEALTH;
	
	private int speedX, centerX, centerY;
	
	private Background bg = Starter.getBg1();
	
	public void update() {
		centerX += speedX;
		speedX = bg.getSpeed() * Starter.PACE;
		collisionArea.setBounds(centerX - 25, centerY - 25, 50, 60);
		
		if (collisionArea.intersects(Robot.getCollisionArea())) {
			checkCollision();
		}
	}
	
	private void checkCollision() {
		if (collisionArea.intersects(Robot.getRtop()) ||
			collisionArea.intersects(Robot.getRbot()) ||
			collisionArea.intersects(Robot.getRlefth()) ||
			collisionArea.intersects(Robot.getRrighth())) {
			System.out.println("collision");
		}
		
	}

	public void suffer() {
		if (health > 0) {
			health--;
			Starter.incrementScore();
		} else {
			die();
		}
	}
	
	public void die() {
		setCenterX(-100);
		Starter.incrementScore(MAX_HEALTH);
	}
	
	public void attack() {}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

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

	public Background getBg() {
		return bg;
	}

	public void setBg(Background bg) {
		this.bg = bg;
	}
		
	public Rectangle getCollisionArea() {
		return collisionArea;
	}

}
