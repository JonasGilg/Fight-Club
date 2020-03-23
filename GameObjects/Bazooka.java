package GameObjects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Scenes.Level;

public final class Bazooka extends Item {
	private static final int dmg = 20;
	private int ammo = 15;

	private Rocket rocket;

	public static final ArrayList<BufferedImage> images = new ArrayList<>();

	public Bazooka(Point pCoord, int activeTime, Level level) {
		super(new Rectangle(pCoord, hitBoxSize), images, activeTime, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public final void giveStats(Charakter c) {
		myCharakter = c;
		c.setShoot(true);
		c.setShootDmg(dmg);

	}

	public final void shoot(boolean movedLeft, Scenes.Level lvl, Charakter source) {
		if (ammo > 0) {
			if (movedLeft) {
				rocket = new Rocket(new Point(myCharakter.leftHitbox.x - 31, myCharakter.leftHitbox.y + (myCharakter.leftHitbox.height / 8)), movedLeft, lvl);

			} else {
				rocket = new Rocket(new Point(myCharakter.rightHitbox.x + 1 + myCharakter.rightHitbox.width, myCharakter.rightHitbox.y + (myCharakter.rightHitbox.height / 8)), movedLeft, lvl);
			}
			rocket.setSource(source);
			lvl.projectiles.add(rocket);
			//FileManagement.SoundManagement.playFromFile("Sounds/Actions/punch/shootBazooka.wav");
			lvl.projectiles.get(level.projectiles.indexOf(rocket)).start();
			ammo--;
		} else {
			removeStats();
			lvl.items.remove(this);
		}
	}

	@Override
	public final String getActiveTimeAsString() { // Output as String for HUDItem
		if (ammo > 0)
			return "" + ammo;
		return "";
	}

	@Override
	public final void removeStats() {
		myCharakter.removeItem(this);
		myCharakter.setShoot(false);

	}

	@Override
	public final synchronized void run() {

		while (nichtAufgehoben && level.levelAlive) {
			freeze(sleepThread);
			update();
		}

	}
}
