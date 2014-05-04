package kiloboltgame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Robot {
	
	public static final int JUMPSPEED = -15;
	
	public static final int MOVESPEED = 5;	

	private static Background bg1 = Starter.getBg1();

	private static Background bg2 = Starter.getBg2();
	
	// Collision detection (top and bottom of the body)
	private static Rectangle rtop = new Rectangle();
	private static Rectangle rbot = new Rectangle();
	
	// Collision detection (left and right hand)
	private static Rectangle rlefth = new Rectangle();
	private static Rectangle rrighth = new Rectangle();

	// Collision detection (left and right foot)
	private static Rectangle rleftf = new Rectangle();
	private static Rectangle rrightf = new Rectangle();
	
	// Collision detection (the area of the near vicinity of the robot - an increased risk for collisions)
	private static Rectangle collisionArea = new Rectangle();

	
	private int centerX = 100;
	
	private int centerY = 377;
	
	private boolean jumped = false;
	
	private boolean movingLeft = false;
	
	private boolean movingRight = false;
	
	private boolean ducked = false;
	
	private boolean readyToFire = true;

	private int speedX = 0;
	
	private int speedY = 0;

	private List<Projectile> projectiles = new ArrayList<>();


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
				final int speed = -MOVESPEED / Starter.PACE;
				bg1.setSpeed(speed);
				bg2.setSpeed(speed);				
			}
		}
		 				
		// Updates Y Position
		centerY += speedY;
		
		// Handles Jumping
		speedY += 1;
		
		if (speedY > 3) {
			setJumped(true);
		}

		// Prevents going beyond X coordinate of 0
		if (centerX + speedX <= 60) {
			centerX = 61;
		}
		
		// Collision detection (body)
		rtop.setRect(centerX - 34, centerY - 63, 68, 63);
		rbot.setRect(rtop.getX(), rtop.getY() + 63, 68, 64);
		
		// Collision detection (hands)
		rlefth.setRect(rtop.getX() - 26, rtop.getY()+32, 26, 20);
		rrighth.setRect(rtop.getX() + 68, rtop.getY()+32, 26, 20);

		// Collision detection (feet)
		rleftf.setRect(centerX - 50, centerY + 20, 50, 15);
		rrightf.setRect(centerX, centerY + 20, 50, 15);		
		
		// Collision detection (the critical area)
		collisionArea.setRect(centerX - 110, centerY - 110, 180, 180);
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
		if (!isJumped()) {
			speedY = JUMPSPEED;
			setJumped(true);
		}
	}

	public void shoot() {
		if (readyToFire) {
			Projectile p = new Projectile(centerX + 50, centerY - 25);
			projectiles.add(p);			
		}
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

	public boolean isReadyToFire() {
		return readyToFire;
	}

	public void setReadyToFire(boolean readyToFire) {
		this.readyToFire = readyToFire;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public static Rectangle getRtop() {
		return rtop;
	}

	public static Rectangle getRbot() {
		return rbot;
	}

	public static Rectangle getRlefth() {
		return rlefth;
	}

	public static Rectangle getRrighth() {
		return rrighth;
	}
	
	public static Rectangle getRleftf() {
		return rleftf;
	}

	public static Rectangle getRrightf() {
		return rrightf;
	}

	public static Rectangle getCollisionArea() {
		return collisionArea;
	}

	// For debug purposes - collision detection
	public static void paintRectangles(Graphics g) {
		paintRectangle(g, rtop);
		paintRectangle(g, rbot);
		paintRectangle(g, rlefth);
		paintRectangle(g, rrighth);
		paintRectangle(g, collisionArea);
	}
	
	private static void paintRectangle(Graphics g, Rectangle r) {
		g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
	}
	
	
}
