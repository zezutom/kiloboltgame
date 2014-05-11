package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kiloboltgame.framework.Animation;

public class Starter extends Applet implements Runnable, KeyListener {

	private static final int VISIBILITY_EDGE = 500;

	private static final long serialVersionUID = -7011665112885159197L;
	
	public static final int PACE = 5;

	public static final int MIN_LINES = 12;
	
	private static Background bg1, bg2;
	
	private static Robot robot;
	
	private static Heliboy hb, hb2;
	
	private static int score = 0;
	
	private static Map<IEntity, Image> imageMap;	
		
	private Image image, currentSprite;
	
	private Animation anim, hanim;
	
	private URL base;
	
	private Graphics second;
	
	private List<Tile> tiles;
	
	private Font font = new Font(null, Font.BOLD, 30);
	
	private GameState gameState = GameState.RUNNING;
	
	@Override
	public void init() {
		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot Alpha");
		base = getDocumentBase();
		
		mapImage(CharacterType.CHARACTER, "character");
		mapImage(CharacterType.CHARACTER_DOWN, "down");
		mapImage(CharacterType.CHARACTER_JUMPED, "jumped");
		mapImage(CharacterType.CHARACTER2, "character2");
		mapImage(CharacterType.CHARACTER3, "character3");
		mapImage(CharacterType.HELIBOY, "heliboy");
		mapImage(CharacterType.HELIBOY2, "heliboy2");
		mapImage(CharacterType.HELIBOY3, "heliboy3");
		mapImage(CharacterType.HELIBOY4, "heliboy4");
		mapImage(CharacterType.HELIBOY5, "heliboy5");
		mapImage(CharacterType.BACKGROUND, "background");
		mapImage(TileType.DIRT, "tiledirt");
		mapImage(TileType.OCEAN, "tileocean");
		mapImage(TileType.GRASS_TOP, "tilegrasstop");
		mapImage(TileType.GRASS_BOTTOM, "tilegrassbot");
		mapImage(TileType.GRASS_RIGHT, "tilegrassright");
		mapImage(TileType.GRASS_LEFT, "tilegrassleft");
								
		anim = new Animation();
		anim.addFrame(getImage(CharacterType.CHARACTER), 1250);
		anim.addFrame(getImage(CharacterType.CHARACTER2),  50);
		anim.addFrame(getImage(CharacterType.CHARACTER3),  50);
		anim.addFrame(getImage(CharacterType.CHARACTER2),  50);
		
		hanim = new Animation();
		hanim.addFrame(getImage(CharacterType.HELIBOY),  100);
		hanim.addFrame(getImage(CharacterType.HELIBOY2), 100);
		hanim.addFrame(getImage(CharacterType.HELIBOY3), 100);
		hanim.addFrame(getImage(CharacterType.HELIBOY4), 100);
		hanim.addFrame(getImage(CharacterType.HELIBOY5), 100);
		hanim.addFrame(getImage(CharacterType.HELIBOY4), 100);
		hanim.addFrame(getImage(CharacterType.HELIBOY3), 100);
		hanim.addFrame(getImage(CharacterType.HELIBOY2), 100);
		
		currentSprite = anim.getImage();
	}

	private void mapImage(IEntity key, String imageName) {
		if (imageMap == null) {
			imageMap = new HashMap<>();
		}
		imageMap.put(key, getImage(base, "data/" + imageName + ".png"));
	}
	
	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(Background.WIDTH, 0);
		robot = new Robot();
		try {
			loadMap("map1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		hb = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);
		Thread thread = new Thread(this);
		thread.start();		
	}

	private void loadMap(String fileName) throws IOException {
		List<String> lines = new ArrayList<>();
		int width = 0, height = 0;
		
		BufferedReader reader = new BufferedReader(
				new FileReader("data/" + fileName + ".txt"));
		
		while (true) {
			String line = reader.readLine();
			
			// no more lines to read
			if (line == null) {
				reader.close();
				break;
			}
			
			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}
		}
		height = lines.size();
		
		if (height < MIN_LINES) {
			throw new IOException("There must be at least " + MIN_LINES + " lines!");
		}
		
		initTiles(width, lines);
		
		
	}

	private void initTiles(int width, List<String> lines) {
		tiles = new ArrayList<>();
		for (int i = 0; i < MIN_LINES; i++) {
			final String line = lines.get(i);
			
			for (int j = 0; j < width; j++) {
				if (j < line.length()) {
					char c = line.charAt(j);
					final TileType t = TileType.get(Character.getNumericValue(c));
					tiles.add(new Tile(j, i, t));
				}
			}
		}
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
		
		if (gameState != GameState.RUNNING) {
			return;
		}
		
		while (true) {
			robot.update();
			if (robot.isJumped()) {
				currentSprite = getImage(CharacterType.CHARACTER_JUMPED);
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
			updateTiles();
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
			if (robot.getCenterY() > VISIBILITY_EDGE) {
				gameState = GameState.DEAD;
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
		
		if (gameState == GameState.DEAD) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.RED);
			g.drawString("Game Over!!", 360, 240);
			return;
		}
		
		final Image background = getImage(CharacterType.BACKGROUND);
		g.drawImage(background, bg1.getX(), bg1.getY(), this);
		g.drawImage(background, bg2.getX(), bg2.getY(), this);
		paintTiles(g);
		
		for (Projectile p : robot.getProjectiles()) {
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}
		
		g.drawImage(currentSprite, robot.getCenterX() - 61, robot.getCenterY() - 63, this);
		// DEBUG:
		// Robot.paintRectangles(g);
		g.drawImage(hanim.getImage(), hb.getCenterX() - 48, hb.getCenterY() - 48, this);
		g.drawImage(hanim.getImage(), hb2.getCenterX() - 48, hb2.getCenterY() - 48, this);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(Integer.toString(score), 740, 30);
	}
	
	private void updateTiles() {
		for (Tile t : tiles) {
			t.update();
		}
	}
	
	private void paintTiles(Graphics g) {
		for (Tile t : tiles) {
			g.drawImage(t.getImage(), t.getX(), t.getY(), this);
		}
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
				currentSprite = getImage(CharacterType.CHARACTER_DOWN);
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
				if (!(robot.isDucked() || robot.isJumped()) && robot.isReadyToFire()) {
					robot.shoot();
					robot.setReadyToFire(false);
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
				currentSprite = getImage(CharacterType.CHARACTER);
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
			case KeyEvent.VK_CONTROL:
				robot.setReadyToFire(true);
				break;
		}
	}

	public static Image getImage(IEntity key) {
		return imageMap.get(key);
	}
	
	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

	public static Robot getRobot() {
		return robot;
	}

	public static Heliboy getHb() {
		return hb;
	}

	public static Heliboy getHb2() {
		return hb2;
	}
	
	public static void incrementScore() {
		score++;
	}

	public static void incrementScore(int bonus) {
		score += bonus;
	}
		
}
