package GameObjects;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Data.Waypoint;
import Scenes.Level;

public abstract class GameObject extends Thread {

	protected ArrayList<BufferedImage> graphic = new ArrayList<BufferedImage>(); // Liste
																					// der
																					// Bilddateien
																					// eines
																					// Objects
	public Rectangle hitbox = new Rectangle(); // Hitbox des Objects
	protected Level level;
	protected Waypoint currentWP;

	public GameObject(Rectangle pHitbox, Level level) {
		this.hitbox = pHitbox;
		this.level = level;
	}

	public GameObject() {

	}

	public final void setTheLevel(Level pLevel) {
		this.level = pLevel;
	}

	public final void setHitbox(Rectangle r) {
		hitbox = r;
	}

	public final Rectangle getHitbox() {
		return hitbox;
	}

	public final void setHitboxSize(Dimension d) {
		hitbox.setSize(d);
	}

	public final Dimension getHitboxSize() {
		return hitbox.getSize();
	}
	
	public final void setCoord(Point p) {
		hitbox.setLocation(p);
	}

	public final void moveObject(Point destination) {
		hitbox.setLocation(destination);
	}

	public final void moveObject(int x, int y) {
		hitbox.setLocation(x, y);
	}

	public final void moveObjectLeft(int distance) {
		hitbox.setLocation(hitbox.x - distance, hitbox.y);
	}

	public final void moveObjectRight(int distance) {
		hitbox.setLocation(hitbox.x + distance, hitbox.y);
	}

	public final void moveObjectUp(int distance) {
		hitbox.setLocation(hitbox.x, hitbox.y - distance);
	}

	public final void moveObjectDown(int distance) {
		hitbox.setLocation(hitbox.x, hitbox.y + distance);
	}

	public final Point getCoord() {
		return hitbox.getLocation();
	}

	public ArrayList<BufferedImage> getGraphic() {
		return graphic;
	}

	public final void setGraphic(ArrayList<BufferedImage> g) {
		graphic = g;
	}

	public boolean feetToObstacleCollision(GameObject obj) {
		return !((((this.getHitbox().x + this.getHitbox().width) < obj.getHitbox().x) || ((obj.getHitbox().x + obj.getHitbox().width) < this.getHitbox().x)) || (((this.getHitbox().y + this.getHitbox().height) < obj.getHitbox().y || (obj.getHitbox().y + obj.getHitbox().height) < this.getHitbox().y)));
	}

	protected final void findLastWaypoint() {
		for (int i = 0; i < level.waypoints.size(); i++) {
			if (hitbox.contains(level.waypoints.get(i).p)) {
				currentWP = level.waypoints.get(i);
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
