package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class Starter extends Applet implements Runnable, KeyListener {

	private static final long serialVersionUID = -7011665112885159197L;

	private static Background bg1, bg2;
	
	private Robot robot;
	private Image image, currentSprite, character, characterDown, characterJumped, background;
	private URL base;
	private Graphics second;
	
	@Override
	public void init() {
		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot Alpha");
		base = getDocumentBase();
		character = getImage(base, "data/character.png");
		characterDown = getImage(base, "data/down.png");
		characterJumped = getImage(base, "data/jumped.png");
		background = getImage(base, "data/background.png");
		currentSprite = character;
	}

	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(Background.WIDTH, 0);
		robot = new Robot();
		Thread thread = new Thread(this);
		thread.start();		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void run() {
		while (true) {
			robot.update();
			if (robot.isJumped()) {
				currentSprite = characterJumped;
			}
			// sprite update to 'ducked' is handled in onKeyDown
			else if (!robot.isDucked()) {
				currentSprite = character;
			}
			bg1.update();
			bg2.update();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}
		
		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);
		
		g.drawImage(image, 0, 0, this);
	}
	
	@Override
	public void paint(Graphics g) {		
		g.drawImage(background, bg1.getX(), bg1.getY(), this);
		g.drawImage(background, bg2.getX(), bg2.getY(), this);
		g.drawImage(currentSprite, robot.getCenterX() - 61, robot.getCenterY() - 63, this);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				System.out.println("Move up");
				break;
			case KeyEvent.VK_DOWN:
				currentSprite = characterDown;
				if (!robot.isJumped()) {
					robot.setDucked(true);
					robot.setSpeedX(0);
				}
				break;
			case KeyEvent.VK_LEFT:
				robot.moveLeft();
				robot.setMovingLeft(true);
				break;
			case KeyEvent.VK_RIGHT:
				robot.moveRight();
				robot.setMovingRight(true);
				break;
			case KeyEvent.VK_SPACE:
				robot.jump();
				break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				System.out.println("Stop moving up");
				break;
			case KeyEvent.VK_DOWN:
				currentSprite = character;
				robot.setDucked(false);
				break;
			case KeyEvent.VK_LEFT:
				robot.stopLeft();
				break;
			case KeyEvent.VK_RIGHT:
				robot.stopRight();
				break;
			case KeyEvent.VK_SPACE:
				System.out.println("Stop jumping");
				break;
		}
	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}
		
}
