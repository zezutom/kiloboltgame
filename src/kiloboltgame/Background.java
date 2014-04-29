package kiloboltgame;

public class Background {

	public static final int WIDTH = 2160;
	
	public static final int HEIGHT = 480;
	
	private int x;
	
	private int y;
	
	private int speed;

	public Background(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.speed = 0;
	}
	
	public void update() {
		x += speed;
		
		if (x <= -WIDTH) {
			x += 2 * WIDTH;
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
	
}
