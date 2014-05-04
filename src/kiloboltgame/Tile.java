package kiloboltgame;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile {

	public static final int OFFSET_PX = 40;
	
	private int x, y, speed;
	
	private TileType type;
	
	private Background bg = Starter.getBg1();
	
	private Robot robot = Starter.getRobot();
	
	// Collision detection
	private Rectangle r;

	public Tile(int x, int y, TileType type) {
		this.x = OFFSET_PX * x;
		this.y = OFFSET_PX * y;
		this.type = type;
		r = new Rectangle();
	}
	
	public void update() {
		speed = bg.getSpeed() * Starter.PACE;
		x += speed;
		r.setBounds(x, y, 40, 40);
		
		if (r.intersects(Robot.getCollisionArea()) && type != null) {
			checkVerticalCollision(Robot.getRtop(), Robot.getRbot());
			checkSideCollision(Robot.getRlefth(), Robot.getRrighth(), Robot.getRleftf(), Robot.getRrightf());
		}
		
	}

	public void checkVerticalCollision(Rectangle rtop, Rectangle rbot) {
	
		if (rtop.intersects(r)) {
			// TODO
		}
		
		if (rbot.intersects(r) && type == TileType.GRASS_TOP) {
			robot.setJumped(false);
			robot.setSpeedY(0);
			robot.setCenterY(y - 63);
		}
	}
	
	private void checkSideCollision(Rectangle rlefth, Rectangle rrighth,
			Rectangle rleftf, Rectangle rrightf) {
		if (type != null && !(type == TileType.DIRT || type == TileType.GRASS_BOTTOM)) {
			int shift = 0;
			
			if (rlefth.intersects(r)) {
				shift = 102;
			}
			else if (rleftf.intersects(r)) {
				shift = 85;				
			}
			else if (rrighth.intersects(r)) {
				shift = -62;				
			}
			else if (rrightf.intersects(r)) {
				shift = -45;				
			}
			
			if (shift != 0) {
				robot.setCenterX(x + shift);
				robot.setSpeedX(0);
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public TileType getType() {
		return type;
	}

	public Image getImage() {
		return Starter.getImage(type);
	}

	public Background getBg() {
		return bg;
	}

	public void setBg(Background bg) {
		this.bg = bg;
	}	
		
}
