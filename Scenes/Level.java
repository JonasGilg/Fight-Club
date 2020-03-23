package Scenes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import main.CreateScene;
import main.Main;
import main.SceneChanger;
import Data.HudItem;
import Data.Waypoint;
import FileManagement.ImageManagement;
import GameObjects.Area;
import GameObjects.Charakter;
import GameObjects.Enemy;
import GameObjects.Item;
import GameObjects.Obstacle;
import GameObjects.PhysicsObject;
import GameObjects.Platform;
import GameObjects.Player;
import GameObjects.Projectile;

@SuppressWarnings("serial")
public final class Level extends Scene {
	public int gravityNumber = 5;
	public int spawnFromBorder = 150;
	public final ArrayList<TextItem> playerTags;
	public final ArrayList<Obstacle> obstacles;
	public final ArrayList<Player> players;
	public final ArrayList<Enemy> enemies;
	public final ArrayList<Projectile> projectiles;
	public final ArrayList<Item> items;
	public final ArrayList<Charakter> charakters;
	public final ArrayList<Area> areas;
	public final ArrayList<PhysicsObject> physicsObjs;
	public final ArrayList<Waypoint> waypoints;
	public final ArrayList<Platform> platforms;
	public final ArrayList<HudItem> hudItems;
	public final ArrayList<Integer> itemSpawnTimes;
	public final SceneChanger winScene;
	public final SceneChanger loseScene;
	private boolean multiplayer = false;

	private final int deathZone = 150;
	public boolean levelAlive = true;
	private int minutes;
	private int seconds;
	private int maxSeconds = 90;
	private boolean abort = false;
	private boolean endgegnerLevel = false;

	public Level(String soundFile, String imageFile, SceneChanger winScene, SceneChanger loseScene) {
		super(soundFile, imageFile, "Other/BLOODY.ttf");
		this.winScene = winScene;
		this.loseScene = loseScene;
		platforms = new ArrayList<Platform>();
		obstacles = new ArrayList<Obstacle>();
		players = new ArrayList<Player>();
		enemies = new ArrayList<Enemy>();
		projectiles = new ArrayList<Projectile>();

		items = new ArrayList<Item>();
		charakters = new ArrayList<Charakter>();
		areas = new ArrayList<Area>();
		playerTags = new ArrayList<TextItem>();
		physicsObjs = new ArrayList<PhysicsObject>();
		waypoints = new ArrayList<Waypoint>();
		hudItems = new ArrayList<HudItem>();
		itemSpawnTimes = new ArrayList<Integer>();
		this.setBounds(Main.MAIN.getBounds());

		KeyListener klLevel = new KeyAdapter() {
			@Override
			public final void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 0x1B) {
					abort = true;
					levelAlive = false;
					Main.MAIN.removeAllListeners();
					Main.MAIN.changeScene(CreateScene.createMenu());
				}
			}

		};
		Main.MAIN.addKeyListener(klLevel);
	}

	public void setLevelTime(int sec) {
		this.maxSeconds = sec;
	}

	public void setType(String s) {
		if (s.equals("Endgegner")) {
			endgegnerLevel = true;
		}
	}

	public final void buildLevel() {

		charakters.addAll(players);
		charakters.addAll(enemies);
		areas.addAll(platforms);
		areas.addAll(obstacles);
		physicsObjs.addAll(players);
		physicsObjs.addAll(enemies);
		physicsObjs.addAll(projectiles);
		if (enemies.isEmpty()) {
			multiplayer = true;
			seconds = 0;
		} else {
			multiplayer = false;
			seconds = maxSeconds;
		}
		if (endgegnerLevel) {
			seconds = 0;
		}
		levelAlive = true;

		TimerThread timerThread = new TimerThread();

		int i = 0;
		for (Charakter c : charakters) {
			if (c instanceof Player) {
				c.setPlayerColor(Settings.playerColor[i]);
				playerTags.add(new TextItem(c.getCharakterName(), c.getCoord(), c));
				if (Settings.playerModebit == 1 || Settings.playerModebit == 0) {
					hudItems.add(new HudItem(new Point(0 + (c.getPlayerNumber() * Main.WIDTH / 2), 10), c));
				}
				if (Settings.playerModebit == 2) {
					hudItems.add(new HudItem(new Point(0 + (c.getPlayerNumber() * Main.WIDTH / 3), 10), c));
				}
				if (Settings.playerModebit == 3) {
					hudItems.add(new HudItem(new Point(0 + (c.getPlayerNumber() * Main.WIDTH / 4), 10), c));
				}

			} else if (c instanceof Enemy) {
				c.setPlayerNumber(i);
				c.setPlayerColor(Color.black);
				playerTags.add(new TextItem(c.getCharakterName(), c.getCoord(), c));
				if (Settings.playerModebit == 0) {
					hudItems.add(new HudItem(new Point(0 + (c.getPlayerNumber() * Main.WIDTH / 2), 10), c));
				}

			}
			i++;
			c.start();

		}

		for (Item item : items) {
			item.setTheLevel(this);
		}

		// this.add(hud);
		timerThread.start();
	}

	public final void startProjectile(Projectile prj) {

		projectiles.add(prj);
		int tmp = projectiles.size() - 1;
		projectiles.get(tmp).start();

	}

	public final void removeProjectile(Projectile prj) {
		projectiles.remove(prj);
	}

	public final void updateCharakters() {
		for (Charakter c : charakters) {
			c.setTheLevel(this);
		}
	}

	public final synchronized void deleteCharakter(Charakter c) {
		if (c instanceof Player) {
			players.remove(c);
			if (!timerRunOut) {
				if (players.isEmpty() && enemies.size() == 1) {

					levelAlive = false;
					if (!abort) {
						levelAuswerten(false);
						Main.MAIN.changeScene(loseScene.nextScene());
					}
				}
				if (players.size() == 1) {
					levelAlive = false;
					if (!abort) {
						Main.MAIN.changeScene(CreateScene.createMenu());
					}
				}

			}
		}
		if (c instanceof Enemy) {
			if (!timerRunOut) {
				enemies.remove(c);
				if (enemies.isEmpty() && players.size() == 1) {

					levelAlive = false;
					if (!abort) {
						levelAuswerten(true);
						Main.MAIN.changeScene(winScene.nextScene());
					}

				}
			}
		}
		// System.out.println("delete "+c.getCharakterName()+" pl#: "+c.getPlayerNumber()+" idx: "+charakters.indexOf(c));
		playerTags.remove(c.getPlayerNumber());
		charakters.clear();
		if (players.size() > 0)
			charakters.addAll(players);
		if (enemies.size() > 0)
			charakters.addAll(enemies);
		updateCharakters();
	}

	private int timeFactor = 100;
	private boolean timerRunOut = false;

	public void levelAuswerten(boolean win) {
		int tmpScore = 0;
		tmpScore = (maxSeconds - seconds) * timeFactor;
		if (win) {
			tmpScore += players.get(0).getHP() * 10;
		} else {
			tmpScore += enemies.get(0).getMaxHP() - enemies.get(0).getHP();
		}
		tmpScore *= (Settings.difbyte + 1);

		Data.Scoredata.score += tmpScore;
	}

	public final void update() {

		int i = 0;
		for (Charakter c : charakters) {
			setPlayerTags(c, i);
			if (c.getHitbox().y > deathZone + Main.HEIGHT) {
				c.heal(-100);
				if (c instanceof Enemy) {
					c.moveObject(1920 - spawnFromBorder, 100);
				} else {
					c.moveObject(spawnFromBorder + (i * 200), 100);
				}
			}
			i++;
		}
		if (seconds == 0 && !(multiplayer || endgegnerLevel)) {
			levelAlive = false;
			timerRunOut = true;
			Main.MAIN.changeScene(loseScene.nextScene());
		}

	}

	// Nach Timer von au�erhalb ausgef�hrt!
	public final void spawnItem() {

		int i = 1;
		while (items.size() > 1) {
			if (!items.get(i).isAlive()) {
				items.get(i).start();
				break;
			}
			i++;
		}

	}

	public final void setPlayerTags(Charakter c, int i) {
		Point coord = c.getCoord();
		if (coord.x > 0 && coord.y > 0 && coord.x < Main.NATIVE_RESOLUTION.getWidth() && coord.y < Main.NATIVE_RESOLUTION.getHeight())
			playerTags.get(i).setLocation(coord);
		else {
			if (coord.x < 0)
				playerTags.get(i).setLocation(10, coord.y);
			if (coord.x > Main.NATIVE_RESOLUTION.getWidth())
				playerTags.get(i).setLocation((int) Main.NATIVE_RESOLUTION.getWidth() - (playerTags.get(i).getLaenge() * 8), coord.y);
			if (coord.y < 0)
				playerTags.get(i).setLocation(coord.x, 50);
			if (coord.y > Main.NATIVE_RESOLUTION.getHeight())
				playerTags.get(i).setLocation(coord.x, (int) Main.NATIVE_RESOLUTION.getHeight() - 10);
		}
	}

	@Override
	public final void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		update();
		drawGameObjects(g2d);
		g2d.dispose();
	}

	private final synchronized void drawGameObjects(Graphics2D g2d) { // zeichnet
																		// alle
		// Objekte
		g2d.setColor(Platform.drawColor);
		for (int i = 0; i < platforms.size(); i++) {
			g2d.draw(new Rectangle((int) (platforms.get(i).getHitbox().x * Main.rescaleConstant), (int) (platforms.get(i).getHitbox().y * Main.rescaleConstant), (int) (platforms.get(i).getHitbox().width * Main.rescaleConstant), (int) (platforms.get(i).getHitbox().height * Main.rescaleConstant)));
		}
		for (int i = 0; i < obstacles.size(); i++) {
			g2d.drawImage(ImageManagement.rescaleImage(obstacles.get(i).getGraphic().get(0), new Dimension((int) (obstacles.get(i).getHitbox().getWidth() * Main.rescaleConstant), (int) (obstacles.get(i).getHitbox().getHeight() * Main.rescaleConstant))), (int) (obstacles.get(i).getHitbox().getX() * Main.rescaleConstant), (int) (obstacles.get(i).getHitbox().getY() * Main.rescaleConstant), null);
		}
		if (items.size() >= 1) {
			for (int i = 0; i < items.size(); i++) {
				g2d.drawImage(ImageManagement.rescaleImage(items.get(i).getGraphic().get(0), new Dimension((int) (items.get(i).getHitbox().getWidth() * Main.rescaleConstant), (int) (items.get(i).getHitbox().getHeight() * Main.rescaleConstant))), (int) (items.get(i).getHitbox().getX() * Main.rescaleConstant), (int) (items.get(i).getHitbox().getY() * Main.rescaleConstant), null);
			}
		}
//		for (int i = 0; i < waypoints.size(); i++) {
//			waypoints.get(i).draw(g2d);
//		}

		if (!charakters.isEmpty()) {
			if (playerTags.size() >= 1) {
				for (int i = 0; i < playerTags.size(); i++) {
					g2d.setFont(new Font("Arial", Font.PLAIN, (int) (20 * Main.rescaleConstant)));
					g2d.setColor(charakters.get(i).getPlayerColor());
					g2d.drawString(playerTags.get(i).getText(), (int) (playerTags.get(i).getLocation().x * Main.rescaleConstant), (int) (playerTags.get(i).getLocation().y * Main.rescaleConstant));

				}
			}
		}
		g2d.setColor(Color.RED);
		if (enemies.size() >= 1) {
			for (Enemy e : enemies) {
				e.draw(g2d);
			}
		}
		if (players.size() >= 1) {
			for (int i = 0; i < players.size(); i++) {
				players.get(i).draw(g2d);
			}
		}
		try {
			if (projectiles.size() >= 1) {

				for (Projectile prj : projectiles) {
					prj.draw(g2d);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < hudItems.size(); i++) {
			hudItems.get(i).draw(g2d);
		}
		g2d.setColor(new Color(170, 0, 0));
		g2d.drawString(String.format("%02d", minutes) + ":" + String.format("%02d", seconds % 60), Main.WIDTH / 2 - 50, (int) (40 * Main.rescaleConstant));

	}

	final class TextItem {
		Point coord;
		String text;
		int laenge;
		Charakter player;

		public TextItem(String t, Point p, Charakter player) {
			text = t;
			coord = new Point(p.x, p.y - 10);
			laenge = text.length();
			this.player = player;
		}

		public TextItem(String t, int x, int y, Charakter p) {
			text = t;
			coord = new Point(x, y - 10);
			player = p;
		}

		public final String getText() {
			return text;
		}

		public final int getLaenge() {
			return laenge;
		}

		public final Charakter getPlayer() {
			return player;
		}

		public final void setLocation(Point p) {
			coord = p;
		}

		public final void setLocation(int x, int y) {
			coord.setLocation(x, y);
		}

		public final Point getLocation() {
			return coord;
		}
	}

	final class TimerThread extends Thread {
		@Override
		public final void run() {
			while (levelAlive) {

				minutes = seconds / 60;

				if (itemSpawnTimes.size() > 0) {
					int i = itemSpawnTimes.get(0);
					if (seconds == i) {
						itemSpawnTimes.remove(0);
						spawnItem();
					}
				}
				freeze(1000);
				if (multiplayer || endgegnerLevel) {
					seconds++;
				} else {
					seconds--;
				}

			}

		}

		public final void freeze(int time) {
			try {
				sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
