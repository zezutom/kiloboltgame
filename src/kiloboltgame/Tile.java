package kiloboltgame;

import java.awt.Image;

public class Tile {

	public static final int OFFSET_PX = 40;
	
	private int x, y, speed;
	
	private TileType type;
	
	private Background bg = Starter.getBg1();

	public Tile(int x, int y, TileType type) {
		this.x = OFFSET_PX * x;
		this.y = OFFSET_PX * y;
		this.type = type;
	}
	
	public void update() {
		switch(type) {
			case OCEAN:
				if (bg.getSpeed() == 0) {
					speed = -1;
				}
				else {
					speed = -2;
				}
				break;
			default:
				speed = bg.getSpeed() * Starter.PACE;
				break;
		}
		x += speed;
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
