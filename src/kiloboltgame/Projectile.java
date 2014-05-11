package kiloboltgame;

import java.awt.Rectangle;


public class Projectile {

	private int x, y, speedX;
	
	private boolean visible;

	// Collision detection
	private Rectangle r;
	
	public Projectile(int x, int y) {
		super();
		this.x = x;
		this.y = y;		
		speedX = 7;
		visible = true;
		r = new Rectangle();
	}
	
	public void update() {
		x += speedX;
		r.setBounds(x, y, 10, 5);
		if (x > 800) {
		   visible = false;
		   r = null;
		}		
		else {
			checkCollision();
		}
	}

	private void checkCollision() {
		checkHit(Starter.getHb(), Starter.getHb2());
	}
	
	private void checkHit(Enemy... enemies) {
		for (Enemy enemy : enemies) {
			if (r.intersects(enemy.getCollisionArea())) {
				setVisible(false);
				enemy.suffer();
			}			
		}		
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

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
}
