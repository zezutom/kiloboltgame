package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Iterator;

import kiloboltgame.framework.Animation;

public class Starter extends Applet implements Runnable, KeyListener {

	private static final long serialVersionUID = -7011665112885159197L;

	private static Background bg1, bg2;
	
	private Robot robot;
	private Heliboy hb, hb2;
	private Image 	image, 
					currentSprite, 
					character,  
					characterJumped, 
					character2,
					character3,
					background, 
					heliboy,
					heliboy2,
					heliboy3,
					heliboy4,
					heliboy5;	
	private Animation anim, hanim;
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
		characterJumped = getImage(base, "data/jumped.png");
		character2 = getImage(base, "data/character2.png");
		character3 = getImage(base, "data/character3.png");		
		
		heliboy = getImage(base, "data/heliboy.png");
		heliboy2 = getImage(base, "data/heliboy2.png");
		heliboy3 = getImage(base, "data/heliboy3.png");
		heliboy4 = getImage(base, "data/heliboy4.png");
		heliboy5 = getImage(base, "data/heliboy5.png");
		
		background = getImage(base, "data/background.png");
		
		anim = new Animation();
		anim.addFrame(character, 1250);
		anim.addFrame(character2,  50);
		anim.addFrame(character3,  50);
		anim.addFrame(character2,  50);
		
		hanim = new Animation();
		hanim.addFrame(heliboy,  100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);
		
		currentSprite = anim.getImage();
	}

	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(Background.WIDTH, 0);
		robot = new Robot();
		hb = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);
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
				currentSprite = anim.getImage();
			}
		
			Iterator<Projectile> iter = robot.getProjectiles().iterator();
			
			while (iter.hasNext()) {
				Projectile p = iter.next();
				if (p.isVisible()) {
					p.update();
				} 
				else {
					iter.remove();
				}
			}
			hb.update();
			hb2.update();
			bg1.update();
			bg2.update();
			animate();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void animate() {		
		anim.update(10);
		hanim.update(50);
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
		
		for (Projectile p : robot.getProjectiles()) {
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}
		
		g.drawImage(currentSprite, robot.getCenterX() - 61, robot.getCenterY() - 63, this);
		g.drawImage(hanim.getImage(), hb.getCenterX() - 48, hb.getCenterY() - 48, this);
		g.drawImage(hanim.getImage(), hb2.getCenterX() - 48, hb2.getCenterY() - 48, this);						
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
				currentSprite = anim.getImage();
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
			case KeyEvent.VK_CONTROL:
				if (!(robot.isDucked() || robot.isJumped())) {
					robot.shoot();
				}
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
