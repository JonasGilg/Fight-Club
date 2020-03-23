package GameObjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import FileManagement.SoundManagement;
import Scenes.Level;

public class Projectile extends PhysicsObject {

	protected ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	protected int speed;
	private int sleepThread = 3; // ms für sleep();
	protected boolean isProjectile = true;
	protected boolean hit = false;
	protected int projectileDmg;
	private Charakter hitObject = null;
	protected BufferedImage currentImage;
	protected Scenes.Level level;
	protected boolean outOfBounds = false;
	protected Charakter myCharakter;
	protected static final SoundManagement explosion = new SoundManagement("/Sounds/Actions/punch/explosion.wav");

	public Projectile(int speed, int dmg, boolean movesLeft, Rectangle pHitbox, Level level) {
		super(pHitbox, level);
		this.speed = speed;
		this.projectileDmg = dmg;
		this.movedLeft = movesLeft;

	}

	public final void setSource(Charakter source) {
		myCharakter = source;
	}

	public final ArrayList<BufferedImage> getGraphic() {
		return images;
	}

	public final void fly() {
		if (movedLeft) {
			moveObjectLeft(speed);
			currentImage = images.get(0);
		}
		if (!movedLeft) {
			moveObjectRight(speed);
			currentImage = images.get(1);

		}
	}

	public final boolean isHit() {
		for (Obstacle o : level.obstacles) {
			if (this.hitbox.intersects(o.hitbox)) {
				currentImage = images.get(2);
				
				return true;
			}
		}
		for (Charakter c : level.charakters) {
			if ((this.hitbox.intersects(c.bodyHitbox))
					&& !c.equals(myCharakter)) {
				currentImage = images.get(2);
				hitObject = c;
				return true;
			}
		}
		if (this.hitbox.x < -200 || this.hitbox.x > 1920 + 100) {
			outOfBounds = true;
			return true;
		}
		return false;
	}

	@Override
	public synchronized void run() {
		currentImage = images.get(0);
		setName("Projectile Thread");
		while (!isHit() && level.levelAlive) {
			fly();
			freeze(sleepThread);
		}
		explosion.play();
		if (hitObject != null) {

			hitObject.takeDmg(projectileDmg, this);

		}

		freeze(200);
		level.removeProjectile(this);
	}

	public void draw(Graphics2D g2d) {

	}

}
